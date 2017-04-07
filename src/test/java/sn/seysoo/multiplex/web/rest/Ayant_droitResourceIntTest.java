package sn.seysoo.multiplex.web.rest;

import sn.seysoo.multiplex.MutuplexApp;

import sn.seysoo.multiplex.domain.Ayant_droit;
import sn.seysoo.multiplex.repository.Ayant_droitRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Ayant_droitResource REST controller.
 *
 * @see Ayant_droitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MutuplexApp.class)
public class Ayant_droitResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAA";
    private static final String UPDATED_NOM = "BBBBB";

    private static final String DEFAULT_PRENOM = "AAAAA";
    private static final String UPDATED_PRENOM = "BBBBB";

    private static final String DEFAULT_SEXE = "AAAAA";
    private static final String UPDATED_SEXE = "BBBBB";

    private static final String DEFAULT_LIEN_PARENT = "AAAAA";
    private static final String UPDATED_LIEN_PARENT = "BBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private Ayant_droitRepository ayant_droitRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAyant_droitMockMvc;

    private Ayant_droit ayant_droit;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Ayant_droitResource ayant_droitResource = new Ayant_droitResource();
        ReflectionTestUtils.setField(ayant_droitResource, "ayant_droitRepository", ayant_droitRepository);
        this.restAyant_droitMockMvc = MockMvcBuilders.standaloneSetup(ayant_droitResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ayant_droit createEntity(EntityManager em) {
        Ayant_droit ayant_droit = new Ayant_droit()
                .nom(DEFAULT_NOM)
                .prenom(DEFAULT_PRENOM)
                .sexe(DEFAULT_SEXE)
                .lien_parent(DEFAULT_LIEN_PARENT)
                .date_naissance(DEFAULT_DATE_NAISSANCE);
        return ayant_droit;
    }

    @Before
    public void initTest() {
        ayant_droit = createEntity(em);
    }

    @Test
    @Transactional
    public void createAyant_droit() throws Exception {
        int databaseSizeBeforeCreate = ayant_droitRepository.findAll().size();

        // Create the Ayant_droit

        restAyant_droitMockMvc.perform(post("/api/ayant-droits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ayant_droit)))
                .andExpect(status().isCreated());

        // Validate the Ayant_droit in the database
        List<Ayant_droit> ayant_droits = ayant_droitRepository.findAll();
        assertThat(ayant_droits).hasSize(databaseSizeBeforeCreate + 1);
        Ayant_droit testAyant_droit = ayant_droits.get(ayant_droits.size() - 1);
        assertThat(testAyant_droit.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAyant_droit.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testAyant_droit.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testAyant_droit.getLien_parent()).isEqualTo(DEFAULT_LIEN_PARENT);
        assertThat(testAyant_droit.getDate_naissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = ayant_droitRepository.findAll().size();
        // set the field null
        ayant_droit.setPrenom(null);

        // Create the Ayant_droit, which fails.

        restAyant_droitMockMvc.perform(post("/api/ayant-droits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ayant_droit)))
                .andExpect(status().isBadRequest());

        List<Ayant_droit> ayant_droits = ayant_droitRepository.findAll();
        assertThat(ayant_droits).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ayant_droitRepository.findAll().size();
        // set the field null
        ayant_droit.setSexe(null);

        // Create the Ayant_droit, which fails.

        restAyant_droitMockMvc.perform(post("/api/ayant-droits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ayant_droit)))
                .andExpect(status().isBadRequest());

        List<Ayant_droit> ayant_droits = ayant_droitRepository.findAll();
        assertThat(ayant_droits).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAyant_droits() throws Exception {
        // Initialize the database
        ayant_droitRepository.saveAndFlush(ayant_droit);

        // Get all the ayant_droits
        restAyant_droitMockMvc.perform(get("/api/ayant-droits?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ayant_droit.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
                .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
                .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
                .andExpect(jsonPath("$.[*].lien_parent").value(hasItem(DEFAULT_LIEN_PARENT.toString())))
                .andExpect(jsonPath("$.[*].date_naissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())));
    }

    @Test
    @Transactional
    public void getAyant_droit() throws Exception {
        // Initialize the database
        ayant_droitRepository.saveAndFlush(ayant_droit);

        // Get the ayant_droit
        restAyant_droitMockMvc.perform(get("/api/ayant-droits/{id}", ayant_droit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ayant_droit.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.lien_parent").value(DEFAULT_LIEN_PARENT.toString()))
            .andExpect(jsonPath("$.date_naissance").value(DEFAULT_DATE_NAISSANCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAyant_droit() throws Exception {
        // Get the ayant_droit
        restAyant_droitMockMvc.perform(get("/api/ayant-droits/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAyant_droit() throws Exception {
        // Initialize the database
        ayant_droitRepository.saveAndFlush(ayant_droit);
        int databaseSizeBeforeUpdate = ayant_droitRepository.findAll().size();

        // Update the ayant_droit
        Ayant_droit updatedAyant_droit = ayant_droitRepository.findOne(ayant_droit.getId());
        updatedAyant_droit
                .nom(UPDATED_NOM)
                .prenom(UPDATED_PRENOM)
                .sexe(UPDATED_SEXE)
                .lien_parent(UPDATED_LIEN_PARENT)
                .date_naissance(UPDATED_DATE_NAISSANCE);

        restAyant_droitMockMvc.perform(put("/api/ayant-droits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAyant_droit)))
                .andExpect(status().isOk());

        // Validate the Ayant_droit in the database
        List<Ayant_droit> ayant_droits = ayant_droitRepository.findAll();
        assertThat(ayant_droits).hasSize(databaseSizeBeforeUpdate);
        Ayant_droit testAyant_droit = ayant_droits.get(ayant_droits.size() - 1);
        assertThat(testAyant_droit.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAyant_droit.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testAyant_droit.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testAyant_droit.getLien_parent()).isEqualTo(UPDATED_LIEN_PARENT);
        assertThat(testAyant_droit.getDate_naissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void deleteAyant_droit() throws Exception {
        // Initialize the database
        ayant_droitRepository.saveAndFlush(ayant_droit);
        int databaseSizeBeforeDelete = ayant_droitRepository.findAll().size();

        // Get the ayant_droit
        restAyant_droitMockMvc.perform(delete("/api/ayant-droits/{id}", ayant_droit.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Ayant_droit> ayant_droits = ayant_droitRepository.findAll();
        assertThat(ayant_droits).hasSize(databaseSizeBeforeDelete - 1);
    }
}
