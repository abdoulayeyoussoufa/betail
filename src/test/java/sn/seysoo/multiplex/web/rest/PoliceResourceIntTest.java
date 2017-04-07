package sn.seysoo.multiplex.web.rest;

import sn.seysoo.multiplex.MutuplexApp;

import sn.seysoo.multiplex.domain.Police;
import sn.seysoo.multiplex.repository.PoliceRepository;
import sn.seysoo.multiplex.service.PoliceService;

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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PoliceResource REST controller.
 *
 * @see PoliceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MutuplexApp.class)
public class PoliceResourceIntTest {

    private static final String DEFAULT_RAISON = "AAAAA";
    private static final String UPDATED_RAISON = "BBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAA";
    private static final String UPDATED_ADRESSE = "BBBBB";

    private static final String DEFAULT_TEL = "AAAAA";
    private static final String UPDATED_TEL = "BBBBB";

    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    private static final String DEFAULT_INTERLOC = "AAAAA";
    private static final String UPDATED_INTERLOC = "BBBBB";

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TIERS = "AAAAA";
    private static final String UPDATED_TIERS = "BBBBB";

    private static final Double DEFAULT_TPX = 1D;
    private static final Double UPDATED_TPX = 2D;

    private static final String DEFAULT_ACTIVITE = "AAAAA";
    private static final String UPDATED_ACTIVITE = "BBBBB";

    private static final String DEFAULT_FORMULE_SOINS = "AAAAA";
    private static final String UPDATED_FORMULE_SOINS = "BBBBB";

    private static final String DEFAULT_TERRITOIRE = "AAAAA";
    private static final String UPDATED_TERRITOIRE = "BBBBB";

    private static final Integer DEFAULT_PLAFOND = 1;
    private static final Integer UPDATED_PLAFOND = 2;

    private static final String DEFAULT_COMPLEMENT = "AAAAA";
    private static final String UPDATED_COMPLEMENT = "BBBBB";

    private static final Float DEFAULT_CONSULTATION_TAUX = 1F;
    private static final Float UPDATED_CONSULTATION_TAUX = 2F;

    private static final String DEFAULT_CONSULTATION_FRAIS = "AAAAA";
    private static final String UPDATED_CONSULTATION_FRAIS = "BBBBB";

    private static final String DEFAULT_CONSULTATION_LIMITE = "AAAAA";
    private static final String UPDATED_CONSULTATION_LIMITE = "BBBBB";

    private static final Float DEFAULT_CONSULTATION_JOUR = 1F;
    private static final Float UPDATED_CONSULTATION_JOUR = 2F;

    private static final Float DEFAULT_CONSULTATION_AN = 1F;
    private static final Float UPDATED_CONSULTATION_AN = 2F;

    private static final Float DEFAULT_CONSULTATION_DEUX_AN = 1F;
    private static final Float UPDATED_CONSULTATION_DEUX_AN = 2F;

    private static final Float DEFAULT_PHARMACIE_TAUX = 1F;
    private static final Float UPDATED_PHARMACIE_TAUX = 2F;

    private static final String DEFAULT_PHARMACIE_FRAIS = "AAAAA";
    private static final String UPDATED_PHARMACIE_FRAIS = "BBBBB";

    private static final String DEFAULT_PHARMACIE_LIMITE = "AAAAA";
    private static final String UPDATED_PHARMACIE_LIMITE = "BBBBB";

    private static final Float DEFAULT_PHARMACIE_JOUR = 1F;
    private static final Float UPDATED_PHARMACIE_JOUR = 2F;

    private static final Float DEFAULT_PHARMACIE_AN = 1F;
    private static final Float UPDATED_PHARMACIE_AN = 2F;

    private static final Float DEFAULT_PHARMACIE_DEUX_AN = 1F;
    private static final Float UPDATED_PHARMACIE_DEUX_AN = 2F;

    private static final Float DEFAULT_ANALYSE_TAUX = 1F;
    private static final Float UPDATED_ANALYSE_TAUX = 2F;

    private static final String DEFAULT_ANALYSE_FRAIS = "AAAAA";
    private static final String UPDATED_ANALYSE_FRAIS = "BBBBB";

    private static final String DEFAULT_ANALYSE_LIMITE = "AAAAA";
    private static final String UPDATED_ANALYSE_LIMITE = "BBBBB";

    private static final Float DEFAULT_ANALYSE_JOUR = 1F;
    private static final Float UPDATED_ANALYSE_JOUR = 2F;

    private static final Float DEFAULT_ANALYSE_AN = 1F;
    private static final Float UPDATED_ANALYSE_AN = 2F;

    private static final Float DEFAULT_ANALYSE_DEUX_AN = 1F;
    private static final Float UPDATED_ANALYSE_DEUX_AN = 2F;

    private static final Float DEFAULT_ACTE_TAUX = 1F;
    private static final Float UPDATED_ACTE_TAUX = 2F;

    private static final String DEFAULT_ACTE_FRAIS = "AAAAA";
    private static final String UPDATED_ACTE_FRAIS = "BBBBB";

    private static final String DEFAULT_ACTE_LIMITE = "AAAAA";
    private static final String UPDATED_ACTE_LIMITE = "BBBBB";

    private static final Float DEFAULT_ACTE_JOUR = 1F;
    private static final Float UPDATED_ACTE_JOUR = 2F;

    private static final Float DEFAULT_ACTE_AN = 1F;
    private static final Float UPDATED_ACTE_AN = 2F;

    private static final Float DEFAULT_ACTE_DEUX_AN = 1F;
    private static final Float UPDATED_ACTE_DEUX_AN = 2F;

    private static final Float DEFAULT_PRINCIPAL_CHAMBRE_TAUX = 1F;
    private static final Float UPDATED_PRINCIPAL_CHAMBRE_TAUX = 2F;

    private static final String DEFAULT_PRINCIPAL_CHAMBRE_FRAIS = "AAAAA";
    private static final String UPDATED_PRINCIPAL_CHAMBRE_FRAIS = "BBBBB";

    private static final String DEFAULT_PRINCIPAL_CHAMBRE_LIMITE = "AAAAA";
    private static final String UPDATED_PRINCIPAL_CHAMBRE_LIMITE = "BBBBB";

    private static final Float DEFAULT_PRINCIPAL_CHAMBRE_JOUR = 1F;
    private static final Float UPDATED_PRINCIPAL_CHAMBRE_JOUR = 2F;

    private static final Float DEFAULT_PRINCIPAL_CHAMBRE_AN = 1F;
    private static final Float UPDATED_PRINCIPAL_CHAMBRE_AN = 2F;

    private static final Float DEFAULT_PRINCIPAL_CHAMBRE_DEUX_AN = 1F;
    private static final Float UPDATED_PRINCIPAL_CHAMBRE_DEUX_AN = 2F;

    private static final Float DEFAULT_PRINCIPAL_FRAIS_TAUX = 1F;
    private static final Float UPDATED_PRINCIPAL_FRAIS_TAUX = 2F;

    private static final String DEFAULT_PRINCIPAL_FRAIS_LIMITE = "AAAAA";
    private static final String UPDATED_PRINCIPAL_FRAIS_LIMITE = "BBBBB";

    private static final Float DEFAULT_PRINCIPAL_FRAIS_JOUR = 1F;
    private static final Float UPDATED_PRINCIPAL_FRAIS_JOUR = 2F;

    private static final Float DEFAULT_PRINCIPAL_FRAIS_AN = 1F;
    private static final Float UPDATED_PRINCIPAL_FRAIS_AN = 2F;

    private static final Float DEFAULT_PRINCIPAL_FRAIS_DEUX_AN = 1F;
    private static final Float UPDATED_PRINCIPAL_FRAIS_DEUX_AN = 2F;

    private static final Float DEFAULT_PRIVE_CHAMBRE_TAUX = 1F;
    private static final Float UPDATED_PRIVE_CHAMBRE_TAUX = 2F;

    private static final String DEFAULT_PRIVE_CHAMBRE_FRAIS = "AAAAA";
    private static final String UPDATED_PRIVE_CHAMBRE_FRAIS = "BBBBB";

    private static final String DEFAULT_PRIVE_CHAMBRE_LIMITE = "AAAAA";
    private static final String UPDATED_PRIVE_CHAMBRE_LIMITE = "BBBBB";

    private static final Float DEFAULT_PRIVE_CHAMBRE_JOUR = 1F;
    private static final Float UPDATED_PRIVE_CHAMBRE_JOUR = 2F;

    private static final Float DEFAULT_PRIVE_CHAMBRE_AN = 1F;
    private static final Float UPDATED_PRIVE_CHAMBRE_AN = 2F;

    private static final Float DEFAULT_PRIVE_CHAMBRE_DEUX_AN = 1F;
    private static final Float UPDATED_PRIVE_CHAMBRE_DEUX_AN = 2F;

    private static final Float DEFAULT_PRIVE_FRAIS_TAUX = 1F;
    private static final Float UPDATED_PRIVE_FRAIS_TAUX = 2F;

    private static final String DEFAULT_PRIVE_FRAIS_LIMITE = "AAAAA";
    private static final String UPDATED_PRIVE_FRAIS_LIMITE = "BBBBB";

    private static final Float DEFAULT_PRIVE_FRAIS_JOUR = 1F;
    private static final Float UPDATED_PRIVE_FRAIS_JOUR = 2F;

    private static final Float DEFAULT_PRIVE_FRAIS_AN = 1F;
    private static final Float UPDATED_PRIVE_FRAIS_AN = 2F;

    private static final Float DEFAULT_PRIVE_FRAIS_DEUX_AN = 1F;
    private static final Float UPDATED_PRIVE_FRAIS_DEUX_AN = 2F;

    private static final Float DEFAULT_PUBLIC_CHAMBRE_TAUX = 1F;
    private static final Float UPDATED_PUBLIC_CHAMBRE_TAUX = 2F;

    private static final String DEFAULT_PUBLIC_CHAMBRE_FRAIS = "AAAAA";
    private static final String UPDATED_PUBLIC_CHAMBRE_FRAIS = "BBBBB";

    private static final String DEFAULT_PUBLIC_CHAMBRE_LIMITE = "AAAAA";
    private static final String UPDATED_PUBLIC_CHAMBRE_LIMITE = "BBBBB";

    private static final Float DEFAULT_PUBLIC_CHAMBRE_JOUR = 1F;
    private static final Float UPDATED_PUBLIC_CHAMBRE_JOUR = 2F;

    private static final Float DEFAULT_PUBLIC_CHAMBRE_AN = 1F;
    private static final Float UPDATED_PUBLIC_CHAMBRE_AN = 2F;

    private static final Float DEFAULT_PUBLIC_CHAMBRE_DEUX_AN = 1F;
    private static final Float UPDATED_PUBLIC_CHAMBRE_DEUX_AN = 2F;

    private static final Float DEFAULT_PUBLIC_FRAIS_TAUX = 1F;
    private static final Float UPDATED_PUBLIC_FRAIS_TAUX = 2F;

    private static final String DEFAULT_PUBLIC_FRAIS_FRAIS = "AAAAA";
    private static final String UPDATED_PUBLIC_FRAIS_FRAIS = "BBBBB";

    private static final String DEFAULT_PUBLIC_FRAIS_LIMITE = "AAAAA";
    private static final String UPDATED_PUBLIC_FRAIS_LIMITE = "BBBBB";

    private static final Float DEFAULT_PUBLIC_FRAIS_JOUR = 1F;
    private static final Float UPDATED_PUBLIC_FRAIS_JOUR = 2F;

    private static final Float DEFAULT_PUBLIC_FRAIS_AN = 1F;
    private static final Float UPDATED_PUBLIC_FRAIS_AN = 2F;

