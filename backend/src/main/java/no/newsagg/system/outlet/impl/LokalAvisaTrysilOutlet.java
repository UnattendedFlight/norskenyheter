package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class LokalAvisaTrysilOutlet extends RssNewsOutlet {
  public LokalAvisaTrysilOutlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("lat"));
  }
}