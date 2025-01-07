package no.newsagg.system.ollama;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OllamaEmbeddingsUsageResponse(
    // Array of arrays of floats
    @JsonProperty(required = true, value = "prompt_tokens") Integer promptTokens,
    @JsonProperty(required = true, value = "total_tokens") Integer totalTokens) {
}
