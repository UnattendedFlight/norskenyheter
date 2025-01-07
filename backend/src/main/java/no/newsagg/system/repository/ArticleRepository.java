package no.newsagg.system.repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import no.newsagg.system.domain.Article;
import no.newsagg.system.domain.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
  boolean existsByUrl(String url);

  Optional<Article> findByUrl(String url);


  Page<Article> findByStatusAndPublishedAtAfter(ArticleStatus status, Instant after,
                                                Pageable pageable);

  Page<Article> findBySourceAndStatusAndPublishedAtAfter(String source, ArticleStatus status,
                                                         Instant after, Pageable pageable);

  @Query("SELECT COUNT(a) FROM Article a")
  long countTotalArticles();

  @Query("SELECT a.source, COUNT(a) FROM Article a GROUP BY a.source")
  List<Object[]> countArticlesBySource();

  @Query("SELECT a FROM Article a WHERE a.source = :source AND a.status = :status AND a.publishedAt > :after AND (a.originalTitle LIKE %:query% OR a.description LIKE %:query% OR a.summary LIKE %:query% OR a.aiGeneratedTitle LIKE %:query%)")
  Page<Article> findBySourceAndStatusAndPublishedAtAfterSearch(String source, ArticleStatus status,
                                                               Instant after, Pageable pageable,
                                                               String query);

  Page<Article> findBySourceAndStatus(String source, ArticleStatus status, Pageable pageable);

  @Query("SELECT a FROM Article a WHERE a.source = :source AND a.status = :status AND (a.originalTitle LIKE %:query% OR a.description LIKE %:query% OR a.summary LIKE %:query% OR a.aiGeneratedTitle LIKE %:query%)")
  Page<Article> findBySourceAndStatusSearch(String source, ArticleStatus status, Pageable pageable,
                                            String query);

  Page<Article> findBySource(String source, Pageable pageable);

  @Query("SELECT a FROM Article a WHERE a.source = :source AND (a.originalTitle LIKE %:query% OR a.description LIKE %:query% OR a.summary LIKE %:query% OR a.aiGeneratedTitle LIKE %:query%)")
  Page<Article> findBySourceSearch(String source, Pageable pageable, String query);


  Page<Article> findByStatus(ArticleStatus status, Pageable pageable);

  @Query("SELECT a FROM Article a WHERE a.status = :status AND (a.originalTitle LIKE %:query% OR a.description LIKE %:query% OR a.summary LIKE %:query% OR a.aiGeneratedTitle LIKE %:query%)")
  Page<Article> findByStatusSearch(ArticleStatus status, Pageable pageable, String query);

  List<Article> findByStatusIn(Collection<ArticleStatus> statuses);

  @Modifying
  @Query("UPDATE Article a SET a.status = :newStatus WHERE a.id = :id")
  void updateStatus(Long id, ArticleStatus newStatus);

  List<Article> findByStatusInAndRetryCountLessThan(Collection<ArticleStatus> statuses,
                                                    Integer retryCountIsLessThan);

  List<Article> findAllByUrlIn(Collection<String> urls);

  List<Article> findAllByUrlIsIn(Collection<String> urls);

  @Query(value = """
            SELECT * FROM articles 
            WHERE status = :status 
            AND id <> :excludeId
            ORDER BY embedding <=> CAST(:queryVector AS vector(768))
            LIMIT :limit
            """, nativeQuery = true)
  List<Article> findSimilarArticles(
      @Param("excludeId") Long excludeId,
      @Param("queryVector") float[] queryVector,
      @Param("status") String status,
      @Param("limit") int limit);
}