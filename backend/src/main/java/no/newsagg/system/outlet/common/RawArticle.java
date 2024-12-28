package no.newsagg.system.outlet.common;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawArticle {
  private String url;
  private String title;
  private String source;
  private String description;
  private String imageUrl;
  private String articleId;
  private Instant publishedAt;
  private List<String> categories;
}