package no.newsagg.system.domain;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

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

  // Add content field
  @Column(columnDefinition = "TEXT")
  private String content;

  // Add embedding field using a custom type for vector(768)
//  @Column(columnDefinition = "vector(768)")
//  @JdbcTypeCode(SqlTypes.VECTOR)
  @Basic
  @Type(JsonType.class)
  @Column(name = "embedding", columnDefinition = "vector(768)")
  private float[] embedding;

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


  @Column(name = "categories", columnDefinition = "text[]")
  @JdbcTypeCode(SqlTypes.ARRAY)
  private List<String> categories;
}