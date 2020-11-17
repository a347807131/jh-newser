package fun.gatsby.repository;

import fun.gatsby.domain.News;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the News entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
