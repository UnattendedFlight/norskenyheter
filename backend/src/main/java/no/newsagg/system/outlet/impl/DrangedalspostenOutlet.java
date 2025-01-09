package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class DrangedalspostenOutlet extends RssNewsOutlet {
  public DrangedalspostenOutlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("drangedalsposten"));
  }
}