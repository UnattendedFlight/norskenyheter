package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class AftenpostenOutlet extends RssNewsOutlet {
  public AftenpostenOutlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("ap"));
  }

  @Override
  protected String cleanTitle(String title) {
    return title.replace("<!\\[CDATA\\[", "").replace("]]>", "").strip();
  }

  @Override
  protected String cleanDescription(String description) {
    return cleanTitle(description);
  }
}