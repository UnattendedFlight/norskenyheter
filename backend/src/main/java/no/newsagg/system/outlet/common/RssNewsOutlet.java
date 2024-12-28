package no.newsagg.system.outlet.common;

import com.apptasticsoftware.rssreader.Enclosure;
import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import no.newsagg.system.utils.rss.RssDateTimeParser;

@Slf4j
public abstract class RssNewsOutlet {
  private final OutletConfig config;
  private final RssReader reader;

  protected RssNewsOutlet(OutletConfig config) {
    this.config = config;
    this.reader = new RssReader();
    this.reader.setDateTimeParser(new RssDateTimeParser());
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
            List<Item> items = modifyReader(reader).read(feedUrl).toList();
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

  protected RssReader modifyReader(RssReader reader) {
    return (RssReader) reader.addItemExtension("media:content", "url", (item, url) -> {
      boolean hasEnclosure = item.getEnclosure().isPresent();
      Enclosure enclosure = item.getEnclosure().orElse(new Enclosure());
      enclosure.setUrl(url);
      enclosure.setType("image/jpeg");
      if (!hasEnclosure) {
        item.addEnclosure(enclosure);
      }
    });
  }

  protected RawArticle convertToRawArticle(Item item) {
    String articleId = extractArticleId(item.getGuid().orElse(""));
    String imageUrl = extractImageUrl(item.getEnclosures());
    Instant publishedAt = parsePublishedDate(item.getPubDate().orElse(null));

    return RawArticle.builder()
        .articleId(articleId)
        .url(cleanUrl(item.getLink().orElse("")))
        .title(cleanTitle(item.getTitle().orElse("")))
        .source(getName())
        .imageUrl(imageUrl)
        .description(cleanDescription(item.getDescription().orElse("")))
        .publishedAt(publishedAt)
        .categories(item.getCategories())
        .build();
  }

  // Default implementations that can be overridden by specific outlets
  protected String cleanTitle(String title) {
    return title;
  }

  protected String cleanDescription(String description) {
    return description;
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
      return url.split("\\?")[0];
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
