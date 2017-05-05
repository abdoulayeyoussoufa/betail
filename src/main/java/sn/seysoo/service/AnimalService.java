package sn.seysoo.service;

import sn.seysoo.domain.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Animal.
 */
public interface AnimalService {

    /**
     * Save a animal.
     *
     * @param animal the entity to save
     * @return the persisted entity
     */
    Animal save(Animal animal);

    /**
     *  Get all the animals.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Animal> findAll(Pageable pageable);

    /**
     *  Get the "id" animal.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Animal findOne(String id);

    /**
     *  Delete the "id" animal.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
