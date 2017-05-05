package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.Troupeau;
import sn.seysoo.repository.TroupeauRepository;
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
 * Test class for the TroupeauResource REST controller.
 *
 * @see TroupeauResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class TroupeauResourceIntTest {

    private static final String DEFAULT_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE = "BBBBBBBBBB";

    private static final Integer DEFAULT_EFFECTIF = 1;
    private static final Integer UPDATED_EFFECTIF = 2;

    @Autowired
    private TroupeauRepository troupeauRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restTroupeauMockMvc;

    private Troupeau troupeau;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TroupeauResource troupeauResource = new TroupeauResource(troupeauRepository);
        this.restTroupeauMockMvc = MockMvcBuilders.standaloneSetup(troupeauResource)
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
    public static Troupeau createEntity() {
        Troupeau troupeau = new Troupeau()
            .categorie(DEFAULT_CATEGORIE)
            .effectif(DEFAULT_EFFECTIF);
        return troupeau;
    }

    @Before
    public void initTest() {
        troupeauRepository.deleteAll();
        troupeau = createEntity();
    }

    @Test
    public void createTroupeau() throws Exception {
        int databaseSizeBeforeCreate = troupeauRepository.findAll().size();

        // Create the Troupeau
        restTroupeauMockMvc.perform(post("/api/troupeaus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(troupeau)))
            .andExpect(status().isCreated());

        // Validate the Troupeau in the database
        List<Troupeau> troupeauList = troupeauRepository.findAll();
        assertThat(troupeauList).hasSize(databaseSizeBeforeCreate + 1);
        Troupeau testTroupeau = troupeauList.get(troupeauList.size() - 1);
        assertThat(testTroupeau.getCategorie()).isEqualTo(DEFAULT_CATEGORIE);
        assertThat(testTroupeau.getEffectif()).isEqualTo(DEFAULT_EFFECTIF);
    }

    @Test
    public void createTroupeauWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = troupeauRepository.findAll().size();

        // Create the Troupeau with an existing ID
        troupeau.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTroupeauMockMvc.perform(post("/api/troupeaus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(troupeau)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Troupeau> troupeauList = troupeauRepository.findAll();
        assertThat(troupeauList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkEffectifIsRequired() throws Exception {
        int databaseSizeBeforeTest = troupeauRepository.findAll().size();
        // set the field null
        troupeau.setEffectif(null);

        // Create the Troupeau, which fails.

        restTroupeauMockMvc.perform(post("/api/troupeaus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(troupeau)))
            .andExpect(status().isBadRequest());

        List<Troupeau> troupeauList = troupeauRepository.findAll();
        assertThat(troupeauList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTroupeaus() throws Exception {
        // Initialize the database
        troupeauRepository.save(troupeau);

        // Get all the troupeauList
        restTroupeauMockMvc.perform(get("/api/troupeaus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(troupeau.getId())))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE.toString())))
            .andExpect(jsonPath("$.[*].effectif").value(hasItem(DEFAULT_EFFECTIF)));
    }

    @Test
    public void getTroupeau() throws Exception {
        // Initialize the database
        troupeauRepository.save(troupeau);

        // Get the troupeau
        restTroupeauMockMvc.perform(get("/api/troupeaus/{id}", troupeau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(troupeau.getId()))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE.toString()))
            .andExpect(jsonPath("$.effectif").value(DEFAULT_EFFECTIF));
    }

    @Test
    public void getNonExistingTroupeau() throws Exception {
        // Get the troupeau
        restTroupeauMockMvc.perform(get("/api/troupeaus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTroupeau() throws Exception {
        // Initialize the database
        troupeauRepository.save(troupeau);
        int databaseSizeBeforeUpdate = troupeauRepository.findAll().size();

        // Update the troupeau
        Troupeau updatedTroupeau = troupeauRepository.findOne(troupeau.getId());
        updatedTroupeau
            .categorie(UPDATED_CATEGORIE)
            .effectif(UPDATED_EFFECTIF);

        restTroupeauMockMvc.perform(put("/api/troupeaus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTroupeau)))
            .andExpect(status().isOk());

        // Validate the Troupeau in the database
        List<Troupeau> troupeauList = troupeauRepository.findAll();
        assertThat(troupeauList).hasSize(databaseSizeBeforeUpdate);
        Troupeau testTroupeau = troupeauList.get(troupeauList.size() - 1);
        assertThat(testTroupeau.getCategorie()).isEqualTo(UPDATED_CATEGORIE);
        assertThat(testTroupeau.getEffectif()).isEqualTo(UPDATED_EFFECTIF);
    }

    @Test
    public void updateNonExistingTroupeau() throws Exception {
        int databaseSizeBeforeUpdate = troupeauRepository.findAll().size();

        // Create the Troupeau

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTroupeauMockMvc.perform(put("/api/troupeaus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(troupeau)))
            .andExpect(status().isCreated());

        // Validate the Troupeau in the database
        List<Troupeau> troupeauList = troupeauRepository.findAll();
        assertThat(troupeauList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteTroupeau() throws Exception {
        // Initialize the database
        troupeauRepository.save(troupeau);
        int databaseSizeBeforeDelete = troupeauRepository.findAll().size();

        // Get the troupeau
        restTroupeauMockMvc.perform(delete("/api/troupeaus/{id}", troupeau.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Troupeau> troupeauList = troupeauRepository.findAll();
        assertThat(troupeauList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Troupeau.class);
    }
}
