package sn.seysoo.multiplex.web.rest;

import sn.seysoo.multiplex.MutuplexApp;

import sn.seysoo.multiplex.domain.Groupe;
import sn.seysoo.multiplex.repository.GroupeRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GroupeResource REST controller.
 *
 * @see GroupeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MutuplexApp.class)
public class GroupeResourceIntTest {

    private static final String DEFAULT_INTITULE = "AAAAA";
    private static final String UPDATED_INTITULE = "BBBBB";

    @Inject
    private GroupeRepository groupeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restGroupeMockMvc;

    private Groupe groupe;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GroupeResource groupeResource = new GroupeResource();
        ReflectionTestUtils.setField(groupeResource, "groupeRepository", groupeRepository);
        this.restGroupeMockMvc = MockMvcBuilders.standaloneSetup(groupeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Groupe createEntity(EntityManager em) {
        Groupe groupe = new Groupe()
                .intitule(DEFAULT_INTITULE);
        return groupe;
    }

    @Before
    public void initTest() {
        groupe = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupe() throws Exception {
        int databaseSizeBeforeCreate = groupeRepository.findAll().size();

        // Create the Groupe

        restGroupeMockMvc.perform(post("/api/groupes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(groupe)))
                .andExpect(status().isCreated());

        // Validate the Groupe in the database
        List<Groupe> groupes = groupeRepository.findAll();
        assertThat(groupes).hasSize(databaseSizeBeforeCreate + 1);
        Groupe testGroupe = groupes.get(groupes.size() - 1);
        assertThat(testGroupe.getIntitule()).isEqualTo(DEFAULT_INTITULE);
    }

    @Test
    @Transactional
    public void getAllGroupes() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        // Get all the groupes
        restGroupeMockMvc.perform(get("/api/groupes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(groupe.getId().intValue())))
                .andExpect(jsonPath("$.[*].intitule").value(hasItem(DEFAULT_INTITULE.toString())));
    }

    @Test
    @Transactional
    public void getGroupe() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        // Get the groupe
        restGroupeMockMvc.perform(get("/api/groupes/{id}", groupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(groupe.getId().intValue()))
            .andExpect(jsonPath("$.intitule").value(DEFAULT_INTITULE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGroupe() throws Exception {
        // Get the groupe
        restGroupeMockMvc.perform(get("/api/groupes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupe() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);
        int databaseSizeBeforeUpdate = groupeRepository.findAll().size();

        // Update the groupe
        Groupe updatedGroupe = groupeRepository.findOne(groupe.getId());
        updatedGroupe
                .intitule(UPDATED_INTITULE);

        restGroupeMockMvc.perform(put("/api/groupes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedGroupe)))
                .andExpect(status().isOk());

        // Validate the Groupe in the database
        List<Groupe> groupes = groupeRepository.findAll();
        assertThat(groupes).hasSize(databaseSizeBeforeUpdate);
        Groupe testGroupe = groupes.get(groupes.size() - 1);
        assertThat(testGroupe.getIntitule()).isEqualTo(UPDATED_INTITULE);
    }

    @Test
    @Transactional
    public void deleteGroupe() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);
        int databaseSizeBeforeDelete = groupeRepository.findAll().size();

        // Get the groupe
        restGroupeMockMvc.perform(delete("/api/groupes/{id}", groupe.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Groupe> groupes = groupeRepository.findAll();
        assertThat(groupes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
