package no.newsagg.system.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.newsagg.system.domain.Article;
import no.newsagg.system.domain.ArticleStatus;
import no.newsagg.system.messaging.ArticleProcessor;
import no.newsagg.system.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleRetryService {
  private final ArticleRepository articleRepository;
  private final ArticleProcessor articleProcessor;

  private static final Duration RETRY_DELAY = Duration.ofMinutes(5);

  @Value("${app.processing.retry-attempts:3}")
  private static final int MAX_RETRIES = 3;

  @Scheduled(fixedDelayString = "${app.retry-interval:300000}") // Default 5 minutes
  @Transactional
  public void retryFailedArticles() {
    log.debug("Starting retry process for failed articles");

    // Find articles that are either NEW or FAILED
    List<Article> unprocessedArticles = articleRepository.findByStatusInAndRetryCountLessThan(
        List.of(ArticleStatus.NEW, ArticleStatus.FAILED),
        MAX_RETRIES
    );

    for (Article article : unprocessedArticles) {
      processArticleRetry(article);
    }
    if (unprocessedArticles.isEmpty()) {
      log.debug("No articles to retry");
    }
  }

  private void processArticleRetry(Article article) {
    try {
      // Skip if the article is too new (wait for RETRY_DELAY before retrying)
      if (article.getProcessedAt() != null &&
          Duration.between(article.getProcessedAt(), Instant.now()).compareTo(RETRY_DELAY) < 0) {
        return;
      }

      // Skip if max retries reached
      if (article.getRetryCount() != null && article.getRetryCount() >= MAX_RETRIES) {
        // log.warn("Article {} has reached max retries ({}), skipping", article.getId(), MAX_RETRIES);
        return;
      }

      // Increment retry count
      article.setRetryCount(article.getRetryCount() == null ? 1 : article.getRetryCount() + 1);
      article = articleRepository.save(article);

      log.info("Retrying article {} (attempt {})", article.getId(), article.getRetryCount());
      articleProcessor.queueForProcessing(article);

    } catch (Exception e) {
      log.error("Error processing retry for article {}: {}", article.getId(), e.getMessage());
    }
  }
}