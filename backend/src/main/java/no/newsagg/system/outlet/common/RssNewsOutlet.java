package no.newsagg.system.outlet.common;

import com.apptasticsoftware.rssreader.Enclosure;
import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import no.newsagg.system.utils.rss.RssDateTimeParser;

@Slf4j
public abstract class RssNewsOutlet {
  private final OutletConfig config;
  private final RssReader reader;
  private final Map<String, FieldExtractor> customFieldExtractors;

  protected RssNewsOutlet(OutletConfig config) {
    this.config = config;
    this.reader = new RssReader();
    this.reader.setDateTimeParser(new RssDateTimeParser());
    this.customFieldExtractors = new HashMap<>();
    configureCustomFieldExtractors();
  }

  // Optional method for outlets to configure their custom field extractors
  protected void configureCustomFieldExtractors() {
    // Default implementation does nothing
  }

  // Register custom field extractors
  protected void registerFieldExtractor(String fieldName, FieldExtractor extractor) {
    customFieldExtractors.put(fieldName, extractor);
  }

  public String getName() {
    return config.getName();
  }

  protected List<String> getFeedUrls() {
    return config.getFeedUrls();
  }

  public List<RawArticle> fetchArticles() {
    return getFeedUrls().stream()
        .flatMap(feedUrl -> {
          try {
            List<Item> items = configureReader(reader).read(feedUrl).toList();
            return items.stream().map(this::convertToRawArticle);
          } catch (Exception e) {
            log.error("Error fetching articles from {} feed {}: {}", getName(), feedUrl,
                e.getMessage());
            return Stream.empty();
          }
        })
        .distinct()
        .toList();
  }

  // Method to configure the RSS reader with custom extensions
  protected RssReader configureReader(RssReader reader) {
    // Add default media:content handler
    reader.addItemExtension("media:content", "url", (item, url) -> {
      handleMediaContent(item, url);
    });

    // Add content:encoded handler
    reader.addItemExtension("content:encoded", "value", (item, content) -> {
      item.setDescription(content);
    });

    // Allow subclasses to add their own extensions
    configureCustomExtensions(reader);

    return reader;
  }

  // Method to be overridden by subclasses to add custom extensions
  protected void configureCustomExtensions(RssReader reader) {
    // Default implementation does nothing
  }

  private void handleMediaContent(Item item, String url) {
    boolean hasEnclosure = item.getEnclosure().isPresent();
    Enclosure enclosure = item.getEnclosure().orElse(new Enclosure());
    enclosure.setUrl(url);
    enclosure.setType("image/jpeg");
    if (!hasEnclosure) {
      item.addEnclosure(enclosure);
    }
  }

  protected RawArticle convertToRawArticle(Item item) {
    return RawArticle.builder()
        .articleId(
            extractField("articleId", item, () -> extractArticleId(item.getGuid().orElse(""))))
        .url(extractField("url", item, () -> cleanUrl(item.getLink().orElse(""))))
        .title(extractField("title", item, () -> cleanTitle(item.getTitle().orElse(""))))
        .source(getName())
        .imageUrl(extractField("imageUrl", item, () -> extractImageUrl(item.getEnclosures())))
        .description(extractField("description", item,
            () -> cleanDescription(item.getDescription().orElse(""))))
        .publishedAt(extractField("publishedAt", item,
            () -> parsePublishedDate(item.getPubDate().orElse(null))))
        .categories(extractField("categories", item, item::getCategories))
        .build();
  }

  // Generic field extraction method
  protected <T> T extractField(String fieldName, Item item, Supplier<T> defaultExtractor) {
    FieldExtractor extractor = customFieldExtractors.get(fieldName);
    if (extractor != null) {
      try {
        @SuppressWarnings("unchecked")
        T result = (T) extractor.extract(item);
        return result;
      } catch (Exception e) {
        log.warn("Error extracting custom field {}: {}", fieldName, e.getMessage());
      }
    }
    return defaultExtractor.get();
  }

  // Default implementations that can be overridden by specific outlets
  protected String cleanTitle(String title) {
    return title;
  }

  protected String cleanDescription(String description) {
    if (description == null || description.isEmpty()) {
      return "";
    }
    return description.replaceAll("<!\\[CDATA\\[|]]>", "").trim();
  }

  protected String extractArticleId(String guid) {
    try {
      return guid.substring(guid.lastIndexOf("/") + 1);
    } catch (Exception e) {
      log.warn("Could not extract article ID from guid: {}", guid);
      return null;
    }
  }

  protected String extractImageUrl(List<Enclosure> enclosures) {
    return Optional.ofNullable(enclosures)
        .flatMap(list -> list.stream()
            .filter(this::isImageEnclosure)
            .map(Enclosure::getUrl)
            .findFirst())
        .orElse(null);
  }

  protected boolean isImageEnclosure(Enclosure enclosure) {
    String type = enclosure.getType();
    return "image/jpeg".equals(type) || "img/jpg".equals(type) || "image/jpg".equals(type);
  }

  protected String cleanUrl(String url) {
    try {
      return url.split("\\?")[0].replaceFirst("^http:", "https:");
    } catch (Exception e) {
      log.warn("Error cleaning URL: {}, returning original", url);
      return url;
    }
  }

  protected Instant parsePublishedDate(String dateString) {
    if (dateString == null || dateString.isEmpty()) {
      return Instant.now();
    }

    try {
      return new RssDateTimeParser().toInstant(dateString);
    } catch (Exception e) {
      log.warn("Failed to parse date '{}', using current time: {}", dateString, e.getMessage());
      return Instant.now();
    }
  }
}