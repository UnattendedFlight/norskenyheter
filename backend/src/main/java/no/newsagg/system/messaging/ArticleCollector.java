package no.newsagg.system.messaging;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.newsagg.system.domain.Article;
import no.newsagg.system.domain.ArticleStatus;
import no.newsagg.system.outlet.common.RawArticle;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import no.newsagg.system.repository.ArticleRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleCollector {
  private final List<RssNewsOutlet> newsOutlets;
  private final ArticleRepository articleRepository;
  private final ArticleProcessor articleProcessor;

  @RabbitListener(queues = RabbitMQConfig.COLLECTOR_QUEUE_NAME)
  @Transactional
  public void collectArticles(Integer outletIndex) {
    try {
      RssNewsOutlet outlet = newsOutlets.get(outletIndex);
      List<Article> createdArticles = new ArrayList<>();
      try {
        List<RawArticle> rawArticles = outlet.fetchArticles();
        log.info("Found {} articles from {}", rawArticles.size(), outlet.getName());

        for (RawArticle rawArticle : rawArticles) {
          Article processedArticle = processRawArticle(rawArticle);
          if (processedArticle != null) {
            createdArticles.add(processedArticle);
          }
        }
      } catch (Exception e) {
        log.error("Error processing outlet {}: {}", outlet.getName(), e.getMessage(), e);
      }
      if (!createdArticles.isEmpty()) {
        try {
          log.info("Processing {} articles from {}", createdArticles.size(), outlet.getName());
          for (Article article : createdArticles) {
            articleProcessor.queueForProcessing(article);
            log.debug("Queued new article: {}", article.getUrl());
          }
        } catch (Exception e) {
          log.error("Error processing articles from {}: {}", outlet.getName(), e.getMessage(), e);
        }
      }
    } catch (Exception e) {
      log.error("Error processing source {}: {}", outletIndex, e.getMessage(), e);
      // log.error("Error processing article {}", articleId);
    }
  }

  private Article processRawArticle(RawArticle rawArticle) {
    try {
      if (articleRepository.existsByUrl(rawArticle.getUrl())) {
        return null;
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
      return article;
    } catch (Exception e) {
      log.error("Error processing article {}: {}", rawArticle.getUrl(), e.getMessage());
    }
    return null;
  }
}