    private static final Float DEFAULT_PUBLIC_FRAIS_DEUX_AN = 1F;
    private static final Float UPDATED_PUBLIC_FRAIS_DEUX_AN = 2F;

    private static final Float DEFAULT_SOINS_TAUX = 1F;
    private static final Float UPDATED_SOINS_TAUX = 2F;

    private static final String DEFAULT_SOINS_FRAIS = "AAAAA";
    private static final String UPDATED_SOINS_FRAIS = "BBBBB";

    private static final String DEFAULT_SOINS_LIMITE = "AAAAA";
    private static final String UPDATED_SOINS_LIMITE = "BBBBB";

    private static final Float DEFAULT_SOINS_JOUR = 1F;
    private static final Float UPDATED_SOINS_JOUR = 2F;

    private static final Float DEFAULT_SOINS_AN = 1F;
    private static final Float UPDATED_SOINS_AN = 2F;

    private static final Float DEFAULT_SOINS_DEUX_AN = 1F;
    private static final Float UPDATED_SOINS_DEUX_AN = 2F;

    private static final Float DEFAULT_VERRE_TAUX = 1F;
    private static final Float UPDATED_VERRE_TAUX = 2F;

    private static final String DEFAULT_VERRE_FRAIS = "AAAAA";
    private static final String UPDATED_VERRE_FRAIS = "BBBBB";

    private static final String DEFAULT_VERRE_LIMITE = "AAAAA";
    private static final String UPDATED_VERRE_LIMITE = "BBBBB";

    private static final Float DEFAULT_VERRE_JOUR = 1F;
    private static final Float UPDATED_VERRE_JOUR = 2F;

    private static final Float DEFAULT_VERRE_AN = 1F;
    private static final Float UPDATED_VERRE_AN = 2F;

    private static final Float DEFAULT_VERRE_DEUX_AN = 1F;
    private static final Float UPDATED_VERRE_DEUX_AN = 2F;

    private static final Float DEFAULT_MONTURE_TAUX = 1F;
    private static final Float UPDATED_MONTURE_TAUX = 2F;

    private static final String DEFAULT_MONTURE_FRAIS = "AAAAA";
    private static final String UPDATED_MONTURE_FRAIS = "BBBBB";

    private static final String DEFAULT_MONTURE_LIMITE = "AAAAA";
    private static final String UPDATED_MONTURE_LIMITE = "BBBBB";

    private static final Float DEFAULT_MONTURE_JOUR = 1F;
    private static final Float UPDATED_MONTURE_JOUR = 2F;

    private static final Float DEFAULT_MONTURE_AN = 1F;
    private static final Float UPDATED_MONTURE_AN = 2F;

    private static final Float DEFAULT_MONTURE_DEUX_AN = 1F;
    private static final Float UPDATED_MONTURE_DEUX_AN = 2F;

    private static final Float DEFAULT_ACCOUCHEMENT_TAUX = 1F;
    private static final Float UPDATED_ACCOUCHEMENT_TAUX = 2F;

    private static final String DEFAULT_ACCOUCHEMENT_FRAIS = "AAAAA";
    private static final String UPDATED_ACCOUCHEMENT_FRAIS = "BBBBB";

    private static final String DEFAULT_ACCOUCHEMENT_LIMITE = "AAAAA";
    private static final String UPDATED_ACCOUCHEMENT_LIMITE = "BBBBB";

    private static final Float DEFAULT_ACCOUCHEMENT_JOUR = 1F;
    private static final Float UPDATED_ACCOUCHEMENT_JOUR = 2F;

    private static final String DEFAULT_ACCOUCHEMENT_AN = "AAAAA";
    private static final String UPDATED_ACCOUCHEMENT_AN = "BBBBB";

    private static final Float DEFAULT_ACCOUCHEMENT_DEUX_AN = 1F;
    private static final Float UPDATED_ACCOUCHEMENT_DEUX_AN = 2F;

    private static final Float DEFAULT_EDUCATION_TAUX = 1F;
    private static final Float UPDATED_EDUCATION_TAUX = 2F;

    private static final String DEFAULT_EDUCATION_FRAIS = "AAAAA";
    private static final String UPDATED_EDUCATION_FRAIS = "BBBBB";

    private static final String DEFAULT_EDUCATION_LIMITE = "AAAAA";
    private static final String UPDATED_EDUCATION_LIMITE = "BBBBB";

    private static final Float DEFAULT_EDUCATION_JOUR = 1F;
    private static final Float UPDATED_EDUCATION_JOUR = 2F;

    private static final Float DEFAULT_EDUCATION_AN = 1F;
    private static final Float UPDATED_EDUCATION_AN = 2F;

    private static final Float DEFAULT_EDUCATION_DEUX_AN = 1F;
    private static final Float UPDATED_EDUCATION_DEUX_AN = 2F;

    private static final Float DEFAULT_SEJOUR_TAUX = 1F;
    private static final Float UPDATED_SEJOUR_TAUX = 2F;

    private static final String DEFAULT_SEJOUR_FRAIS = "AAAAA";
    private static final String UPDATED_SEJOUR_FRAIS = "BBBBB";

    private static final String DEFAULT_SEJOUR_LIMITE = "AAAAA";
    private static final String UPDATED_SEJOUR_LIMITE = "BBBBB";

    private static final Float DEFAULT_SEJOUR_JOUR = 1F;
    private static final Float UPDATED_SEJOUR_JOUR = 2F;

    private static final Float DEFAULT_SEJOUR_AN = 1F;
    private static final Float UPDATED_SEJOUR_AN = 2F;

    private static final Float DEFAULT_SEJOUR_DEUX_AN = 1F;
    private static final Float UPDATED_SEJOUR_DEUX_AN = 2F;

    private static final Integer DEFAULT_NB_COLLAB = 1;
    private static final Integer UPDATED_NB_COLLAB = 2;

    private static final Integer DEFAULT_NB_CONJOINT = 1;
    private static final Integer UPDATED_NB_CONJOINT = 2;

    private static final Integer DEFAULT_NB_ENFANT_P = 1;
    private static final Integer UPDATED_NB_ENFANT_P = 2;

    private static final Integer DEFAULT_NB_ENFANT_G = 1;
    private static final Integer UPDATED_NB_ENFANT_G = 2;

    private static final String DEFAULT_FIN = "AAAAA";
    private static final String UPDATED_FIN = "BBBBB";

    @Inject
    private PoliceRepository policeRepository;

    @Inject
    private PoliceService policeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPoliceMockMvc;

