package no.newsagg.system.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.newsagg.system.domain.Article;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArticleProcessor {
  private final RabbitTemplate rabbitTemplate;
  private static final long PROCESSING_DELAY_MS = 1000; // 1 second delay

  public void queueForProcessing(Article article) {
    // Sleep for 20ms
    try {
      Thread.sleep(20);
    } catch (InterruptedException e) {
      log.error("Error while sleeping", e);
    }
    rabbitTemplate.convertAndSend(
        RabbitMQConfig.EXCHANGE_NAME,
        RabbitMQConfig.ROUTING_KEY,
        article.getId(),
        message -> {
          MessageProperties props = message.getMessageProperties();
          props.setDelayLong(PROCESSING_DELAY_MS);
          return message;
        }
    );
    log.debug("Queued article {} for processing with {}ms delay", article.getId(),
        PROCESSING_DELAY_MS);
  }
}