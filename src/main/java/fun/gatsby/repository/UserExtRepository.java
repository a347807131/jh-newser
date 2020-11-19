package fun.gatsby.repository;

import fun.gatsby.domain.UserExt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserExt entity.
 */
@Repository
public interface UserExtRepository extends JpaRepository<UserExt, Long> {

    @Query(value = "select distinct userExt from UserExt userExt left join fetch userExt.news",
        countQuery = "select count(distinct userExt) from UserExt userExt")
    Page<UserExt> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct userExt from UserExt userExt left join fetch userExt.news")
    List<UserExt> findAllWithEagerRelationships();

    @Query("select userExt from UserExt userExt left join fetch userExt.news where userExt.id =:id")
    Optional<UserExt> findOneWithEagerRelationships(@Param("id") Long id);
}
