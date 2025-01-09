package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class StangeAvisaOutlet extends RssNewsOutlet {
  public StangeAvisaOutlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("sta"));
  }
}