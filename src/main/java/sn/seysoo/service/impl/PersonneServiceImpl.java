package sn.seysoo.service.impl;

import sn.seysoo.service.PersonneService;
import sn.seysoo.domain.Personne;
import sn.seysoo.repository.PersonneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Personne.
 */
@Service
public class PersonneServiceImpl implements PersonneService{

    private final Logger log = LoggerFactory.getLogger(PersonneServiceImpl.class);
    
    private final PersonneRepository personneRepository;

    public PersonneServiceImpl(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    /**
     * Save a personne.
     *
     * @param personne the entity to save
     * @return the persisted entity
     */
    @Override
    public Personne save(Personne personne) {
        log.debug("Request to save Personne : {}", personne);
        Personne result = personneRepository.save(personne);
        return result;
    }

    /**
     *  Get all the personnes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<Personne> findAll(Pageable pageable) {
        log.debug("Request to get all Personnes");
        Page<Personne> result = personneRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personne by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public Personne findOne(String id) {
        log.debug("Request to get Personne : {}", id);
        Personne personne = personneRepository.findOne(id);
        return personne;
    }

    /**
     *  Delete the  personne by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Personne : {}", id);
        personneRepository.delete(id);
    }
}
