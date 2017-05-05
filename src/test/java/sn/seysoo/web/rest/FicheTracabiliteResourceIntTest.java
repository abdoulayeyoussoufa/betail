package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.FicheTracabilite;
import sn.seysoo.repository.FicheTracabiliteRepository;
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
 * Test class for the FicheTracabiliteResource REST controller.
 *
 * @see FicheTracabiliteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class FicheTracabiliteResourceIntTest {

    private static final String DEFAULT_PROPRIETAIRE_ACTU = "AAAAAAAAAA";
    private static final String UPDATED_PROPRIETAIRE_ACTU = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OBT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OBT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU_ACTUEL = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_ACTUEL = "BBBBBBBBBB";

    private static final String DEFAULT_DERNIER_PROP = "AAAAAAAAAA";
    private static final String UPDATED_DERNIER_PROP = "BBBBBBBBBB";

    @Autowired
    private FicheTracabiliteRepository ficheTracabiliteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restFicheTracabiliteMockMvc;

    private FicheTracabilite ficheTracabilite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FicheTracabiliteResource ficheTracabiliteResource = new FicheTracabiliteResource(ficheTracabiliteRepository);
        this.restFicheTracabiliteMockMvc = MockMvcBuilders.standaloneSetup(ficheTracabiliteResource)
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
    public static FicheTracabilite createEntity() {
        FicheTracabilite ficheTracabilite = new FicheTracabilite()
            .proprietaireActu(DEFAULT_PROPRIETAIRE_ACTU)
            .dateObt(DEFAULT_DATE_OBT)
            .lieuActuel(DEFAULT_LIEU_ACTUEL)
            .dernierProp(DEFAULT_DERNIER_PROP);
        return ficheTracabilite;
    }

    @Before
    public void initTest() {
        ficheTracabiliteRepository.deleteAll();
        ficheTracabilite = createEntity();
    }

    @Test
    public void createFicheTracabilite() throws Exception {
        int databaseSizeBeforeCreate = ficheTracabiliteRepository.findAll().size();

        // Create the FicheTracabilite
        restFicheTracabiliteMockMvc.perform(post("/api/fiche-tracabilites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheTracabilite)))
            .andExpect(status().isCreated());

        // Validate the FicheTracabilite in the database
        List<FicheTracabilite> ficheTracabiliteList = ficheTracabiliteRepository.findAll();
        assertThat(ficheTracabiliteList).hasSize(databaseSizeBeforeCreate + 1);
        FicheTracabilite testFicheTracabilite = ficheTracabiliteList.get(ficheTracabiliteList.size() - 1);
        assertThat(testFicheTracabilite.getProprietaireActu()).isEqualTo(DEFAULT_PROPRIETAIRE_ACTU);
        assertThat(testFicheTracabilite.getDateObt()).isEqualTo(DEFAULT_DATE_OBT);
        assertThat(testFicheTracabilite.getLieuActuel()).isEqualTo(DEFAULT_LIEU_ACTUEL);
        assertThat(testFicheTracabilite.getDernierProp()).isEqualTo(DEFAULT_DERNIER_PROP);
    }

    @Test
    public void createFicheTracabiliteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ficheTracabiliteRepository.findAll().size();

        // Create the FicheTracabilite with an existing ID
        ficheTracabilite.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFicheTracabiliteMockMvc.perform(post("/api/fiche-tracabilites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheTracabilite)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FicheTracabilite> ficheTracabiliteList = ficheTracabiliteRepository.findAll();
        assertThat(ficheTracabiliteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkProprietaireActuIsRequired() throws Exception {
        int databaseSizeBeforeTest = ficheTracabiliteRepository.findAll().size();
        // set the field null
        ficheTracabilite.setProprietaireActu(null);

        // Create the FicheTracabilite, which fails.

        restFicheTracabiliteMockMvc.perform(post("/api/fiche-tracabilites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheTracabilite)))
            .andExpect(status().isBadRequest());

        List<FicheTracabilite> ficheTracabiliteList = ficheTracabiliteRepository.findAll();
        assertThat(ficheTracabiliteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDateObtIsRequired() throws Exception {
        int databaseSizeBeforeTest = ficheTracabiliteRepository.findAll().size();
        // set the field null
        ficheTracabilite.setDateObt(null);

        // Create the FicheTracabilite, which fails.

        restFicheTracabiliteMockMvc.perform(post("/api/fiche-tracabilites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheTracabilite)))
            .andExpect(status().isBadRequest());

        List<FicheTracabilite> ficheTracabiliteList = ficheTracabiliteRepository.findAll();
        assertThat(ficheTracabiliteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLieuActuelIsRequired() throws Exception {
        int databaseSizeBeforeTest = ficheTracabiliteRepository.findAll().size();
        // set the field null
        ficheTracabilite.setLieuActuel(null);

        // Create the FicheTracabilite, which fails.

        restFicheTracabiliteMockMvc.perform(post("/api/fiche-tracabilites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheTracabilite)))
            .andExpect(status().isBadRequest());

        List<FicheTracabilite> ficheTracabiliteList = ficheTracabiliteRepository.findAll();
        assertThat(ficheTracabiliteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFicheTracabilites() throws Exception {
        // Initialize the database
        ficheTracabiliteRepository.save(ficheTracabilite);

        // Get all the ficheTracabiliteList
        restFicheTracabiliteMockMvc.perform(get("/api/fiche-tracabilites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ficheTracabilite.getId())))
            .andExpect(jsonPath("$.[*].proprietaireActu").value(hasItem(DEFAULT_PROPRIETAIRE_ACTU.toString())))
            .andExpect(jsonPath("$.[*].dateObt").value(hasItem(DEFAULT_DATE_OBT.toString())))
            .andExpect(jsonPath("$.[*].lieuActuel").value(hasItem(DEFAULT_LIEU_ACTUEL.toString())))
            .andExpect(jsonPath("$.[*].dernierProp").value(hasItem(DEFAULT_DERNIER_PROP.toString())));
    }

    @Test
    public void getFicheTracabilite() throws Exception {
        // Initialize the database
        ficheTracabiliteRepository.save(ficheTracabilite);

        // Get the ficheTracabilite
        restFicheTracabiliteMockMvc.perform(get("/api/fiche-tracabilites/{id}", ficheTracabilite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ficheTracabilite.getId()))
            .andExpect(jsonPath("$.proprietaireActu").value(DEFAULT_PROPRIETAIRE_ACTU.toString()))
            .andExpect(jsonPath("$.dateObt").value(DEFAULT_DATE_OBT.toString()))
            .andExpect(jsonPath("$.lieuActuel").value(DEFAULT_LIEU_ACTUEL.toString()))
            .andExpect(jsonPath("$.dernierProp").value(DEFAULT_DERNIER_PROP.toString()));
    }

    @Test
    public void getNonExistingFicheTracabilite() throws Exception {
        // Get the ficheTracabilite
        restFicheTracabiliteMockMvc.perform(get("/api/fiche-tracabilites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFicheTracabilite() throws Exception {
        // Initialize the database
        ficheTracabiliteRepository.save(ficheTracabilite);
        int databaseSizeBeforeUpdate = ficheTracabiliteRepository.findAll().size();

        // Update the ficheTracabilite
        FicheTracabilite updatedFicheTracabilite = ficheTracabiliteRepository.findOne(ficheTracabilite.getId());
        updatedFicheTracabilite
            .proprietaireActu(UPDATED_PROPRIETAIRE_ACTU)
            .dateObt(UPDATED_DATE_OBT)
            .lieuActuel(UPDATED_LIEU_ACTUEL)
            .dernierProp(UPDATED_DERNIER_PROP);

        restFicheTracabiliteMockMvc.perform(put("/api/fiche-tracabilites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFicheTracabilite)))
            .andExpect(status().isOk());

        // Validate the FicheTracabilite in the database
        List<FicheTracabilite> ficheTracabiliteList = ficheTracabiliteRepository.findAll();
        assertThat(ficheTracabiliteList).hasSize(databaseSizeBeforeUpdate);
        FicheTracabilite testFicheTracabilite = ficheTracabiliteList.get(ficheTracabiliteList.size() - 1);
        assertThat(testFicheTracabilite.getProprietaireActu()).isEqualTo(UPDATED_PROPRIETAIRE_ACTU);
        assertThat(testFicheTracabilite.getDateObt()).isEqualTo(UPDATED_DATE_OBT);
        assertThat(testFicheTracabilite.getLieuActuel()).isEqualTo(UPDATED_LIEU_ACTUEL);
        assertThat(testFicheTracabilite.getDernierProp()).isEqualTo(UPDATED_DERNIER_PROP);
    }

    @Test
    public void updateNonExistingFicheTracabilite() throws Exception {
        int databaseSizeBeforeUpdate = ficheTracabiliteRepository.findAll().size();

        // Create the FicheTracabilite

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFicheTracabiliteMockMvc.perform(put("/api/fiche-tracabilites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ficheTracabilite)))
            .andExpect(status().isCreated());

        // Validate the FicheTracabilite in the database
        List<FicheTracabilite> ficheTracabiliteList = ficheTracabiliteRepository.findAll();
        assertThat(ficheTracabiliteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFicheTracabilite() throws Exception {
        // Initialize the database
        ficheTracabiliteRepository.save(ficheTracabilite);
        int databaseSizeBeforeDelete = ficheTracabiliteRepository.findAll().size();

        // Get the ficheTracabilite
        restFicheTracabiliteMockMvc.perform(delete("/api/fiche-tracabilites/{id}", ficheTracabilite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FicheTracabilite> ficheTracabiliteList = ficheTracabiliteRepository.findAll();
        assertThat(ficheTracabiliteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FicheTracabilite.class);
    }
}
