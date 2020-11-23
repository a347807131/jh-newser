package fun.gatsby.service;

import fun.gatsby.domain.News;
import fun.gatsby.domain.User;
import fun.gatsby.domain.UserExt;
import fun.gatsby.repository.UserExtRepository;
import fun.gatsby.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link UserExt}.
 */
@Service
@Transactional
public class UserExtService {

    private final Logger log = LoggerFactory.getLogger(UserExtService.class);

    private final UserExtRepository userExtRepository;

    private final UserRepository userRepository;

    @Autowired
    private NewsService newsService;

    public UserExtService(UserExtRepository userExtRepository, UserRepository userRepository) {
        this.userExtRepository = userExtRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a userExt.
     *
     * @param userExt the entity to save.
     * @return the persisted entity.
     */
    public UserExt save(UserExt userExt) {
        log.debug("Request to save UserExt : {}", userExt);
        Long userId = userExt.getUser().getId();
        userRepository.findById(userId).ifPresent(userExt::user);
        return userExtRepository.save(userExt);
    }

    /**
     * Get all the userExts.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserExt> findAll() {
        log.debug("Request to get all UserExts");
        return userExtRepository.findAllWithEagerRelationships();
    }


    /**
     * Get all the userExts with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UserExt> findAllWithEagerRelationships(Pageable pageable) {
        return userExtRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one userExt by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserExt> findOne(Long id) {
        log.debug("Request to get UserExt : {}", id);
        return userExtRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the userExt by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserExt : {}", id);
        userExtRepository.deleteById(id);
    }

    /**
     *  Prefer news by userLogin and newsId
     */
    public UserExt preferNews(String userLogin, Long newsId) {
        News news = newsService.findOne(newsId).orElseThrow();
        User user = userRepository.findOneByLogin(userLogin).orElseThrow();
        UserExt userExt = userExtRepository.findOneWithEagerRelationships(user.getId()).orElseThrow();
        userExt.addNews(news);
        return userExtRepository.save(userExt);
    }

    public UserExt unpreferNews(String userLogin, Long newsId) {
        News news = newsService.findOne(newsId).orElseThrow();
        User user = userRepository.findOneByLogin(userLogin).orElseThrow();
        UserExt userExt = userExtRepository.findOneWithEagerRelationships(user.getId()).orElseThrow();
        userExt.removeNews(news);
        return userExtRepository.save(userExt);
    }
}
