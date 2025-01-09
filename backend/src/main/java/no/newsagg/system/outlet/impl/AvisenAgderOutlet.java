package no.newsagg.system.outlet.impl;

import lombok.extern.slf4j.Slf4j;
import no.newsagg.system.outlet.common.OutletsConfiguration;
import no.newsagg.system.outlet.common.RssNewsOutlet;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AvisenAgderOutlet extends RssNewsOutlet {
  public AvisenAgderOutlet(OutletsConfiguration outletsConfiguration) {
    super(outletsConfiguration.getConfig("avisenagder"));
  }
}