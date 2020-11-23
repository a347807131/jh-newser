package fun.gatsby.web.rest;

import fun.gatsby.JhdApp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static fun.gatsby.web.rest.AccountResourceIT.TEST_USER_LOGIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the PreferResource REST controller.
 *
 * @see PreferResource
 */
@AutoConfigureMockMvc
@WithMockUser(value = "admin")
@SpringBootTest(classes = JhdApp.class)
public class PreferResourceIT {
    private final Logger log = LoggerFactory.getLogger(PreferResource.class);

    private MockMvc restMockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        PreferResource preferResource = new PreferResource();
        restMockMvc = MockMvcBuilders
            .standaloneSetup(preferResource)
            .build();
    }

    /**
     * Test news
     */
    @Test
    public void testNews() throws Exception {
        MvcResult result = restMockMvc.perform(get("/api/prefer/news"))
            .andExpect(status().isOk()).andReturn();
        log.info(result.getResponse().getContentAsString());
    }
}
