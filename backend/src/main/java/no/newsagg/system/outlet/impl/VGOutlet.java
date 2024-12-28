package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class VGOutlet extends RssNewsOutlet {
  public VGOutlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("vg"));
  }
}