package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.Localite;
import sn.seysoo.repository.LocaliteRepository;
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
 * Test class for the LocaliteResource REST controller.
 *
 * @see LocaliteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class LocaliteResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_POSTAL = "AAAAAA";
    private static final String UPDATED_CODE_POSTAL = "BBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    @Autowired
    private LocaliteRepository localiteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restLocaliteMockMvc;

    private Localite localite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LocaliteResource localiteResource = new LocaliteResource(localiteRepository);
        this.restLocaliteMockMvc = MockMvcBuilders.standaloneSetup(localiteResource)
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
    public static Localite createEntity() {
        Localite localite = new Localite()
            .nom(DEFAULT_NOM)
            .codePostal(DEFAULT_CODE_POSTAL)
            .pays(DEFAULT_PAYS);
        return localite;
    }

    @Before
    public void initTest() {
        localiteRepository.deleteAll();
        localite = createEntity();
    }

    @Test
    public void createLocalite() throws Exception {
        int databaseSizeBeforeCreate = localiteRepository.findAll().size();

        // Create the Localite
        restLocaliteMockMvc.perform(post("/api/localites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localite)))
            .andExpect(status().isCreated());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeCreate + 1);
        Localite testLocalite = localiteList.get(localiteList.size() - 1);
        assertThat(testLocalite.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testLocalite.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testLocalite.getPays()).isEqualTo(DEFAULT_PAYS);
    }

    @Test
    public void createLocaliteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localiteRepository.findAll().size();

        // Create the Localite with an existing ID
        localite.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocaliteMockMvc.perform(post("/api/localites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localite)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = localiteRepository.findAll().size();
        // set the field null
        localite.setNom(null);

        // Create the Localite, which fails.

        restLocaliteMockMvc.perform(post("/api/localites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localite)))
            .andExpect(status().isBadRequest());

        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllLocalites() throws Exception {
        // Initialize the database
        localiteRepository.save(localite);

        // Get all the localiteList
        restLocaliteMockMvc.perform(get("/api/localites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localite.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL.toString())))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS.toString())));
    }

    @Test
    public void getLocalite() throws Exception {
        // Initialize the database
        localiteRepository.save(localite);

        // Get the localite
        restLocaliteMockMvc.perform(get("/api/localites/{id}", localite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(localite.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL.toString()))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS.toString()));
    }

    @Test
    public void getNonExistingLocalite() throws Exception {
        // Get the localite
        restLocaliteMockMvc.perform(get("/api/localites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateLocalite() throws Exception {
        // Initialize the database
        localiteRepository.save(localite);
        int databaseSizeBeforeUpdate = localiteRepository.findAll().size();

        // Update the localite
        Localite updatedLocalite = localiteRepository.findOne(localite.getId());
        updatedLocalite
            .nom(UPDATED_NOM)
            .codePostal(UPDATED_CODE_POSTAL)
            .pays(UPDATED_PAYS);

        restLocaliteMockMvc.perform(put("/api/localites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocalite)))
            .andExpect(status().isOk());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeUpdate);
        Localite testLocalite = localiteList.get(localiteList.size() - 1);
        assertThat(testLocalite.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testLocalite.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testLocalite.getPays()).isEqualTo(UPDATED_PAYS);
    }

    @Test
    public void updateNonExistingLocalite() throws Exception {
        int databaseSizeBeforeUpdate = localiteRepository.findAll().size();

        // Create the Localite

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLocaliteMockMvc.perform(put("/api/localites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localite)))
            .andExpect(status().isCreated());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteLocalite() throws Exception {
        // Initialize the database
        localiteRepository.save(localite);
        int databaseSizeBeforeDelete = localiteRepository.findAll().size();

        // Get the localite
        restLocaliteMockMvc.perform(delete("/api/localites/{id}", localite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Localite.class);
    }
}
