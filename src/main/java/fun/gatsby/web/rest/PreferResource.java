package fun.gatsby.web.rest;

import fun.gatsby.domain.News;
import fun.gatsby.domain.UserExt;
import fun.gatsby.security.DomainUserDetailsService;
import fun.gatsby.security.SecurityUtils;
import fun.gatsby.service.UserExtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * PreferResource controller
 */
@RestController
@RequestMapping("/api/prefer")
public class PreferResource {

    private final Logger log = LoggerFactory.getLogger(PreferResource.class);

    @Autowired
    private UserExtService userExtService;

    @Autowired
    private DomainUserDetailsService userDetailsService;

    /**
    * GET all prefered news of the current user.
    */
    @GetMapping("/news")
    public ResponseEntity<Set<News>> news() throws Exception {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new Exception("Current user login not found"));
        log.debug("Rest to get prefered news :{}",userLogin);
        UserExt userExt = userDetailsService.loadUserExtByUsername(userLogin);
        Set<News> newsSet = userExt.getNews();
        return ResponseEntity.ok().body(newsSet);
    }

    /**
     *  make news prefered
     * @return
     */
    @PostMapping("/news/{newsId}")
    public ResponseEntity<Void> makeNewsPrefered(@PathVariable Long newsId) throws Exception {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new Exception("Current user login not found"));
        userExtService.preferNews(userLogin,newsId);
        return  ResponseEntity.ok().build();
    }

    @DeleteMapping("/news/{newsId}")
    public ResponseEntity<Void> makeNewsUnPrefered(@PathVariable Long newsId) throws Exception {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new Exception("Current user login not found"));
        userExtService.unpreferNews(userLogin,newsId);
        return  ResponseEntity.ok().build();
    }

}
