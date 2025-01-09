package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class NumedalsnettOutlet extends RssNewsOutlet {
  public NumedalsnettOutlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("numedal"));
  }
}