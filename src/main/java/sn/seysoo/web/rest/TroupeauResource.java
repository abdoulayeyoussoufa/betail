package sn.seysoo.web.rest;

import com.codahale.metrics.annotation.Timed;
import sn.seysoo.domain.Troupeau;

import sn.seysoo.repository.TroupeauRepository;
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
 * REST controller for managing Troupeau.
 */
@RestController
@RequestMapping("/api")
public class TroupeauResource {

    private final Logger log = LoggerFactory.getLogger(TroupeauResource.class);

    private static final String ENTITY_NAME = "troupeau";
        
    private final TroupeauRepository troupeauRepository;

    public TroupeauResource(TroupeauRepository troupeauRepository) {
        this.troupeauRepository = troupeauRepository;
    }

    /**
     * POST  /troupeaus : Create a new troupeau.
     *
     * @param troupeau the troupeau to create
     * @return the ResponseEntity with status 201 (Created) and with body the new troupeau, or with status 400 (Bad Request) if the troupeau has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/troupeaus")
    @Timed
    public ResponseEntity<Troupeau> createTroupeau(@Valid @RequestBody Troupeau troupeau) throws URISyntaxException {
        log.debug("REST request to save Troupeau : {}", troupeau);
        if (troupeau.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new troupeau cannot already have an ID")).body(null);
        }
        Troupeau result = troupeauRepository.save(troupeau);
        return ResponseEntity.created(new URI("/api/troupeaus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /troupeaus : Updates an existing troupeau.
     *
     * @param troupeau the troupeau to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated troupeau,
     * or with status 400 (Bad Request) if the troupeau is not valid,
     * or with status 500 (Internal Server Error) if the troupeau couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/troupeaus")
    @Timed
    public ResponseEntity<Troupeau> updateTroupeau(@Valid @RequestBody Troupeau troupeau) throws URISyntaxException {
        log.debug("REST request to update Troupeau : {}", troupeau);
        if (troupeau.getId() == null) {
            return createTroupeau(troupeau);
        }
        Troupeau result = troupeauRepository.save(troupeau);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, troupeau.getId().toString()))
            .body(result);
    }

    /**
     * GET  /troupeaus : get all the troupeaus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of troupeaus in body
     */
    @GetMapping("/troupeaus")
    @Timed
    public List<Troupeau> getAllTroupeaus() {
        log.debug("REST request to get all Troupeaus");
        List<Troupeau> troupeaus = troupeauRepository.findAll();
        return troupeaus;
    }

    /**
     * GET  /troupeaus/:id : get the "id" troupeau.
     *
     * @param id the id of the troupeau to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the troupeau, or with status 404 (Not Found)
     */
    @GetMapping("/troupeaus/{id}")
    @Timed
    public ResponseEntity<Troupeau> getTroupeau(@PathVariable String id) {
        log.debug("REST request to get Troupeau : {}", id);
        Troupeau troupeau = troupeauRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(troupeau));
    }

    /**
     * DELETE  /troupeaus/:id : delete the "id" troupeau.
     *
     * @param id the id of the troupeau to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/troupeaus/{id}")
    @Timed
    public ResponseEntity<Void> deleteTroupeau(@PathVariable String id) {
        log.debug("REST request to delete Troupeau : {}", id);
        troupeauRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
