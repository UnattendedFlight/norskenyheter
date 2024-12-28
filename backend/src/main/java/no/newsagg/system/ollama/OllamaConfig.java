package no.newsagg.system.ollama;


import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.ollama.management.ModelManagementOptions;
import org.springframework.ai.ollama.management.PullModelStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OllamaConfig {
  @Bean
  public OllamaChatModel chatClient() {
    OllamaApi api = new OllamaApi();
    OllamaOptions options = OllamaOptions.builder()
        // .withModel(OllamaModel.LLAMA3_2.getName())
        // .withModel("aya")
        //.withModel(OllamaModel.LLAMA3_2)
        //.withModel("qwen2.5:3b")
        .withModel("qwen2.5:3b-instruct-q6_K")
        //.withModel("qwen2.5:7b-instruct-q4_K_M")
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
}
