package no.newsagg.system.service;

import java.time.Duration;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Slf4j
@Service
public class ArticleContentFetcher {
  private final WebClient webClient;
  private final Map<String, SiteConfig> siteConfigs;

  // Common patterns to identify content areas
  private static final List<String> CONTENT_SELECTORS = Arrays.asList(
      "article",
      "[role='article']",
      "[role='main']",
      "main",
      ".article-body",
      ".article-content",
      ".story-content",
      ".post-content",
      ".entry-content",
      "#article-body",
      ".story-body",
      ".content-body"
  );

  // Elements that should be removed
  private static final List<String> NOISE_SELECTORS = Arrays.asList(
      "script",
      "style",
      "iframe",
      "nav",
      "header",
      "footer",
      "aside",
      ".ads",
      ".advertisement",
      ".social-share",
      ".share-buttons",
      ".comments",
      ".related-articles",
      ".newsletter-signup",
      ".subscription-prompt",
      ".popup",
      ".modal",
      "[role='complementary']",
      "[aria-hidden='true']"
  );

  @Builder
  @Data
  private static class SiteConfig {
    private String contentSelector;
    private List<String> additionalNoiseSelectors;
    private Pattern datePattern;
    private String dateSelector;
    private String authorSelector;
  }

  public ArticleContentFetcher(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder
        .codecs(configurer -> configurer
            .defaultCodecs()
            .maxInMemorySize(2 * 1024 * 1024)) // 2MB buffer
        .build();

    // Initialize site-specific configurations
    this.siteConfigs = initializeSiteConfigs();
  }

  private Map<String, SiteConfig> initializeSiteConfigs() {
    Map<String, SiteConfig> configs = new HashMap<>();

    // Dagbladet configuration
    configs.put("dagbladet.no", SiteConfig.builder()
        .contentSelector(".article-body, .article-text")
        .additionalNoiseSelectors(Arrays.asList(".premium-overlay", ".weather-widget"))
        .dateSelector(".article-published")
        .authorSelector(".article-author")
        .build());

    // VG configuration
    configs.put("vg.no", SiteConfig.builder()
        .contentSelector(".article-body, .artikkel-content")
        .additionalNoiseSelectors(Arrays.asList(".betting-widget", ".stocks-widget"))
        .dateSelector(".publish-date")
        .authorSelector(".byline")
        .build());

    return configs;
  }

  @Data
  @Builder
  public static class ArticleContent {
    private String title;
    private String content;
    private String author;
    private String publishDate;
    private Map<String, String> metadata;
  }

  public ArticleContent fetchContent(String url) {
    try {
      String html = fetchHtml(url);
      Document doc = Jsoup.parse(html);

      // Clean the document
      cleanDocument(doc, url);

      // Extract content
      Element contentElement = findMainContent(doc, url);
      if (contentElement == null) {
        throw new RuntimeException("No main content found in " + url);
      }

      // Build article content
      return buildArticleContent(contentElement, doc, url);

    } catch (Exception e) {
      log.error("Error fetching content from {}: {}", url, e.getMessage(), e);
      throw new RuntimeException("Failed to fetch article content: " + e.getMessage(), e);
    }
  }

