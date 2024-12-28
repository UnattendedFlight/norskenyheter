package no.newsagg.system.controller;

import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.newsagg.system.domain.Article;
import no.newsagg.system.domain.ArticleStatus;
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

  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping
  public Page<Article> getArticles(
      @RequestParam(required = false) String source,
      @RequestParam(required = false) ArticleStatus status,
      @RequestParam(required = false) Instant since,
      @RequestParam(required = false) String query,
      @PageableDefault(sort = "publishedAt", direction = Sort.Direction.DESC) Pageable pageable) {

    if (source != null && status != null && since != null) {
      return articleRepository.findBySourceAndStatusAndPublishedAtAfterSearch(source, status, since,
          pageable, query);
    } else if (source != null && status != null) {
      return articleRepository.findBySourceAndStatusSearch(source, status, pageable, query);
    } else if (source != null) {
      return articleRepository.findBySourceSearch(source, pageable, query);
    } else if (status != null) {
      return articleRepository.findByStatusSearch(status, pageable, query);
    }

    return articleRepository.findAll(pageable);
  }


  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/stats")
  public ArticleStatsDTO getArticleStats() {
    return articleService.getArticleStats();
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/{id}")
  public Article getArticle(@PathVariable Long id) {
    return articleRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @PutMapping("/{id}/refetch")
  public Article refetchArticle(@PathVariable Long id) {
    Optional<Article> opt = articleRepository.findById(id);
    if (opt.isEmpty()) {
      log.debug("Article not found: {}", id);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    Article article = opt.get();
    log.debug("Refetching article: {}", article.getUrl());
    article.setRetryCount(0);
    article.setStatus(ArticleStatus.NEW);
    articleProcessor.queueForProcessing(article);
    return articleRepository.save(article);
  }
}