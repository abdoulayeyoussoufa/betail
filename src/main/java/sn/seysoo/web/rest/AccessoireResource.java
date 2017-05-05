package sn.seysoo.web.rest;

import com.codahale.metrics.annotation.Timed;
import sn.seysoo.domain.Accessoire;

import sn.seysoo.repository.AccessoireRepository;
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
 * REST controller for managing Accessoire.
 */
@RestController
@RequestMapping("/api")
public class AccessoireResource {

    private final Logger log = LoggerFactory.getLogger(AccessoireResource.class);

    private static final String ENTITY_NAME = "accessoire";
        
    private final AccessoireRepository accessoireRepository;

    public AccessoireResource(AccessoireRepository accessoireRepository) {
        this.accessoireRepository = accessoireRepository;
    }

    /**
     * POST  /accessoires : Create a new accessoire.
     *
     * @param accessoire the accessoire to create
     * @return the ResponseEntity with status 201 (Created) and with body the new accessoire, or with status 400 (Bad Request) if the accessoire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/accessoires")
    @Timed
    public ResponseEntity<Accessoire> createAccessoire(@Valid @RequestBody Accessoire accessoire) throws URISyntaxException {
        log.debug("REST request to save Accessoire : {}", accessoire);
        if (accessoire.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new accessoire cannot already have an ID")).body(null);
        }
        Accessoire result = accessoireRepository.save(accessoire);
        return ResponseEntity.created(new URI("/api/accessoires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
      * PUT  /accessoires : Updates an existing accessoire.
     *
     * @param accessoire the accessoire to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accessoire,
     * or with status 400 (Bad Request) if the accessoire is not valid,
     * or with status 500 (Internal Server Error) if the accessoire couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/accessoires")
    @Timed
    public ResponseEntity<Accessoire> updateAccessoire(@Valid @RequestBody Accessoire accessoire) throws URISyntaxException {
        log.debug("REST request to update Accessoire : {}", accessoire);
        if (accessoire.getId() == null) {
            return createAccessoire(accessoire);
        }
        Accessoire result = accessoireRepository.save(accessoire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, accessoire.getId().toString()))
            .body(result);
    }

    /**
     * GET  /accessoires : get all the accessoires.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of accessoires in body
     */
    @GetMapping("/accessoires")
    @Timed
    public List<Accessoire> getAllAccessoires() {
        log.debug("REST request to get all Accessoires");
        List<Accessoire> accessoires = accessoireRepository.findAll();
        return accessoires;
    }

    /**
     * GET  /accessoires/:id : get the "id" accessoire.
     *
     * @param id the id of the accessoire to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accessoire, or with status 404 (Not Found)
     */
    @GetMapping("/accessoires/{id}")
    @Timed
    public ResponseEntity<Accessoire> getAccessoire(@PathVariable String id) {
        log.debug("REST request to get Accessoire : {}", id);
        Accessoire accessoire = accessoireRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(accessoire));
    }

    /**
     * DELETE  /accessoires/:id : delete the "id" accessoire.
     *
     * @param id the id of the accessoire to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/accessoires/{id}")
    @Timed
    public ResponseEntity<Void> deleteAccessoire(@PathVariable String id) {
        log.debug("REST request to delete Accessoire : {}", id);
        accessoireRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
