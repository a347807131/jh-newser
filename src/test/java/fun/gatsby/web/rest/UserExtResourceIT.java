package fun.gatsby.web.rest;

import fun.gatsby.JhdApp;
import fun.gatsby.domain.UserExt;
import fun.gatsby.domain.User;
import fun.gatsby.repository.UserExtRepository;
import fun.gatsby.service.UserExtService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserExtResource} REST controller.
 */
@SpringBootTest(classes = JhdApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserExtResourceIT {

    private static final String DEFAULT_PTHONE = "AAAAAAAAAA";
    private static final String UPDATED_PTHONE = "BBBBBBBBBB";

    @Autowired
    private UserExtRepository userExtRepository;

    @Mock
    private UserExtRepository userExtRepositoryMock;

    @Mock
    private UserExtService userExtServiceMock;

    @Autowired
    private UserExtService userExtService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserExtMockMvc;

    private UserExt userExt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExt createEntity(EntityManager em) {
        UserExt userExt = new UserExt()
            .pthone(DEFAULT_PTHONE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userExt.setUser(user);
        return userExt;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExt createUpdatedEntity(EntityManager em) {
        UserExt userExt = new UserExt()
            .pthone(UPDATED_PTHONE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userExt.setUser(user);
        return userExt;
    }

    @BeforeEach
    public void initTest() {
        userExt = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserExt() throws Exception {
        int databaseSizeBeforeCreate = userExtRepository.findAll().size();
        // Create the UserExt
        restUserExtMockMvc.perform(post("/api/user-exts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExt)))
            .andExpect(status().isCreated());

        // Validate the UserExt in the database
        List<UserExt> userExtList = userExtRepository.findAll();
        assertThat(userExtList).hasSize(databaseSizeBeforeCreate + 1);
        UserExt testUserExt = userExtList.get(userExtList.size() - 1);
        assertThat(testUserExt.getPthone()).isEqualTo(DEFAULT_PTHONE);

        // Validate the id for MapsId, the ids must be same
        assertThat(testUserExt.getId()).isEqualTo(testUserExt.getUser().getId());
    }

    @Test
    @Transactional
    public void createUserExtWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userExtRepository.findAll().size();

        // Create the UserExt with an existing ID
        userExt.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExtMockMvc.perform(post("/api/user-exts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExt)))
            .andExpect(status().isBadRequest());

        // Validate the UserExt in the database
        List<UserExt> userExtList = userExtRepository.findAll();
        assertThat(userExtList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateUserExtMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        userExtService.save(userExt);
        int databaseSizeBeforeCreate = userExtRepository.findAll().size();

        // Add a new parent entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();

        // Load the userExt
        UserExt updatedUserExt = userExtRepository.findById(userExt.getId()).get();
        // Disconnect from session so that the updates on updatedUserExt are not directly saved in db
        em.detach(updatedUserExt);

        // Update the User with new association value
        updatedUserExt.setUser(user);

        // Update the entity
        restUserExtMockMvc.perform(put("/api/user-exts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserExt)))
            .andExpect(status().isOk());

        // Validate the UserExt in the database
        List<UserExt> userExtList = userExtRepository.findAll();
        assertThat(userExtList).hasSize(databaseSizeBeforeCreate);
        UserExt testUserExt = userExtList.get(userExtList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testUserExt.getId()).isEqualTo(testUserExt.getUser().getId());
    }

    @Test
    @Transactional
    public void getAllUserExts() throws Exception {
        // Initialize the database
        userExtRepository.saveAndFlush(userExt);

        // Get all the userExtList
        restUserExtMockMvc.perform(get("/api/user-exts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExt.getId().intValue())))
            .andExpect(jsonPath("$.[*].pthone").value(hasItem(DEFAULT_PTHONE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllUserExtsWithEagerRelationshipsIsEnabled() throws Exception {
        when(userExtServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserExtMockMvc.perform(get("/api/user-exts?eagerload=true"))
            .andExpect(status().isOk());

        verify(userExtServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUserExtsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(userExtServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserExtMockMvc.perform(get("/api/user-exts?eagerload=true"))
            .andExpect(status().isOk());

        verify(userExtServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUserExt() throws Exception {
        // Initialize the database
        userExtRepository.saveAndFlush(userExt);

        // Get the userExt
        restUserExtMockMvc.perform(get("/api/user-exts/{id}", userExt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userExt.getId().intValue()))
            .andExpect(jsonPath("$.pthone").value(DEFAULT_PTHONE));
    }
    @Test
    @Transactional
    public void getNonExistingUserExt() throws Exception {
        // Get the userExt
        restUserExtMockMvc.perform(get("/api/user-exts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserExt() throws Exception {
        // Initialize the database
        userExtService.save(userExt);

        int databaseSizeBeforeUpdate = userExtRepository.findAll().size();

        // Update the userExt
        UserExt updatedUserExt = userExtRepository.findById(userExt.getId()).get();
        // Disconnect from session so that the updates on updatedUserExt are not directly saved in db
        em.detach(updatedUserExt);
        updatedUserExt
            .pthone(UPDATED_PTHONE);

        restUserExtMockMvc.perform(put("/api/user-exts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserExt)))
            .andExpect(status().isOk());

        // Validate the UserExt in the database
        List<UserExt> userExtList = userExtRepository.findAll();
        assertThat(userExtList).hasSize(databaseSizeBeforeUpdate);
        UserExt testUserExt = userExtList.get(userExtList.size() - 1);
        assertThat(testUserExt.getPthone()).isEqualTo(UPDATED_PTHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserExt() throws Exception {
        int databaseSizeBeforeUpdate = userExtRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtMockMvc.perform(put("/api/user-exts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExt)))
            .andExpect(status().isBadRequest());

        // Validate the UserExt in the database
        List<UserExt> userExtList = userExtRepository.findAll();
        assertThat(userExtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserExt() throws Exception {
        // Initialize the database
        userExtService.save(userExt);

        int databaseSizeBeforeDelete = userExtRepository.findAll().size();

        // Delete the userExt
        restUserExtMockMvc.perform(delete("/api/user-exts/{id}", userExt.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserExt> userExtList = userExtRepository.findAll();
        assertThat(userExtList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
