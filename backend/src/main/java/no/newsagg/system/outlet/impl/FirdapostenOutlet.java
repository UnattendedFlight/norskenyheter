package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class FirdapostenOutlet extends RssNewsOutlet {
  public FirdapostenOutlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("firdaposten"));
  }
}