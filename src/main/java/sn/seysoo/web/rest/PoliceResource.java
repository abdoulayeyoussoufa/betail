package sn.seysoo.web.rest;

import com.codahale.metrics.annotation.Timed;
import sn.seysoo.domain.Police;

import sn.seysoo.repository.PoliceRepository;
import sn.seysoo.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Police.
 */
@RestController
@RequestMapping("/api")
public class PoliceResource {

    private final Logger log = LoggerFactory.getLogger(PoliceResource.class);

    private static final String ENTITY_NAME = "police";
        
    private final PoliceRepository policeRepository;

    public PoliceResource(PoliceRepository policeRepository) {
        this.policeRepository = policeRepository;
    }

    /**
     * POST  /police : Create a new police.
     *
     * @param police the police to create
     * @return the ResponseEntity with status 201 (Created) and with body the new police, or with status 400 (Bad Request) if the police has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/police")
    @Timed
    public ResponseEntity<Police> createPolice(@Valid @RequestBody Police police) throws URISyntaxException {
        log.debug("REST request to save Police : {}", police);
        if (police.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new police cannot already have an ID")).body(null);
        }
        Police result = policeRepository.save(police);
        return ResponseEntity.created(new URI("/api/police/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /police : Updates an existing police.
     *
     * @param police the police to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated police,
     * or with status 400 (Bad Request) if the police is not valid,
     * or with status 500 (Internal Server Error) if the police couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/police")
    @Timed
    public ResponseEntity<Police> updatePolice(@Valid @RequestBody Police police) throws URISyntaxException {
        log.debug("REST request to update Police : {}", police);
        if (police.getId() == null) {
            return createPolice(police);
        }
        Police result = policeRepository.save(police);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, police.getId().toString()))
            .body(result);
    }

    /**
     * GET  /police : get all the police.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of police in body
     */
    @GetMapping("/police")
    @Timed
    public List<Police> getAllPolice() {
        log.debug("REST request to get all Police");
        List<Police> police = policeRepository.findAll();
        return police;
    }

    /**
     * GET  /police/:id : get the "id" police.
     *
     * @param id the id of the police to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the police, or with status 404 (Not Found)
     */
    @GetMapping("/police/{id}")
    @Timed
    public ResponseEntity<Police> getPolice(@PathVariable String id) {
        log.debug("REST request to get Police : {}", id);
        Police police = policeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(police));
    }

    /**
     * DELETE  /police/:id : delete the "id" police.
     *
     * @param id the id of the police to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/police/{id}")
    @Timed
    public ResponseEntity<Void> deletePolice(@PathVariable String id) {
        log.debug("REST request to delete Police : {}", id);
        policeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
