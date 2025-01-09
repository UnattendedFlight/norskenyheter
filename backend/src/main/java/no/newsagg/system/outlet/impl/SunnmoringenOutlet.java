package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class SunnmoringenOutlet extends RssNewsOutlet {
  public SunnmoringenOutlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("sm"));
  }
}