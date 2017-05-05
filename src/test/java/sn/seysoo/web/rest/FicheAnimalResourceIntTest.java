package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.FicheAnimal;
import sn.seysoo.repository.FicheAnimalRepository;
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

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static sn.seysoo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FicheAnimalResource REST controller.
 *
 * @see FicheAnimalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class FicheAnimalResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE_DEC = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_DEC = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_NAIS = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_NAIS = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LIEU_NAIS = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_NAIS = "BBBBBBBBBB";

    @Autowired
    private FicheAnimalRepository ficheAnimalRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restFicheAnimalMockMvc;

    private FicheAnimal ficheAnimal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FicheAnimalResource ficheAnimalResource = new FicheAnimalResource(ficheAnimalRepository);
        this.restFicheAnimalMockMvc = MockMvcBuilders.standaloneSetup(ficheAnimalResource)
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
    public static FicheAnimal createEntity() {
        FicheAnimal ficheAnimal = new FicheAnimal()
            .dateDec(DEFAULT_DATE_DEC)
            .dateNais(DEFAULT_DATE_NAIS)
            .lieuNais(DEFAULT_LIEU_NAIS);
        return ficheAnimal;
    }

    @Before
    public void initTest() {
        ficheAnimalRepository.deleteAll();
        ficheAnimal = createEntity();
    }

    @Test
    public void createFicheAnimal() throws Exception {
        int databaseSizeBeforeCreate = ficheAnimalRepository.findAll().size();

        // Create the FicheAnimal
        restFicheAnimalMockMvc.perform(post("/api/fiche-animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheAnimal)))
            .andExpect(status().isCreated());

        // Validate the FicheAnimal in the database
        List<FicheAnimal> ficheAnimalList = ficheAnimalRepository.findAll();
        assertThat(ficheAnimalList).hasSize(databaseSizeBeforeCreate + 1);
        FicheAnimal testFicheAnimal = ficheAnimalList.get(ficheAnimalList.size() - 1);
        assertThat(testFicheAnimal.getDateDec()).isEqualTo(DEFAULT_DATE_DEC);
        assertThat(testFicheAnimal.getDateNais()).isEqualTo(DEFAULT_DATE_NAIS);
        assertThat(testFicheAnimal.getLieuNais()).isEqualTo(DEFAULT_LIEU_NAIS);
    }

    @Test
    public void createFicheAnimalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ficheAnimalRepository.findAll().size();

        // Create the FicheAnimal with an existing ID
        ficheAnimal.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFicheAnimalMockMvc.perform(post("/api/fiche-animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheAnimal)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FicheAnimal> ficheAnimalList = ficheAnimalRepository.findAll();
        assertThat(ficheAnimalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkDateDecIsRequired() throws Exception {
        int databaseSizeBeforeTest = ficheAnimalRepository.findAll().size();
        // set the field null
        ficheAnimal.setDateDec(null);

        // Create the FicheAnimal, which fails.

        restFicheAnimalMockMvc.perform(post("/api/fiche-animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheAnimal)))
            .andExpect(status().isBadRequest());

        List<FicheAnimal> ficheAnimalList = ficheAnimalRepository.findAll();
        assertThat(ficheAnimalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDateNaisIsRequired() throws Exception {
        int databaseSizeBeforeTest = ficheAnimalRepository.findAll().size();
        // set the field null
        ficheAnimal.setDateNais(null);

        // Create the FicheAnimal, which fails.

        restFicheAnimalMockMvc.perform(post("/api/fiche-animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheAnimal)))
            .andExpect(status().isBadRequest());

        List<FicheAnimal> ficheAnimalList = ficheAnimalRepository.findAll();
        assertThat(ficheAnimalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLieuNaisIsRequired() throws Exception {
        int databaseSizeBeforeTest = ficheAnimalRepository.findAll().size();
        // set the field null
        ficheAnimal.setLieuNais(null);

        // Create the FicheAnimal, which fails.

        restFicheAnimalMockMvc.perform(post("/api/fiche-animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheAnimal)))
            .andExpect(status().isBadRequest());

        List<FicheAnimal> ficheAnimalList = ficheAnimalRepository.findAll();
        assertThat(ficheAnimalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFicheAnimals() throws Exception {
        // Initialize the database
        ficheAnimalRepository.save(ficheAnimal);

        // Get all the ficheAnimalList
        restFicheAnimalMockMvc.perform(get("/api/fiche-animals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ficheAnimal.getId())))
            .andExpect(jsonPath("$.[*].dateDec").value(hasItem(sameInstant(DEFAULT_DATE_DEC))))
            .andExpect(jsonPath("$.[*].dateNais").value(hasItem(sameInstant(DEFAULT_DATE_NAIS))))
            .andExpect(jsonPath("$.[*].lieuNais").value(hasItem(DEFAULT_LIEU_NAIS.toString())));
    }

    @Test
    public void getFicheAnimal() throws Exception {
        // Initialize the database
        ficheAnimalRepository.save(ficheAnimal);

        // Get the ficheAnimal
        restFicheAnimalMockMvc.perform(get("/api/fiche-animals/{id}", ficheAnimal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ficheAnimal.getId()))
            .andExpect(jsonPath("$.dateDec").value(sameInstant(DEFAULT_DATE_DEC)))
            .andExpect(jsonPath("$.dateNais").value(sameInstant(DEFAULT_DATE_NAIS)))
            .andExpect(jsonPath("$.lieuNais").value(DEFAULT_LIEU_NAIS.toString()));
    }

    @Test
    public void getNonExistingFicheAnimal() throws Exception {
        // Get the ficheAnimal
        restFicheAnimalMockMvc.perform(get("/api/fiche-animals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFicheAnimal() throws Exception {
        // Initialize the database
        ficheAnimalRepository.save(ficheAnimal);
        int databaseSizeBeforeUpdate = ficheAnimalRepository.findAll().size();

        // Update the ficheAnimal
        FicheAnimal updatedFicheAnimal = ficheAnimalRepository.findOne(ficheAnimal.getId());
        updatedFicheAnimal
            .dateDec(UPDATED_DATE_DEC)
            .dateNais(UPDATED_DATE_NAIS)
            .lieuNais(UPDATED_LIEU_NAIS);

        restFicheAnimalMockMvc.perform(put("/api/fiche-animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFicheAnimal)))
            .andExpect(status().isOk());

        // Validate the FicheAnimal in the database
        List<FicheAnimal> ficheAnimalList = ficheAnimalRepository.findAll();
        assertThat(ficheAnimalList).hasSize(databaseSizeBeforeUpdate);
        FicheAnimal testFicheAnimal = ficheAnimalList.get(ficheAnimalList.size() - 1);
        assertThat(testFicheAnimal.getDateDec()).isEqualTo(UPDATED_DATE_DEC);
        assertThat(testFicheAnimal.getDateNais()).isEqualTo(UPDATED_DATE_NAIS);
        assertThat(testFicheAnimal.getLieuNais()).isEqualTo(UPDATED_LIEU_NAIS);
    }

    @Test
    public void updateNonExistingFicheAnimal() throws Exception {
        int databaseSizeBeforeUpdate = ficheAnimalRepository.findAll().size();

        // Create the FicheAnimal

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFicheAnimalMockMvc.perform(put("/api/fiche-animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheAnimal)))
            .andExpect(status().isCreated());

        // Validate the FicheAnimal in the database
        List<FicheAnimal> ficheAnimalList = ficheAnimalRepository.findAll();
        assertThat(ficheAnimalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFicheAnimal() throws Exception {
        // Initialize the database
        ficheAnimalRepository.save(ficheAnimal);
        int databaseSizeBeforeDelete = ficheAnimalRepository.findAll().size();

        // Get the ficheAnimal
        restFicheAnimalMockMvc.perform(delete("/api/fiche-animals/{id}", ficheAnimal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FicheAnimal> ficheAnimalList = ficheAnimalRepository.findAll();
        assertThat(ficheAnimalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FicheAnimal.class);
    }
}
