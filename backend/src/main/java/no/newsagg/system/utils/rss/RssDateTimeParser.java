package no.newsagg.system.utils.rss;

import com.apptasticsoftware.rssreader.DateTimeParser;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RssDateTimeParser implements DateTimeParser {
  private static final DateTimeFormatter RSS_DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .appendPattern("EEE, dd MMM yyyy HH:mm:ss")
      .appendLiteral(' ')
      .appendPattern("z")
      .toFormatter(Locale.ENGLISH);
  private static final DateTimeFormatter RSS_DATE_TIME_FORMATTER_2 = new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      // Wed, 25 Sep 2024 09:07:22 +0200
      .appendPattern("EEE, dd MMM yyyy HH:mm:ss Z")
      .toFormatter(Locale.ENGLISH);

  @Override
  public ZonedDateTime parse(String dateString) {
    try {
      return ZonedDateTime.parse(dateString, RSS_DATE_TIME_FORMATTER);
    } catch (Exception e) {
      try {
        return ZonedDateTime.parse(dateString, RSS_DATE_TIME_FORMATTER_2);
      } catch (Exception e2) {
        log.error("Failed to parse date '{}': {}", dateString, e2.getMessage());
        throw e2;
      }
    }
  }

  @Override
  public Instant toInstant(String dateString) {
    try {
      return parse(dateString).toInstant();
    } catch (Exception e) {
      log.error("Failed to convert date '{}' to Instant: {}", dateString, e.getMessage());
      throw e;
    }
  }
}