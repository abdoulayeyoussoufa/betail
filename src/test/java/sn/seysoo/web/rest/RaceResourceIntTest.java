package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.Race;
import sn.seysoo.repository.RaceRepository;
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
 * Test class for the RaceResource REST controller.
 *
 * @see RaceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class RaceResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_EFFECTIF = 1;
    private static final Integer UPDATED_EFFECTIF = 2;

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restRaceMockMvc;

    private Race race;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RaceResource raceResource = new RaceResource(raceRepository);
        this.restRaceMockMvc = MockMvcBuilders.standaloneSetup(raceResource)
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
    public static Race createEntity() {
        Race race = new Race()
            .nom(DEFAULT_NOM)
            .effectif(DEFAULT_EFFECTIF);
        return race;
    }

    @Before
    public void initTest() {
        raceRepository.deleteAll();
        race = createEntity();
    }

    @Test
    public void createRace() throws Exception {
        int databaseSizeBeforeCreate = raceRepository.findAll().size();

        // Create the Race
        restRaceMockMvc.perform(post("/api/races")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(race)))
            .andExpect(status().isCreated());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeCreate + 1);
        Race testRace = raceList.get(raceList.size() - 1);
        assertThat(testRace.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testRace.getEffectif()).isEqualTo(DEFAULT_EFFECTIF);
    }

    @Test
    public void createRaceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = raceRepository.findAll().size();

        // Create the Race with an existing ID
        race.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restRaceMockMvc.perform(post("/api/races")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(race)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = raceRepository.findAll().size();
        // set the field null
        race.setNom(null);

        // Create the Race, which fails.

        restRaceMockMvc.perform(post("/api/races")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(race)))
            .andExpect(status().isBadRequest());

        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEffectifIsRequired() throws Exception {
        int databaseSizeBeforeTest = raceRepository.findAll().size();
        // set the field null
        race.setEffectif(null);

        // Create the Race, which fails.

        restRaceMockMvc.perform(post("/api/races")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(race)))
            .andExpect(status().isBadRequest());

        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllRaces() throws Exception {
        // Initialize the database
        raceRepository.save(race);

        // Get all the raceList
        restRaceMockMvc.perform(get("/api/races?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(race.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].effectif").value(hasItem(DEFAULT_EFFECTIF)));
    }

    @Test
    public void getRace() throws Exception {
        // Initialize the database
        raceRepository.save(race);

        // Get the race
        restRaceMockMvc.perform(get("/api/races/{id}", race.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(race.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.effectif").value(DEFAULT_EFFECTIF));
    }

    @Test
    public void getNonExistingRace() throws Exception {
        // Get the race
        restRaceMockMvc.perform(get("/api/races/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRace() throws Exception {
        // Initialize the database
        raceRepository.save(race);
        int databaseSizeBeforeUpdate = raceRepository.findAll().size();

        // Update the race
        Race updatedRace = raceRepository.findOne(race.getId());
        updatedRace
            .nom(UPDATED_NOM)
            .effectif(UPDATED_EFFECTIF);

        restRaceMockMvc.perform(put("/api/races")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRace)))
            .andExpect(status().isOk());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
        Race testRace = raceList.get(raceList.size() - 1);
        assertThat(testRace.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testRace.getEffectif()).isEqualTo(UPDATED_EFFECTIF);
    }

    @Test
    public void updateNonExistingRace() throws Exception {
        int databaseSizeBeforeUpdate = raceRepository.findAll().size();

        // Create the Race

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRaceMockMvc.perform(put("/api/races")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(race)))
            .andExpect(status().isCreated());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteRace() throws Exception {
        // Initialize the database
        raceRepository.save(race);
        int databaseSizeBeforeDelete = raceRepository.findAll().size();

        // Get the race
        restRaceMockMvc.perform(delete("/api/races/{id}", race.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Race.class);
    }
}
