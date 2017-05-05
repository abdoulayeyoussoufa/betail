package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.FicheGeolocalisatio;
import sn.seysoo.repository.FicheGeolocalisatioRepository;
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
 * Test class for the FicheGeolocalisatioResource REST controller.
 *
 * @see FicheGeolocalisatioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class FicheGeolocalisatioResourceIntTest {

    private static final Float DEFAULT_LATITUDE = 1F;
    private static final Float UPDATED_LATITUDE = 2F;

    private static final Float DEFAULT_LONGITUDE = 1F;
    private static final Float UPDATED_LONGITUDE = 2F;

    private static final Float DEFAULT_PRECISION = 1F;
    private static final Float UPDATED_PRECISION = 2F;

    private static final Float DEFAULT_ALTITUDE = 1F;
    private static final Float UPDATED_ALTITUDE = 2F;

    @Autowired
    private FicheGeolocalisatioRepository ficheGeolocalisatioRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restFicheGeolocalisatioMockMvc;

    private FicheGeolocalisatio ficheGeolocalisatio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FicheGeolocalisatioResource ficheGeolocalisatioResource = new FicheGeolocalisatioResource(ficheGeolocalisatioRepository);
        this.restFicheGeolocalisatioMockMvc = MockMvcBuilders.standaloneSetup(ficheGeolocalisatioResource)
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
    public static FicheGeolocalisatio createEntity() {
        FicheGeolocalisatio ficheGeolocalisatio = new FicheGeolocalisatio()
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .precision(DEFAULT_PRECISION)
            .altitude(DEFAULT_ALTITUDE);
        return ficheGeolocalisatio;
    }

    @Before
    public void initTest() {
        ficheGeolocalisatioRepository.deleteAll();
        ficheGeolocalisatio = createEntity();
    }

    @Test
    public void createFicheGeolocalisatio() throws Exception {
        int databaseSizeBeforeCreate = ficheGeolocalisatioRepository.findAll().size();

        // Create the FicheGeolocalisatio
        restFicheGeolocalisatioMockMvc.perform(post("/api/fiche-geolocalisatios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheGeolocalisatio)))
            .andExpect(status().isCreated());

        // Validate the FicheGeolocalisatio in the database
        List<FicheGeolocalisatio> ficheGeolocalisatioList = ficheGeolocalisatioRepository.findAll();
        assertThat(ficheGeolocalisatioList).hasSize(databaseSizeBeforeCreate + 1);
        FicheGeolocalisatio testFicheGeolocalisatio = ficheGeolocalisatioList.get(ficheGeolocalisatioList.size() - 1);
        assertThat(testFicheGeolocalisatio.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testFicheGeolocalisatio.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testFicheGeolocalisatio.getPrecision()).isEqualTo(DEFAULT_PRECISION);
        assertThat(testFicheGeolocalisatio.getAltitude()).isEqualTo(DEFAULT_ALTITUDE);
    }

    @Test
    public void createFicheGeolocalisatioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ficheGeolocalisatioRepository.findAll().size();

        // Create the FicheGeolocalisatio with an existing ID
        ficheGeolocalisatio.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFicheGeolocalisatioMockMvc.perform(post("/api/fiche-geolocalisatios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheGeolocalisatio)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FicheGeolocalisatio> ficheGeolocalisatioList = ficheGeolocalisatioRepository.findAll();
        assertThat(ficheGeolocalisatioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ficheGeolocalisatioRepository.findAll().size();
        // set the field null
        ficheGeolocalisatio.setLatitude(null);

        // Create the FicheGeolocalisatio, which fails.

        restFicheGeolocalisatioMockMvc.perform(post("/api/fiche-geolocalisatios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheGeolocalisatio)))
            .andExpect(status().isBadRequest());

        List<FicheGeolocalisatio> ficheGeolocalisatioList = ficheGeolocalisatioRepository.findAll();
        assertThat(ficheGeolocalisatioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ficheGeolocalisatioRepository.findAll().size();
        // set the field null
        ficheGeolocalisatio.setLongitude(null);

        // Create the FicheGeolocalisatio, which fails.

        restFicheGeolocalisatioMockMvc.perform(post("/api/fiche-geolocalisatios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheGeolocalisatio)))
            .andExpect(status().isBadRequest());

        List<FicheGeolocalisatio> ficheGeolocalisatioList = ficheGeolocalisatioRepository.findAll();
        assertThat(ficheGeolocalisatioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFicheGeolocalisatios() throws Exception {
        // Initialize the database
        ficheGeolocalisatioRepository.save(ficheGeolocalisatio);

        // Get all the ficheGeolocalisatioList
        restFicheGeolocalisatioMockMvc.perform(get("/api/fiche-geolocalisatios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ficheGeolocalisatio.getId())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].precision").value(hasItem(DEFAULT_PRECISION.doubleValue())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE.doubleValue())));
    }

    @Test
    public void getFicheGeolocalisatio() throws Exception {
        // Initialize the database
        ficheGeolocalisatioRepository.save(ficheGeolocalisatio);

        // Get the ficheGeolocalisatio
        restFicheGeolocalisatioMockMvc.perform(get("/api/fiche-geolocalisatios/{id}", ficheGeolocalisatio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ficheGeolocalisatio.getId()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.precision").value(DEFAULT_PRECISION.doubleValue()))
            .andExpect(jsonPath("$.altitude").value(DEFAULT_ALTITUDE.doubleValue()));
    }

    @Test
    public void getNonExistingFicheGeolocalisatio() throws Exception {
        // Get the ficheGeolocalisatio
        restFicheGeolocalisatioMockMvc.perform(get("/api/fiche-geolocalisatios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFicheGeolocalisatio() throws Exception {
        // Initialize the database
        ficheGeolocalisatioRepository.save(ficheGeolocalisatio);
        int databaseSizeBeforeUpdate = ficheGeolocalisatioRepository.findAll().size();

        // Update the ficheGeolocalisatio
        FicheGeolocalisatio updatedFicheGeolocalisatio = ficheGeolocalisatioRepository.findOne(ficheGeolocalisatio.getId());
        updatedFicheGeolocalisatio
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .precision(UPDATED_PRECISION)
            .altitude(UPDATED_ALTITUDE);

        restFicheGeolocalisatioMockMvc.perform(put("/api/fiche-geolocalisatios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFicheGeolocalisatio)))
            .andExpect(status().isOk());

        // Validate the FicheGeolocalisatio in the database
        List<FicheGeolocalisatio> ficheGeolocalisatioList = ficheGeolocalisatioRepository.findAll();
        assertThat(ficheGeolocalisatioList).hasSize(databaseSizeBeforeUpdate);
        FicheGeolocalisatio testFicheGeolocalisatio = ficheGeolocalisatioList.get(ficheGeolocalisatioList.size() - 1);
        assertThat(testFicheGeolocalisatio.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testFicheGeolocalisatio.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testFicheGeolocalisatio.getPrecision()).isEqualTo(UPDATED_PRECISION);
        assertThat(testFicheGeolocalisatio.getAltitude()).isEqualTo(UPDATED_ALTITUDE);
    }

    @Test
    public void updateNonExistingFicheGeolocalisatio() throws Exception {
        int databaseSizeBeforeUpdate = ficheGeolocalisatioRepository.findAll().size();

        // Create the FicheGeolocalisatio

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFicheGeolocalisatioMockMvc.perform(put("/api/fiche-geolocalisatios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheGeolocalisatio)))
            .andExpect(status().isCreated());

        // Validate the FicheGeolocalisatio in the database
        List<FicheGeolocalisatio> ficheGeolocalisatioList = ficheGeolocalisatioRepository.findAll();
        assertThat(ficheGeolocalisatioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFicheGeolocalisatio() throws Exception {
        // Initialize the database
        ficheGeolocalisatioRepository.save(ficheGeolocalisatio);
        int databaseSizeBeforeDelete = ficheGeolocalisatioRepository.findAll().size();

        // Get the ficheGeolocalisatio
        restFicheGeolocalisatioMockMvc.perform(delete("/api/fiche-geolocalisatios/{id}", ficheGeolocalisatio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FicheGeolocalisatio> ficheGeolocalisatioList = ficheGeolocalisatioRepository.findAll();
        assertThat(ficheGeolocalisatioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FicheGeolocalisatio.class);
    }
}
