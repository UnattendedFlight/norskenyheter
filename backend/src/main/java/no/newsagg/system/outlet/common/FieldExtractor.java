package no.newsagg.system.outlet.common;

import com.apptasticsoftware.rssreader.Item;

// Interface for custom field extractors
@FunctionalInterface
public interface FieldExtractor {
  Object extract(Item item);
}
