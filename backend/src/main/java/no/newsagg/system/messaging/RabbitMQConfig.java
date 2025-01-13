package no.newsagg.system.messaging;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
  public static final String EXCHANGE_NAME = "newsagg.articles";
  public static final String QUEUE_NAME = "newsagg.articles.process";
  public static final String ROUTING_KEY = "article.new";
  public static final String COLLECTOR_QUEUE_NAME = "newsagg.articles.collector";
  public static final String COLLECTOR_ROUTING_KEY = "article.collector";
  public static final String CATEGORY_QUEUE_NAME = "newsagg.articles.category";
  public static final String CATEGORY_ROUTING_KEY = "article.category";

  @Bean
  public TopicExchange articleExchange() {
    return new TopicExchange(EXCHANGE_NAME);
  }

  @Bean
  public Queue articleQueue() {
    return QueueBuilder.durable(QUEUE_NAME)
        .build();
  }

  @Bean
  public Binding binding(Queue articleQueue, TopicExchange articleExchange) {
    return BindingBuilder
        .bind(articleQueue)
        .to(articleExchange)
        .with(ROUTING_KEY);
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(jsonMessageConverter());
    return rabbitTemplate;
  }

  @Bean
  public Queue collectorQueue() {
    return QueueBuilder.durable(COLLECTOR_QUEUE_NAME)
        .build();
  }

  @Bean
  public Binding collectorBinding(Queue collectorQueue, TopicExchange articleExchange) {
    return BindingBuilder
        .bind(collectorQueue)
        .to(articleExchange)
        .with(COLLECTOR_ROUTING_KEY);
  }

  @Bean
  public Queue categoryQueue() {
    return QueueBuilder.durable(CATEGORY_QUEUE_NAME)
        .build();
  }

  @Bean
  public Binding categoryBinding(Queue categoryQueue, TopicExchange articleExchange) {
    return BindingBuilder
        .bind(categoryQueue)
        .to(articleExchange)
        .with(CATEGORY_ROUTING_KEY);
  }
}