package no.newsagg.system.dto;

import java.util.Map;

public class ArticleStatsDTO {
  private long totalArticles;
  private Map<String, Long> articlesPerSource;

  public ArticleStatsDTO(long totalArticles, Map<String, Long> articlesPerSource) {
    this.totalArticles = totalArticles;
    this.articlesPerSource = articlesPerSource;
  }

  public long getTotalArticles() {
    return totalArticles;
  }

  public void setTotalArticles(long totalArticles) {
    this.totalArticles = totalArticles;
  }

  public Map<String, Long> getArticlesPerSource() {
    return articlesPerSource;
  }

  public void setArticlesPerSource(Map<String, Long> articlesPerSource) {
    this.articlesPerSource = articlesPerSource;
  }
}