package fun.gatsby.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestResource controller
 */
@RestController
@RequestMapping("/test")
public class TestResource {

    private final Logger log = LoggerFactory.getLogger(TestResource.class);

    /**
    * GET demo
    */
    @GetMapping("/demo")
    public String demo() {
        return "demo";
    }

    @GetMapping("/demo1")
    public  String demo1(){return "demo1";}

}
