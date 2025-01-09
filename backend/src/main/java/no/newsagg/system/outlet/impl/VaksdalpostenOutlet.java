package no.newsagg.system.outlet.impl;

import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Component
public class VaksdalpostenOutlet extends RssNewsOutlet {
  public VaksdalpostenOutlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("vkp"));
  }
}