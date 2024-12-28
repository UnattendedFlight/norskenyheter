package no.newsagg.system.messaging;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.newsagg.system.service.ArticleService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class ArticleWorker {
  private final ArticleService articleService;

  @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
  public void processArticle(Long articleId) {
    try {
      articleService.processArticle(articleId);
    } catch (Exception e) {
      log.error("Error processing article {}: {}", articleId, e.getMessage(), e);
      // log.error("Error processing article {}", articleId);
    }
  }
}