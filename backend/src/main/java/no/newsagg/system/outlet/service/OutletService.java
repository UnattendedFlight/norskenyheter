package no.newsagg.system.outlet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import no.newsagg.system.outlet.common.NewsOutlet;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OutletService {
  private final Map<String, NewsOutlet> outlets;

  public OutletService(List<NewsOutlet> configuredOutlets) {
    this.outlets = configuredOutlets.stream()
        .collect(Collectors.toMap(
            NewsOutlet::getName,
            outlet -> outlet
        ));
  }

  public List<NewsOutlet> getEnabledOutlets() {
    return new ArrayList<>(outlets.values());
  }

  public Optional<NewsOutlet> getOutlet(String name) {
    return Optional.ofNullable(outlets.get(name));
  }
}
