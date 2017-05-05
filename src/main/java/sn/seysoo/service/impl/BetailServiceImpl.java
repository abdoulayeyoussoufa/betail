package sn.seysoo.service.impl;

import sn.seysoo.service.BetailService;
import sn.seysoo.domain.Betail;
import sn.seysoo.repository.BetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Betail.
 */
@Service
public class BetailServiceImpl implements BetailService{

    private final Logger log = LoggerFactory.getLogger(BetailServiceImpl.class);
    
    private final BetailRepository betailRepository;

    public BetailServiceImpl(BetailRepository betailRepository) {
        this.betailRepository = betailRepository;
    }

    /**
     * Save a betail.
     *
     * @param betail the entity to save
     * @return the persisted entity
     */
    @Override
    public Betail save(Betail betail) {
        log.debug("Request to save Betail : {}", betail);
        Betail result = betailRepository.save(betail);
        return result;
    }

    /**
     *  Get all the betails.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<Betail> findAll(Pageable pageable) {
        log.debug("Request to get all Betails");
        Page<Betail> result = betailRepository.findAll(pageable);
        return result;
    }
   

    /**
     *  Get one betail by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public Betail findOne(String id) {
        log.debug("Request to get Betail : {}", id);
        Betail betail = betailRepository.findOne(id);
        return betail;
    }

    /**
     *  Delete the  betail by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Betail : {}", id);
        betailRepository.delete(id);
    }
    /**
     * Algorithmes d'apprentissage du betail
     */
    public Page<Betail> algo(Pageable pageable) {
        log.debug("Algorithmes d'apprentissage du betail");
        Page<Betail> result = betailRepository.findAll(pageable);
        return result;
    }
}
