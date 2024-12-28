package no.newsagg.system.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "articles")
public class Article {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String articleId;

  @Column(nullable = false, unique = true)
  private String url;

  @Column(nullable = false)
  private String source;

  @Column(nullable = false)
  private String originalTitle;

  @Column
  private String aiGeneratedTitle;

  @Column(columnDefinition = "TEXT")
  private String summary;

  @Column(columnDefinition = "TEXT", nullable = true)
  private String description;

  @Column(nullable = false)
  private String imageUrl;

  @Column(nullable = false)
  private Instant publishedAt;

  @Column
  private Instant processedAt;

  @Column
  private boolean isPaywalled;

  @Column
  private Integer retryCount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ArticleStatus status;
}