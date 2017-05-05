package sn.seysoo.service.impl;

import sn.seysoo.service.AnimalService;
import sn.seysoo.domain.Animal;
import sn.seysoo.repository.AnimalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Animal.
 */
@Service
public class AnimalServiceImpl implements AnimalService{

    private final Logger log = LoggerFactory.getLogger(AnimalServiceImpl.class);
    
    private final AnimalRepository animalRepository;

    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    /**
     * Save a animal.
     *
     * @param animal the entity to save
     * @return the persisted entity
     */
    @Override
    public Animal save(Animal animal) {
        log.debug("Request to save Animal : {}", animal);
        Animal result = animalRepository.save(animal);
        return result;
    }

    /**
     *  Get all the animals.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<Animal> findAll(Pageable pageable) {
        log.debug("Request to get all Animals");
        Page<Animal> result = animalRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one animal by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public Animal findOne(String id) {
        log.debug("Request to get Animal : {}", id);
        Animal animal = animalRepository.findOne(id);
        return animal;
    }

    /**
     *  Delete the  animal by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Animal : {}", id);
        animalRepository.delete(id);
    }
}
