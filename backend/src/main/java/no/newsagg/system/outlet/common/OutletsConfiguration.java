package no.newsagg.system.outlet.common;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "")
public class OutletsConfiguration {
  private Map<String, OutletConfig> outlets = new HashMap<>();

  // Getters and setters
  public OutletConfig getConfig(String outletKey) {
    return outlets.get(outletKey);
  }
}
