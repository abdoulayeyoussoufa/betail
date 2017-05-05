package sn.seysoo.service;

import sn.seysoo.domain.Betail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Betail.
 */
public interface BetailService {

    /**
     * Save a betail.
     *
     * @param betail the entity to save
     * @return the persisted entity
     */
    Betail save(Betail betail);

    /**
     *  Get all the betails.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Betail> findAll(Pageable pageable);

    /**
     *  Get the "id" betail.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Betail findOne(String id);

    /**
     *  Delete the "id" betail.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
