package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.CarnetSante;
import sn.seysoo.repository.CarnetSanteRepository;
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
 * Test class for the CarnetSanteResource REST controller.
 *
 * @see CarnetSanteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class CarnetSanteResourceIntTest {

    private static final ZonedDateTime DEFAULT_DAT_VACCINATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DAT_VACCINATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private CarnetSanteRepository carnetSanteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restCarnetSanteMockMvc;

    private CarnetSante carnetSante;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarnetSanteResource carnetSanteResource = new CarnetSanteResource(carnetSanteRepository);
        this.restCarnetSanteMockMvc = MockMvcBuilders.standaloneSetup(carnetSanteResource)
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
    public static CarnetSante createEntity() {
        CarnetSante carnetSante = new CarnetSante()
            .datVaccination(DEFAULT_DAT_VACCINATION)
            .nom(DEFAULT_NOM);
        return carnetSante;
    }

    @Before
    public void initTest() {
        carnetSanteRepository.deleteAll();
        carnetSante = createEntity();
    }

    @Test
    public void createCarnetSante() throws Exception {
        int databaseSizeBeforeCreate = carnetSanteRepository.findAll().size();

        // Create the CarnetSante
        restCarnetSanteMockMvc.perform(post("/api/carnet-santes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carnetSante)))
            .andExpect(status().isCreated());

        // Validate the CarnetSante in the database
        List<CarnetSante> carnetSanteList = carnetSanteRepository.findAll();
        assertThat(carnetSanteList).hasSize(databaseSizeBeforeCreate + 1);
        CarnetSante testCarnetSante = carnetSanteList.get(carnetSanteList.size() - 1);
        assertThat(testCarnetSante.getDatVaccination()).isEqualTo(DEFAULT_DAT_VACCINATION);
        assertThat(testCarnetSante.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    public void createCarnetSanteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carnetSanteRepository.findAll().size();

        // Create the CarnetSante with an existing ID
        carnetSante.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarnetSanteMockMvc.perform(post("/api/carnet-santes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carnetSante)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CarnetSante> carnetSanteList = carnetSanteRepository.findAll();
        assertThat(carnetSanteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkDatVaccinationIsRequired() throws Exception {
        int databaseSizeBeforeTest = carnetSanteRepository.findAll().size();
        // set the field null
        carnetSante.setDatVaccination(null);

        // Create the CarnetSante, which fails.

        restCarnetSanteMockMvc.perform(post("/api/carnet-santes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carnetSante)))
            .andExpect(status().isBadRequest());

        List<CarnetSante> carnetSanteList = carnetSanteRepository.findAll();
        assertThat(carnetSanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllCarnetSantes() throws Exception {
        // Initialize the database
        carnetSanteRepository.save(carnetSante);

        // Get all the carnetSanteList
        restCarnetSanteMockMvc.perform(get("/api/carnet-santes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carnetSante.getId())))
            .andExpect(jsonPath("$.[*].datVaccination").value(hasItem(sameInstant(DEFAULT_DAT_VACCINATION))))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }

    @Test
    public void getCarnetSante() throws Exception {
        // Initialize the database
        carnetSanteRepository.save(carnetSante);

        // Get the carnetSante
        restCarnetSanteMockMvc.perform(get("/api/carnet-santes/{id}", carnetSante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carnetSante.getId()))
            .andExpect(jsonPath("$.datVaccination").value(sameInstant(DEFAULT_DAT_VACCINATION)))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()));
    }

    @Test
    public void getNonExistingCarnetSante() throws Exception {
        // Get the carnetSante
        restCarnetSanteMockMvc.perform(get("/api/carnet-santes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCarnetSante() throws Exception {
        // Initialize the database
        carnetSanteRepository.save(carnetSante);
        int databaseSizeBeforeUpdate = carnetSanteRepository.findAll().size();

        // Update the carnetSante
        CarnetSante updatedCarnetSante = carnetSanteRepository.findOne(carnetSante.getId());
        updatedCarnetSante
            .datVaccination(UPDATED_DAT_VACCINATION)
            .nom(UPDATED_NOM);

        restCarnetSanteMockMvc.perform(put("/api/carnet-santes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCarnetSante)))
            .andExpect(status().isOk());

        // Validate the CarnetSante in the database
        List<CarnetSante> carnetSanteList = carnetSanteRepository.findAll();
        assertThat(carnetSanteList).hasSize(databaseSizeBeforeUpdate);
        CarnetSante testCarnetSante = carnetSanteList.get(carnetSanteList.size() - 1);
        assertThat(testCarnetSante.getDatVaccination()).isEqualTo(UPDATED_DAT_VACCINATION);
        assertThat(testCarnetSante.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    public void updateNonExistingCarnetSante() throws Exception {
        int databaseSizeBeforeUpdate = carnetSanteRepository.findAll().size();

        // Create the CarnetSante

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCarnetSanteMockMvc.perform(put("/api/carnet-santes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carnetSante)))
            .andExpect(status().isCreated());

        // Validate the CarnetSante in the database
        List<CarnetSante> carnetSanteList = carnetSanteRepository.findAll();
        assertThat(carnetSanteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteCarnetSante() throws Exception {
        // Initialize the database
        carnetSanteRepository.save(carnetSante);
        int databaseSizeBeforeDelete = carnetSanteRepository.findAll().size();

        // Get the carnetSante
        restCarnetSanteMockMvc.perform(delete("/api/carnet-santes/{id}", carnetSante.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CarnetSante> carnetSanteList = carnetSanteRepository.findAll();
        assertThat(carnetSanteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarnetSante.class);
    }
}
