package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.Adresse;
import sn.seysoo.repository.AdresseRepository;

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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AdresseResource REST controller.
 *
 * @see AdresseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class AdresseResourceIntTest {

    private static final String DEFAULT_REGION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final String DEFAULT_DEPARTEMENT = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DEPARTEMENT = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final String DEFAULT_RUE = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_RUE = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private AdresseRepository adresseRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAdresseMockMvc;

    private Adresse adresse;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AdresseResource adresseResource = new AdresseResource();
        ReflectionTestUtils.setField(adresseResource, "adresseRepository", adresseRepository);
        this.restAdresseMockMvc = MockMvcBuilders.standaloneSetup(adresseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresse createEntity() {
        Adresse adresse = new Adresse()
                .region(DEFAULT_REGION)
                .departement(DEFAULT_DEPARTEMENT)
                .ville(DEFAULT_VILLE)
                .rue(DEFAULT_RUE);
        return adresse;
    }

    @Before
    public void initTest() {
        adresseRepository.deleteAll();
        adresse = createEntity();
    }

    @Test
    public void createAdresse() throws Exception {
        int databaseSizeBeforeCreate = adresseRepository.findAll().size();

        // Create the Adresse

        restAdresseMockMvc.perform(post("/api/adresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adresse)))
                .andExpect(status().isCreated());

        // Validate the Adresse in the database
        List<Adresse> adresses = adresseRepository.findAll();
        assertThat(adresses).hasSize(databaseSizeBeforeCreate + 1);
        Adresse testAdresse = adresses.get(adresses.size() - 1);
        assertThat(testAdresse.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testAdresse.getDepartement()).isEqualTo(DEFAULT_DEPARTEMENT);
        assertThat(testAdresse.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testAdresse.getRue()).isEqualTo(DEFAULT_RUE);
    }

    @Test
    public void checkRegionIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresseRepository.findAll().size();
        // set the field null
        adresse.setRegion(null);

        // Create the Adresse, which fails.

        restAdresseMockMvc.perform(post("/api/adresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adresse)))
                .andExpect(status().isBadRequest());

        List<Adresse> adresses = adresseRepository.findAll();
        assertThat(adresses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDepartementIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresseRepository.findAll().size();
        // set the field null
        adresse.setDepartement(null);

        // Create the Adresse, which fails.

        restAdresseMockMvc.perform(post("/api/adresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adresse)))
                .andExpect(status().isBadRequest());

        List<Adresse> adresses = adresseRepository.findAll();
        assertThat(adresses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAdresses() throws Exception {
        // Initialize the database
        adresseRepository.save(adresse);

        // Get all the adresses
        restAdresseMockMvc.perform(get("/api/adresses?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(adresse.getId())))
                .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
                .andExpect(jsonPath("$.[*].departement").value(hasItem(DEFAULT_DEPARTEMENT.toString())))
                .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
                .andExpect(jsonPath("$.[*].rue").value(hasItem(DEFAULT_RUE.toString())));
    }

    @Test
    public void getAdresse() throws Exception {
        // Initialize the database
        adresseRepository.save(adresse);

        // Get the adresse
        restAdresseMockMvc.perform(get("/api/adresses/{id}", adresse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adresse.getId()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.departement").value(DEFAULT_DEPARTEMENT.toString()))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()))
            .andExpect(jsonPath("$.rue").value(DEFAULT_RUE.toString()));
    }

    @Test
    public void getNonExistingAdresse() throws Exception {
        // Get the adresse
        restAdresseMockMvc.perform(get("/api/adresses/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAdresse() throws Exception {
        // Initialize the database
        adresseRepository.save(adresse);
        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();

        // Update the adresse
        Adresse updatedAdresse = adresseRepository.findOne(adresse.getId());
        updatedAdresse
                .region(UPDATED_REGION)
                .departement(UPDATED_DEPARTEMENT)
                .ville(UPDATED_VILLE)
                .rue(UPDATED_RUE);

        restAdresseMockMvc.perform(put("/api/adresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAdresse)))
                .andExpect(status().isOk());

        // Validate the Adresse in the database
        List<Adresse> adresses = adresseRepository.findAll();
        assertThat(adresses).hasSize(databaseSizeBeforeUpdate);
        Adresse testAdresse = adresses.get(adresses.size() - 1);
        assertThat(testAdresse.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testAdresse.getDepartement()).isEqualTo(UPDATED_DEPARTEMENT);
        assertThat(testAdresse.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAdresse.getRue()).isEqualTo(UPDATED_RUE);
    }

    @Test
    public void deleteAdresse() throws Exception {
        // Initialize the database
        adresseRepository.save(adresse);
        int databaseSizeBeforeDelete = adresseRepository.findAll().size();

        // Get the adresse
        restAdresseMockMvc.perform(delete("/api/adresses/{id}", adresse.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Adresse> adresses = adresseRepository.findAll();
        assertThat(adresses).hasSize(databaseSizeBeforeDelete - 1);
    }
}
