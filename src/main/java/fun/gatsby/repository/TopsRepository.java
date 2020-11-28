package fun.gatsby.repository;

import fun.gatsby.domain.Tops;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data  repository for the Tops entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TopsRepository extends JpaRepository<Tops, Long> {
    List<Tops> findByTime(LocalDate date);
}
