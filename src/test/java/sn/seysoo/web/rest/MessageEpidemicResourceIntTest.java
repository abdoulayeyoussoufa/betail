package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.MessageEpidemic;
import sn.seysoo.repository.MessageEpidemicRepository;
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
 * Test class for the MessageEpidemicResource REST controller.
 *
 * @see MessageEpidemicResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class MessageEpidemicResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU = "AAAAAAAAAA";
    private static final String UPDATED_LIEU = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private MessageEpidemicRepository messageEpidemicRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMessageEpidemicMockMvc;

    private MessageEpidemic messageEpidemic;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MessageEpidemicResource messageEpidemicResource = new MessageEpidemicResource(messageEpidemicRepository);
        this.restMessageEpidemicMockMvc = MockMvcBuilders.standaloneSetup(messageEpidemicResource)
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
    public static MessageEpidemic createEntity() {
        MessageEpidemic messageEpidemic = new MessageEpidemic()
            .nom(DEFAULT_NOM)
            .date(DEFAULT_DATE)
            .lieu(DEFAULT_LIEU)
            .description(DEFAULT_DESCRIPTION);
        return messageEpidemic;
    }

    @Before
    public void initTest() {
        messageEpidemicRepository.deleteAll();
        messageEpidemic = createEntity();
    }

    @Test
    public void createMessageEpidemic() throws Exception {
        int databaseSizeBeforeCreate = messageEpidemicRepository.findAll().size();

        // Create the MessageEpidemic
        restMessageEpidemicMockMvc.perform(post("/api/message-epidemics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageEpidemic)))
            .andExpect(status().isCreated());

        // Validate the MessageEpidemic in the database
        List<MessageEpidemic> messageEpidemicList = messageEpidemicRepository.findAll();
        assertThat(messageEpidemicList).hasSize(databaseSizeBeforeCreate + 1);
        MessageEpidemic testMessageEpidemic = messageEpidemicList.get(messageEpidemicList.size() - 1);
        assertThat(testMessageEpidemic.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMessageEpidemic.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMessageEpidemic.getLieu()).isEqualTo(DEFAULT_LIEU);
        assertThat(testMessageEpidemic.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void createMessageEpidemicWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageEpidemicRepository.findAll().size();

        // Create the MessageEpidemic with an existing ID
        messageEpidemic.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageEpidemicMockMvc.perform(post("/api/message-epidemics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageEpidemic)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MessageEpidemic> messageEpidemicList = messageEpidemicRepository.findAll();
        assertThat(messageEpidemicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageEpidemicRepository.findAll().size();
        // set the field null
        messageEpidemic.setNom(null);

        // Create the MessageEpidemic, which fails.

        restMessageEpidemicMockMvc.perform(post("/api/message-epidemics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageEpidemic)))
            .andExpect(status().isBadRequest());

        List<MessageEpidemic> messageEpidemicList = messageEpidemicRepository.findAll();
        assertThat(messageEpidemicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageEpidemicRepository.findAll().size();
        // set the field null
        messageEpidemic.setDate(null);

        // Create the MessageEpidemic, which fails.

        restMessageEpidemicMockMvc.perform(post("/api/message-epidemics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageEpidemic)))
            .andExpect(status().isBadRequest());

        List<MessageEpidemic> messageEpidemicList = messageEpidemicRepository.findAll();
        assertThat(messageEpidemicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllMessageEpidemics() throws Exception {
        // Initialize the database
        messageEpidemicRepository.save(messageEpidemic);

        // Get all the messageEpidemicList
        restMessageEpidemicMockMvc.perform(get("/api/message-epidemics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageEpidemic.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].lieu").value(hasItem(DEFAULT_LIEU.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    public void getMessageEpidemic() throws Exception {
        // Initialize the database
        messageEpidemicRepository.save(messageEpidemic);

        // Get the messageEpidemic
        restMessageEpidemicMockMvc.perform(get("/api/message-epidemics/{id}", messageEpidemic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(messageEpidemic.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.lieu").value(DEFAULT_LIEU.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    public void getNonExistingMessageEpidemic() throws Exception {
        // Get the messageEpidemic
        restMessageEpidemicMockMvc.perform(get("/api/message-epidemics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateMessageEpidemic() throws Exception {
        // Initialize the database
        messageEpidemicRepository.save(messageEpidemic);
        int databaseSizeBeforeUpdate = messageEpidemicRepository.findAll().size();

        // Update the messageEpidemic
        MessageEpidemic updatedMessageEpidemic = messageEpidemicRepository.findOne(messageEpidemic.getId());
        updatedMessageEpidemic
            .nom(UPDATED_NOM)
            .date(UPDATED_DATE)
            .lieu(UPDATED_LIEU)
            .description(UPDATED_DESCRIPTION);

        restMessageEpidemicMockMvc.perform(put("/api/message-epidemics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMessageEpidemic)))
            .andExpect(status().isOk());

        // Validate the MessageEpidemic in the database
        List<MessageEpidemic> messageEpidemicList = messageEpidemicRepository.findAll();
        assertThat(messageEpidemicList).hasSize(databaseSizeBeforeUpdate);
        MessageEpidemic testMessageEpidemic = messageEpidemicList.get(messageEpidemicList.size() - 1);
        assertThat(testMessageEpidemic.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMessageEpidemic.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMessageEpidemic.getLieu()).isEqualTo(UPDATED_LIEU);
        assertThat(testMessageEpidemic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void updateNonExistingMessageEpidemic() throws Exception {
        int databaseSizeBeforeUpdate = messageEpidemicRepository.findAll().size();

        // Create the MessageEpidemic

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMessageEpidemicMockMvc.perform(put("/api/message-epidemics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageEpidemic)))
            .andExpect(status().isCreated());

        // Validate the MessageEpidemic in the database
        List<MessageEpidemic> messageEpidemicList = messageEpidemicRepository.findAll();
        assertThat(messageEpidemicList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteMessageEpidemic() throws Exception {
        // Initialize the database
        messageEpidemicRepository.save(messageEpidemic);
        int databaseSizeBeforeDelete = messageEpidemicRepository.findAll().size();

        // Get the messageEpidemic
        restMessageEpidemicMockMvc.perform(delete("/api/message-epidemics/{id}", messageEpidemic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MessageEpidemic> messageEpidemicList = messageEpidemicRepository.findAll();
        assertThat(messageEpidemicList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageEpidemic.class);
    }
}
