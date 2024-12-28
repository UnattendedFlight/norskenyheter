package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class TV2Outlet extends RssNewsOutlet {
  public TV2Outlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("tv2"));
  }
}