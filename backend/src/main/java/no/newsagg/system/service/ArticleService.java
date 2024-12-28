package no.newsagg.system.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.newsagg.system.domain.Article;
import no.newsagg.system.domain.ArticleStatus;
import no.newsagg.system.dto.ArticleStatsDTO;
import no.newsagg.system.ollama.OllamaConfig;
import no.newsagg.system.ollama.OllamaResponse;
import no.newsagg.system.repository.ArticleRepository;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
  private final ArticleRepository articleRepository;
  private final OllamaChatModel ollamaClient = new OllamaConfig().chatClient();
  private final ArticleContentFetcher contentFetcher;

  public ArticleStatsDTO getArticleStats() {
    long totalArticles = articleRepository.countTotalArticles();
    List<Object[]> rawRows = articleRepository.countArticlesBySource();
    Map<String, Long> articlesPerSource = new HashMap<>();
    for (Object[] row : rawRows) {
      articlesPerSource.put((String) row[0], (Long) row[1]);
    }
    return new ArticleStatsDTO(totalArticles, articlesPerSource);
  }

  @Transactional
  public void processArticle(Long articleId) {
    Optional<Article> articleOptional = articleRepository.findById(articleId);
    if (articleOptional.isEmpty()) {
      log.error("Article {} not found", articleId);
      throw new RuntimeException("Article not found: " + articleId);
    }

    Article article = articleOptional.get();
    log.info("Processing article {}: {}", articleId, article.getSource());
    if (article.getStatus() != ArticleStatus.NEW && article.getStatus() != ArticleStatus.FAILED &&
        article.getStatus() != ArticleStatus.PROCESSING) {
      log.error("Article {} is not in NEW, FAILED or PROCESSING state", articleId);
      return;
    }
    try {
      // Update status to processing
      article.setStatus(ArticleStatus.PROCESSING);
      articleRepository.save(article);

      // Fetch article content
      String content = contentFetcher.fetchContent(article.getUrl());

      // Generate AI content
      OllamaResponse aiResponse = generateAiContent(article, content);
      String aiTitle = aiResponse.title();
      String summary = aiResponse.summary();

      // Update article
      article.setAiGeneratedTitle(aiTitle);
      article.setSummary(summary);
      article.setPaywalled(aiResponse.isPaywalled());
      article.setStatus(ArticleStatus.COMPLETED);
      article.setProcessedAt(Instant.now());

      articleRepository.save(article);
      log.info("Successfully processed article: {}", article.getId());

    } catch (Exception e) {
      // log.error("Failed to process article {}: {}", articleId, e.getMessage(), e);
      log.error("Failed to process article {}", articleId);
      article.setStatus(ArticleStatus.FAILED);
      article.setProcessedAt(Instant.now());
      articleRepository.save(article);
      throw new RuntimeException("Failed to process article", e);
    }
  }

  private OllamaResponse generateAiContent(Article article, String articleContent) {
    String prompt = String.format("""
        VIKTIG: ALLE SVAR MÅ VÆRE PÅ NORSK (BOKMÅL)
        DU SKAL ALDRI SVARE PÅ ENGELSK
        
        Du er en ekspert norsk nyhetsredaktør-AI som spesialiserer seg på konsis, faktabasert journalistikk.
        
        SPRÅKKRAV:
        - Skal KUN skrive på norsk bokmål
        - Må følge dialekten fra kildeinnholdet
        - Aldri bruk svensk eller dansk
        - Hvis kilden er på engelsk, oversett til norsk før bearbeiding
        
        OVERSKRIFTKRAV:
        - Maksimum 120 tegn
        - Skal være informativ og spesifikk
        - Unngå clickbait, overdrivelser og sensasjonalisme
        - Skal fungere som selvstendig beskrivelse
        - Må reflektere det mest aktuelle nyhetspunktet
        
        SAMMENDRAGSKRAV:
        - Nøyaktig 3 setninger
        - Første setning: Hovednyhet/hovedhendelse
        - Andre setning: Viktige detaljer eller kontekst
        - Tredje setning: Tilleggsinformasjon eller implikasjoner
        - Maksimum 400 tegn totalt
        - Skriv i aktiv form
        - Bruk presens for aktuelle hendelser
        - Ingen meta-referanser (f.eks. "denne artikkelen", "skribenten")
        - Ved utilstrekkelig innhold, returner tom streng
        
        BETALINGSMURKRAV:
        - Sett is_paywall til true ved følgende indikatorer:
          * Abonnementsmeldinger
          * Påloggingskrav
          * Premium/Pluss-markører
          * Avkortet innhold
          * "Abonnent"-referanser
        - Standard er false for korte artikler uten eksplisitte betalingsmurindikatorer
        
        INNDATA:
        Artikkelinnhold: %s
        
        FORMATKRAV:
        {format}
        """, articleContent);
    BeanOutputConverter<OllamaResponse> converter =
        new BeanOutputConverter<>(OllamaResponse.class);

    PromptTemplate promptTemplate =
        new PromptTemplate(prompt, Map.of("format", converter.getFormat()));
    Prompt aiPrompt = new Prompt(promptTemplate.createMessage());

    ChatResponse response = ollamaClient.call(aiPrompt);
    try {
      String content = response.getResult().getOutput().getContent().strip();
      if (content.startsWith("{") && !content.endsWith("}")) {
        content = content + "}";
      }

      return converter.convert(content);
    } catch (Exception e) {
      log.error("Failed to parse AI response: {}", response);
      throw new RuntimeException("Failed to parse AI response", e);
    }
  }
}