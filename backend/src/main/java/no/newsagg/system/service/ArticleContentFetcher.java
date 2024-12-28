package no.newsagg.system.service;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Slf4j
@Service
public class ArticleContentFetcher {
  private final WebClient webClient;

  public ArticleContentFetcher(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.build();
  }

  public String fetchContent(String url) {
    try {
      String html = webClient.get()
          .uri(url)
          .retrieve()
          .bodyToMono(String.class)
          .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
          .timeout(Duration.ofSeconds(30))
          .block(Duration.ofSeconds(10));

      if (html == null || html.isEmpty()) {
        throw new RuntimeException("Empty response");
      }

      Document doc = Jsoup.parse(html);

      // Remove unwanted elements
      doc.select("script, style, iframe, nav, footer, header, .ads, .social-share, .comments")
          .remove();

      // Try different content selectors based on news outlets
      String content = "";
      if (url.contains("dagbladet.no")) {
        content = extractDagbladetContent(doc);
      } else if (url.contains("vg.no")) {
        content = extractVGContent(doc);
      } else {
        content = extractGenericContent(doc);
      }

      // Clean up the content
      content = content.replaceAll("\\s+", " ").trim();

      if (content.isEmpty()) {
        throw new RuntimeException("No content found in " + url);
      }

      return content;

    } catch (Exception e) {
      log.error("Error fetching content from {}: {}", url, e.getMessage());
      throw new RuntimeException("Failed to fetch article content: " + e.getMessage(), e);
    }
  }

  private String extractDagbladetContent(Document doc) {
    // Dagbladet specific selectors
    return doc.select(".article-body, .article-text, .article-content")
        .stream()
        .map(Element::text)
        .findFirst()
        .orElseGet(() -> extractGenericContent(doc));
  }

  private String extractVGContent(Document doc) {
    // VG specific selectors
    return doc.select("article, .article-body, .artikkel-content")
        .stream()
        .map(Element::text)
        .findFirst()
        .orElseGet(() -> extractGenericContent(doc));
  }

  private String extractGenericContent(Document doc) {
    // Try common content selectors
    return doc.select("article, .article-body, .article-content, main, #main-content")
        .stream()
        .map(Element::text)
        .findFirst()
        .orElse(doc.body().text()); // Fallback to body text
  }
}