package sn.seysoo.multiplex.service;

import sn.seysoo.multiplex.domain.Police;
import sn.seysoo.multiplex.repository.PoliceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Police.
 */
@Service
@Transactional
public class PoliceService {

    private final Logger log = LoggerFactory.getLogger(PoliceService.class);
    
    @Inject
    private PoliceRepository policeRepository;

    /**
     * Save a police.
     *
     * @param police the entity to save
     * @return the persisted entity
     */
    public Police save(Police police) {
        log.debug("Request to save Police : {}", police);
        Police result = policeRepository.save(police);
        return result;
    }

    /**
     *  Get all the police.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Police> findAll(Pageable pageable) {
        log.debug("Request to get all Police");
        Page<Police> result = policeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one police by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Police findOne(Long id) {
        log.debug("Request to get Police : {}", id);
        Police police = policeRepository.findOne(id);
        return police;
    }

    /**
     *  Delete the  police by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Police : {}", id);
        policeRepository.delete(id);
    }
}
