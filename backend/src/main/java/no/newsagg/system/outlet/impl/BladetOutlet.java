package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class BladetOutlet extends RssNewsOutlet {
  public BladetOutlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("bladet"));
  }
}