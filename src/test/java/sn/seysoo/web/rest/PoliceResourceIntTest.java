package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.Police;
import sn.seysoo.repository.PoliceRepository;
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
 * Test class for the PoliceResource REST controller.
 *
 * @see PoliceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class PoliceResourceIntTest {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_SECTION = "AAAAAAAAAA";
    private static final String UPDATED_SECTION = "BBBBBBBBBB";

    @Autowired
    private PoliceRepository policeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPoliceMockMvc;

    private Police police;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PoliceResource policeResource = new PoliceResource(policeRepository);
        this.restPoliceMockMvc = MockMvcBuilders.standaloneSetup(policeResource)
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
    public static Police createEntity() {
        Police police = new Police()
            .numero(DEFAULT_NUMERO)
            .section(DEFAULT_SECTION);
        return police;
    }

    @Before
    public void initTest() {
        policeRepository.deleteAll();
        police = createEntity();
    }

    @Test
    public void createPolice() throws Exception {
        int databaseSizeBeforeCreate = policeRepository.findAll().size();

        // Create the Police
        restPoliceMockMvc.perform(post("/api/police")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(police)))
            .andExpect(status().isCreated());

        // Validate the Police in the database
        List<Police> policeList = policeRepository.findAll();
        assertThat(policeList).hasSize(databaseSizeBeforeCreate + 1);
        Police testPolice = policeList.get(policeList.size() - 1);
        assertThat(testPolice.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testPolice.getSection()).isEqualTo(DEFAULT_SECTION);
    }

    @Test
    public void createPoliceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = policeRepository.findAll().size();

        // Create the Police with an existing ID
        police.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoliceMockMvc.perform(post("/api/police")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(police)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Police> policeList = policeRepository.findAll();
        assertThat(policeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = policeRepository.findAll().size();
        // set the field null
        police.setNumero(null);

        // Create the Police, which fails.

        restPoliceMockMvc.perform(post("/api/police")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(police)))
            .andExpect(status().isBadRequest());

        List<Police> policeList = policeRepository.findAll();
        assertThat(policeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllPolice() throws Exception {
        // Initialize the database
        policeRepository.save(police);

        // Get all the policeList
        restPoliceMockMvc.perform(get("/api/police?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(police.getId())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].section").value(hasItem(DEFAULT_SECTION.toString())));
    }

    @Test
    public void getPolice() throws Exception {
        // Initialize the database
        policeRepository.save(police);

        // Get the police
        restPoliceMockMvc.perform(get("/api/police/{id}", police.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(police.getId()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.section").value(DEFAULT_SECTION.toString()));
    }

    @Test
    public void getNonExistingPolice() throws Exception {
        // Get the police
        restPoliceMockMvc.perform(get("/api/police/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePolice() throws Exception {
        // Initialize the database
        policeRepository.save(police);
        int databaseSizeBeforeUpdate = policeRepository.findAll().size();

        // Update the police
        Police updatedPolice = policeRepository.findOne(police.getId());
        updatedPolice
            .numero(UPDATED_NUMERO)
            .section(UPDATED_SECTION);

        restPoliceMockMvc.perform(put("/api/police")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPolice)))
            .andExpect(status().isOk());

        // Validate the Police in the database
        List<Police> policeList = policeRepository.findAll();
        assertThat(policeList).hasSize(databaseSizeBeforeUpdate);
        Police testPolice = policeList.get(policeList.size() - 1);
        assertThat(testPolice.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testPolice.getSection()).isEqualTo(UPDATED_SECTION);
    }

    @Test
    public void updateNonExistingPolice() throws Exception {
        int databaseSizeBeforeUpdate = policeRepository.findAll().size();

        // Create the Police

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPoliceMockMvc.perform(put("/api/police")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(police)))
            .andExpect(status().isCreated());

        // Validate the Police in the database
        List<Police> policeList = policeRepository.findAll();
        assertThat(policeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deletePolice() throws Exception {
        // Initialize the database
        policeRepository.save(police);
        int databaseSizeBeforeDelete = policeRepository.findAll().size();

        // Get the police
        restPoliceMockMvc.perform(delete("/api/police/{id}", police.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Police> policeList = policeRepository.findAll();
        assertThat(policeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Police.class);
    }
}