    private Police police;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PoliceResource policeResource = new PoliceResource();
        ReflectionTestUtils.setField(policeResource, "policeService", policeService);
        this.restPoliceMockMvc = MockMvcBuilders.standaloneSetup(policeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Police createEntity(EntityManager em) {
        Police police = new Police()
                .raison(DEFAULT_RAISON)
                .adresse(DEFAULT_ADRESSE)
                .tel(DEFAULT_TEL)
                .email(DEFAULT_EMAIL)
                .interloc(DEFAULT_INTERLOC)
                .dateDebut(DEFAULT_DATE_DEBUT)
                .dateFin(DEFAULT_DATE_FIN)
                .tiers(DEFAULT_TIERS)
                .tpx(DEFAULT_TPX)
                .activite(DEFAULT_ACTIVITE)
                .formule_soins(DEFAULT_FORMULE_SOINS)
                .territoire(DEFAULT_TERRITOIRE)
                .plafond(DEFAULT_PLAFOND)
                .complement(DEFAULT_COMPLEMENT)
                .consultation_taux(DEFAULT_CONSULTATION_TAUX)
                .consultation_frais(DEFAULT_CONSULTATION_FRAIS)
                .consultation_limite(DEFAULT_CONSULTATION_LIMITE)
                .consultation_jour(DEFAULT_CONSULTATION_JOUR)
                .consultation_an(DEFAULT_CONSULTATION_AN)
                .consultation_deux_an(DEFAULT_CONSULTATION_DEUX_AN)
                .pharmacie_taux(DEFAULT_PHARMACIE_TAUX)
                .pharmacie_frais(DEFAULT_PHARMACIE_FRAIS)
                .pharmacie_limite(DEFAULT_PHARMACIE_LIMITE)
                .pharmacie_jour(DEFAULT_PHARMACIE_JOUR)
                .pharmacie_an(DEFAULT_PHARMACIE_AN)
                .pharmacie_deux_an(DEFAULT_PHARMACIE_DEUX_AN)
                .analyse_taux(DEFAULT_ANALYSE_TAUX)
                .analyse_frais(DEFAULT_ANALYSE_FRAIS)
                .analyse_limite(DEFAULT_ANALYSE_LIMITE)
                .analyse_jour(DEFAULT_ANALYSE_JOUR)
                .analyse_an(DEFAULT_ANALYSE_AN)
                .analyse_deux_an(DEFAULT_ANALYSE_DEUX_AN)
                .acte_taux(DEFAULT_ACTE_TAUX)
                .acte_frais(DEFAULT_ACTE_FRAIS)
                .acte_limite(DEFAULT_ACTE_LIMITE)
                .acte_jour(DEFAULT_ACTE_JOUR)
                .acte_an(DEFAULT_ACTE_AN)
                .acte_deux_an(DEFAULT_ACTE_DEUX_AN)
                .principal_chambre_taux(DEFAULT_PRINCIPAL_CHAMBRE_TAUX)
                .principal_chambre_frais(DEFAULT_PRINCIPAL_CHAMBRE_FRAIS)
                .principal_chambre_limite(DEFAULT_PRINCIPAL_CHAMBRE_LIMITE)
                .principal_chambre_jour(DEFAULT_PRINCIPAL_CHAMBRE_JOUR)
                .principal_chambre_an(DEFAULT_PRINCIPAL_CHAMBRE_AN)
                .principal_chambre_deux_an(DEFAULT_PRINCIPAL_CHAMBRE_DEUX_AN)
                .principal_frais_taux(DEFAULT_PRINCIPAL_FRAIS_TAUX)
                .principal_frais_limite(DEFAULT_PRINCIPAL_FRAIS_LIMITE)
                .principal_frais_jour(DEFAULT_PRINCIPAL_FRAIS_JOUR)
                .principal_frais_an(DEFAULT_PRINCIPAL_FRAIS_AN)
                .principal_frais_deux_an(DEFAULT_PRINCIPAL_FRAIS_DEUX_AN)
                .prive_chambre_taux(DEFAULT_PRIVE_CHAMBRE_TAUX)
                .prive_chambre_frais(DEFAULT_PRIVE_CHAMBRE_FRAIS)
                .prive_chambre_limite(DEFAULT_PRIVE_CHAMBRE_LIMITE)
                .prive_chambre_jour(DEFAULT_PRIVE_CHAMBRE_JOUR)
                .prive_chambre_an(DEFAULT_PRIVE_CHAMBRE_AN)
                .prive_chambre_deux_an(DEFAULT_PRIVE_CHAMBRE_DEUX_AN)
                .prive_frais_taux(DEFAULT_PRIVE_FRAIS_TAUX)
                .prive_frais_limite(DEFAULT_PRIVE_FRAIS_LIMITE)
                .prive_frais_jour(DEFAULT_PRIVE_FRAIS_JOUR)
                .prive_frais_an(DEFAULT_PRIVE_FRAIS_AN)
                .prive_frais_deux_an(DEFAULT_PRIVE_FRAIS_DEUX_AN)
                .public_chambre_taux(DEFAULT_PUBLIC_CHAMBRE_TAUX)
                .public_chambre_frais(DEFAULT_PUBLIC_CHAMBRE_FRAIS)
                .public_chambre_limite(DEFAULT_PUBLIC_CHAMBRE_LIMITE)
                .public_chambre_jour(DEFAULT_PUBLIC_CHAMBRE_JOUR)
                .public_chambre_an(DEFAULT_PUBLIC_CHAMBRE_AN)
                .public_chambre_deux_an(DEFAULT_PUBLIC_CHAMBRE_DEUX_AN)
                .public_frais_taux(DEFAULT_PUBLIC_FRAIS_TAUX)
                .public_frais_frais(DEFAULT_PUBLIC_FRAIS_FRAIS)
                .public_frais_limite(DEFAULT_PUBLIC_FRAIS_LIMITE)
                .public_frais_jour(DEFAULT_PUBLIC_FRAIS_JOUR)
                .public_frais_an(DEFAULT_PUBLIC_FRAIS_AN)
                .public_frais_deux_an(DEFAULT_PUBLIC_FRAIS_DEUX_AN)
                .soins_taux(DEFAULT_SOINS_TAUX)
                .soins_frais(DEFAULT_SOINS_FRAIS)
                .soins_limite(DEFAULT_SOINS_LIMITE)
                .soins_jour(DEFAULT_SOINS_JOUR)
                .soins_an(DEFAULT_SOINS_AN)
                .soins_deux_an(DEFAULT_SOINS_DEUX_AN)
                .verre_taux(DEFAULT_VERRE_TAUX)
                .verre_frais(DEFAULT_VERRE_FRAIS)
                .verre_limite(DEFAULT_VERRE_LIMITE)
                .verre_jour(DEFAULT_VERRE_JOUR)
                .verre_an(DEFAULT_VERRE_AN)
                .verre_deux_an(DEFAULT_VERRE_DEUX_AN)
                .monture_taux(DEFAULT_MONTURE_TAUX)
                .monture_frais(DEFAULT_MONTURE_FRAIS)
                .monture_limite(DEFAULT_MONTURE_LIMITE)
                .monture_jour(DEFAULT_MONTURE_JOUR)
                .monture_an(DEFAULT_MONTURE_AN)
                .monture_deux_an(DEFAULT_MONTURE_DEUX_AN)
                .accouchement_taux(DEFAULT_ACCOUCHEMENT_TAUX)
                .accouchement_frais(DEFAULT_ACCOUCHEMENT_FRAIS)
                .accouchement_limite(DEFAULT_ACCOUCHEMENT_LIMITE)
                .accouchement_jour(DEFAULT_ACCOUCHEMENT_JOUR)
                .accouchement_an(DEFAULT_ACCOUCHEMENT_AN)
                .accouchement_deux_an(DEFAULT_ACCOUCHEMENT_DEUX_AN)
                .education_taux(DEFAULT_EDUCATION_TAUX)
                .education_frais(DEFAULT_EDUCATION_FRAIS)
                .education_limite(DEFAULT_EDUCATION_LIMITE)
                .education_jour(DEFAULT_EDUCATION_JOUR)
                .education_an(DEFAULT_EDUCATION_AN)
                .education_deux_an(DEFAULT_EDUCATION_DEUX_AN)
                .sejour_taux(DEFAULT_SEJOUR_TAUX)
                .sejour_frais(DEFAULT_SEJOUR_FRAIS)
                .sejour_limite(DEFAULT_SEJOUR_LIMITE)
                .sejour_jour(DEFAULT_SEJOUR_JOUR)
                .sejour_an(DEFAULT_SEJOUR_AN)
                .sejour_deux_an(DEFAULT_SEJOUR_DEUX_AN)
                .nbCollab(DEFAULT_NB_COLLAB)
                .nbConjoint(DEFAULT_NB_CONJOINT)
                .nbEnfantP(DEFAULT_NB_ENFANT_P)
                .nbEnfantG(DEFAULT_NB_ENFANT_G)
                .fin(DEFAULT_FIN);
        return police;
    }

    @Before
    public void initTest() {
        police = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolice() throws Exception {
        int databaseSizeBeforeCreate = policeRepository.findAll().size();

        // Create the Police

        restPoliceMockMvc.perform(post("/api/police")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(police)))
                .andExpect(status().isCreated());

        // Validate the Police in the database
        List<Police> police = policeRepository.findAll();
        assertThat(police).hasSize(databaseSizeBeforeCreate + 1);
        Police testPolice = police.get(police.size() - 1);
        assertThat(testPolice.getRaison()).isEqualTo(DEFAULT_RAISON);
        assertThat(testPolice.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testPolice.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testPolice.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPolice.getInterloc()).isEqualTo(DEFAULT_INTERLOC);
        assertThat(testPolice.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testPolice.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testPolice.getTiers()).isEqualTo(DEFAULT_TIERS);
        assertThat(testPolice.getTpx()).isEqualTo(DEFAULT_TPX);
        assertThat(testPolice.getActivite()).isEqualTo(DEFAULT_ACTIVITE);
        assertThat(testPolice.getFormule_soins()).isEqualTo(DEFAULT_FORMULE_SOINS);
        assertThat(testPolice.getTerritoire()).isEqualTo(DEFAULT_TERRITOIRE);
        assertThat(testPolice.getPlafond()).isEqualTo(DEFAULT_PLAFOND);
        assertThat(testPolice.getComplement()).isEqualTo(DEFAULT_COMPLEMENT);
        assertThat(testPolice.getConsultation_taux()).isEqualTo(DEFAULT_CONSULTATION_TAUX);
        assertThat(testPolice.getConsultation_frais()).isEqualTo(DEFAULT_CONSULTATION_FRAIS);
        assertThat(testPolice.getConsultation_limite()).isEqualTo(DEFAULT_CONSULTATION_LIMITE);
        assertThat(testPolice.getConsultation_jour()).isEqualTo(DEFAULT_CONSULTATION_JOUR);
        assertThat(testPolice.getConsultation_an()).isEqualTo(DEFAULT_CONSULTATION_AN);
        assertThat(testPolice.getConsultation_deux_an()).isEqualTo(DEFAULT_CONSULTATION_DEUX_AN);
        assertThat(testPolice.getPharmacie_taux()).isEqualTo(DEFAULT_PHARMACIE_TAUX);
        assertThat(testPolice.getPharmacie_frais()).isEqualTo(DEFAULT_PHARMACIE_FRAIS);
        assertThat(testPolice.getPharmacie_limite()).isEqualTo(DEFAULT_PHARMACIE_LIMITE);
        assertThat(testPolice.getPharmacie_jour()).isEqualTo(DEFAULT_PHARMACIE_JOUR);
        assertThat(testPolice.getPharmacie_an()).isEqualTo(DEFAULT_PHARMACIE_AN);
        assertThat(testPolice.getPharmacie_deux_an()).isEqualTo(DEFAULT_PHARMACIE_DEUX_AN);
        assertThat(testPolice.getAnalyse_taux()).isEqualTo(DEFAULT_ANALYSE_TAUX);
        assertThat(testPolice.getAnalyse_frais()).isEqualTo(DEFAULT_ANALYSE_FRAIS);
        assertThat(testPolice.getAnalyse_limite()).isEqualTo(DEFAULT_ANALYSE_LIMITE);
        assertThat(testPolice.getAnalyse_jour()).isEqualTo(DEFAULT_ANALYSE_JOUR);
        assertThat(testPolice.getAnalyse_an()).isEqualTo(DEFAULT_ANALYSE_AN);
        assertThat(testPolice.getAnalyse_deux_an()).isEqualTo(DEFAULT_ANALYSE_DEUX_AN);
        assertThat(testPolice.getActe_taux()).isEqualTo(DEFAULT_ACTE_TAUX);
        assertThat(testPolice.getActe_frais()).isEqualTo(DEFAULT_ACTE_FRAIS);
        assertThat(testPolice.getActe_limite()).isEqualTo(DEFAULT_ACTE_LIMITE);
        assertThat(testPolice.getActe_jour()).isEqualTo(DEFAULT_ACTE_JOUR);
        assertThat(testPolice.getActe_an()).isEqualTo(DEFAULT_ACTE_AN);
        assertThat(testPolice.getActe_deux_an()).isEqualTo(DEFAULT_ACTE_DEUX_AN);
        assertThat(testPolice.getPrincipal_chambre_taux()).isEqualTo(DEFAULT_PRINCIPAL_CHAMBRE_TAUX);
        assertThat(testPolice.getPrincipal_chambre_frais()).isEqualTo(DEFAULT_PRINCIPAL_CHAMBRE_FRAIS);
        assertThat(testPolice.getPrincipal_chambre_limite()).isEqualTo(DEFAULT_PRINCIPAL_CHAMBRE_LIMITE);
        assertThat(testPolice.getPrincipal_chambre_jour()).isEqualTo(DEFAULT_PRINCIPAL_CHAMBRE_JOUR);
        assertThat(testPolice.getPrincipal_chambre_an()).isEqualTo(DEFAULT_PRINCIPAL_CHAMBRE_AN);
        assertThat(testPolice.getPrincipal_chambre_deux_an()).isEqualTo(DEFAULT_PRINCIPAL_CHAMBRE_DEUX_AN);
        assertThat(testPolice.getPrincipal_frais_taux()).isEqualTo(DEFAULT_PRINCIPAL_FRAIS_TAUX);
        assertThat(testPolice.getPrincipal_frais_limite()).isEqualTo(DEFAULT_PRINCIPAL_FRAIS_LIMITE);
        assertThat(testPolice.getPrincipal_frais_jour()).isEqualTo(DEFAULT_PRINCIPAL_FRAIS_JOUR);
        assertThat(testPolice.getPrincipal_frais_an()).isEqualTo(DEFAULT_PRINCIPAL_FRAIS_AN);
        assertThat(testPolice.getPrincipal_frais_deux_an()).isEqualTo(DEFAULT_PRINCIPAL_FRAIS_DEUX_AN);
        assertThat(testPolice.getPrive_chambre_taux()).isEqualTo(DEFAULT_PRIVE_CHAMBRE_TAUX);
        assertThat(testPolice.getPrive_chambre_frais()).isEqualTo(DEFAULT_PRIVE_CHAMBRE_FRAIS);
        assertThat(testPolice.getPrive_chambre_limite()).isEqualTo(DEFAULT_PRIVE_CHAMBRE_LIMITE);
        assertThat(testPolice.getPrive_chambre_jour()).isEqualTo(DEFAULT_PRIVE_CHAMBRE_JOUR);
        assertThat(testPolice.getPrive_chambre_an()).isEqualTo(DEFAULT_PRIVE_CHAMBRE_AN);
        assertThat(testPolice.getPrive_chambre_deux_an()).isEqualTo(DEFAULT_PRIVE_CHAMBRE_DEUX_AN);
        assertThat(testPolice.getPrive_frais_taux()).isEqualTo(DEFAULT_PRIVE_FRAIS_TAUX);
        assertThat(testPolice.getPrive_frais_limite()).isEqualTo(DEFAULT_PRIVE_FRAIS_LIMITE);
        assertThat(testPolice.getPrive_frais_jour()).isEqualTo(DEFAULT_PRIVE_FRAIS_JOUR);
        assertThat(testPolice.getPrive_frais_an()).isEqualTo(DEFAULT_PRIVE_FRAIS_AN);
        assertThat(testPolice.getPrive_frais_deux_an()).isEqualTo(DEFAULT_PRIVE_FRAIS_DEUX_AN);
        assertThat(testPolice.getPublic_chambre_taux()).isEqualTo(DEFAULT_PUBLIC_CHAMBRE_TAUX);
        assertThat(testPolice.getPublic_chambre_frais()).isEqualTo(DEFAULT_PUBLIC_CHAMBRE_FRAIS);
        assertThat(testPolice.getPublic_chambre_limite()).isEqualTo(DEFAULT_PUBLIC_CHAMBRE_LIMITE);
        assertThat(testPolice.getPublic_chambre_jour()).isEqualTo(DEFAULT_PUBLIC_CHAMBRE_JOUR);
        assertThat(testPolice.getPublic_chambre_an()).isEqualTo(DEFAULT_PUBLIC_CHAMBRE_AN);
        assertThat(testPolice.getPublic_chambre_deux_an()).isEqualTo(DEFAULT_PUBLIC_CHAMBRE_DEUX_AN);
        assertThat(testPolice.getPublic_frais_taux()).isEqualTo(DEFAULT_PUBLIC_FRAIS_TAUX);
        assertThat(testPolice.getPublic_frais_frais()).isEqualTo(DEFAULT_PUBLIC_FRAIS_FRAIS);
        assertThat(testPolice.getPublic_frais_limite()).isEqualTo(DEFAULT_PUBLIC_FRAIS_LIMITE);
        assertThat(testPolice.getPublic_frais_jour()).isEqualTo(DEFAULT_PUBLIC_FRAIS_JOUR);
        assertThat(testPolice.getPublic_frais_an()).isEqualTo(DEFAULT_PUBLIC_FRAIS_AN);
        assertThat(testPolice.getPublic_frais_deux_an()).isEqualTo(DEFAULT_PUBLIC_FRAIS_DEUX_AN);
        assertThat(testPolice.getSoins_taux()).isEqualTo(DEFAULT_SOINS_TAUX);
        assertThat(testPolice.getSoins_frais()).isEqualTo(DEFAULT_SOINS_FRAIS);
        assertThat(testPolice.getSoins_limite()).isEqualTo(DEFAULT_SOINS_LIMITE);
        assertThat(testPolice.getSoins_jour()).isEqualTo(DEFAULT_SOINS_JOUR);
        assertThat(testPolice.getSoins_an()).isEqualTo(DEFAULT_SOINS_AN);
        assertThat(testPolice.getSoins_deux_an()).isEqualTo(DEFAULT_SOINS_DEUX_AN);
        assertThat(testPolice.getVerre_taux()).isEqualTo(DEFAULT_VERRE_TAUX);
        assertThat(testPolice.getVerre_frais()).isEqualTo(DEFAULT_VERRE_FRAIS);
        assertThat(testPolice.getVerre_limite()).isEqualTo(DEFAULT_VERRE_LIMITE);
        assertThat(testPolice.getVerre_jour()).isEqualTo(DEFAULT_VERRE_JOUR);
        assertThat(testPolice.getVerre_an()).isEqualTo(DEFAULT_VERRE_AN);
        assertThat(testPolice.getVerre_deux_an()).isEqualTo(DEFAULT_VERRE_DEUX_AN);
        assertThat(testPolice.getMonture_taux()).isEqualTo(DEFAULT_MONTURE_TAUX);
        assertThat(testPolice.getMonture_frais()).isEqualTo(DEFAULT_MONTURE_FRAIS);
        assertThat(testPolice.getMonture_limite()).isEqualTo(DEFAULT_MONTURE_LIMITE);
        assertThat(testPolice.getMonture_jour()).isEqualTo(DEFAULT_MONTURE_JOUR);
        assertThat(testPolice.getMonture_an()).isEqualTo(DEFAULT_MONTURE_AN);
        assertThat(testPolice.getMonture_deux_an()).isEqualTo(DEFAULT_MONTURE_DEUX_AN);
        assertThat(testPolice.getAccouchement_taux()).isEqualTo(DEFAULT_ACCOUCHEMENT_TAUX);
        assertThat(testPolice.getAccouchement_frais()).isEqualTo(DEFAULT_ACCOUCHEMENT_FRAIS);
        assertThat(testPolice.getAccouchement_limite()).isEqualTo(DEFAULT_ACCOUCHEMENT_LIMITE);
        assertThat(testPolice.getAccouchement_jour()).isEqualTo(DEFAULT_ACCOUCHEMENT_JOUR);
        assertThat(testPolice.getAccouchement_an()).isEqualTo(DEFAULT_ACCOUCHEMENT_AN);
        assertThat(testPolice.getAccouchement_deux_an()).isEqualTo(DEFAULT_ACCOUCHEMENT_DEUX_AN);
        assertThat(testPolice.getEducation_taux()).isEqualTo(DEFAULT_EDUCATION_TAUX);
        assertThat(testPolice.getEducation_frais()).isEqualTo(DEFAULT_EDUCATION_FRAIS);
        assertThat(testPolice.getEducation_limite()).isEqualTo(DEFAULT_EDUCATION_LIMITE);
        assertThat(testPolice.getEducation_jour()).isEqualTo(DEFAULT_EDUCATION_JOUR);
        assertThat(testPolice.getEducation_an()).isEqualTo(DEFAULT_EDUCATION_AN);
        assertThat(testPolice.getEducation_deux_an()).isEqualTo(DEFAULT_EDUCATION_DEUX_AN);
        assertThat(testPolice.getSejour_taux()).isEqualTo(DEFAULT_SEJOUR_TAUX);
        assertThat(testPolice.getSejour_frais()).isEqualTo(DEFAULT_SEJOUR_FRAIS);
        assertThat(testPolice.getSejour_limite()).isEqualTo(DEFAULT_SEJOUR_LIMITE);
        assertThat(testPolice.getSejour_jour()).isEqualTo(DEFAULT_SEJOUR_JOUR);
        assertThat(testPolice.getSejour_an()).isEqualTo(DEFAULT_SEJOUR_AN);
        assertThat(testPolice.getSejour_deux_an()).isEqualTo(DEFAULT_SEJOUR_DEUX_AN);
        assertThat(testPolice.getNbCollab()).isEqualTo(DEFAULT_NB_COLLAB);
        assertThat(testPolice.getNbConjoint()).isEqualTo(DEFAULT_NB_CONJOINT);
        assertThat(testPolice.getNbEnfantP()).isEqualTo(DEFAULT_NB_ENFANT_P);
        assertThat(testPolice.getNbEnfantG()).isEqualTo(DEFAULT_NB_ENFANT_G);
        assertThat(testPolice.getFin()).isEqualTo(DEFAULT_FIN);
    }

    @Test
    @Transactional
    public void checkRaisonIsRequired() throws Exception {
        int databaseSizeBeforeTest = policeRepository.findAll().size();
        // set the field null
        police.setRaison(null);

        // Create the Police, which fails.

        restPoliceMockMvc.perform(post("/api/police")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(police)))
                .andExpect(status().isBadRequest());

        List<Police> police = policeRepository.findAll();
        assertThat(police).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelIsRequired() throws Exception {
        int databaseSizeBeforeTest = policeRepository.findAll().size();
        // set the field null
        police.setTel(null);

        // Create the Police, which fails.

        restPoliceMockMvc.perform(post("/api/police")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(police)))
                .andExpect(status().isBadRequest());

        List<Police> police = policeRepository.findAll();
        assertThat(police).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = policeRepository.findAll().size();
        // set the field null
        police.setEmail(null);

        // Create the Police, which fails.

        restPoliceMockMvc.perform(post("/api/police")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(police)))
                .andExpect(status().isBadRequest());

