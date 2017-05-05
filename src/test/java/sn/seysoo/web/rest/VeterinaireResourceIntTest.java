package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.Veterinaire;
import sn.seysoo.repository.VeterinaireRepository;
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
 * Test class for the VeterinaireResource REST controller.
 *
 * @see VeterinaireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class VeterinaireResourceIntTest {

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "+@e.fV";
    private static final String UPDATED_EMAIL = "c@ZV.Ox";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_CNI = "2x6l289142952";
    private static final String UPDATED_NUMERO_CNI = "16y8206580888";

    @Autowired
    private VeterinaireRepository veterinaireRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restVeterinaireMockMvc;

    private Veterinaire veterinaire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VeterinaireResource veterinaireResource = new VeterinaireResource(veterinaireRepository);
        this.restVeterinaireMockMvc = MockMvcBuilders.standaloneSetup(veterinaireResource)
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
    public static Veterinaire createEntity() {
        Veterinaire veterinaire = new Veterinaire()
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE)
            .numeroCNI(DEFAULT_NUMERO_CNI);
        return veterinaire;
    }

    @Before
    public void initTest() {
        veterinaireRepository.deleteAll();
        veterinaire = createEntity();
    }

    @Test
    public void createVeterinaire() throws Exception {
        int databaseSizeBeforeCreate = veterinaireRepository.findAll().size();

        // Create the Veterinaire
        restVeterinaireMockMvc.perform(post("/api/veterinaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veterinaire)))
            .andExpect(status().isCreated());

        // Validate the Veterinaire in the database
        List<Veterinaire> veterinaireList = veterinaireRepository.findAll();
        assertThat(veterinaireList).hasSize(databaseSizeBeforeCreate + 1);
        Veterinaire testVeterinaire = veterinaireList.get(veterinaireList.size() - 1);
        assertThat(testVeterinaire.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testVeterinaire.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testVeterinaire.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testVeterinaire.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testVeterinaire.getNumeroCNI()).isEqualTo(DEFAULT_NUMERO_CNI);
    }

    @Test
    public void createVeterinaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = veterinaireRepository.findAll().size();

        // Create the Veterinaire with an existing ID
        veterinaire.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restVeterinaireMockMvc.perform(post("/api/veterinaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veterinaire)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Veterinaire> veterinaireList = veterinaireRepository.findAll();
        assertThat(veterinaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = veterinaireRepository.findAll().size();
        // set the field null
        veterinaire.setPrenom(null);

        // Create the Veterinaire, which fails.

        restVeterinaireMockMvc.perform(post("/api/veterinaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veterinaire)))
            .andExpect(status().isBadRequest());

        List<Veterinaire> veterinaireList = veterinaireRepository.findAll();
        assertThat(veterinaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = veterinaireRepository.findAll().size();
        // set the field null
        veterinaire.setNom(null);

        // Create the Veterinaire, which fails.

        restVeterinaireMockMvc.perform(post("/api/veterinaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veterinaire)))
            .andExpect(status().isBadRequest());

        List<Veterinaire> veterinaireList = veterinaireRepository.findAll();
        assertThat(veterinaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllVeterinaires() throws Exception {
        // Initialize the database
        veterinaireRepository.save(veterinaire);

        // Get all the veterinaireList
        restVeterinaireMockMvc.perform(get("/api/veterinaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(veterinaire.getId())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].numeroCNI").value(hasItem(DEFAULT_NUMERO_CNI.toString())));
    }

    @Test
    public void getVeterinaire() throws Exception {
        // Initialize the database
        veterinaireRepository.save(veterinaire);

        // Get the veterinaire
        restVeterinaireMockMvc.perform(get("/api/veterinaires/{id}", veterinaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(veterinaire.getId()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.numeroCNI").value(DEFAULT_NUMERO_CNI.toString()));
    }

    @Test
    public void getNonExistingVeterinaire() throws Exception {
        // Get the veterinaire
        restVeterinaireMockMvc.perform(get("/api/veterinaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVeterinaire() throws Exception {
        // Initialize the database
        veterinaireRepository.save(veterinaire);
        int databaseSizeBeforeUpdate = veterinaireRepository.findAll().size();

        // Update the veterinaire
        Veterinaire updatedVeterinaire = veterinaireRepository.findOne(veterinaire.getId());
        updatedVeterinaire
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .numeroCNI(UPDATED_NUMERO_CNI);

        restVeterinaireMockMvc.perform(put("/api/veterinaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVeterinaire)))
            .andExpect(status().isOk());

        // Validate the Veterinaire in the database
        List<Veterinaire> veterinaireList = veterinaireRepository.findAll();
        assertThat(veterinaireList).hasSize(databaseSizeBeforeUpdate);
        Veterinaire testVeterinaire = veterinaireList.get(veterinaireList.size() - 1);
        assertThat(testVeterinaire.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testVeterinaire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testVeterinaire.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testVeterinaire.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testVeterinaire.getNumeroCNI()).isEqualTo(UPDATED_NUMERO_CNI);
    }

    @Test
    public void updateNonExistingVeterinaire() throws Exception {
        int databaseSizeBeforeUpdate = veterinaireRepository.findAll().size();

        // Create the Veterinaire

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVeterinaireMockMvc.perform(put("/api/veterinaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(veterinaire)))
            .andExpect(status().isCreated());

        // Validate the Veterinaire in the database
        List<Veterinaire> veterinaireList = veterinaireRepository.findAll();
        assertThat(veterinaireList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteVeterinaire() throws Exception {
        // Initialize the database
        veterinaireRepository.save(veterinaire);
        int databaseSizeBeforeDelete = veterinaireRepository.findAll().size();

        // Get the veterinaire
        restVeterinaireMockMvc.perform(delete("/api/veterinaires/{id}", veterinaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Veterinaire> veterinaireList = veterinaireRepository.findAll();
        assertThat(veterinaireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Veterinaire.class);
    }
}
