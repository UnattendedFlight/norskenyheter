package no.newsagg.system.outlet.common;

import java.util.List;
import lombok.Data;


@Data
public class OutletConfig {
  private String name;
  private String feedUrl;
  private List<String> feedUrls;
  private String language;
  private boolean enabled;
  private int fetchIntervalMinutes;

  public List<String> getFeedUrls() {
    if (feedUrls != null && !feedUrls.isEmpty()) {
      return feedUrls;
    }
    return feedUrl != null ? List.of(feedUrl) : List.of();
  }
}
