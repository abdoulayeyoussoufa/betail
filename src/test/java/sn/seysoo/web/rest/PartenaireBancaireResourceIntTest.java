package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.PartenaireBancaire;
import sn.seysoo.repository.PartenaireBancaireRepository;
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
 * Test class for the PartenaireBancaireResource REST controller.
 *
 * @see PartenaireBancaireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class PartenaireBancaireResourceIntTest {

    private static final String DEFAULT_NOM_RESEAU = "AAAAAAAAAA";
    private static final String UPDATED_NOM_RESEAU = "BBBBBBBBBB";

    @Autowired
    private PartenaireBancaireRepository partenaireBancaireRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPartenaireBancaireMockMvc;

    private PartenaireBancaire partenaireBancaire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PartenaireBancaireResource partenaireBancaireResource = new PartenaireBancaireResource(partenaireBancaireRepository);
        this.restPartenaireBancaireMockMvc = MockMvcBuilders.standaloneSetup(partenaireBancaireResource)
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
    public static PartenaireBancaire createEntity() {
        PartenaireBancaire partenaireBancaire = new PartenaireBancaire()
            .nomReseau(DEFAULT_NOM_RESEAU);
        return partenaireBancaire;
    }

    @Before
    public void initTest() {
        partenaireBancaireRepository.deleteAll();
        partenaireBancaire = createEntity();
    }

    @Test
    public void createPartenaireBancaire() throws Exception {
        int databaseSizeBeforeCreate = partenaireBancaireRepository.findAll().size();

        // Create the PartenaireBancaire
        restPartenaireBancaireMockMvc.perform(post("/api/partenaire-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partenaireBancaire)))
            .andExpect(status().isCreated());

        // Validate the PartenaireBancaire in the database
        List<PartenaireBancaire> partenaireBancaireList = partenaireBancaireRepository.findAll();
        assertThat(partenaireBancaireList).hasSize(databaseSizeBeforeCreate + 1);
        PartenaireBancaire testPartenaireBancaire = partenaireBancaireList.get(partenaireBancaireList.size() - 1);
        assertThat(testPartenaireBancaire.getNomReseau()).isEqualTo(DEFAULT_NOM_RESEAU);
    }

    @Test
    public void createPartenaireBancaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partenaireBancaireRepository.findAll().size();

        // Create the PartenaireBancaire with an existing ID
        partenaireBancaire.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartenaireBancaireMockMvc.perform(post("/api/partenaire-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partenaireBancaire)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PartenaireBancaire> partenaireBancaireList = partenaireBancaireRepository.findAll();
        assertThat(partenaireBancaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNomReseauIsRequired() throws Exception {
        int databaseSizeBeforeTest = partenaireBancaireRepository.findAll().size();
        // set the field null
        partenaireBancaire.setNomReseau(null);

        // Create the PartenaireBancaire, which fails.

        restPartenaireBancaireMockMvc.perform(post("/api/partenaire-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partenaireBancaire)))
            .andExpect(status().isBadRequest());

        List<PartenaireBancaire> partenaireBancaireList = partenaireBancaireRepository.findAll();
        assertThat(partenaireBancaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllPartenaireBancaires() throws Exception {
        // Initialize the database
        partenaireBancaireRepository.save(partenaireBancaire);

        // Get all the partenaireBancaireList
        restPartenaireBancaireMockMvc.perform(get("/api/partenaire-bancaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partenaireBancaire.getId())))
            .andExpect(jsonPath("$.[*].nomReseau").value(hasItem(DEFAULT_NOM_RESEAU.toString())));
    }

    @Test
    public void getPartenaireBancaire() throws Exception {
        // Initialize the database
        partenaireBancaireRepository.save(partenaireBancaire);

        // Get the partenaireBancaire
        restPartenaireBancaireMockMvc.perform(get("/api/partenaire-bancaires/{id}", partenaireBancaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partenaireBancaire.getId()))
            .andExpect(jsonPath("$.nomReseau").value(DEFAULT_NOM_RESEAU.toString()));
    }

    @Test
    public void getNonExistingPartenaireBancaire() throws Exception {
        // Get the partenaireBancaire
        restPartenaireBancaireMockMvc.perform(get("/api/partenaire-bancaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePartenaireBancaire() throws Exception {
        // Initialize the database
        partenaireBancaireRepository.save(partenaireBancaire);
        int databaseSizeBeforeUpdate = partenaireBancaireRepository.findAll().size();

        // Update the partenaireBancaire
        PartenaireBancaire updatedPartenaireBancaire = partenaireBancaireRepository.findOne(partenaireBancaire.getId());
        updatedPartenaireBancaire
            .nomReseau(UPDATED_NOM_RESEAU);

        restPartenaireBancaireMockMvc.perform(put("/api/partenaire-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartenaireBancaire)))
            .andExpect(status().isOk());

        // Validate the PartenaireBancaire in the database
        List<PartenaireBancaire> partenaireBancaireList = partenaireBancaireRepository.findAll();
        assertThat(partenaireBancaireList).hasSize(databaseSizeBeforeUpdate);
        PartenaireBancaire testPartenaireBancaire = partenaireBancaireList.get(partenaireBancaireList.size() - 1);
        assertThat(testPartenaireBancaire.getNomReseau()).isEqualTo(UPDATED_NOM_RESEAU);
    }

    @Test
    public void updateNonExistingPartenaireBancaire() throws Exception {
        int databaseSizeBeforeUpdate = partenaireBancaireRepository.findAll().size();

        // Create the PartenaireBancaire

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPartenaireBancaireMockMvc.perform(put("/api/partenaire-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partenaireBancaire)))
            .andExpect(status().isCreated());

        // Validate the PartenaireBancaire in the database
        List<PartenaireBancaire> partenaireBancaireList = partenaireBancaireRepository.findAll();
        assertThat(partenaireBancaireList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deletePartenaireBancaire() throws Exception {
        // Initialize the database
        partenaireBancaireRepository.save(partenaireBancaire);
        int databaseSizeBeforeDelete = partenaireBancaireRepository.findAll().size();

        // Get the partenaireBancaire
        restPartenaireBancaireMockMvc.perform(delete("/api/partenaire-bancaires/{id}", partenaireBancaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PartenaireBancaire> partenaireBancaireList = partenaireBancaireRepository.findAll();
        assertThat(partenaireBancaireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartenaireBancaire.class);
    }
}
