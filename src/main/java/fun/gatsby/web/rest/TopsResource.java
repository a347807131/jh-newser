package fun.gatsby.web.rest;

import fun.gatsby.domain.Tops;
import fun.gatsby.service.TopsService;
import fun.gatsby.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import liquibase.util.ISODateFormat;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fun.gatsby.domain.Tops}.
 */
@RestController
@RequestMapping("/api")
public class TopsResource {

    private final Logger log = LoggerFactory.getLogger(TopsResource.class);

    private static final String ENTITY_NAME = "tops";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopsService topsService;

    public TopsResource(TopsService topsService) {
        this.topsService = topsService;
    }

    /**
     * {@code POST  /tops} : Create a new tops.
     *
     * @param tops the tops to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tops, or with status {@code 400 (Bad Request)} if the tops has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tops")
    public ResponseEntity<Tops> createTops(@RequestBody Tops tops) throws URISyntaxException {
        log.debug("REST request to save Tops : {}", tops);
        if (tops.getId() != null) {
            throw new BadRequestAlertException("A new tops cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tops result = topsService.save(tops);
        return ResponseEntity.created(new URI("/api/tops/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tops} : Updates an existing tops.
     *
     * @param tops the tops to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tops,
     * or with status {@code 400 (Bad Request)} if the tops is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tops couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tops")
    public ResponseEntity<Tops> updateTops(@RequestBody Tops tops) throws URISyntaxException {
        log.debug("REST request to update Tops : {}", tops);
        if (tops.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tops result = topsService.save(tops);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tops.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tops} : get all the tops.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tops in body.
     */
    @GetMapping("/tops")
    public ResponseEntity<List<Tops>> getAllTops(Pageable pageable) {
        log.debug("REST request to get a page of Tops");
        Page<Tops> page = topsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     *  Get
     * @param date target date
     * @return a list of tops
     */
    @GetMapping("/tops/by/{date}")
    public ResponseEntity<List<Tops>> getByDate(@PathVariable String date) {
        log.debug("REST request to get a page of Tops");
        List<Tops> page = topsService.findByDate(LocalDate.parse(date));
        return ResponseEntity.ok().body(page);
    }


    /**
     * {@code GET  /tops/:id} : get the "id" tops.
     *
     * @param id the id of the tops to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tops, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tops/{id}")
    public ResponseEntity<Tops> getTops(@PathVariable Long id) {
        log.debug("REST request to get Tops : {}", id);
        Optional<Tops> tops = topsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tops);
    }

    /**
     * {@code DELETE  /tops/:id} : delete the "id" tops.
     *
     * @param id the id of the tops to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tops/{id}")
    public ResponseEntity<Void> deleteTops(@PathVariable Long id) {
        log.debug("REST request to delete Tops : {}", id);
        topsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
