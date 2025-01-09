package no.newsagg.system.ollama;


import io.netty.channel.ChannelOption;
import java.time.Duration;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.ollama.management.ModelManagementOptions;
import org.springframework.ai.ollama.management.PullModelStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class OllamaConfig {
  private final String ollamaUrl;

  public OllamaConfig(@Value("${spring.ai.ollama.base-url}") String ollamaUrl) {
    this.ollamaUrl = ollamaUrl;
  }
  
  @Bean
  public OllamaChatModel chatClient() {
    HttpClient httpClient = HttpClient.create()
        .responseTimeout(Duration.ofMinutes(2))  // Increase timeout for large model responses
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 60000) // 60 seconds connection timeout
        .option(ChannelOption.SO_TIMEOUT, 60000); // 60 seconds connection timeout

    WebClient.Builder webClientB = WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient));

    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
    factory.setConnectTimeout(Duration.ofSeconds(5));
    factory.setReadTimeout(Duration.ofMinutes(5));

    RestClient.Builder restClientB = RestClient.builder().requestFactory(factory);
    OllamaApi api = new OllamaApi(ollamaUrl, restClientB, webClientB);
    OllamaOptions options = OllamaOptions.builder()
        // .withModel(OllamaModel.LLAMA3_2.getName())
        // .withModel("aya")
        //.withModel(OllamaModel.LLAMA3_2)
        //.withModel("qwen2.5:3b")
        //.withModel("qwen2.5:14b-instruct-q6_K")
        .withModel("llama3.1:8b-instruct-q8_0")
        //.withModel(OllamaModel.GEMMA)
        .build();
    return OllamaChatModel.builder()
        .withOllamaApi(api)
        .withDefaultOptions(options)
        .withModelManagementOptions(ModelManagementOptions.builder()
            .withPullModelStrategy(PullModelStrategy.WHEN_MISSING)
            .build())
        .build();
  }

  @Bean
  public OllamaEmbeddingModel embeddingsClient() {
    HttpClient httpClient = HttpClient.create()
        .responseTimeout(Duration.ofMinutes(2))  // Increase timeout for large model responses
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 60000) // 60 seconds connection timeout
        .option(ChannelOption.SO_TIMEOUT, 60000); // 60 seconds connection timeout

    WebClient.Builder webClientB = WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient));

    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
    factory.setConnectTimeout(Duration.ofSeconds(5));
    factory.setReadTimeout(Duration.ofMinutes(5));

    RestClient.Builder restClientB = RestClient.builder().requestFactory(factory);
    OllamaApi api = new OllamaApi(ollamaUrl, restClientB, webClientB);
    OllamaOptions options = OllamaOptions.builder()
        .withModel("nomic-embed-text")
        .build();
    return OllamaEmbeddingModel.builder()
        .withOllamaApi(api)
        .withDefaultOptions(options)
        .withModelManagementOptions(ModelManagementOptions.builder()
            .withPullModelStrategy(PullModelStrategy.WHEN_MISSING)
            .build())
        .build();
  }
}
