package no.newsagg.system.outlet.common;

import java.util.List;

public interface NewsOutlet {
  String getName();

  String getFeedUrl();

  List<RawArticle> fetchArticles();

  // Optional method for custom article parsing if needed
  default RawArticle parseArticle(String url) {
    return null;
  }

  List<String> getFeedUrls();
}
