package no.newsagg.system.service;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.newsagg.system.domain.Article;
import no.newsagg.system.domain.ArticleStatus;
import no.newsagg.system.dto.ArticleStatsDTO;
import no.newsagg.system.ollama.OllamaCategoryResponse;
import no.newsagg.system.ollama.OllamaConfig;
import no.newsagg.system.ollama.OllamaResponse;
import no.newsagg.system.repository.ArticleRepository;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.embedding.EmbeddingOptionsBuilder;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
  private final ArticleRepository articleRepository;
  private final OllamaConfig ollamaConfig;
  //private final OllamaChatModel ollamaClient = new OllamaConfig().chatClient();
  //private final OllamaEmbeddingModel embeddingsClient = new OllamaConfig().embeddingsClient();
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
      log.debug("Fetching content for article {}", articleId);
      ArticleContentFetcher.ArticleContent content = contentFetcher.fetchContent(article.getUrl());
      log.debug("Successfully fetched content for article {}", articleId);

      // Generate AI content
      log.debug("Generating AI content for article {}", articleId);
      OllamaResponse aiResponse = generateAiContent(article, content.getContent());
      String aiTitle = aiResponse.title();
      String summary = aiResponse.summary();
      log.debug("Successfully generated AI content for article {}", articleId);

      // Generate embeddings
      log.debug("Generating embeddings for article {}", articleId);
      EmbeddingResponse embeddingsResponse = generateEmbeddings(content.getContent());
      log.debug("Successfully generated embeddings for article {}", articleId);

      // Update article
      article.setAiGeneratedTitle(aiTitle);
      article.setSummary(summary);
      article.setPaywalled(aiResponse.isPaywalled());
      article.setStatus(ArticleStatus.COMPLETED);
      article.setProcessedAt(Instant.now());
      article.setContent(content.getContent());
      article.setEmbedding(embeddingsResponse.getResult().getOutput());

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

  public void processArticleCategory(Long articleId) {
    Optional<Article> articleOptional = articleRepository.findById(articleId);
    if (articleOptional.isEmpty()) {
      log.error("Article {} not found", articleId);
      throw new RuntimeException("Article not found: " + articleId);
    }

    Article article = articleOptional.get();
    log.info("Processing article category {}: {}", articleId, article.getSource());
    if (article.getStatus() != ArticleStatus.COMPLETED) {
      log.error("Article {} is not in COMPLETED state", articleId);
      return;
    }
    try {
      // Generate AI categories
      log.debug("Generating AI categories for article {}", articleId);
      OllamaCategoryResponse aiCategories =
          generateAICategories(article.getContent(), article.getSummary());
      List<String> categories = Arrays.stream(aiCategories.categories()).toList();
      log.debug("Successfully generated AI categories for article {}", articleId);

      // Update article
      article.setCategories(categories);
      articleRepository.save(article);
      log.info("Successfully processed article categories: {}", article.getId());

    } catch (Exception e) {
      log.error("Failed to process article categories {}", articleId);
      throw new RuntimeException("Failed to process article categories", e);
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

    OllamaChatModel ollamaClient = this.ollamaConfig.chatClient();

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

  private OllamaCategoryResponse generateAICategories(String articleContent,
                                                      String articleSummary) {
    String prompt = String.format("""
        VIKTIG: ALLE SVAR MÅ VÆRE PÅ NORSK (BOKMÅL)
        DU SKAL ALDRI SVARE PÅ ENGELSK
        
        Du er en erfaren norsk nyhetsklassifiserer-AI som analyserer og kategoriserer nyhetsinnhold.
        
        KATEGORIKRAV:
        - Returner en liste med minst én kategori fra følgende liste:
          Nyheter > Innenriks
          Nyheter > Utenriks
          Nyheter > Politikk
          Nyheter > Krim og Rettsvesen
          Økonomi og Næringsliv > Børs og Finans
          Økonomi og Næringsliv > Eiendom
          Økonomi og Næringsliv > Arbeidsliv
          Økonomi og Næringsliv > Forbruker
          Teknologi og Vitenskap > Digital og IT
          Teknologi og Vitenskap > Forskning
          Teknologi og Vitenskap > Miljø og Klima
          Teknologi og Vitenskap > Innovasjon
          Kultur og Underholdning > Sport
          Kultur og Underholdning > Film og TV
          Kultur og Underholdning > Musikk
          Kultur og Underholdning > Litteratur
          Kultur og Underholdning > Kunst
          Livsstil > Helse og Velvære
          Livsstil > Mat og Drikke
          Livsstil > Reise
          Livsstil > Familie og Oppvekst
          Meninger > Kommentar
          Meninger > Debatt
          Meninger > Leder
        
        - Kan tildele flere kategorier hvis innholdet er relevant for flere kategorier
        - Kategoriene må reflektere hovedtemaene i innholdet
        - Ved tvetydig innhold, velg den mest relevante kategorien
        - Hvis kilden er på engelsk, analyser det oversatte innholdet
        
        INNDATA:
        Artikkelsammendrag: %s
        Artikkelinnhold: %s
        
        FORMATKRAV:
        {format}
        """, articleSummary, articleContent);
    BeanOutputConverter<OllamaCategoryResponse> converter =
        new BeanOutputConverter<>(OllamaCategoryResponse.class);

    PromptTemplate promptTemplate =
        new PromptTemplate(prompt, Map.of("format", converter.getFormat()));
    Prompt aiPrompt = new Prompt(promptTemplate.createMessage());

    OllamaChatModel ollamaClient = this.ollamaConfig.chatClient();

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

  private EmbeddingResponse generateEmbeddings(String content) {
    try {
      OllamaEmbeddingModel embeddingsClient = this.ollamaConfig.embeddingsClient();
      return embeddingsClient.call(new EmbeddingRequest(List.of(content),
          EmbeddingOptionsBuilder.builder().withModel("nomic-embed-text").withDimensions(1536)
              .build()));
    } catch (Exception e) {
      log.error("Failed to parse embeddings response", e);
      throw new RuntimeException("Failed to parse embeddings response", e);
    }
  }

  private double[] convertEmbedding(Double[][] aiEmbeddings) {
    if (aiEmbeddings == null || aiEmbeddings.length == 0 || aiEmbeddings[0] == null) {
      return null;
    }
    Double[] embedding = aiEmbeddings[0];
    double[] result = new double[embedding.length];
    for (int i = 0; i < embedding.length; i++) {
      result[i] = embedding[i] != null ? embedding[i] : 0.0;
    }
    return result;
  }
}