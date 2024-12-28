package no.newsagg.system.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.newsagg.system.domain.Article;
import no.newsagg.system.domain.ArticleStatus;
import no.newsagg.system.messaging.ArticleProcessor;
import no.newsagg.system.outlet.common.RawArticle;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import no.newsagg.system.repository.ArticleRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleFetcherService {
  private final List<RssNewsOutlet> newsOutlets;
  private final ArticleRepository articleRepository;
  private final ArticleProcessor articleProcessor;

  @Scheduled(fixedDelayString = "${app.fetch-interval:300000}")  // Default 5 minutes
  @Transactional
  public void fetchArticles() {
    newsOutlets.forEach(outlet -> {
      try {
        List<RawArticle> rawArticles = outlet.fetchArticles();
        log.info("Found {} articles from {}", rawArticles.size(), outlet.getName());

        for (RawArticle rawArticle : rawArticles) {
          processRawArticle(rawArticle);
        }
      } catch (Exception e) {
        log.error("Error processing outlet {}: {}", outlet.getName(), e.getMessage(), e);
      }
    });
  }

  private void processRawArticle(RawArticle rawArticle) {
    try {
      if (articleRepository.existsByUrl(rawArticle.getUrl())) {
        return;
      }
      Article article = Article.builder()
          .url(rawArticle.getUrl())
          .source(rawArticle.getSource())
          .originalTitle(rawArticle.getTitle())
          .description(rawArticle.getDescription())
          .imageUrl(rawArticle.getImageUrl())
          .articleId(rawArticle.getArticleId())
          .publishedAt(rawArticle.getPublishedAt())
          .status(ArticleStatus.NEW)
          .retryCount(0)
          .build();

      article = articleRepository.save(article);
      Thread.sleep(10); // Wait for the article to be persisted before processing, java too fast
      articleProcessor.queueForProcessing(article);
      log.debug("Queued new article: {}", article.getUrl());
    } catch (Exception e) {
      log.error("Error processing article {}: {}", rawArticle.getUrl(), e.getMessage());
    }
  }
}