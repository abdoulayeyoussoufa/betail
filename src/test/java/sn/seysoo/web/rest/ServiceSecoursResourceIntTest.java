package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.ServiceSecours;
import sn.seysoo.repository.ServiceSecoursRepository;
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
 * Test class for the ServiceSecoursResource REST controller.
 *
 * @see ServiceSecoursResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class ServiceSecoursResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_SIEGE = "AAAAAAAAAA";
    private static final String UPDATED_SIEGE = "BBBBBBBBBB";

    @Autowired
    private ServiceSecoursRepository serviceSecoursRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restServiceSecoursMockMvc;

    private ServiceSecours serviceSecours;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ServiceSecoursResource serviceSecoursResource = new ServiceSecoursResource(serviceSecoursRepository);
        this.restServiceSecoursMockMvc = MockMvcBuilders.standaloneSetup(serviceSecoursResource)
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
    public static ServiceSecours createEntity() {
        ServiceSecours serviceSecours = new ServiceSecours()
            .nom(DEFAULT_NOM)
            .siege(DEFAULT_SIEGE);
        return serviceSecours;
    }

    @Before
    public void initTest() {
        serviceSecoursRepository.deleteAll();
        serviceSecours = createEntity();
    }

    @Test
    public void createServiceSecours() throws Exception {
        int databaseSizeBeforeCreate = serviceSecoursRepository.findAll().size();

        // Create the ServiceSecours
        restServiceSecoursMockMvc.perform(post("/api/service-secours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSecours)))
            .andExpect(status().isCreated());

        // Validate the ServiceSecours in the database
        List<ServiceSecours> serviceSecoursList = serviceSecoursRepository.findAll();
        assertThat(serviceSecoursList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceSecours testServiceSecours = serviceSecoursList.get(serviceSecoursList.size() - 1);
        assertThat(testServiceSecours.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testServiceSecours.getSiege()).isEqualTo(DEFAULT_SIEGE);
    }

    @Test
    public void createServiceSecoursWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceSecoursRepository.findAll().size();

        // Create the ServiceSecours with an existing ID
        serviceSecours.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceSecoursMockMvc.perform(post("/api/service-secours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSecours)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ServiceSecours> serviceSecoursList = serviceSecoursRepository.findAll();
        assertThat(serviceSecoursList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllServiceSecours() throws Exception {
        // Initialize the database
        serviceSecoursRepository.save(serviceSecours);

        // Get all the serviceSecoursList
        restServiceSecoursMockMvc.perform(get("/api/service-secours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceSecours.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].siege").value(hasItem(DEFAULT_SIEGE.toString())));
    }

    @Test
    public void getServiceSecours() throws Exception {
        // Initialize the database
        serviceSecoursRepository.save(serviceSecours);

        // Get the serviceSecours
        restServiceSecoursMockMvc.perform(get("/api/service-secours/{id}", serviceSecours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceSecours.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.siege").value(DEFAULT_SIEGE.toString()));
    }

    @Test
    public void getNonExistingServiceSecours() throws Exception {
        // Get the serviceSecours
        restServiceSecoursMockMvc.perform(get("/api/service-secours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateServiceSecours() throws Exception {
        // Initialize the database
        serviceSecoursRepository.save(serviceSecours);
        int databaseSizeBeforeUpdate = serviceSecoursRepository.findAll().size();

        // Update the serviceSecours
        ServiceSecours updatedServiceSecours = serviceSecoursRepository.findOne(serviceSecours.getId());
        updatedServiceSecours
            .nom(UPDATED_NOM)
            .siege(UPDATED_SIEGE);

        restServiceSecoursMockMvc.perform(put("/api/service-secours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceSecours)))
            .andExpect(status().isOk());

        // Validate the ServiceSecours in the database
        List<ServiceSecours> serviceSecoursList = serviceSecoursRepository.findAll();
        assertThat(serviceSecoursList).hasSize(databaseSizeBeforeUpdate);
        ServiceSecours testServiceSecours = serviceSecoursList.get(serviceSecoursList.size() - 1);
        assertThat(testServiceSecours.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testServiceSecours.getSiege()).isEqualTo(UPDATED_SIEGE);
    }

    @Test
    public void updateNonExistingServiceSecours() throws Exception {
        int databaseSizeBeforeUpdate = serviceSecoursRepository.findAll().size();

        // Create the ServiceSecours

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServiceSecoursMockMvc.perform(put("/api/service-secours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceSecours)))
            .andExpect(status().isCreated());

        // Validate the ServiceSecours in the database
        List<ServiceSecours> serviceSecoursList = serviceSecoursRepository.findAll();
        assertThat(serviceSecoursList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteServiceSecours() throws Exception {
        // Initialize the database
        serviceSecoursRepository.save(serviceSecours);
        int databaseSizeBeforeDelete = serviceSecoursRepository.findAll().size();

        // Get the serviceSecours
        restServiceSecoursMockMvc.perform(delete("/api/service-secours/{id}", serviceSecours.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ServiceSecours> serviceSecoursList = serviceSecoursRepository.findAll();
        assertThat(serviceSecoursList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceSecours.class);
    }
}
