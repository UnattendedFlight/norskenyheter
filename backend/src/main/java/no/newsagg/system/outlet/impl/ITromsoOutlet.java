package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class ITromsoOutlet extends RssNewsOutlet {
  public ITromsoOutlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("itromso"));
  }
}