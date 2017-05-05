package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.VolMessage;
import sn.seysoo.repository.VolMessageRepository;
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
 * Test class for the VolMessageResource REST controller.
 *
 * @see VolMessageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class VolMessageResourceIntTest {

    @Autowired
    private VolMessageRepository volMessageRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restVolMessageMockMvc;

    private VolMessage volMessage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VolMessageResource volMessageResource = new VolMessageResource(volMessageRepository);
        this.restVolMessageMockMvc = MockMvcBuilders.standaloneSetup(volMessageResource)
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
    public static VolMessage createEntity() {
        VolMessage volMessage = new VolMessage();
        return volMessage;
    }

    @Before
    public void initTest() {
        volMessageRepository.deleteAll();
        volMessage = createEntity();
    }

    @Test
    public void createVolMessage() throws Exception {
        int databaseSizeBeforeCreate = volMessageRepository.findAll().size();

        // Create the VolMessage
        restVolMessageMockMvc.perform(post("/api/vol-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(volMessage)))
            .andExpect(status().isCreated());

        // Validate the VolMessage in the database
        List<VolMessage> volMessageList = volMessageRepository.findAll();
        assertThat(volMessageList).hasSize(databaseSizeBeforeCreate + 1);
        VolMessage testVolMessage = volMessageList.get(volMessageList.size() - 1);
    }

    @Test
    public void createVolMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = volMessageRepository.findAll().size();

        // Create the VolMessage with an existing ID
        volMessage.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restVolMessageMockMvc.perform(post("/api/vol-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(volMessage)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<VolMessage> volMessageList = volMessageRepository.findAll();
        assertThat(volMessageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllVolMessages() throws Exception {
        // Initialize the database
        volMessageRepository.save(volMessage);

        // Get all the volMessageList
        restVolMessageMockMvc.perform(get("/api/vol-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(volMessage.getId())));
    }

    @Test
    public void getVolMessage() throws Exception {
        // Initialize the database
        volMessageRepository.save(volMessage);

        // Get the volMessage
        restVolMessageMockMvc.perform(get("/api/vol-messages/{id}", volMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(volMessage.getId()));
    }

    @Test
    public void getNonExistingVolMessage() throws Exception {
        // Get the volMessage
        restVolMessageMockMvc.perform(get("/api/vol-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVolMessage() throws Exception {
        // Initialize the database
        volMessageRepository.save(volMessage);
        int databaseSizeBeforeUpdate = volMessageRepository.findAll().size();

        // Update the volMessage
        VolMessage updatedVolMessage = volMessageRepository.findOne(volMessage.getId());

        restVolMessageMockMvc.perform(put("/api/vol-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVolMessage)))
            .andExpect(status().isOk());

        // Validate the VolMessage in the database
        List<VolMessage> volMessageList = volMessageRepository.findAll();
        assertThat(volMessageList).hasSize(databaseSizeBeforeUpdate);
        VolMessage testVolMessage = volMessageList.get(volMessageList.size() - 1);
    }

    @Test
    public void updateNonExistingVolMessage() throws Exception {
        int databaseSizeBeforeUpdate = volMessageRepository.findAll().size();

        // Create the VolMessage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVolMessageMockMvc.perform(put("/api/vol-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(volMessage)))
            .andExpect(status().isCreated());

        // Validate the VolMessage in the database
        List<VolMessage> volMessageList = volMessageRepository.findAll();
        assertThat(volMessageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteVolMessage() throws Exception {
        // Initialize the database
        volMessageRepository.save(volMessage);
        int databaseSizeBeforeDelete = volMessageRepository.findAll().size();

        // Get the volMessage
        restVolMessageMockMvc.perform(delete("/api/vol-messages/{id}", volMessage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VolMessage> volMessageList = volMessageRepository.findAll();
        assertThat(volMessageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VolMessage.class);
    }
}
