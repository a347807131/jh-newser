package fun.gatsby.web.rest;

import fun.gatsby.domain.UserExt;
import fun.gatsby.repository.UserExtRepository;
import fun.gatsby.repository.UserRepository;
import fun.gatsby.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link fun.gatsby.domain.UserExt}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserExtResource {

    private final Logger log = LoggerFactory.getLogger(UserExtResource.class);

    private static final String ENTITY_NAME = "userExt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserExtRepository userExtRepository;

    private final UserRepository userRepository;

    public UserExtResource(UserExtRepository userExtRepository, UserRepository userRepository) {
        this.userExtRepository = userExtRepository;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /user-exts} : Create a new userExt.
     *
     * @param userExt the userExt to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userExt, or with status {@code 400 (Bad Request)} if the userExt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-exts")
    public ResponseEntity<UserExt> createUserExt(@RequestBody UserExt userExt) throws URISyntaxException {
        log.debug("REST request to save UserExt : {}", userExt);
        if (userExt.getId() != null) {
            throw new BadRequestAlertException("A new userExt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(userExt.getUser())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        Long userId = userExt.getUser().getId();
        userRepository.findById(userId).ifPresent(userExt::user);
        UserExt result = userExtRepository.save(userExt);
        return ResponseEntity.created(new URI("/api/user-exts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-exts} : Updates an existing userExt.
     *
     * @param userExt the userExt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userExt,
     * or with status {@code 400 (Bad Request)} if the userExt is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userExt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-exts")
    public ResponseEntity<UserExt> updateUserExt(@RequestBody UserExt userExt) throws URISyntaxException {
        log.debug("REST request to update UserExt : {}", userExt);
        if (userExt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserExt result = userExtRepository.save(userExt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userExt.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-exts} : get all the userExts.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userExts in body.
     */
    @GetMapping("/user-exts")
    @Transactional(readOnly = true)
    public List<UserExt> getAllUserExts(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all UserExts");
        return userExtRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /user-exts/:id} : get the "id" userExt.
     *
     * @param id the id of the userExt to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userExt, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-exts/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<UserExt> getUserExt(@PathVariable Long id) {
        log.debug("REST request to get UserExt : {}", id);
        Optional<UserExt> userExt = userExtRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(userExt);
    }

    /**
     * {@code DELETE  /user-exts/:id} : delete the "id" userExt.
     *
     * @param id the id of the userExt to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-exts/{id}")
    public ResponseEntity<Void> deleteUserExt(@PathVariable Long id) {
        log.debug("REST request to delete UserExt : {}", id);
        userExtRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
