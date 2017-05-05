package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.AvisPerte;
import sn.seysoo.repository.AvisPerteRepository;
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
 * Test class for the AvisPerteResource REST controller.
 *
 * @see AvisPerteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class AvisPerteResourceIntTest {

    private static final String DEFAULT_ANIMAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_ANIMAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_ANIMALPER = "AAAAAAAAAA";
    private static final String UPDATED_NOM_ANIMALPER = "BBBBBBBBBB";

    @Autowired
    private AvisPerteRepository avisPerteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAvisPerteMockMvc;

    private AvisPerte avisPerte;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AvisPerteResource avisPerteResource = new AvisPerteResource(avisPerteRepository);
        this.restAvisPerteMockMvc = MockMvcBuilders.standaloneSetup(avisPerteResource)
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
    public static AvisPerte createEntity() {
        AvisPerte avisPerte = new AvisPerte()
            .animalId(DEFAULT_ANIMAL_ID)
            .nomAnimalper(DEFAULT_NOM_ANIMALPER);
        return avisPerte;
    }

    @Before
    public void initTest() {
        avisPerteRepository.deleteAll();
        avisPerte = createEntity();
    }

    @Test
    public void createAvisPerte() throws Exception {
        int databaseSizeBeforeCreate = avisPerteRepository.findAll().size();

        // Create the AvisPerte
        restAvisPerteMockMvc.perform(post("/api/avis-pertes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avisPerte)))
            .andExpect(status().isCreated());

        // Validate the AvisPerte in the database
        List<AvisPerte> avisPerteList = avisPerteRepository.findAll();
        assertThat(avisPerteList).hasSize(databaseSizeBeforeCreate + 1);
        AvisPerte testAvisPerte = avisPerteList.get(avisPerteList.size() - 1);
        assertThat(testAvisPerte.getAnimalId()).isEqualTo(DEFAULT_ANIMAL_ID);
        assertThat(testAvisPerte.getNomAnimalper()).isEqualTo(DEFAULT_NOM_ANIMALPER);
    }

    @Test
    public void createAvisPerteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avisPerteRepository.findAll().size();

        // Create the AvisPerte with an existing ID
        avisPerte.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvisPerteMockMvc.perform(post("/api/avis-pertes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avisPerte)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AvisPerte> avisPerteList = avisPerteRepository.findAll();
        assertThat(avisPerteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkAnimalIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisPerteRepository.findAll().size();
        // set the field null
        avisPerte.setAnimalId(null);

        // Create the AvisPerte, which fails.

        restAvisPerteMockMvc.perform(post("/api/avis-pertes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avisPerte)))
            .andExpect(status().isBadRequest());

        List<AvisPerte> avisPerteList = avisPerteRepository.findAll();
        assertThat(avisPerteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNomAnimalperIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisPerteRepository.findAll().size();
        // set the field null
        avisPerte.setNomAnimalper(null);

        // Create the AvisPerte, which fails.

        restAvisPerteMockMvc.perform(post("/api/avis-pertes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avisPerte)))
            .andExpect(status().isBadRequest());

        List<AvisPerte> avisPerteList = avisPerteRepository.findAll();
        assertThat(avisPerteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAvisPertes() throws Exception {
        // Initialize the database
        avisPerteRepository.save(avisPerte);

        // Get all the avisPerteList
        restAvisPerteMockMvc.perform(get("/api/avis-pertes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avisPerte.getId())))
            .andExpect(jsonPath("$.[*].animalId").value(hasItem(DEFAULT_ANIMAL_ID.toString())))
            .andExpect(jsonPath("$.[*].nomAnimalper").value(hasItem(DEFAULT_NOM_ANIMALPER.toString())));
    }

    @Test
    public void getAvisPerte() throws Exception {
        // Initialize the database
        avisPerteRepository.save(avisPerte);

        // Get the avisPerte
        restAvisPerteMockMvc.perform(get("/api/avis-pertes/{id}", avisPerte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(avisPerte.getId()))
            .andExpect(jsonPath("$.animalId").value(DEFAULT_ANIMAL_ID.toString()))
            .andExpect(jsonPath("$.nomAnimalper").value(DEFAULT_NOM_ANIMALPER.toString()));
    }

    @Test
    public void getNonExistingAvisPerte() throws Exception {
        // Get the avisPerte
        restAvisPerteMockMvc.perform(get("/api/avis-pertes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAvisPerte() throws Exception {
        // Initialize the database
        avisPerteRepository.save(avisPerte);
        int databaseSizeBeforeUpdate = avisPerteRepository.findAll().size();

        // Update the avisPerte
        AvisPerte updatedAvisPerte = avisPerteRepository.findOne(avisPerte.getId());
        updatedAvisPerte
            .animalId(UPDATED_ANIMAL_ID)
            .nomAnimalper(UPDATED_NOM_ANIMALPER);

        restAvisPerteMockMvc.perform(put("/api/avis-pertes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvisPerte)))
            .andExpect(status().isOk());

        // Validate the AvisPerte in the database
        List<AvisPerte> avisPerteList = avisPerteRepository.findAll();
        assertThat(avisPerteList).hasSize(databaseSizeBeforeUpdate);
        AvisPerte testAvisPerte = avisPerteList.get(avisPerteList.size() - 1);
        assertThat(testAvisPerte.getAnimalId()).isEqualTo(UPDATED_ANIMAL_ID);
        assertThat(testAvisPerte.getNomAnimalper()).isEqualTo(UPDATED_NOM_ANIMALPER);
    }

    @Test
    public void updateNonExistingAvisPerte() throws Exception {
        int databaseSizeBeforeUpdate = avisPerteRepository.findAll().size();

        // Create the AvisPerte

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAvisPerteMockMvc.perform(put("/api/avis-pertes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avisPerte)))
            .andExpect(status().isCreated());

        // Validate the AvisPerte in the database
        List<AvisPerte> avisPerteList = avisPerteRepository.findAll();
        assertThat(avisPerteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteAvisPerte() throws Exception {
        // Initialize the database
        avisPerteRepository.save(avisPerte);
        int databaseSizeBeforeDelete = avisPerteRepository.findAll().size();

        // Get the avisPerte
        restAvisPerteMockMvc.perform(delete("/api/avis-pertes/{id}", avisPerte.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AvisPerte> avisPerteList = avisPerteRepository.findAll();
        assertThat(avisPerteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvisPerte.class);
    }
}
