package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class MossAvisOutlet extends RssNewsOutlet {
  public MossAvisOutlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("mossavis"));
  }
}