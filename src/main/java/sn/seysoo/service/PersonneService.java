package sn.seysoo.service;

import sn.seysoo.domain.Personne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Personne.
 */
public interface PersonneService {

    /**
     * Save a personne.
     *
     * @param personne the entity to save
     * @return the persisted entity
     */
    Personne save(Personne personne);

    /**
     *  Get all the personnes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Personne> findAll(Pageable pageable);

    /**
     *  Get the "id" personne.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Personne findOne(String id);

    /**
     *  Delete the "id" personne.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
