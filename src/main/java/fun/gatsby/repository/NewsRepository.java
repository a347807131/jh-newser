package fun.gatsby.repository;

import fun.gatsby.domain.News;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the News entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    /**
     * 1.@Query 这个是jpa自定定义sql语句时用到的注解
     * 2.nativeQuery代表value中的语句为 sql语句，而非hql语句
     * 3. countQuery代表当前分页的总页数，如果不设置这个参数相信你的分页一定不顺利。
     * @param pageable
     * @return
     */
    @Query(value = "SELECT id,title,link,time,source,kind,'' as content FROM news",
        countQuery = "SELECT count(*) FROM news",
        nativeQuery = true
    )
    Page<News> findAllWithoutContent( Pageable pageable);
}
