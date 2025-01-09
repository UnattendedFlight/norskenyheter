package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class F7Outlet extends RssNewsOutlet {
  public F7Outlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("f7"));
  }
}