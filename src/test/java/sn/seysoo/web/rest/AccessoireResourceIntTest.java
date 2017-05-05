package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.Accessoire;
import sn.seysoo.repository.AccessoireRepository;
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
 * Test class for the AccessoireResource REST controller.
 *
 * @see AccessoireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class AccessoireResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_TYPE = "AAAA";
    private static final String UPDATED_TYPE = "BBBB";

    private static final String DEFAULT_COULEUR = "AAAAAAAAAA";
    private static final String UPDATED_COULEUR = "BBBBBBBBBB";

    private static final String DEFAULT_PRIX = "AAAAAAAAAA";
    private static final String UPDATED_PRIX = "BBBBBBBBBB";

    @Autowired
    private AccessoireRepository accessoireRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAccessoireMockMvc;

    private Accessoire accessoire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AccessoireResource accessoireResource = new AccessoireResource(accessoireRepository);
        this.restAccessoireMockMvc = MockMvcBuilders.standaloneSetup(accessoireResource)
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
    public static Accessoire createEntity() {
        Accessoire accessoire = new Accessoire()
            .code(DEFAULT_CODE)
            .type(DEFAULT_TYPE)
            .couleur(DEFAULT_COULEUR)
            .prix(DEFAULT_PRIX);
        return accessoire;
    }

    @Before
    public void initTest() {
        accessoireRepository.deleteAll();
        accessoire = createEntity();
    }

    @Test
    public void createAccessoire() throws Exception {
        int databaseSizeBeforeCreate = accessoireRepository.findAll().size();

        // Create the Accessoire
        restAccessoireMockMvc.perform(post("/api/accessoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accessoire)))
            .andExpect(status().isCreated());

        // Validate the Accessoire in the database
        List<Accessoire> accessoireList = accessoireRepository.findAll();
        assertThat(accessoireList).hasSize(databaseSizeBeforeCreate + 1);
        Accessoire testAccessoire = accessoireList.get(accessoireList.size() - 1);
        assertThat(testAccessoire.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAccessoire.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAccessoire.getCouleur()).isEqualTo(DEFAULT_COULEUR);
        assertThat(testAccessoire.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    public void createAccessoireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accessoireRepository.findAll().size();

        // Create the Accessoire with an existing ID
        accessoire.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccessoireMockMvc.perform(post("/api/accessoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accessoire)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Accessoire> accessoireList = accessoireRepository.findAll();
        assertThat(accessoireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = accessoireRepository.findAll().size();
        // set the field null
        accessoire.setCode(null);

        // Create the Accessoire, which fails.

        restAccessoireMockMvc.perform(post("/api/accessoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accessoire)))
            .andExpect(status().isBadRequest());

        List<Accessoire> accessoireList = accessoireRepository.findAll();
        assertThat(accessoireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAccessoires() throws Exception {
        // Initialize the database
        accessoireRepository.save(accessoire);

        // Get all the accessoireList
        restAccessoireMockMvc.perform(get("/api/accessoires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accessoire.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].couleur").value(hasItem(DEFAULT_COULEUR.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.toString())));
    }

    @Test
    public void getAccessoire() throws Exception {
        // Initialize the database
        accessoireRepository.save(accessoire);

        // Get the accessoire
        restAccessoireMockMvc.perform(get("/api/accessoires/{id}", accessoire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accessoire.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.couleur").value(DEFAULT_COULEUR.toString()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.toString()));
    }

    @Test
    public void getNonExistingAccessoire() throws Exception {
        // Get the accessoire
        restAccessoireMockMvc.perform(get("/api/accessoires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAccessoire() throws Exception {
        // Initialize the database
        accessoireRepository.save(accessoire);
        int databaseSizeBeforeUpdate = accessoireRepository.findAll().size();

        // Update the accessoire
        Accessoire updatedAccessoire = accessoireRepository.findOne(accessoire.getId());
        updatedAccessoire
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE)
            .couleur(UPDATED_COULEUR)
            .prix(UPDATED_PRIX);

        restAccessoireMockMvc.perform(put("/api/accessoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAccessoire)))
            .andExpect(status().isOk());

        // Validate the Accessoire in the database
        List<Accessoire> accessoireList = accessoireRepository.findAll();
        assertThat(accessoireList).hasSize(databaseSizeBeforeUpdate);
        Accessoire testAccessoire = accessoireList.get(accessoireList.size() - 1);
        assertThat(testAccessoire.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAccessoire.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAccessoire.getCouleur()).isEqualTo(UPDATED_COULEUR);
        assertThat(testAccessoire.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    public void updateNonExistingAccessoire() throws Exception {
        int databaseSizeBeforeUpdate = accessoireRepository.findAll().size();

        // Create the Accessoire

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAccessoireMockMvc.perform(put("/api/accessoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accessoire)))
            .andExpect(status().isCreated());

        // Validate the Accessoire in the database
        List<Accessoire> accessoireList = accessoireRepository.findAll();
        assertThat(accessoireList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteAccessoire() throws Exception {
        // Initialize the database
        accessoireRepository.save(accessoire);
        int databaseSizeBeforeDelete = accessoireRepository.findAll().size();

        // Get the accessoire
        restAccessoireMockMvc.perform(delete("/api/accessoires/{id}", accessoire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Accessoire> accessoireList = accessoireRepository.findAll();
        assertThat(accessoireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Accessoire.class);
    }
}
