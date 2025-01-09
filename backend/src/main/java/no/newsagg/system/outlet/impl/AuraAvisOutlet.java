package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class AuraAvisOutlet extends RssNewsOutlet {
  public AuraAvisOutlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("auraavis"));
  }
}