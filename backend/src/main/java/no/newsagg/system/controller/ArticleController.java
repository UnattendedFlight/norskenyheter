package no.newsagg.system.controller;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.newsagg.system.domain.Article;
import no.newsagg.system.domain.ArticleStatus;
import no.newsagg.system.dto.ArticleResponseDTO;
import no.newsagg.system.dto.ArticleStatsDTO;
import no.newsagg.system.messaging.ArticleProcessor;
import no.newsagg.system.repository.ArticleRepository;
import no.newsagg.system.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {
  private final ArticleRepository articleRepository;
  private final ArticleProcessor articleProcessor;
  private final ArticleService articleService;

  @CrossOrigin(origins = {"http://localhost:3000", "http://auc.no", "https://auc.no",
      "http://api.auc.no", "https://api.auc.no"})
  @GetMapping
  public Page<ArticleResponseDTO> getArticles(
      @RequestParam(required = false) String source,
      @RequestParam(required = false) ArticleStatus status,
      @RequestParam(required = false) Instant since,
      @RequestParam(required = false) String query,
      @PageableDefault(sort = "publishedAt", direction = Sort.Direction.DESC) Pageable pageable) {

    Page<Article> articles;

    if (source != null && status != null && since != null) {
      log.debug("Fetching articles by source, status and since: {}, {}, {}", source, status, since);
      articles = query != null && !query.isEmpty()
          ? articleRepository.findBySourceAndStatusAndPublishedAtAfterSearch(source, status, since,
          pageable, query)
          : articleRepository.findBySourceAndStatusAndPublishedAtAfter(source, status, since,
          pageable);
    } else if (source != null && status != null) {
      log.debug("Fetching articles by source and status: {}, {}", source, status);
      articles = query != null && !query.isEmpty()
          ? articleRepository.findBySourceAndStatusSearch(source, status, pageable, query)
          : articleRepository.findBySourceAndStatus(source, status, pageable);
    } else if (source != null) {
      log.debug("Fetching articles by source: {}", source);
      articles = query != null && !query.isEmpty()
          ? articleRepository.findBySourceSearch(source, pageable, query)
          : articleRepository.findBySource(source, pageable);
    } else if (status != null) {
      log.debug("Fetching articles by status: {}", status);
      articles = query != null && !query.isEmpty()
          ? articleRepository.findByStatusSearch(status, pageable, query)
          : articleRepository.findByStatus(status, pageable);
    } else {
      log.debug("Fetching all articles");
      articles = articleRepository.findAll(pageable);
    }

    return articles.map(ArticleResponseDTO::fromArticle);
  }


  @CrossOrigin(origins = {"http://localhost:3000", "http://auc.no", "https://auc.no",
      "http://api.auc.no", "https://api.auc.no"})
  @GetMapping("/stats")
  public ArticleStatsDTO getArticleStats() {
    return articleService.getArticleStats();
  }

  @CrossOrigin(origins = {"http://localhost:3000", "http://auc.no", "https://auc.no",
      "http://api.auc.no", "https://api.auc.no"})
  @GetMapping("/{id}")
  public ArticleResponseDTO getArticle(@PathVariable Long id) {
    return ArticleResponseDTO.fromArticle(articleRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
  }

  @CrossOrigin(origins = {"http://localhost:3000", "http://auc.no", "https://auc.no",
      "http://api.auc.no", "https://api.auc.no"})
  @PutMapping("/{id}/refetch")
  public ArticleResponseDTO refetchArticle(@PathVariable Long id) throws InterruptedException {
    Optional<Article> opt = articleRepository.findById(id);
    if (opt.isEmpty()) {
      log.debug("Article not found: {}", id);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    Article article = opt.get();
    log.debug("Refetching article: {}", article.getUrl());
    article.setRetryCount(0);
    article.setStatus(ArticleStatus.NEW);
    articleRepository.save(article);
    Thread.sleep(20);
    articleProcessor.queueForProcessing(article);
    return ArticleResponseDTO.fromArticle(article);
  }

  @CrossOrigin(origins = {"http://localhost:3000", "http://auc.no", "https://auc.no",
      "http://api.auc.no", "https://api.auc.no"})
  @GetMapping("/{id}/similar")
  public List<ArticleResponseDTO> getSimilarArticles(@PathVariable Long id,
                                                     @PageableDefault(sort = "publishedAt", direction = Sort.Direction.DESC)
                                                     Pageable pageable) {
    Optional<Article> opt = articleRepository.findById(id);
    if (opt.isEmpty()) {
      log.debug("Article not found: {}", id);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    Article article = opt.get();
    log.debug("Fetching similar articles for: {}", article.getUrl());
    return articleRepository.findSimilarArticles(
            article.getId(),
            article.getEmbedding(),
            ArticleStatus.COMPLETED.toString(),
            pageable.getPageSize()
        ).stream()
        .map(ArticleResponseDTO::fromArticle).toList();
  }

  @CrossOrigin(origins = {"http://localhost:3000", "http://auc.no", "https://auc.no",
      "http://api.auc.no", "https://api.auc.no"})
  @GetMapping("/updateCategories")
  public String updateCategories() {
    List<Article> articles = articleRepository.findAllByStatus(ArticleStatus.COMPLETED);
    for (Article article : articles) {
      articleProcessor.queueForCategoryProcessing(article);
    }
    return "Queued " + articles.size() + " articles for category processing";
  }
}