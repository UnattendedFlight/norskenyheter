package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class Energi24Outlet extends RssNewsOutlet {
  public Energi24Outlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("energi24"));
  }
}