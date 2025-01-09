package no.newsagg.system.outlet.impl;

import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AustAgderBladOutlet extends RssNewsOutlet {
  public AustAgderBladOutlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("austagderblad"));
  }

  @Override
  protected String cleanDescription(String description) {
    if (description == null || description.isEmpty()) {
      return "";
    }
    return description.replaceAll("<!\\[CDATA\\[|\\]\\]>", "").trim();
  }

  @Override
  protected String cleanUrl(String url) {
    String cleanUrl = super.cleanUrl(url);
    return cleanUrl.replaceFirst("^http:", "https:");
  }
}