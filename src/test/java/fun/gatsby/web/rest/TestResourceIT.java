package fun.gatsby.web.rest;

import fun.gatsby.JhdApp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the TestResource REST controller.
 *
 * @see TestResource
 */
@SpringBootTest(classes = JhdApp.class)
public class TestResourceIT {

    private MockMvc restMockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        TestResource testResource = new TestResource();
        restMockMvc = MockMvcBuilders
            .standaloneSetup(testResource)
            .build();
    }

    /**
     * Test demo
     */
    @Test
    public void testDemo() throws Exception {
        restMockMvc.perform(get("/api/test/demo"))
            .andExpect(status().isOk());
    }
}
