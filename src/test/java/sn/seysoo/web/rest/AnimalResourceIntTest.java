package sn.seysoo.web.rest;

import sn.seysoo.VolBetailApp;

import sn.seysoo.domain.Animal;
import sn.seysoo.repository.AnimalRepository;
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

import sn.seysoo.domain.enumeration.Sexe;
/**
 * Test class for the AnimalResource REST controller.
 *
 * @see AnimalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VolBetailApp.class)
public class AnimalResourceIntTest {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_COULEUR_ROBE = "AAAAAAAAAA";
    private static final String UPDATED_COULEUR_ROBE = "BBBBBBBBBB";

    private static final Sexe DEFAULT_SEXE = Sexe.M;
    private static final Sexe UPDATED_SEXE = Sexe.F;

    private static final Double DEFAULT_TAILLE = 1D;
    private static final Double UPDATED_TAILLE = 2D;

    private static final Double DEFAULT_POIDS = 1D;
    private static final Double UPDATED_POIDS = 2D;

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAnimalMockMvc;

    private Animal animal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AnimalResource animalResource = new AnimalResource(animalRepository);
        this.restAnimalMockMvc = MockMvcBuilders.standaloneSetup(animalResource)
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
    public static Animal createEntity() {
        Animal animal = new Animal()
            .numero(DEFAULT_NUMERO)
            .couleurRobe(DEFAULT_COULEUR_ROBE)
            .sexe(DEFAULT_SEXE)
            .taille(DEFAULT_TAILLE)
            .poids(DEFAULT_POIDS)
            .age(DEFAULT_AGE);
        return animal;
    }

    @Before
    public void initTest() {
        animalRepository.deleteAll();
        animal = createEntity();
    }

    @Test
    public void createAnimal() throws Exception {
        int databaseSizeBeforeCreate = animalRepository.findAll().size();

        // Create the Animal
        restAnimalMockMvc.perform(post("/api/animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animal)))
            .andExpect(status().isCreated());

        // Validate the Animal in the database
        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeCreate + 1);
        Animal testAnimal = animalList.get(animalList.size() - 1);
        assertThat(testAnimal.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testAnimal.getCouleurRobe()).isEqualTo(DEFAULT_COULEUR_ROBE);
        assertThat(testAnimal.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testAnimal.getTaille()).isEqualTo(DEFAULT_TAILLE);
        assertThat(testAnimal.getPoids()).isEqualTo(DEFAULT_POIDS);
        assertThat(testAnimal.getAge()).isEqualTo(DEFAULT_AGE);
    }

    @Test
    public void createAnimalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = animalRepository.findAll().size();

        // Create the Animal with an existing ID
        animal.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnimalMockMvc.perform(post("/api/animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animal)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = animalRepository.findAll().size();
        // set the field null
        animal.setNumero(null);

        // Create the Animal, which fails.

        restAnimalMockMvc.perform(post("/api/animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animal)))
            .andExpect(status().isBadRequest());

        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAnimals() throws Exception {
        // Initialize the database
        animalRepository.save(animal);

        // Get all the animalList
        restAnimalMockMvc.perform(get("/api/animals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animal.getId())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].couleurRobe").value(hasItem(DEFAULT_COULEUR_ROBE.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].taille").value(hasItem(DEFAULT_TAILLE.doubleValue())))
            .andExpect(jsonPath("$.[*].poids").value(hasItem(DEFAULT_POIDS.doubleValue())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)));
    }

    @Test
    public void getAnimal() throws Exception {
        // Initialize the database
        animalRepository.save(animal);

        // Get the animal
        restAnimalMockMvc.perform(get("/api/animals/{id}", animal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(animal.getId()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.couleurRobe").value(DEFAULT_COULEUR_ROBE.toString()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.taille").value(DEFAULT_TAILLE.doubleValue()))
            .andExpect(jsonPath("$.poids").value(DEFAULT_POIDS.doubleValue()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE));
    }

    @Test
    public void getNonExistingAnimal() throws Exception {
        // Get the animal
        restAnimalMockMvc.perform(get("/api/animals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAnimal() throws Exception {
        // Initialize the database
        animalRepository.save(animal);
        int databaseSizeBeforeUpdate = animalRepository.findAll().size();

        // Update the animal
        Animal updatedAnimal = animalRepository.findOne(animal.getId());
        updatedAnimal
            .numero(UPDATED_NUMERO)
            .couleurRobe(UPDATED_COULEUR_ROBE)
            .sexe(UPDATED_SEXE)
            .taille(UPDATED_TAILLE)
            .poids(UPDATED_POIDS)
            .age(UPDATED_AGE);

        restAnimalMockMvc.perform(put("/api/animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnimal)))
            .andExpect(status().isOk());

        // Validate the Animal in the database
        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeUpdate);
        Animal testAnimal = animalList.get(animalList.size() - 1);
        assertThat(testAnimal.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAnimal.getCouleurRobe()).isEqualTo(UPDATED_COULEUR_ROBE);
        assertThat(testAnimal.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testAnimal.getTaille()).isEqualTo(UPDATED_TAILLE);
        assertThat(testAnimal.getPoids()).isEqualTo(UPDATED_POIDS);
        assertThat(testAnimal.getAge()).isEqualTo(UPDATED_AGE);
    }

    @Test
    public void updateNonExistingAnimal() throws Exception {
        int databaseSizeBeforeUpdate = animalRepository.findAll().size();

        // Create the Animal

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnimalMockMvc.perform(put("/api/animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animal)))
            .andExpect(status().isCreated());

        // Validate the Animal in the database
        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteAnimal() throws Exception {
        // Initialize the database
        animalRepository.save(animal);
        int databaseSizeBeforeDelete = animalRepository.findAll().size();

        // Get the animal
        restAnimalMockMvc.perform(delete("/api/animals/{id}", animal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Animal.class);
    }
}
