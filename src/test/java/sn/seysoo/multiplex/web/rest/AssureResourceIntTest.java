package sn.seysoo.multiplex.web.rest;

import sn.seysoo.multiplex.MutuplexApp;

import sn.seysoo.multiplex.domain.Assure;
import sn.seysoo.multiplex.repository.AssureRepository;

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
 * Test class for the AssureResource REST controller.
 *
 * @see AssureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MutuplexApp.class)
public class AssureResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAA";
    private static final String UPDATED_NOM = "BBBBB";

    private static final String DEFAULT_PRENOM = "AAAAA";
    private static final String UPDATED_PRENOM = "BBBBB";

    private static final String DEFAULT_SEXE = "AAAAA";
    private static final String UPDATED_SEXE = "BBBBB";

    private static final String DEFAULT_DATE_NAISSANCE = "AAAAA";
    private static final String UPDATED_DATE_NAISSANCE = "BBBBB";

    private static final String DEFAULT_LIEU = "AAAAA";
    private static final String UPDATED_LIEU = "BBBBB";

    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    private static final String DEFAULT_TEL_F = "AAAAA";
    private static final String UPDATED_TEL_F = "BBBBB";

    private static final String DEFAULT_TEL_P = "AAAAA";
    private static final String UPDATED_TEL_P = "BBBBB";

    private static final String DEFAULT_MATRICULE = "AAAAA";
    private static final String UPDATED_MATRICULE = "BBBBB";

    @Inject
    private AssureRepository assureRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAssureMockMvc;

    private Assure assure;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssureResource assureResource = new AssureResource();
        ReflectionTestUtils.setField(assureResource, "assureRepository", assureRepository);
        this.restAssureMockMvc = MockMvcBuilders.standaloneSetup(assureResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assure createEntity(EntityManager em) {
        Assure assure = new Assure()
                .nom(DEFAULT_NOM)
                .prenom(DEFAULT_PRENOM)
                .sexe(DEFAULT_SEXE)
                .date_naissance(DEFAULT_DATE_NAISSANCE)
                .lieu(DEFAULT_LIEU)
                .email(DEFAULT_EMAIL)
                .telF(DEFAULT_TEL_F)
                .telP(DEFAULT_TEL_P)
                .matricule(DEFAULT_MATRICULE);
        return assure;
    }

    @Before
    public void initTest() {
        assure = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssure() throws Exception {
        int databaseSizeBeforeCreate = assureRepository.findAll().size();

        // Create the Assure

        restAssureMockMvc.perform(post("/api/assures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assure)))
                .andExpect(status().isCreated());

        // Validate the Assure in the database
        List<Assure> assures = assureRepository.findAll();
        assertThat(assures).hasSize(databaseSizeBeforeCreate + 1);
        Assure testAssure = assures.get(assures.size() - 1);
        assertThat(testAssure.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAssure.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testAssure.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testAssure.getDate_naissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testAssure.getLieu()).isEqualTo(DEFAULT_LIEU);
        assertThat(testAssure.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAssure.getTelF()).isEqualTo(DEFAULT_TEL_F);
        assertThat(testAssure.getTelP()).isEqualTo(DEFAULT_TEL_P);
        assertThat(testAssure.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = assureRepository.findAll().size();
        // set the field null
        assure.setNom(null);

        // Create the Assure, which fails.

        restAssureMockMvc.perform(post("/api/assures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assure)))
                .andExpect(status().isBadRequest());

        List<Assure> assures = assureRepository.findAll();
        assertThat(assures).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = assureRepository.findAll().size();
        // set the field null
        assure.setPrenom(null);

        // Create the Assure, which fails.

        restAssureMockMvc.perform(post("/api/assures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assure)))
                .andExpect(status().isBadRequest());

        List<Assure> assures = assureRepository.findAll();
        assertThat(assures).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssures() throws Exception {
        // Initialize the database
        assureRepository.saveAndFlush(assure);

        // Get all the assures
        restAssureMockMvc.perform(get("/api/assures?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(assure.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
                .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
                .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
                .andExpect(jsonPath("$.[*].date_naissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
                .andExpect(jsonPath("$.[*].lieu").value(hasItem(DEFAULT_LIEU.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].telF").value(hasItem(DEFAULT_TEL_F.toString())))
                .andExpect(jsonPath("$.[*].telP").value(hasItem(DEFAULT_TEL_P.toString())))
                .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE.toString())));
    }

    @Test
    @Transactional
    public void getAssure() throws Exception {
        // Initialize the database
        assureRepository.saveAndFlush(assure);

        // Get the assure
        restAssureMockMvc.perform(get("/api/assures/{id}", assure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assure.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.date_naissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.lieu").value(DEFAULT_LIEU.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telF").value(DEFAULT_TEL_F.toString()))
            .andExpect(jsonPath("$.telP").value(DEFAULT_TEL_P.toString()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAssure() throws Exception {
        // Get the assure
        restAssureMockMvc.perform(get("/api/assures/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssure() throws Exception {
        // Initialize the database
        assureRepository.saveAndFlush(assure);
        int databaseSizeBeforeUpdate = assureRepository.findAll().size();

        // Update the assure
        Assure updatedAssure = assureRepository.findOne(assure.getId());
        updatedAssure
                .nom(UPDATED_NOM)
                .prenom(UPDATED_PRENOM)
                .sexe(UPDATED_SEXE)
                .date_naissance(UPDATED_DATE_NAISSANCE)
                .lieu(UPDATED_LIEU)
                .email(UPDATED_EMAIL)
                .telF(UPDATED_TEL_F)
                .telP(UPDATED_TEL_P)
                .matricule(UPDATED_MATRICULE);

        restAssureMockMvc.perform(put("/api/assures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAssure)))
                .andExpect(status().isOk());

        // Validate the Assure in the database
        List<Assure> assures = assureRepository.findAll();
        assertThat(assures).hasSize(databaseSizeBeforeUpdate);
        Assure testAssure = assures.get(assures.size() - 1);
        assertThat(testAssure.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAssure.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testAssure.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testAssure.getDate_naissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testAssure.getLieu()).isEqualTo(UPDATED_LIEU);
        assertThat(testAssure.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAssure.getTelF()).isEqualTo(UPDATED_TEL_F);
        assertThat(testAssure.getTelP()).isEqualTo(UPDATED_TEL_P);
        assertThat(testAssure.getMatricule()).isEqualTo(UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void deleteAssure() throws Exception {
        // Initialize the database
        assureRepository.saveAndFlush(assure);
        int databaseSizeBeforeDelete = assureRepository.findAll().size();

        // Get the assure
        restAssureMockMvc.perform(delete("/api/assures/{id}", assure.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Assure> assures = assureRepository.findAll();
        assertThat(assures).hasSize(databaseSizeBeforeDelete - 1);
    }
}
