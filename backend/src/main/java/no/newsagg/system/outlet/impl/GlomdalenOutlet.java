package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class GlomdalenOutlet extends RssNewsOutlet {
  public GlomdalenOutlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("glomdalen"));
  }
}