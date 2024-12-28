package no.newsagg.system.config;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

  private static final int MAX_BUFFER_SIZE = 50 * 1024 * 1024; // 50MB

  @Bean
  public WebClient.Builder webClientBuilder() {
    ExchangeStrategies strategies = ExchangeStrategies.builder()
        .codecs(configurer -> {
          ClientCodecConfigurer.ClientDefaultCodecs codecs = configurer.defaultCodecs();
          codecs.maxInMemorySize(MAX_BUFFER_SIZE);
        })
        .build();

    // Customize the HttpClient to follow redirects
    HttpClient httpClient = HttpClient.create()
        .responseTimeout(Duration.ofSeconds(40)) // Set response timeout to 40 seconds
        .followRedirect(true); // Enable automatic redirect following

    return WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient)) // Apply customized HttpClient
        .exchangeStrategies(strategies)
        .defaultHeader("User-Agent", "NewsAgg/1.0 (https://newsagg.no)");
  }
}