package fun.gatsby.web.rest;

import fun.gatsby.JhdApp;
import fun.gatsby.domain.Tops;
import fun.gatsby.repository.TopsRepository;
import fun.gatsby.service.TopsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TopsResource} REST controller.
 */
@SpringBootTest(classes = JhdApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TopsResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_SEQ = 1;
    private static final Integer UPDATED_SEQ = 2;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private TopsRepository topsRepository;

    @Autowired
    private TopsService topsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopsMockMvc;

    private Tops tops;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tops createEntity(EntityManager em) {
        Tops tops = new Tops()
            .title(DEFAULT_TITLE)
            .link(DEFAULT_LINK)
            .source(DEFAULT_SOURCE)
            .time(DEFAULT_TIME)
            .seq(DEFAULT_SEQ)
            .note(DEFAULT_NOTE);
        return tops;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tops createUpdatedEntity(EntityManager em) {
        Tops tops = new Tops()
            .title(UPDATED_TITLE)
            .link(UPDATED_LINK)
            .source(UPDATED_SOURCE)
            .time(UPDATED_TIME)
            .seq(UPDATED_SEQ)
            .note(UPDATED_NOTE);
        return tops;
    }

    @BeforeEach
    public void initTest() {
        tops = createEntity(em);
    }

    @Test
    @Transactional
    public void createTops() throws Exception {
        int databaseSizeBeforeCreate = topsRepository.findAll().size();
        // Create the Tops
        restTopsMockMvc.perform(post("/api/tops").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tops)))
            .andExpect(status().isCreated());

        // Validate the Tops in the database
        List<Tops> topsList = topsRepository.findAll();
        assertThat(topsList).hasSize(databaseSizeBeforeCreate + 1);
        Tops testTops = topsList.get(topsList.size() - 1);
        assertThat(testTops.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTops.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testTops.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testTops.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testTops.getSeq()).isEqualTo(DEFAULT_SEQ);
        assertThat(testTops.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createTopsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topsRepository.findAll().size();

        // Create the Tops with an existing ID
        tops.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopsMockMvc.perform(post("/api/tops").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tops)))
            .andExpect(status().isBadRequest());

        // Validate the Tops in the database
        List<Tops> topsList = topsRepository.findAll();
        assertThat(topsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTops() throws Exception {
        // Initialize the database
        topsRepository.saveAndFlush(tops);

        // Get all the topsList
        restTopsMockMvc.perform(get("/api/tops?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tops.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getTops() throws Exception {
        // Initialize the database
        topsRepository.saveAndFlush(tops);

        // Get the tops
        restTopsMockMvc.perform(get("/api/tops/{id}", tops.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tops.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.seq").value(DEFAULT_SEQ))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingTops() throws Exception {
        // Get the tops
        restTopsMockMvc.perform(get("/api/tops/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTops() throws Exception {
        // Initialize the database
        topsService.save(tops);

        int databaseSizeBeforeUpdate = topsRepository.findAll().size();

        // Update the tops
        Tops updatedTops = topsRepository.findById(tops.getId()).get();
        // Disconnect from session so that the updates on updatedTops are not directly saved in db
        em.detach(updatedTops);
        updatedTops
            .title(UPDATED_TITLE)
            .link(UPDATED_LINK)
            .source(UPDATED_SOURCE)
            .time(UPDATED_TIME)
            .seq(UPDATED_SEQ)
            .note(UPDATED_NOTE);

        restTopsMockMvc.perform(put("/api/tops").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTops)))
            .andExpect(status().isOk());

        // Validate the Tops in the database
        List<Tops> topsList = topsRepository.findAll();
        assertThat(topsList).hasSize(databaseSizeBeforeUpdate);
        Tops testTops = topsList.get(topsList.size() - 1);
        assertThat(testTops.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTops.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testTops.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testTops.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testTops.getSeq()).isEqualTo(UPDATED_SEQ);
        assertThat(testTops.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingTops() throws Exception {
        int databaseSizeBeforeUpdate = topsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopsMockMvc.perform(put("/api/tops").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tops)))
            .andExpect(status().isBadRequest());

        // Validate the Tops in the database
        List<Tops> topsList = topsRepository.findAll();
        assertThat(topsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTops() throws Exception {
        // Initialize the database
        topsService.save(tops);

        int databaseSizeBeforeDelete = topsRepository.findAll().size();

        // Delete the tops
        restTopsMockMvc.perform(delete("/api/tops/{id}", tops.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tops> topsList = topsRepository.findAll();
        assertThat(topsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
