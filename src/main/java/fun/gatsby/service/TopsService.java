package fun.gatsby.service;

import fun.gatsby.domain.Tops;
import fun.gatsby.repository.TopsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Tops}.
 */
@Service
@Transactional
public class TopsService {

    private final Logger log = LoggerFactory.getLogger(TopsService.class);

    private final TopsRepository topsRepository;

    public TopsService(TopsRepository topsRepository) {
        this.topsRepository = topsRepository;
    }

    /**
     * Save a tops.
     *
     * @param tops the entity to save.
     * @return the persisted entity.
     */
    public Tops save(Tops tops) {
        log.debug("Request to save Tops : {}", tops);
        return topsRepository.save(tops);
    }

    /**
     * Get all the tops.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Tops> findAll(Pageable pageable) {
        log.debug("Request to get all Tops");
        return topsRepository.findAll(pageable);
    }


    /**
     * Get one tops by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Tops> findOne(Long id) {
        log.debug("Request to get Tops : {}", id);
        return topsRepository.findById(id);
    }

    /**
     * Delete the tops by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tops : {}", id);
        topsRepository.deleteById(id);
    }

    public List<Tops> findByDate(LocalDate date) {
        log.debug("Request to get Tops by date : {}", date);
        return topsRepository.findByTime(date);
    }
}
