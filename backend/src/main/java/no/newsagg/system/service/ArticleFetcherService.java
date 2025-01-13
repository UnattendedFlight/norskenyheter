package no.newsagg.system.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.newsagg.system.messaging.RabbitMQConfig;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleFetcherService {
  private final List<RssNewsOutlet> newsOutlets;
  private final RabbitTemplate rabbitTemplate;

  @Scheduled(fixedDelayString = "${app.fetch-interval:600000}")  // Default 10 minutes
//  @Transactional
  public void fetchArticles() {
    for (int i = 0; i < newsOutlets.size(); i++) {
//      articleCollector.collectArticles(i);
      rabbitTemplate.convertAndSend(
          RabbitMQConfig.EXCHANGE_NAME,
          RabbitMQConfig.COLLECTOR_ROUTING_KEY,
          i
      );
    }
  }
}