        List<Police> police = policeRepository.findAll();
        assertThat(police).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = policeRepository.findAll().size();
        // set the field null
        police.setDateFin(null);

        // Create the Police, which fails.

        restPoliceMockMvc.perform(post("/api/police")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(police)))
                .andExpect(status().isBadRequest());

        List<Police> police = policeRepository.findAll();
        assertThat(police).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConsultation_limiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = policeRepository.findAll().size();
        // set the field null
        police.setConsultation_limite(null);

        // Create the Police, which fails.

        restPoliceMockMvc.perform(post("/api/police")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(police)))
                .andExpect(status().isBadRequest());

        List<Police> police = policeRepository.findAll();
        assertThat(police).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConsultation_deux_anIsRequired() throws Exception {
        int databaseSizeBeforeTest = policeRepository.findAll().size();
        // set the field null
        police.setConsultation_deux_an(null);

        // Create the Police, which fails.

        restPoliceMockMvc.perform(post("/api/police")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(police)))
                .andExpect(status().isBadRequest());

        List<Police> police = policeRepository.findAll();
        assertThat(police).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPolice() throws Exception {
        // Initialize the database
        policeRepository.saveAndFlush(police);

        // Get all the police
        restPoliceMockMvc.perform(get("/api/police?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(police.getId().intValue())))
                .andExpect(jsonPath("$.[*].raison").value(hasItem(DEFAULT_RAISON.toString())))
                .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
                .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].interloc").value(hasItem(DEFAULT_INTERLOC.toString())))
                .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
                .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
                .andExpect(jsonPath("$.[*].tiers").value(hasItem(DEFAULT_TIERS.toString())))
                .andExpect(jsonPath("$.[*].tpx").value(hasItem(DEFAULT_TPX.doubleValue())))
                .andExpect(jsonPath("$.[*].activite").value(hasItem(DEFAULT_ACTIVITE.toString())))
                .andExpect(jsonPath("$.[*].formule_soins").value(hasItem(DEFAULT_FORMULE_SOINS.toString())))
                .andExpect(jsonPath("$.[*].territoire").value(hasItem(DEFAULT_TERRITOIRE.toString())))
                .andExpect(jsonPath("$.[*].plafond").value(hasItem(DEFAULT_PLAFOND)))
                .andExpect(jsonPath("$.[*].complement").value(hasItem(DEFAULT_COMPLEMENT.toString())))
                .andExpect(jsonPath("$.[*].consultation_taux").value(hasItem(DEFAULT_CONSULTATION_TAUX.doubleValue())))
                .andExpect(jsonPath("$.[*].consultation_frais").value(hasItem(DEFAULT_CONSULTATION_FRAIS.toString())))
                .andExpect(jsonPath("$.[*].consultation_limite").value(hasItem(DEFAULT_CONSULTATION_LIMITE.toString())))
                .andExpect(jsonPath("$.[*].consultation_jour").value(hasItem(DEFAULT_CONSULTATION_JOUR.doubleValue())))
                .andExpect(jsonPath("$.[*].consultation_an").value(hasItem(DEFAULT_CONSULTATION_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].consultation_deux_an").value(hasItem(DEFAULT_CONSULTATION_DEUX_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].pharmacie_taux").value(hasItem(DEFAULT_PHARMACIE_TAUX.doubleValue())))
                .andExpect(jsonPath("$.[*].pharmacie_frais").value(hasItem(DEFAULT_PHARMACIE_FRAIS.toString())))
                .andExpect(jsonPath("$.[*].pharmacie_limite").value(hasItem(DEFAULT_PHARMACIE_LIMITE.toString())))
                .andExpect(jsonPath("$.[*].pharmacie_jour").value(hasItem(DEFAULT_PHARMACIE_JOUR.doubleValue())))
                .andExpect(jsonPath("$.[*].pharmacie_an").value(hasItem(DEFAULT_PHARMACIE_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].pharmacie_deux_an").value(hasItem(DEFAULT_PHARMACIE_DEUX_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].analyse_taux").value(hasItem(DEFAULT_ANALYSE_TAUX.doubleValue())))
                .andExpect(jsonPath("$.[*].analyse_frais").value(hasItem(DEFAULT_ANALYSE_FRAIS.toString())))
                .andExpect(jsonPath("$.[*].analyse_limite").value(hasItem(DEFAULT_ANALYSE_LIMITE.toString())))
                .andExpect(jsonPath("$.[*].analyse_jour").value(hasItem(DEFAULT_ANALYSE_JOUR.doubleValue())))
                .andExpect(jsonPath("$.[*].analyse_an").value(hasItem(DEFAULT_ANALYSE_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].analyse_deux_an").value(hasItem(DEFAULT_ANALYSE_DEUX_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].acte_taux").value(hasItem(DEFAULT_ACTE_TAUX.doubleValue())))
                .andExpect(jsonPath("$.[*].acte_frais").value(hasItem(DEFAULT_ACTE_FRAIS.toString())))
                .andExpect(jsonPath("$.[*].acte_limite").value(hasItem(DEFAULT_ACTE_LIMITE.toString())))
                .andExpect(jsonPath("$.[*].acte_jour").value(hasItem(DEFAULT_ACTE_JOUR.doubleValue())))
                .andExpect(jsonPath("$.[*].acte_an").value(hasItem(DEFAULT_ACTE_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].acte_deux_an").value(hasItem(DEFAULT_ACTE_DEUX_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].principal_chambre_taux").value(hasItem(DEFAULT_PRINCIPAL_CHAMBRE_TAUX.doubleValue())))
                .andExpect(jsonPath("$.[*].principal_chambre_frais").value(hasItem(DEFAULT_PRINCIPAL_CHAMBRE_FRAIS.toString())))
                .andExpect(jsonPath("$.[*].principal_chambre_limite").value(hasItem(DEFAULT_PRINCIPAL_CHAMBRE_LIMITE.toString())))
                .andExpect(jsonPath("$.[*].principal_chambre_jour").value(hasItem(DEFAULT_PRINCIPAL_CHAMBRE_JOUR.doubleValue())))
                .andExpect(jsonPath("$.[*].principal_chambre_an").value(hasItem(DEFAULT_PRINCIPAL_CHAMBRE_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].principal_chambre_deux_an").value(hasItem(DEFAULT_PRINCIPAL_CHAMBRE_DEUX_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].principal_frais_taux").value(hasItem(DEFAULT_PRINCIPAL_FRAIS_TAUX.doubleValue())))
                .andExpect(jsonPath("$.[*].principal_frais_limite").value(hasItem(DEFAULT_PRINCIPAL_FRAIS_LIMITE.toString())))
                .andExpect(jsonPath("$.[*].principal_frais_jour").value(hasItem(DEFAULT_PRINCIPAL_FRAIS_JOUR.doubleValue())))
                .andExpect(jsonPath("$.[*].principal_frais_an").value(hasItem(DEFAULT_PRINCIPAL_FRAIS_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].principal_frais_deux_an").value(hasItem(DEFAULT_PRINCIPAL_FRAIS_DEUX_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].prive_chambre_taux").value(hasItem(DEFAULT_PRIVE_CHAMBRE_TAUX.doubleValue())))
                .andExpect(jsonPath("$.[*].prive_chambre_frais").value(hasItem(DEFAULT_PRIVE_CHAMBRE_FRAIS.toString())))
                .andExpect(jsonPath("$.[*].prive_chambre_limite").value(hasItem(DEFAULT_PRIVE_CHAMBRE_LIMITE.toString())))
                .andExpect(jsonPath("$.[*].prive_chambre_jour").value(hasItem(DEFAULT_PRIVE_CHAMBRE_JOUR.doubleValue())))
                .andExpect(jsonPath("$.[*].prive_chambre_an").value(hasItem(DEFAULT_PRIVE_CHAMBRE_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].prive_chambre_deux_an").value(hasItem(DEFAULT_PRIVE_CHAMBRE_DEUX_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].prive_frais_taux").value(hasItem(DEFAULT_PRIVE_FRAIS_TAUX.doubleValue())))
                .andExpect(jsonPath("$.[*].prive_frais_limite").value(hasItem(DEFAULT_PRIVE_FRAIS_LIMITE.toString())))
                .andExpect(jsonPath("$.[*].prive_frais_jour").value(hasItem(DEFAULT_PRIVE_FRAIS_JOUR.doubleValue())))
                .andExpect(jsonPath("$.[*].prive_frais_an").value(hasItem(DEFAULT_PRIVE_FRAIS_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].prive_frais_deux_an").value(hasItem(DEFAULT_PRIVE_FRAIS_DEUX_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].public_chambre_taux").value(hasItem(DEFAULT_PUBLIC_CHAMBRE_TAUX.doubleValue())))
                .andExpect(jsonPath("$.[*].public_chambre_frais").value(hasItem(DEFAULT_PUBLIC_CHAMBRE_FRAIS.toString())))
                .andExpect(jsonPath("$.[*].public_chambre_limite").value(hasItem(DEFAULT_PUBLIC_CHAMBRE_LIMITE.toString())))
                .andExpect(jsonPath("$.[*].public_chambre_jour").value(hasItem(DEFAULT_PUBLIC_CHAMBRE_JOUR.doubleValue())))
                .andExpect(jsonPath("$.[*].public_chambre_an").value(hasItem(DEFAULT_PUBLIC_CHAMBRE_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].public_chambre_deux_an").value(hasItem(DEFAULT_PUBLIC_CHAMBRE_DEUX_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].public_frais_taux").value(hasItem(DEFAULT_PUBLIC_FRAIS_TAUX.doubleValue())))
                .andExpect(jsonPath("$.[*].public_frais_frais").value(hasItem(DEFAULT_PUBLIC_FRAIS_FRAIS.toString())))
                .andExpect(jsonPath("$.[*].public_frais_limite").value(hasItem(DEFAULT_PUBLIC_FRAIS_LIMITE.toString())))
                .andExpect(jsonPath("$.[*].public_frais_jour").value(hasItem(DEFAULT_PUBLIC_FRAIS_JOUR.doubleValue())))
                .andExpect(jsonPath("$.[*].public_frais_an").value(hasItem(DEFAULT_PUBLIC_FRAIS_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].public_frais_deux_an").value(hasItem(DEFAULT_PUBLIC_FRAIS_DEUX_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].soins_taux").value(hasItem(DEFAULT_SOINS_TAUX.doubleValue())))
                .andExpect(jsonPath("$.[*].soins_frais").value(hasItem(DEFAULT_SOINS_FRAIS.toString())))
                .andExpect(jsonPath("$.[*].soins_limite").value(hasItem(DEFAULT_SOINS_LIMITE.toString())))
                .andExpect(jsonPath("$.[*].soins_jour").value(hasItem(DEFAULT_SOINS_JOUR.doubleValue())))
                .andExpect(jsonPath("$.[*].soins_an").value(hasItem(DEFAULT_SOINS_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].soins_deux_an").value(hasItem(DEFAULT_SOINS_DEUX_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].verre_taux").value(hasItem(DEFAULT_VERRE_TAUX.doubleValue())))
                .andExpect(jsonPath("$.[*].verre_frais").value(hasItem(DEFAULT_VERRE_FRAIS.toString())))
                .andExpect(jsonPath("$.[*].verre_limite").value(hasItem(DEFAULT_VERRE_LIMITE.toString())))
                .andExpect(jsonPath("$.[*].verre_jour").value(hasItem(DEFAULT_VERRE_JOUR.doubleValue())))
                .andExpect(jsonPath("$.[*].verre_an").value(hasItem(DEFAULT_VERRE_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].verre_deux_an").value(hasItem(DEFAULT_VERRE_DEUX_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].monture_taux").value(hasItem(DEFAULT_MONTURE_TAUX.doubleValue())))
                .andExpect(jsonPath("$.[*].monture_frais").value(hasItem(DEFAULT_MONTURE_FRAIS.toString())))
                .andExpect(jsonPath("$.[*].monture_limite").value(hasItem(DEFAULT_MONTURE_LIMITE.toString())))
                .andExpect(jsonPath("$.[*].monture_jour").value(hasItem(DEFAULT_MONTURE_JOUR.doubleValue())))
                .andExpect(jsonPath("$.[*].monture_an").value(hasItem(DEFAULT_MONTURE_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].monture_deux_an").value(hasItem(DEFAULT_MONTURE_DEUX_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].accouchement_taux").value(hasItem(DEFAULT_ACCOUCHEMENT_TAUX.doubleValue())))
                .andExpect(jsonPath("$.[*].accouchement_frais").value(hasItem(DEFAULT_ACCOUCHEMENT_FRAIS.toString())))
                .andExpect(jsonPath("$.[*].accouchement_limite").value(hasItem(DEFAULT_ACCOUCHEMENT_LIMITE.toString())))
                .andExpect(jsonPath("$.[*].accouchement_jour").value(hasItem(DEFAULT_ACCOUCHEMENT_JOUR.doubleValue())))
                .andExpect(jsonPath("$.[*].accouchement_an").value(hasItem(DEFAULT_ACCOUCHEMENT_AN.toString())))
                .andExpect(jsonPath("$.[*].accouchement_deux_an").value(hasItem(DEFAULT_ACCOUCHEMENT_DEUX_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].education_taux").value(hasItem(DEFAULT_EDUCATION_TAUX.doubleValue())))
                .andExpect(jsonPath("$.[*].education_frais").value(hasItem(DEFAULT_EDUCATION_FRAIS.toString())))
                .andExpect(jsonPath("$.[*].education_limite").value(hasItem(DEFAULT_EDUCATION_LIMITE.toString())))
                .andExpect(jsonPath("$.[*].education_jour").value(hasItem(DEFAULT_EDUCATION_JOUR.doubleValue())))
                .andExpect(jsonPath("$.[*].education_an").value(hasItem(DEFAULT_EDUCATION_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].education_deux_an").value(hasItem(DEFAULT_EDUCATION_DEUX_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].sejour_taux").value(hasItem(DEFAULT_SEJOUR_TAUX.doubleValue())))
                .andExpect(jsonPath("$.[*].sejour_frais").value(hasItem(DEFAULT_SEJOUR_FRAIS.toString())))
                .andExpect(jsonPath("$.[*].sejour_limite").value(hasItem(DEFAULT_SEJOUR_LIMITE.toString())))
                .andExpect(jsonPath("$.[*].sejour_jour").value(hasItem(DEFAULT_SEJOUR_JOUR.doubleValue())))
                .andExpect(jsonPath("$.[*].sejour_an").value(hasItem(DEFAULT_SEJOUR_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].sejour_deux_an").value(hasItem(DEFAULT_SEJOUR_DEUX_AN.doubleValue())))
                .andExpect(jsonPath("$.[*].nbCollab").value(hasItem(DEFAULT_NB_COLLAB)))
                .andExpect(jsonPath("$.[*].nbConjoint").value(hasItem(DEFAULT_NB_CONJOINT)))
                .andExpect(jsonPath("$.[*].nbEnfantP").value(hasItem(DEFAULT_NB_ENFANT_P)))
                .andExpect(jsonPath("$.[*].nbEnfantG").value(hasItem(DEFAULT_NB_ENFANT_G)))
                .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN.toString())));
    }

    @Test
    @Transactional
    public void getPolice() throws Exception {
        // Initialize the database
        policeRepository.saveAndFlush(police);

        // Get the police
        restPoliceMockMvc.perform(get("/api/police/{id}", police.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(police.getId().intValue()))
            .andExpect(jsonPath("$.raison").value(DEFAULT_RAISON.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.interloc").value(DEFAULT_INTERLOC.toString()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.tiers").value(DEFAULT_TIERS.toString()))
            .andExpect(jsonPath("$.tpx").value(DEFAULT_TPX.doubleValue()))
            .andExpect(jsonPath("$.activite").value(DEFAULT_ACTIVITE.toString()))
            .andExpect(jsonPath("$.formule_soins").value(DEFAULT_FORMULE_SOINS.toString()))
            .andExpect(jsonPath("$.territoire").value(DEFAULT_TERRITOIRE.toString()))
            .andExpect(jsonPath("$.plafond").value(DEFAULT_PLAFOND))
            .andExpect(jsonPath("$.complement").value(DEFAULT_COMPLEMENT.toString()))
            .andExpect(jsonPath("$.consultation_taux").value(DEFAULT_CONSULTATION_TAUX.doubleValue()))
            .andExpect(jsonPath("$.consultation_frais").value(DEFAULT_CONSULTATION_FRAIS.toString()))
            .andExpect(jsonPath("$.consultation_limite").value(DEFAULT_CONSULTATION_LIMITE.toString()))
            .andExpect(jsonPath("$.consultation_jour").value(DEFAULT_CONSULTATION_JOUR.doubleValue()))
            .andExpect(jsonPath("$.consultation_an").value(DEFAULT_CONSULTATION_AN.doubleValue()))
            .andExpect(jsonPath("$.consultation_deux_an").value(DEFAULT_CONSULTATION_DEUX_AN.doubleValue()))
            .andExpect(jsonPath("$.pharmacie_taux").value(DEFAULT_PHARMACIE_TAUX.doubleValue()))
            .andExpect(jsonPath("$.pharmacie_frais").value(DEFAULT_PHARMACIE_FRAIS.toString()))
            .andExpect(jsonPath("$.pharmacie_limite").value(DEFAULT_PHARMACIE_LIMITE.toString()))
            .andExpect(jsonPath("$.pharmacie_jour").value(DEFAULT_PHARMACIE_JOUR.doubleValue()))
            .andExpect(jsonPath("$.pharmacie_an").value(DEFAULT_PHARMACIE_AN.doubleValue()))
            .andExpect(jsonPath("$.pharmacie_deux_an").value(DEFAULT_PHARMACIE_DEUX_AN.doubleValue()))
            .andExpect(jsonPath("$.analyse_taux").value(DEFAULT_ANALYSE_TAUX.doubleValue()))
            .andExpect(jsonPath("$.analyse_frais").value(DEFAULT_ANALYSE_FRAIS.toString()))
            .andExpect(jsonPath("$.analyse_limite").value(DEFAULT_ANALYSE_LIMITE.toString()))
            .andExpect(jsonPath("$.analyse_jour").value(DEFAULT_ANALYSE_JOUR.doubleValue()))
            .andExpect(jsonPath("$.analyse_an").value(DEFAULT_ANALYSE_AN.doubleValue()))
            .andExpect(jsonPath("$.analyse_deux_an").value(DEFAULT_ANALYSE_DEUX_AN.doubleValue()))
            .andExpect(jsonPath("$.acte_taux").value(DEFAULT_ACTE_TAUX.doubleValue()))
            .andExpect(jsonPath("$.acte_frais").value(DEFAULT_ACTE_FRAIS.toString()))
            .andExpect(jsonPath("$.acte_limite").value(DEFAULT_ACTE_LIMITE.toString()))
            .andExpect(jsonPath("$.acte_jour").value(DEFAULT_ACTE_JOUR.doubleValue()))
            .andExpect(jsonPath("$.acte_an").value(DEFAULT_ACTE_AN.doubleValue()))
            .andExpect(jsonPath("$.acte_deux_an").value(DEFAULT_ACTE_DEUX_AN.doubleValue()))
            .andExpect(jsonPath("$.principal_chambre_taux").value(DEFAULT_PRINCIPAL_CHAMBRE_TAUX.doubleValue()))
            .andExpect(jsonPath("$.principal_chambre_frais").value(DEFAULT_PRINCIPAL_CHAMBRE_FRAIS.toString()))
            .andExpect(jsonPath("$.principal_chambre_limite").value(DEFAULT_PRINCIPAL_CHAMBRE_LIMITE.toString()))
            .andExpect(jsonPath("$.principal_chambre_jour").value(DEFAULT_PRINCIPAL_CHAMBRE_JOUR.doubleValue()))
            .andExpect(jsonPath("$.principal_chambre_an").value(DEFAULT_PRINCIPAL_CHAMBRE_AN.doubleValue()))
            .andExpect(jsonPath("$.principal_chambre_deux_an").value(DEFAULT_PRINCIPAL_CHAMBRE_DEUX_AN.doubleValue()))
            .andExpect(jsonPath("$.principal_frais_taux").value(DEFAULT_PRINCIPAL_FRAIS_TAUX.doubleValue()))
            .andExpect(jsonPath("$.principal_frais_limite").value(DEFAULT_PRINCIPAL_FRAIS_LIMITE.toString()))
            .andExpect(jsonPath("$.principal_frais_jour").value(DEFAULT_PRINCIPAL_FRAIS_JOUR.doubleValue()))
            .andExpect(jsonPath("$.principal_frais_an").value(DEFAULT_PRINCIPAL_FRAIS_AN.doubleValue()))
            .andExpect(jsonPath("$.principal_frais_deux_an").value(DEFAULT_PRINCIPAL_FRAIS_DEUX_AN.doubleValue()))
            .andExpect(jsonPath("$.prive_chambre_taux").value(DEFAULT_PRIVE_CHAMBRE_TAUX.doubleValue()))
            .andExpect(jsonPath("$.prive_chambre_frais").value(DEFAULT_PRIVE_CHAMBRE_FRAIS.toString()))
            .andExpect(jsonPath("$.prive_chambre_limite").value(DEFAULT_PRIVE_CHAMBRE_LIMITE.toString()))
            .andExpect(jsonPath("$.prive_chambre_jour").value(DEFAULT_PRIVE_CHAMBRE_JOUR.doubleValue()))
            .andExpect(jsonPath("$.prive_chambre_an").value(DEFAULT_PRIVE_CHAMBRE_AN.doubleValue()))
            .andExpect(jsonPath("$.prive_chambre_deux_an").value(DEFAULT_PRIVE_CHAMBRE_DEUX_AN.doubleValue()))
            .andExpect(jsonPath("$.prive_frais_taux").value(DEFAULT_PRIVE_FRAIS_TAUX.doubleValue()))
            .andExpect(jsonPath("$.prive_frais_limite").value(DEFAULT_PRIVE_FRAIS_LIMITE.toString()))
            .andExpect(jsonPath("$.prive_frais_jour").value(DEFAULT_PRIVE_FRAIS_JOUR.doubleValue()))
            .andExpect(jsonPath("$.prive_frais_an").value(DEFAULT_PRIVE_FRAIS_AN.doubleValue()))
            .andExpect(jsonPath("$.prive_frais_deux_an").value(DEFAULT_PRIVE_FRAIS_DEUX_AN.doubleValue()))
            .andExpect(jsonPath("$.public_chambre_taux").value(DEFAULT_PUBLIC_CHAMBRE_TAUX.doubleValue()))
            .andExpect(jsonPath("$.public_chambre_frais").value(DEFAULT_PUBLIC_CHAMBRE_FRAIS.toString()))
            .andExpect(jsonPath("$.public_chambre_limite").value(DEFAULT_PUBLIC_CHAMBRE_LIMITE.toString()))
            .andExpect(jsonPath("$.public_chambre_jour").value(DEFAULT_PUBLIC_CHAMBRE_JOUR.doubleValue()))
            .andExpect(jsonPath("$.public_chambre_an").value(DEFAULT_PUBLIC_CHAMBRE_AN.doubleValue()))
            .andExpect(jsonPath("$.public_chambre_deux_an").value(DEFAULT_PUBLIC_CHAMBRE_DEUX_AN.doubleValue()))
            .andExpect(jsonPath("$.public_frais_taux").value(DEFAULT_PUBLIC_FRAIS_TAUX.doubleValue()))
            .andExpect(jsonPath("$.public_frais_frais").value(DEFAULT_PUBLIC_FRAIS_FRAIS.toString()))
            .andExpect(jsonPath("$.public_frais_limite").value(DEFAULT_PUBLIC_FRAIS_LIMITE.toString()))
            .andExpect(jsonPath("$.public_frais_jour").value(DEFAULT_PUBLIC_FRAIS_JOUR.doubleValue()))
            .andExpect(jsonPath("$.public_frais_an").value(DEFAULT_PUBLIC_FRAIS_AN.doubleValue()))
            .andExpect(jsonPath("$.public_frais_deux_an").value(DEFAULT_PUBLIC_FRAIS_DEUX_AN.doubleValue()))
            .andExpect(jsonPath("$.soins_taux").value(DEFAULT_SOINS_TAUX.doubleValue()))
            .andExpect(jsonPath("$.soins_frais").value(DEFAULT_SOINS_FRAIS.toString()))
            .andExpect(jsonPath("$.soins_limite").value(DEFAULT_SOINS_LIMITE.toString()))
            .andExpect(jsonPath("$.soins_jour").value(DEFAULT_SOINS_JOUR.doubleValue()))
            .andExpect(jsonPath("$.soins_an").value(DEFAULT_SOINS_AN.doubleValue()))
            .andExpect(jsonPath("$.soins_deux_an").value(DEFAULT_SOINS_DEUX_AN.doubleValue()))
            .andExpect(jsonPath("$.verre_taux").value(DEFAULT_VERRE_TAUX.doubleValue()))
            .andExpect(jsonPath("$.verre_frais").value(DEFAULT_VERRE_FRAIS.toString()))
            .andExpect(jsonPath("$.verre_limite").value(DEFAULT_VERRE_LIMITE.toString()))
            .andExpect(jsonPath("$.verre_jour").value(DEFAULT_VERRE_JOUR.doubleValue()))
            .andExpect(jsonPath("$.verre_an").value(DEFAULT_VERRE_AN.doubleValue()))
            .andExpect(jsonPath("$.verre_deux_an").value(DEFAULT_VERRE_DEUX_AN.doubleValue()))
            .andExpect(jsonPath("$.monture_taux").value(DEFAULT_MONTURE_TAUX.doubleValue()))
            .andExpect(jsonPath("$.monture_frais").value(DEFAULT_MONTURE_FRAIS.toString()))
            .andExpect(jsonPath("$.monture_limite").value(DEFAULT_MONTURE_LIMITE.toString()))
            .andExpect(jsonPath("$.monture_jour").value(DEFAULT_MONTURE_JOUR.doubleValue()))
            .andExpect(jsonPath("$.monture_an").value(DEFAULT_MONTURE_AN.doubleValue()))
            .andExpect(jsonPath("$.monture_deux_an").value(DEFAULT_MONTURE_DEUX_AN.doubleValue()))
            .andExpect(jsonPath("$.accouchement_taux").value(DEFAULT_ACCOUCHEMENT_TAUX.doubleValue()))
            .andExpect(jsonPath("$.accouchement_frais").value(DEFAULT_ACCOUCHEMENT_FRAIS.toString()))
            .andExpect(jsonPath("$.accouchement_limite").value(DEFAULT_ACCOUCHEMENT_LIMITE.toString()))
            .andExpect(jsonPath("$.accouchement_jour").value(DEFAULT_ACCOUCHEMENT_JOUR.doubleValue()))
            .andExpect(jsonPath("$.accouchement_an").value(DEFAULT_ACCOUCHEMENT_AN.toString()))
            .andExpect(jsonPath("$.accouchement_deux_an").value(DEFAULT_ACCOUCHEMENT_DEUX_AN.doubleValue()))
            .andExpect(jsonPath("$.education_taux").value(DEFAULT_EDUCATION_TAUX.doubleValue()))
            .andExpect(jsonPath("$.education_frais").value(DEFAULT_EDUCATION_FRAIS.toString()))
            .andExpect(jsonPath("$.education_limite").value(DEFAULT_EDUCATION_LIMITE.toString()))
            .andExpect(jsonPath("$.education_jour").value(DEFAULT_EDUCATION_JOUR.doubleValue()))
            .andExpect(jsonPath("$.education_an").value(DEFAULT_EDUCATION_AN.doubleValue()))
            .andExpect(jsonPath("$.education_deux_an").value(DEFAULT_EDUCATION_DEUX_AN.doubleValue()))
            .andExpect(jsonPath("$.sejour_taux").value(DEFAULT_SEJOUR_TAUX.doubleValue()))
            .andExpect(jsonPath("$.sejour_frais").value(DEFAULT_SEJOUR_FRAIS.toString()))
            .andExpect(jsonPath("$.sejour_limite").value(DEFAULT_SEJOUR_LIMITE.toString()))
            .andExpect(jsonPath("$.sejour_jour").value(DEFAULT_SEJOUR_JOUR.doubleValue()))
            .andExpect(jsonPath("$.sejour_an").value(DEFAULT_SEJOUR_AN.doubleValue()))
            .andExpect(jsonPath("$.sejour_deux_an").value(DEFAULT_SEJOUR_DEUX_AN.doubleValue()))
            .andExpect(jsonPath("$.nbCollab").value(DEFAULT_NB_COLLAB))
            .andExpect(jsonPath("$.nbConjoint").value(DEFAULT_NB_CONJOINT))
            .andExpect(jsonPath("$.nbEnfantP").value(DEFAULT_NB_ENFANT_P))
            .andExpect(jsonPath("$.nbEnfantG").value(DEFAULT_NB_ENFANT_G))
            .andExpect(jsonPath("$.fin").value(DEFAULT_FIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPolice() throws Exception {
        // Get the police
        restPoliceMockMvc.perform(get("/api/police/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolice() throws Exception {
        // Initialize the database
        policeService.save(police);

        int databaseSizeBeforeUpdate = policeRepository.findAll().size();

        // Update the police
        Police updatedPolice = policeRepository.findOne(police.getId());
        updatedPolice
                .raison(UPDATED_RAISON)
                .adresse(UPDATED_ADRESSE)
                .tel(UPDATED_TEL)
                .email(UPDATED_EMAIL)
                .interloc(UPDATED_INTERLOC)
                .dateDebut(UPDATED_DATE_DEBUT)
                .dateFin(UPDATED_DATE_FIN)
                .tiers(UPDATED_TIERS)
                .tpx(UPDATED_TPX)
                .activite(UPDATED_ACTIVITE)
                .formule_soins(UPDATED_FORMULE_SOINS)
                .territoire(UPDATED_TERRITOIRE)
                .plafond(UPDATED_PLAFOND)
                .complement(UPDATED_COMPLEMENT)
                .consultation_taux(UPDATED_CONSULTATION_TAUX)
                .consultation_frais(UPDATED_CONSULTATION_FRAIS)
                .consultation_limite(UPDATED_CONSULTATION_LIMITE)
                .consultation_jour(UPDATED_CONSULTATION_JOUR)
                .consultation_an(UPDATED_CONSULTATION_AN)
                .consultation_deux_an(UPDATED_CONSULTATION_DEUX_AN)
                .pharmacie_taux(UPDATED_PHARMACIE_TAUX)
                .pharmacie_frais(UPDATED_PHARMACIE_FRAIS)
                .pharmacie_limite(UPDATED_PHARMACIE_LIMITE)
                .pharmacie_jour(UPDATED_PHARMACIE_JOUR)
                .pharmacie_an(UPDATED_PHARMACIE_AN)
                .pharmacie_deux_an(UPDATED_PHARMACIE_DEUX_AN)
                .analyse_taux(UPDATED_ANALYSE_TAUX)
                .analyse_frais(UPDATED_ANALYSE_FRAIS)
                .analyse_limite(UPDATED_ANALYSE_LIMITE)
                .analyse_jour(UPDATED_ANALYSE_JOUR)
                .analyse_an(UPDATED_ANALYSE_AN)
                .analyse_deux_an(UPDATED_ANALYSE_DEUX_AN)
                .acte_taux(UPDATED_ACTE_TAUX)
                .acte_frais(UPDATED_ACTE_FRAIS)
                .acte_limite(UPDATED_ACTE_LIMITE)
                .acte_jour(UPDATED_ACTE_JOUR)
                .acte_an(UPDATED_ACTE_AN)
                .acte_deux_an(UPDATED_ACTE_DEUX_AN)
                .principal_chambre_taux(UPDATED_PRINCIPAL_CHAMBRE_TAUX)
                .principal_chambre_frais(UPDATED_PRINCIPAL_CHAMBRE_FRAIS)
                .principal_chambre_limite(UPDATED_PRINCIPAL_CHAMBRE_LIMITE)
                .principal_chambre_jour(UPDATED_PRINCIPAL_CHAMBRE_JOUR)
                .principal_chambre_an(UPDATED_PRINCIPAL_CHAMBRE_AN)
                .principal_chambre_deux_an(UPDATED_PRINCIPAL_CHAMBRE_DEUX_AN)
                .principal_frais_taux(UPDATED_PRINCIPAL_FRAIS_TAUX)
                .principal_frais_limite(UPDATED_PRINCIPAL_FRAIS_LIMITE)
                .principal_frais_jour(UPDATED_PRINCIPAL_FRAIS_JOUR)
                .principal_frais_an(UPDATED_PRINCIPAL_FRAIS_AN)
                .principal_frais_deux_an(UPDATED_PRINCIPAL_FRAIS_DEUX_AN)
                .prive_chambre_taux(UPDATED_PRIVE_CHAMBRE_TAUX)
                .prive_chambre_frais(UPDATED_PRIVE_CHAMBRE_FRAIS)
                .prive_chambre_limite(UPDATED_PRIVE_CHAMBRE_LIMITE)
                .prive_chambre_jour(UPDATED_PRIVE_CHAMBRE_JOUR)
                .prive_chambre_an(UPDATED_PRIVE_CHAMBRE_AN)
                .prive_chambre_deux_an(UPDATED_PRIVE_CHAMBRE_DEUX_AN)
                .prive_frais_taux(UPDATED_PRIVE_FRAIS_TAUX)
                .prive_frais_limite(UPDATED_PRIVE_FRAIS_LIMITE)
                .prive_frais_jour(UPDATED_PRIVE_FRAIS_JOUR)
                .prive_frais_an(UPDATED_PRIVE_FRAIS_AN)
                .prive_frais_deux_an(UPDATED_PRIVE_FRAIS_DEUX_AN)
                .public_chambre_taux(UPDATED_PUBLIC_CHAMBRE_TAUX)
                .public_chambre_frais(UPDATED_PUBLIC_CHAMBRE_FRAIS)
                .public_chambre_limite(UPDATED_PUBLIC_CHAMBRE_LIMITE)
                .public_chambre_jour(UPDATED_PUBLIC_CHAMBRE_JOUR)
                .public_chambre_an(UPDATED_PUBLIC_CHAMBRE_AN)
                .public_chambre_deux_an(UPDATED_PUBLIC_CHAMBRE_DEUX_AN)
                .public_frais_taux(UPDATED_PUBLIC_FRAIS_TAUX)
                .public_frais_frais(UPDATED_PUBLIC_FRAIS_FRAIS)
                .public_frais_limite(UPDATED_PUBLIC_FRAIS_LIMITE)
                .public_frais_jour(UPDATED_PUBLIC_FRAIS_JOUR)
                .public_frais_an(UPDATED_PUBLIC_FRAIS_AN)
                .public_frais_deux_an(UPDATED_PUBLIC_FRAIS_DEUX_AN)
                .soins_taux(UPDATED_SOINS_TAUX)
                .soins_frais(UPDATED_SOINS_FRAIS)
                .soins_limite(UPDATED_SOINS_LIMITE)
                .soins_jour(UPDATED_SOINS_JOUR)
                .soins_an(UPDATED_SOINS_AN)
                .soins_deux_an(UPDATED_SOINS_DEUX_AN)
                .verre_taux(UPDATED_VERRE_TAUX)
                .verre_frais(UPDATED_VERRE_FRAIS)
                .verre_limite(UPDATED_VERRE_LIMITE)
                .verre_jour(UPDATED_VERRE_JOUR)
                .verre_an(UPDATED_VERRE_AN)
                .verre_deux_an(UPDATED_VERRE_DEUX_AN)
                .monture_taux(UPDATED_MONTURE_TAUX)
                .monture_frais(UPDATED_MONTURE_FRAIS)
                .monture_limite(UPDATED_MONTURE_LIMITE)
                .monture_jour(UPDATED_MONTURE_JOUR)
                .monture_an(UPDATED_MONTURE_AN)
                .monture_deux_an(UPDATED_MONTURE_DEUX_AN)
                .accouchement_taux(UPDATED_ACCOUCHEMENT_TAUX)
                .accouchement_frais(UPDATED_ACCOUCHEMENT_FRAIS)
                .accouchement_limite(UPDATED_ACCOUCHEMENT_LIMITE)
                .accouchement_jour(UPDATED_ACCOUCHEMENT_JOUR)
                .accouchement_an(UPDATED_ACCOUCHEMENT_AN)
                .accouchement_deux_an(UPDATED_ACCOUCHEMENT_DEUX_AN)
                .education_taux(UPDATED_EDUCATION_TAUX)
                .education_frais(UPDATED_EDUCATION_FRAIS)
                .education_limite(UPDATED_EDUCATION_LIMITE)
                .education_jour(UPDATED_EDUCATION_JOUR)
                .education_an(UPDATED_EDUCATION_AN)
                .education_deux_an(UPDATED_EDUCATION_DEUX_AN)
                .sejour_taux(UPDATED_SEJOUR_TAUX)
                .sejour_frais(UPDATED_SEJOUR_FRAIS)
                .sejour_limite(UPDATED_SEJOUR_LIMITE)
                .sejour_jour(UPDATED_SEJOUR_JOUR)
                .sejour_an(UPDATED_SEJOUR_AN)
                .sejour_deux_an(UPDATED_SEJOUR_DEUX_AN)
                .nbCollab(UPDATED_NB_COLLAB)
                .nbConjoint(UPDATED_NB_CONJOINT)
                .nbEnfantP(UPDATED_NB_ENFANT_P)
                .nbEnfantG(UPDATED_NB_ENFANT_G)
                .fin(UPDATED_FIN);

        restPoliceMockMvc.perform(put("/api/police")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPolice)))
                .andExpect(status().isOk());

        // Validate the Police in the database
        List<Police> police = policeRepository.findAll();
        assertThat(police).hasSize(databaseSizeBeforeUpdate);
        Police testPolice = police.get(police.size() - 1);
        assertThat(testPolice.getRaison()).isEqualTo(UPDATED_RAISON);
        assertThat(testPolice.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testPolice.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testPolice.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPolice.getInterloc()).isEqualTo(UPDATED_INTERLOC);
        assertThat(testPolice.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testPolice.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testPolice.getTiers()).isEqualTo(UPDATED_TIERS);
        assertThat(testPolice.getTpx()).isEqualTo(UPDATED_TPX);
        assertThat(testPolice.getActivite()).isEqualTo(UPDATED_ACTIVITE);
        assertThat(testPolice.getFormule_soins()).isEqualTo(UPDATED_FORMULE_SOINS);
        assertThat(testPolice.getTerritoire()).isEqualTo(UPDATED_TERRITOIRE);
        assertThat(testPolice.getPlafond()).isEqualTo(UPDATED_PLAFOND);
        assertThat(testPolice.getComplement()).isEqualTo(UPDATED_COMPLEMENT);
        assertThat(testPolice.getConsultation_taux()).isEqualTo(UPDATED_CONSULTATION_TAUX);
        assertThat(testPolice.getConsultation_frais()).isEqualTo(UPDATED_CONSULTATION_FRAIS);
        assertThat(testPolice.getConsultation_limite()).isEqualTo(UPDATED_CONSULTATION_LIMITE);
        assertThat(testPolice.getConsultation_jour()).isEqualTo(UPDATED_CONSULTATION_JOUR);
        assertThat(testPolice.getConsultation_an()).isEqualTo(UPDATED_CONSULTATION_AN);
        assertThat(testPolice.getConsultation_deux_an()).isEqualTo(UPDATED_CONSULTATION_DEUX_AN);
        assertThat(testPolice.getPharmacie_taux()).isEqualTo(UPDATED_PHARMACIE_TAUX);
        assertThat(testPolice.getPharmacie_frais()).isEqualTo(UPDATED_PHARMACIE_FRAIS);
        assertThat(testPolice.getPharmacie_limite()).isEqualTo(UPDATED_PHARMACIE_LIMITE);
        assertThat(testPolice.getPharmacie_jour()).isEqualTo(UPDATED_PHARMACIE_JOUR);
        assertThat(testPolice.getPharmacie_an()).isEqualTo(UPDATED_PHARMACIE_AN);
        assertThat(testPolice.getPharmacie_deux_an()).isEqualTo(UPDATED_PHARMACIE_DEUX_AN);
        assertThat(testPolice.getAnalyse_taux()).isEqualTo(UPDATED_ANALYSE_TAUX);
        assertThat(testPolice.getAnalyse_frais()).isEqualTo(UPDATED_ANALYSE_FRAIS);
        assertThat(testPolice.getAnalyse_limite()).isEqualTo(UPDATED_ANALYSE_LIMITE);
        assertThat(testPolice.getAnalyse_jour()).isEqualTo(UPDATED_ANALYSE_JOUR);
        assertThat(testPolice.getAnalyse_an()).isEqualTo(UPDATED_ANALYSE_AN);
        assertThat(testPolice.getAnalyse_deux_an()).isEqualTo(UPDATED_ANALYSE_DEUX_AN);
        assertThat(testPolice.getActe_taux()).isEqualTo(UPDATED_ACTE_TAUX);
        assertThat(testPolice.getActe_frais()).isEqualTo(UPDATED_ACTE_FRAIS);
        assertThat(testPolice.getActe_limite()).isEqualTo(UPDATED_ACTE_LIMITE);
        assertThat(testPolice.getActe_jour()).isEqualTo(UPDATED_ACTE_JOUR);
        assertThat(testPolice.getActe_an()).isEqualTo(UPDATED_ACTE_AN);
        assertThat(testPolice.getActe_deux_an()).isEqualTo(UPDATED_ACTE_DEUX_AN);
        assertThat(testPolice.getPrincipal_chambre_taux()).isEqualTo(UPDATED_PRINCIPAL_CHAMBRE_TAUX);
        assertThat(testPolice.getPrincipal_chambre_frais()).isEqualTo(UPDATED_PRINCIPAL_CHAMBRE_FRAIS);
        assertThat(testPolice.getPrincipal_chambre_limite()).isEqualTo(UPDATED_PRINCIPAL_CHAMBRE_LIMITE);
        assertThat(testPolice.getPrincipal_chambre_jour()).isEqualTo(UPDATED_PRINCIPAL_CHAMBRE_JOUR);
        assertThat(testPolice.getPrincipal_chambre_an()).isEqualTo(UPDATED_PRINCIPAL_CHAMBRE_AN);
        assertThat(testPolice.getPrincipal_chambre_deux_an()).isEqualTo(UPDATED_PRINCIPAL_CHAMBRE_DEUX_AN);
        assertThat(testPolice.getPrincipal_frais_taux()).isEqualTo(UPDATED_PRINCIPAL_FRAIS_TAUX);
        assertThat(testPolice.getPrincipal_frais_limite()).isEqualTo(UPDATED_PRINCIPAL_FRAIS_LIMITE);
        assertThat(testPolice.getPrincipal_frais_jour()).isEqualTo(UPDATED_PRINCIPAL_FRAIS_JOUR);
        assertThat(testPolice.getPrincipal_frais_an()).isEqualTo(UPDATED_PRINCIPAL_FRAIS_AN);
        assertThat(testPolice.getPrincipal_frais_deux_an()).isEqualTo(UPDATED_PRINCIPAL_FRAIS_DEUX_AN);
        assertThat(testPolice.getPrive_chambre_taux()).isEqualTo(UPDATED_PRIVE_CHAMBRE_TAUX);
        assertThat(testPolice.getPrive_chambre_frais()).isEqualTo(UPDATED_PRIVE_CHAMBRE_FRAIS);
        assertThat(testPolice.getPrive_chambre_limite()).isEqualTo(UPDATED_PRIVE_CHAMBRE_LIMITE);
        assertThat(testPolice.getPrive_chambre_jour()).isEqualTo(UPDATED_PRIVE_CHAMBRE_JOUR);
        assertThat(testPolice.getPrive_chambre_an()).isEqualTo(UPDATED_PRIVE_CHAMBRE_AN);
        assertThat(testPolice.getPrive_chambre_deux_an()).isEqualTo(UPDATED_PRIVE_CHAMBRE_DEUX_AN);
        assertThat(testPolice.getPrive_frais_taux()).isEqualTo(UPDATED_PRIVE_FRAIS_TAUX);
        assertThat(testPolice.getPrive_frais_limite()).isEqualTo(UPDATED_PRIVE_FRAIS_LIMITE);
        assertThat(testPolice.getPrive_frais_jour()).isEqualTo(UPDATED_PRIVE_FRAIS_JOUR);
        assertThat(testPolice.getPrive_frais_an()).isEqualTo(UPDATED_PRIVE_FRAIS_AN);
        assertThat(testPolice.getPrive_frais_deux_an()).isEqualTo(UPDATED_PRIVE_FRAIS_DEUX_AN);
        assertThat(testPolice.getPublic_chambre_taux()).isEqualTo(UPDATED_PUBLIC_CHAMBRE_TAUX);
        assertThat(testPolice.getPublic_chambre_frais()).isEqualTo(UPDATED_PUBLIC_CHAMBRE_FRAIS);
        assertThat(testPolice.getPublic_chambre_limite()).isEqualTo(UPDATED_PUBLIC_CHAMBRE_LIMITE);
        assertThat(testPolice.getPublic_chambre_jour()).isEqualTo(UPDATED_PUBLIC_CHAMBRE_JOUR);
        assertThat(testPolice.getPublic_chambre_an()).isEqualTo(UPDATED_PUBLIC_CHAMBRE_AN);
        assertThat(testPolice.getPublic_chambre_deux_an()).isEqualTo(UPDATED_PUBLIC_CHAMBRE_DEUX_AN);
        assertThat(testPolice.getPublic_frais_taux()).isEqualTo(UPDATED_PUBLIC_FRAIS_TAUX);
        assertThat(testPolice.getPublic_frais_frais()).isEqualTo(UPDATED_PUBLIC_FRAIS_FRAIS);
        assertThat(testPolice.getPublic_frais_limite()).isEqualTo(UPDATED_PUBLIC_FRAIS_LIMITE);
        assertThat(testPolice.getPublic_frais_jour()).isEqualTo(UPDATED_PUBLIC_FRAIS_JOUR);
        assertThat(testPolice.getPublic_frais_an()).isEqualTo(UPDATED_PUBLIC_FRAIS_AN);
        assertThat(testPolice.getPublic_frais_deux_an()).isEqualTo(UPDATED_PUBLIC_FRAIS_DEUX_AN);
        assertThat(testPolice.getSoins_taux()).isEqualTo(UPDATED_SOINS_TAUX);
        assertThat(testPolice.getSoins_frais()).isEqualTo(UPDATED_SOINS_FRAIS);
        assertThat(testPolice.getSoins_limite()).isEqualTo(UPDATED_SOINS_LIMITE);
        assertThat(testPolice.getSoins_jour()).isEqualTo(UPDATED_SOINS_JOUR);
        assertThat(testPolice.getSoins_an()).isEqualTo(UPDATED_SOINS_AN);
        assertThat(testPolice.getSoins_deux_an()).isEqualTo(UPDATED_SOINS_DEUX_AN);
        assertThat(testPolice.getVerre_taux()).isEqualTo(UPDATED_VERRE_TAUX);
        assertThat(testPolice.getVerre_frais()).isEqualTo(UPDATED_VERRE_FRAIS);
        assertThat(testPolice.getVerre_limite()).isEqualTo(UPDATED_VERRE_LIMITE);
        assertThat(testPolice.getVerre_jour()).isEqualTo(UPDATED_VERRE_JOUR);
        assertThat(testPolice.getVerre_an()).isEqualTo(UPDATED_VERRE_AN);
        assertThat(testPolice.getVerre_deux_an()).isEqualTo(UPDATED_VERRE_DEUX_AN);
        assertThat(testPolice.getMonture_taux()).isEqualTo(UPDATED_MONTURE_TAUX);
        assertThat(testPolice.getMonture_frais()).isEqualTo(UPDATED_MONTURE_FRAIS);
        assertThat(testPolice.getMonture_limite()).isEqualTo(UPDATED_MONTURE_LIMITE);
        assertThat(testPolice.getMonture_jour()).isEqualTo(UPDATED_MONTURE_JOUR);
        assertThat(testPolice.getMonture_an()).isEqualTo(UPDATED_MONTURE_AN);
        assertThat(testPolice.getMonture_deux_an()).isEqualTo(UPDATED_MONTURE_DEUX_AN);
        assertThat(testPolice.getAccouchement_taux()).isEqualTo(UPDATED_ACCOUCHEMENT_TAUX);
        assertThat(testPolice.getAccouchement_frais()).isEqualTo(UPDATED_ACCOUCHEMENT_FRAIS);
        assertThat(testPolice.getAccouchement_limite()).isEqualTo(UPDATED_ACCOUCHEMENT_LIMITE);
        assertThat(testPolice.getAccouchement_jour()).isEqualTo(UPDATED_ACCOUCHEMENT_JOUR);
        assertThat(testPolice.getAccouchement_an()).isEqualTo(UPDATED_ACCOUCHEMENT_AN);
        assertThat(testPolice.getAccouchement_deux_an()).isEqualTo(UPDATED_ACCOUCHEMENT_DEUX_AN);
        assertThat(testPolice.getEducation_taux()).isEqualTo(UPDATED_EDUCATION_TAUX);
        assertThat(testPolice.getEducation_frais()).isEqualTo(UPDATED_EDUCATION_FRAIS);
        assertThat(testPolice.getEducation_limite()).isEqualTo(UPDATED_EDUCATION_LIMITE);
        assertThat(testPolice.getEducation_jour()).isEqualTo(UPDATED_EDUCATION_JOUR);
        assertThat(testPolice.getEducation_an()).isEqualTo(UPDATED_EDUCATION_AN);
        assertThat(testPolice.getEducation_deux_an()).isEqualTo(UPDATED_EDUCATION_DEUX_AN);
        assertThat(testPolice.getSejour_taux()).isEqualTo(UPDATED_SEJOUR_TAUX);
        assertThat(testPolice.getSejour_frais()).isEqualTo(UPDATED_SEJOUR_FRAIS);
        assertThat(testPolice.getSejour_limite()).isEqualTo(UPDATED_SEJOUR_LIMITE);
        assertThat(testPolice.getSejour_jour()).isEqualTo(UPDATED_SEJOUR_JOUR);
        assertThat(testPolice.getSejour_an()).isEqualTo(UPDATED_SEJOUR_AN);
        assertThat(testPolice.getSejour_deux_an()).isEqualTo(UPDATED_SEJOUR_DEUX_AN);
        assertThat(testPolice.getNbCollab()).isEqualTo(UPDATED_NB_COLLAB);
        assertThat(testPolice.getNbConjoint()).isEqualTo(UPDATED_NB_CONJOINT);
        assertThat(testPolice.getNbEnfantP()).isEqualTo(UPDATED_NB_ENFANT_P);
        assertThat(testPolice.getNbEnfantG()).isEqualTo(UPDATED_NB_ENFANT_G);
        assertThat(testPolice.getFin()).isEqualTo(UPDATED_FIN);
    }

    @Test
    @Transactional
    public void deletePolice() throws Exception {
        // Initialize the database
        policeService.save(police);

        int databaseSizeBeforeDelete = policeRepository.findAll().size();

        // Get the police
        restPoliceMockMvc.perform(delete("/api/police/{id}", police.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Police> police = policeRepository.findAll();
        assertThat(police).hasSize(databaseSizeBeforeDelete - 1);
    }
}
