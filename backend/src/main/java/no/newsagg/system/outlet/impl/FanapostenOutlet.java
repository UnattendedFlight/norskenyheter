package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class FanapostenOutlet extends RssNewsOutlet {
  public FanapostenOutlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("fanaposten"));
  }
}