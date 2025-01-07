package no.newsagg.system.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import no.newsagg.system.domain.Article;
import no.newsagg.system.domain.ArticleStatus;

@Data
@AllArgsConstructor
public class ArticleResponseDTO {
  private Long id;
  private String articleId;
  private String url;
  private String source;
  private String originalTitle;
  private String aiGeneratedTitle;
  private String summary;
  private String description;
  private String content;
  private String imageUrl;
  private Instant publishedAt;
  private Instant processedAt;
  private boolean isPaywalled;
  private ArticleStatus status;

  public static ArticleResponseDTO fromArticle(Article article) {
    return new ArticleResponseDTO(
        article.getId(),
        article.getArticleId(),
        article.getUrl(),
        article.getSource(),
        article.getOriginalTitle(),
        article.getAiGeneratedTitle(),
        article.getSummary(),
        article.getDescription(),
        article.getContent(),
        article.getImageUrl(),
        article.getPublishedAt(),
        article.getProcessedAt(),
        article.isPaywalled(),
        article.getStatus()
    );
  }
}