package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.Eleveur;
import sn.seysoo.repository.EleveurRepository;
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

import sn.seysoo.domain.enumeration.Language;
import sn.seysoo.domain.enumeration.Sexe;
/**
 * Test class for the EleveurResource REST controller.
 *
 * @see EleveurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class EleveurResourceIntTest {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUE = Language.WOLOF;
    private static final Language UPDATED_LANGUE = Language.PEULH;

    private static final Sexe DEFAULT_SEX = Sexe.M;
    private static final Sexe UPDATED_SEX = Sexe.F;

    private static final String DEFAULT_EMAIL = "WU@x.V";
    private static final String UPDATED_EMAIL = "Y0@4.O";

    private static final String DEFAULT_NUMERO_CNI = "2J4q634143205";
    private static final String UPDATED_NUMERO_CNI = "1n2Y532963213";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    @Autowired
    private EleveurRepository eleveurRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restEleveurMockMvc;

    private Eleveur eleveur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EleveurResource eleveurResource = new EleveurResource(eleveurRepository);
        this.restEleveurMockMvc = MockMvcBuilders.standaloneSetup(eleveurResource)
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
    public static Eleveur createEntity() {
        Eleveur eleveur = new Eleveur()
            .numero(DEFAULT_NUMERO)
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .langue(DEFAULT_LANGUE)
            .sex(DEFAULT_SEX)
            .email(DEFAULT_EMAIL)
            .numeroCNI(DEFAULT_NUMERO_CNI)
            .telephone(DEFAULT_TELEPHONE);
        return eleveur;
    }

    @Before
    public void initTest() {
        eleveurRepository.deleteAll();
        eleveur = createEntity();
    }

    @Test
    public void createEleveur() throws Exception {
        int databaseSizeBeforeCreate = eleveurRepository.findAll().size();

        // Create the Eleveur
        restEleveurMockMvc.perform(post("/api/eleveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eleveur)))
            .andExpect(status().isCreated());

        // Validate the Eleveur in the database
        List<Eleveur> eleveurList = eleveurRepository.findAll();
        assertThat(eleveurList).hasSize(databaseSizeBeforeCreate + 1);
        Eleveur testEleveur = eleveurList.get(eleveurList.size() - 1);
        assertThat(testEleveur.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testEleveur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testEleveur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEleveur.getLangue()).isEqualTo(DEFAULT_LANGUE);
        assertThat(testEleveur.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testEleveur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEleveur.getNumeroCNI()).isEqualTo(DEFAULT_NUMERO_CNI);
        assertThat(testEleveur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
    }

    @Test
    public void createEleveurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eleveurRepository.findAll().size();

        // Create the Eleveur with an existing ID
        eleveur.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restEleveurMockMvc.perform(post("/api/eleveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eleveur)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Eleveur> eleveurList = eleveurRepository.findAll();
        assertThat(eleveurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = eleveurRepository.findAll().size();
        // set the field null
        eleveur.setNumero(null);

        // Create the Eleveur, which fails.

        restEleveurMockMvc.perform(post("/api/eleveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eleveur)))
            .andExpect(status().isBadRequest());

        List<Eleveur> eleveurList = eleveurRepository.findAll();
        assertThat(eleveurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = eleveurRepository.findAll().size();
        // set the field null
        eleveur.setPrenom(null);

        // Create the Eleveur, which fails.

        restEleveurMockMvc.perform(post("/api/eleveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eleveur)))
            .andExpect(status().isBadRequest());

        List<Eleveur> eleveurList = eleveurRepository.findAll();
        assertThat(eleveurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = eleveurRepository.findAll().size();
        // set the field null
        eleveur.setNom(null);

        // Create the Eleveur, which fails.

        restEleveurMockMvc.perform(post("/api/eleveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eleveur)))
            .andExpect(status().isBadRequest());

        List<Eleveur> eleveurList = eleveurRepository.findAll();
        assertThat(eleveurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNumeroCNIIsRequired() throws Exception {
        int databaseSizeBeforeTest = eleveurRepository.findAll().size();
        // set the field null
        eleveur.setNumeroCNI(null);

        // Create the Eleveur, which fails.

        restEleveurMockMvc.perform(post("/api/eleveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eleveur)))
            .andExpect(status().isBadRequest());

        List<Eleveur> eleveurList = eleveurRepository.findAll();
        assertThat(eleveurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllEleveurs() throws Exception {
        // Initialize the database
        eleveurRepository.save(eleveur);

        // Get all the eleveurList
        restEleveurMockMvc.perform(get("/api/eleveurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eleveur.getId())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].langue").value(hasItem(DEFAULT_LANGUE.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].numeroCNI").value(hasItem(DEFAULT_NUMERO_CNI.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())));
    }

    @Test
    public void getEleveur() throws Exception {
        // Initialize the database
        eleveurRepository.save(eleveur);

        // Get the eleveur
        restEleveurMockMvc.perform(get("/api/eleveurs/{id}", eleveur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eleveur.getId()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.langue").value(DEFAULT_LANGUE.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.numeroCNI").value(DEFAULT_NUMERO_CNI.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()));
    }

    @Test
    public void getNonExistingEleveur() throws Exception {
        // Get the eleveur
        restEleveurMockMvc.perform(get("/api/eleveurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEleveur() throws Exception {
        // Initialize the database
        eleveurRepository.save(eleveur);
        int databaseSizeBeforeUpdate = eleveurRepository.findAll().size();

        // Update the eleveur
        Eleveur updatedEleveur = eleveurRepository.findOne(eleveur.getId());
        updatedEleveur
            .numero(UPDATED_NUMERO)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .langue(UPDATED_LANGUE)
            .sex(UPDATED_SEX)
            .email(UPDATED_EMAIL)
            .numeroCNI(UPDATED_NUMERO_CNI)
            .telephone(UPDATED_TELEPHONE);

        restEleveurMockMvc.perform(put("/api/eleveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEleveur)))
            .andExpect(status().isOk());

        // Validate the Eleveur in the database
        List<Eleveur> eleveurList = eleveurRepository.findAll();
        assertThat(eleveurList).hasSize(databaseSizeBeforeUpdate);
        Eleveur testEleveur = eleveurList.get(eleveurList.size() - 1);
        assertThat(testEleveur.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEleveur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testEleveur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEleveur.getLangue()).isEqualTo(UPDATED_LANGUE);
        assertThat(testEleveur.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testEleveur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEleveur.getNumeroCNI()).isEqualTo(UPDATED_NUMERO_CNI);
        assertThat(testEleveur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
    }

    @Test
    public void updateNonExistingEleveur() throws Exception {
        int databaseSizeBeforeUpdate = eleveurRepository.findAll().size();

        // Create the Eleveur

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEleveurMockMvc.perform(put("/api/eleveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eleveur)))
            .andExpect(status().isCreated());

        // Validate the Eleveur in the database
        List<Eleveur> eleveurList = eleveurRepository.findAll();
        assertThat(eleveurList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteEleveur() throws Exception {
        // Initialize the database
        eleveurRepository.save(eleveur);
        int databaseSizeBeforeDelete = eleveurRepository.findAll().size();

        // Get the eleveur
        restEleveurMockMvc.perform(delete("/api/eleveurs/{id}", eleveur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Eleveur> eleveurList = eleveurRepository.findAll();
        assertThat(eleveurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eleveur.class);
    }
}
