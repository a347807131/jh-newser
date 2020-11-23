package fun.gatsby.web.rest;

import fun.gatsby.domain.UserExt;
import fun.gatsby.repository.UserRepository;
import fun.gatsby.security.SecurityUtils;
import fun.gatsby.service.UserExtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * PreferResource controller
 */
@RestController
@RequestMapping("/api/prefer")
public class PreferResource {

    private final Logger log = LoggerFactory.getLogger(PreferResource.class);

    @Autowired
    private UserExtService userExtService;
    /**
    * GET prefered news
    */
    @GetMapping("/news")
    public UserExt news() throws Exception {
        return null;
    }

    /**
     *  make news prefered
     */
    @PostMapping("/news/{newsId}")
    public String makeNewsPrefered(@PathVariable Long newsId) throws Exception {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new Exception("Current user login not found"));
        userExtService.preferNews(userLogin,newsId);
        return null;

    }




}