  private String fetchHtml(String url) {
    return webClient.get()
        .uri(url)
        .header("User-Agent", "Mozilla/5.0 (compatible; NewsAggregator/1.0;)")
        .retrieve()
        .bodyToMono(String.class)
        .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
            .maxBackoff(Duration.ofSeconds(10)))
        .timeout(Duration.ofSeconds(30))
        .block(Duration.ofSeconds(30));
  }

  private void cleanDocument(Document doc, String url) {
    // Remove standard noise elements
    NOISE_SELECTORS.forEach(selector -> doc.select(selector).remove());

    // Remove site-specific noise if configured
    SiteConfig siteConfig = findSiteConfig(url);
    if (siteConfig != null && siteConfig.getAdditionalNoiseSelectors() != null) {
      siteConfig.getAdditionalNoiseSelectors().forEach(selector ->
          doc.select(selector).remove());
    }

    // Remove empty elements
    doc.getAllElements().stream()
        .filter(element -> element.text().trim().isEmpty() && element.children().isEmpty())
        .forEach(Element::remove);
  }

  private Element findMainContent(Document doc, String url) {
    // Try site-specific selector first
    SiteConfig siteConfig = findSiteConfig(url);
    if (siteConfig != null && siteConfig.getContentSelector() != null) {
      Element element = doc.select(siteConfig.getContentSelector()).first();
      if (element != null) {
        return element;
      }
    }

    // Try common content selectors with scoring
    return findBestContentElement(doc);
  }

  private Element findBestContentElement(Document doc) {
    return CONTENT_SELECTORS.stream()
        .map(selector -> doc.select(selector))
        .filter(elements -> !elements.isEmpty())
        .map(this::scoreElements)
        .max(Comparator.comparingInt(ScoredElement::getScore))
        .map(ScoredElement::getElement)
        .orElseGet(() -> findContentByTextDensity(doc));
  }

  @Data
  @Builder
  private static class ScoredElement {
    private Element element;
    private int score;
  }

  private ScoredElement scoreElements(Elements elements) {
    return elements.stream()
        .map(element -> {
          int score = 0;

          // Score based on text length
          score += element.text().length() * 0.3;

          // Score based on paragraph density
          score += element.select("p").size() * 20;

          // Score based on link density (negative)
          double linkTextLength = element.select("a").stream()
              .mapToInt(e -> e.text().length())
              .sum();
          double linkDensity = linkTextLength / element.text().length();
          score -= linkDensity * 50;

          // Boost score for elements with article-related classes
          String classNames = element.className().toLowerCase();
          if (classNames.contains("article") || classNames.contains("content")) {
            score += 50;
          }

          return ScoredElement.builder()
              .element(element)
              .score(score)
              .build();
        })
        .max(Comparator.comparingInt(ScoredElement::getScore))
        .orElse(null);
  }

  private Element findContentByTextDensity(Document doc) {
    Element bestElement = null;
    int maxScore = 0;

    for (Element element : doc.getAllElements()) {
      if (element.tagName().equals("body")) continue;

      int textLength = element.text().length();
      int childElements = element.children().size();

      if (childElements == 0) continue;

      int density = textLength / childElements;
      if (density > maxScore) {
        maxScore = density;
        bestElement = element;
      }
    }

    return bestElement;
  }

  private ArticleContent buildArticleContent(Element contentElement, Document doc, String url) {
    // Extract and clean content
    String content = cleanContent(contentElement.html());

    // Build metadata
    Map<String, String> metadata = new HashMap<>();
    metadata.put("url", url);
    metadata.put("domain", extractDomain(url));

    // Build article content
    return ArticleContent.builder()
        .title(extractTitle(doc))
        .content(content)
        .author(extractAuthor(doc, url))
        .publishDate(extractDate(doc, url))
        .metadata(metadata)
        .build();
  }

  private String cleanContent(String html) {
    // Convert certain HTML elements to text representations
    html = html.replaceAll("<br\\s*/?>", "\n");
    html = html.replaceAll("<p.*?>", "\n\n");
    html = html.replaceAll("</p>", "");

    // Strip remaining HTML tags
    html = Jsoup.clean(html, "", org.jsoup.safety.Safelist.none());

    // Clean up whitespace
    html = html.replaceAll("\\s*\n\\s*", "\n")
        .replaceAll("\\s+", " ")
        .trim();

    return html;
  }

  private String extractTitle(Document doc) {
    return Optional.ofNullable(doc.select("h1").first())
        .map(Element::text)
        .orElseGet(() -> doc.title());
  }

  private String extractAuthor(Document doc, String url) {
    SiteConfig siteConfig = findSiteConfig(url);
    if (siteConfig != null && siteConfig.getAuthorSelector() != null) {
      Element authorElement = doc.select(siteConfig.getAuthorSelector()).first();
      if (authorElement != null) {
        return authorElement.text().trim();
      }
    }

    // Try common author selectors
    return doc.select("[rel='author'], .author, .byline")
        .stream()
        .map(Element::text)
        .filter(text -> !text.isEmpty())
        .findFirst()
        .orElse(null);
  }

  private String extractDate(Document doc, String url) {
    SiteConfig siteConfig = findSiteConfig(url);
    if (siteConfig != null && siteConfig.getDateSelector() != null) {
      Element dateElement = doc.select(siteConfig.getDateSelector()).first();
      if (dateElement != null) {
        return dateElement.text().trim();
      }
    }

    // Try common date selectors
    return doc.select("time, [datetime], .published, .date")
        .stream()
        .map(element -> element.attr("datetime"))
        .filter(date -> !date.isEmpty())
        .findFirst()
        .orElse(null);
  }

  private String extractDomain(String url) {
    try {
      return new java.net.URL(url).getHost().toLowerCase();
    } catch (Exception e) {
      return url;
    }
  }

  private SiteConfig findSiteConfig(String url) {
    return siteConfigs.entrySet().stream()
        .filter(entry -> url.contains(entry.getKey()))
        .map(Map.Entry::getValue)
        .findFirst()
        .orElse(null);
  }
}