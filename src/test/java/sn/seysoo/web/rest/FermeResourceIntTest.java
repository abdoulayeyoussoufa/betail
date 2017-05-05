package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.Ferme;
import sn.seysoo.repository.FermeRepository;
import sn.seysoo.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FermeResource REST controller.
 *
 * @see FermeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class FermeResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_SUPERFICIE = "AAAAAAAAAA";
    private static final String UPDATED_SUPERFICIE = "BBBBBBBBBB";

    @Autowired
    private FermeRepository fermeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restFermeMockMvc;

    private Ferme ferme;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FermeResource fermeResource = new FermeResource(fermeRepository);
        this.restFermeMockMvc = MockMvcBuilders.standaloneSetup(fermeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ferme createEntity() {
        Ferme ferme = new Ferme()
            .nom(DEFAULT_NOM)
            .superficie(DEFAULT_SUPERFICIE);
        return ferme;
    }

    @Before
    public void initTest() {
        fermeRepository.deleteAll();
        ferme = createEntity();
    }

    @Test
    public void createFerme() throws Exception {
        int databaseSizeBeforeCreate = fermeRepository.findAll().size();

        // Create the Ferme
        restFermeMockMvc.perform(post("/api/fermes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ferme)))
            .andExpect(status().isCreated());

        // Validate the Ferme in the database
        List<Ferme> fermeList = fermeRepository.findAll();
        assertThat(fermeList).hasSize(databaseSizeBeforeCreate + 1);
        Ferme testFerme = fermeList.get(fermeList.size() - 1);
        assertThat(testFerme.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testFerme.getSuperficie()).isEqualTo(DEFAULT_SUPERFICIE);
    }

    @Test
    public void createFermeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fermeRepository.findAll().size();

        // Create the Ferme with an existing ID
        ferme.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFermeMockMvc.perform(post("/api/fermes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ferme)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Ferme> fermeList = fermeRepository.findAll();
        assertThat(fermeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = fermeRepository.findAll().size();
        // set the field null
        ferme.setNom(null);

        // Create the Ferme, which fails.

        restFermeMockMvc.perform(post("/api/fermes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ferme)))
            .andExpect(status().isBadRequest());

        List<Ferme> fermeList = fermeRepository.findAll();
        assertThat(fermeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSuperficieIsRequired() throws Exception {
        int databaseSizeBeforeTest = fermeRepository.findAll().size();
        // set the field null
        ferme.setSuperficie(null);

        // Create the Ferme, which fails.

        restFermeMockMvc.perform(post("/api/fermes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ferme)))
            .andExpect(status().isBadRequest());

        List<Ferme> fermeList = fermeRepository.findAll();
        assertThat(fermeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFermes() throws Exception {
        // Initialize the database
        fermeRepository.save(ferme);

        // Get all the fermeList
        restFermeMockMvc.perform(get("/api/fermes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ferme.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].superficie").value(hasItem(DEFAULT_SUPERFICIE.toString())));
    }

    @Test
    public void getFerme() throws Exception {
        // Initialize the database
        fermeRepository.save(ferme);

        // Get the ferme
        restFermeMockMvc.perform(get("/api/fermes/{id}", ferme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ferme.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.superficie").value(DEFAULT_SUPERFICIE.toString()));
    }

    @Test
    public void getNonExistingFerme() throws Exception {
        // Get the ferme
        restFermeMockMvc.perform(get("/api/fermes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFerme() throws Exception {
        // Initialize the database
        fermeRepository.save(ferme);
        int databaseSizeBeforeUpdate = fermeRepository.findAll().size();

        // Update the ferme
        Ferme updatedFerme = fermeRepository.findOne(ferme.getId());
        updatedFerme
            .nom(UPDATED_NOM)
            .superficie(UPDATED_SUPERFICIE);

        restFermeMockMvc.perform(put("/api/fermes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFerme)))
            .andExpect(status().isOk());

        // Validate the Ferme in the database
        List<Ferme> fermeList = fermeRepository.findAll();
        assertThat(fermeList).hasSize(databaseSizeBeforeUpdate);
        Ferme testFerme = fermeList.get(fermeList.size() - 1);
        assertThat(testFerme.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testFerme.getSuperficie()).isEqualTo(UPDATED_SUPERFICIE);
    }

    @Test
    public void updateNonExistingFerme() throws Exception {
        int databaseSizeBeforeUpdate = fermeRepository.findAll().size();

        // Create the Ferme

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFermeMockMvc.perform(put("/api/fermes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ferme)))
            .andExpect(status().isCreated());

        // Validate the Ferme in the database
        List<Ferme> fermeList = fermeRepository.findAll();
        assertThat(fermeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFerme() throws Exception {
        // Initialize the database
        fermeRepository.save(ferme);
        int databaseSizeBeforeDelete = fermeRepository.findAll().size();

        // Get the ferme
        restFermeMockMvc.perform(delete("/api/fermes/{id}", ferme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ferme> fermeList = fermeRepository.findAll();
        assertThat(fermeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ferme.class);
    }
}
