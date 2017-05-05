package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.Maladie;
import sn.seysoo.repository.MaladieRepository;
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
 * Test class for the MaladieResource REST controller.
 *
 * @see MaladieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class MaladieResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private MaladieRepository maladieRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMaladieMockMvc;

    private Maladie maladie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MaladieResource maladieResource = new MaladieResource(maladieRepository);
        this.restMaladieMockMvc = MockMvcBuilders.standaloneSetup(maladieResource)
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
    public static Maladie createEntity() {
        Maladie maladie = new Maladie()
            .nom(DEFAULT_NOM)
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION);
        return maladie;
    }

    @Before
    public void initTest() {
        maladieRepository.deleteAll();
        maladie = createEntity();
    }

    @Test
    public void createMaladie() throws Exception {
        int databaseSizeBeforeCreate = maladieRepository.findAll().size();

        // Create the Maladie
        restMaladieMockMvc.perform(post("/api/maladies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maladie)))
            .andExpect(status().isCreated());

        // Validate the Maladie in the database
        List<Maladie> maladieList = maladieRepository.findAll();
        assertThat(maladieList).hasSize(databaseSizeBeforeCreate + 1);
        Maladie testMaladie = maladieList.get(maladieList.size() - 1);
        assertThat(testMaladie.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMaladie.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMaladie.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void createMaladieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = maladieRepository.findAll().size();

        // Create the Maladie with an existing ID
        maladie.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaladieMockMvc.perform(post("/api/maladies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maladie)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Maladie> maladieList = maladieRepository.findAll();
        assertThat(maladieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = maladieRepository.findAll().size();
        // set the field null
        maladie.setNom(null);

        // Create the Maladie, which fails.

        restMaladieMockMvc.perform(post("/api/maladies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maladie)))
            .andExpect(status().isBadRequest());

        List<Maladie> maladieList = maladieRepository.findAll();
        assertThat(maladieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllMaladies() throws Exception {
        // Initialize the database
        maladieRepository.save(maladie);

        // Get all the maladieList
        restMaladieMockMvc.perform(get("/api/maladies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maladie.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    public void getMaladie() throws Exception {
        // Initialize the database
        maladieRepository.save(maladie);

        // Get the maladie
        restMaladieMockMvc.perform(get("/api/maladies/{id}", maladie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(maladie.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    public void getNonExistingMaladie() throws Exception {
        // Get the maladie
        restMaladieMockMvc.perform(get("/api/maladies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateMaladie() throws Exception {
        // Initialize the database
        maladieRepository.save(maladie);
        int databaseSizeBeforeUpdate = maladieRepository.findAll().size();

        // Update the maladie
        Maladie updatedMaladie = maladieRepository.findOne(maladie.getId());
        updatedMaladie
            .nom(UPDATED_NOM)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION);

        restMaladieMockMvc.perform(put("/api/maladies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMaladie)))
            .andExpect(status().isOk());

        // Validate the Maladie in the database
        List<Maladie> maladieList = maladieRepository.findAll();
        assertThat(maladieList).hasSize(databaseSizeBeforeUpdate);
        Maladie testMaladie = maladieList.get(maladieList.size() - 1);
        assertThat(testMaladie.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMaladie.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMaladie.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void updateNonExistingMaladie() throws Exception {
        int databaseSizeBeforeUpdate = maladieRepository.findAll().size();

        // Create the Maladie

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMaladieMockMvc.perform(put("/api/maladies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maladie)))
            .andExpect(status().isCreated());

        // Validate the Maladie in the database
        List<Maladie> maladieList = maladieRepository.findAll();
        assertThat(maladieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteMaladie() throws Exception {
        // Initialize the database
        maladieRepository.save(maladie);
        int databaseSizeBeforeDelete = maladieRepository.findAll().size();

        // Get the maladie
        restMaladieMockMvc.perform(delete("/api/maladies/{id}", maladie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Maladie> maladieList = maladieRepository.findAll();
        assertThat(maladieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Maladie.class);
    }
}
