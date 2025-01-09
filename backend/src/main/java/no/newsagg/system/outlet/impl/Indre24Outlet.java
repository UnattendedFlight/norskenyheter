package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class Indre24Outlet extends RssNewsOutlet {
  public Indre24Outlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("indre24"));
  }
}