package no.newsagg.system.ollama;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OllamaResponse(
    @JsonProperty(required = true, value = "title") String title,
    @JsonProperty(required = true, value = "summary") String summary,
    @JsonProperty(required = true, value = "is_paywalled") boolean isPaywalled) {
}
