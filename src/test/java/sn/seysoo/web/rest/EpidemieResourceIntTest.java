package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.Epidemie;
import sn.seysoo.repository.EpidemieRepository;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EpidemieResource REST controller.
 *
 * @see EpidemieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class EpidemieResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCIPTION = "BBBBBBBBBB";

    @Autowired
    private EpidemieRepository epidemieRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restEpidemieMockMvc;

    private Epidemie epidemie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EpidemieResource epidemieResource = new EpidemieResource(epidemieRepository);
        this.restEpidemieMockMvc = MockMvcBuilders.standaloneSetup(epidemieResource)
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
    public static Epidemie createEntity() {
        Epidemie epidemie = new Epidemie()
            .nom(DEFAULT_NOM)
            .date(DEFAULT_DATE)
            .desciption(DEFAULT_DESCIPTION);
        return epidemie;
    }

    @Before
    public void initTest() {
        epidemieRepository.deleteAll();
        epidemie = createEntity();
    }

    @Test
    public void createEpidemie() throws Exception {
        int databaseSizeBeforeCreate = epidemieRepository.findAll().size();

        // Create the Epidemie
        restEpidemieMockMvc.perform(post("/api/epidemies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(epidemie)))
            .andExpect(status().isCreated());

        // Validate the Epidemie in the database
        List<Epidemie> epidemieList = epidemieRepository.findAll();
        assertThat(epidemieList).hasSize(databaseSizeBeforeCreate + 1);
        Epidemie testEpidemie = epidemieList.get(epidemieList.size() - 1);
        assertThat(testEpidemie.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEpidemie.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEpidemie.getDesciption()).isEqualTo(DEFAULT_DESCIPTION);
    }

    @Test
    public void createEpidemieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = epidemieRepository.findAll().size();

        // Create the Epidemie with an existing ID
        epidemie.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restEpidemieMockMvc.perform(post("/api/epidemies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(epidemie)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Epidemie> epidemieList = epidemieRepository.findAll();
        assertThat(epidemieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllEpidemies() throws Exception {
        // Initialize the database
        epidemieRepository.save(epidemie);

        // Get all the epidemieList
        restEpidemieMockMvc.perform(get("/api/epidemies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(epidemie.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].desciption").value(hasItem(DEFAULT_DESCIPTION.toString())));
    }

    @Test
    public void getEpidemie() throws Exception {
        // Initialize the database
        epidemieRepository.save(epidemie);

        // Get the epidemie
        restEpidemieMockMvc.perform(get("/api/epidemies/{id}", epidemie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(epidemie.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.desciption").value(DEFAULT_DESCIPTION.toString()));
    }

    @Test
    public void getNonExistingEpidemie() throws Exception {
        // Get the epidemie
        restEpidemieMockMvc.perform(get("/api/epidemies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEpidemie() throws Exception {
        // Initialize the database
        epidemieRepository.save(epidemie);
        int databaseSizeBeforeUpdate = epidemieRepository.findAll().size();

        // Update the epidemie
        Epidemie updatedEpidemie = epidemieRepository.findOne(epidemie.getId());
        updatedEpidemie
            .nom(UPDATED_NOM)
            .date(UPDATED_DATE)
            .desciption(UPDATED_DESCIPTION);

        restEpidemieMockMvc.perform(put("/api/epidemies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEpidemie)))
            .andExpect(status().isOk());

        // Validate the Epidemie in the database
        List<Epidemie> epidemieList = epidemieRepository.findAll();
        assertThat(epidemieList).hasSize(databaseSizeBeforeUpdate);
        Epidemie testEpidemie = epidemieList.get(epidemieList.size() - 1);
        assertThat(testEpidemie.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEpidemie.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEpidemie.getDesciption()).isEqualTo(UPDATED_DESCIPTION);
    }

    @Test
    public void updateNonExistingEpidemie() throws Exception {
        int databaseSizeBeforeUpdate = epidemieRepository.findAll().size();

        // Create the Epidemie

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEpidemieMockMvc.perform(put("/api/epidemies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(epidemie)))
            .andExpect(status().isCreated());

        // Validate the Epidemie in the database
        List<Epidemie> epidemieList = epidemieRepository.findAll();
        assertThat(epidemieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteEpidemie() throws Exception {
        // Initialize the database
        epidemieRepository.save(epidemie);
        int databaseSizeBeforeDelete = epidemieRepository.findAll().size();

        // Get the epidemie
        restEpidemieMockMvc.perform(delete("/api/epidemies/{id}", epidemie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Epidemie> epidemieList = epidemieRepository.findAll();
        assertThat(epidemieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Epidemie.class);
    }
}
