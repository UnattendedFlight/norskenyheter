package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class BomloNyttOutlet extends RssNewsOutlet {
  public BomloNyttOutlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("bomlonytt"));
  }
}