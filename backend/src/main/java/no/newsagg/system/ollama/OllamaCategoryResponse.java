package no.newsagg.system.ollama;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OllamaCategoryResponse(
    @JsonProperty(required = true, value = "categories") String[] categories) {
}
