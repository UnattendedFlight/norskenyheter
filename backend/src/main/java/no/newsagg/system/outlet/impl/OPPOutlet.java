package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class OPPOutlet extends RssNewsOutlet {
  public OPPOutlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("opp"));
  }
}