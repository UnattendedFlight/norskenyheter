package no.newsagg.system.ollama;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OllamaEmbeddingsResponse(
    // Array of arrays of floats
    @JsonProperty(required = true, value = "embeddings") Double[][] embeddings,
    @JsonProperty(required = true, value = "usage") OllamaEmbeddingsUsageResponse usage,
    @JsonProperty(required = true, value = "model") String model) {
}
