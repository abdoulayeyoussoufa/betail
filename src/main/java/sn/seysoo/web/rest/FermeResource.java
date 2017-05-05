package sn.seysoo.web.rest;

import com.codahale.metrics.annotation.Timed;
import sn.seysoo.domain.Ferme;

import sn.seysoo.repository.FermeRepository;
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
 * REST controller for managing Ferme.
 */
@RestController
@RequestMapping("/api")
public class FermeResource {

    private final Logger log = LoggerFactory.getLogger(FermeResource.class);

    private static final String ENTITY_NAME = "ferme";
        
    private final FermeRepository fermeRepository;

    public FermeResource(FermeRepository fermeRepository) {
        this.fermeRepository = fermeRepository;
    }

    /**
     * POST  /fermes : Create a new ferme.
     *
     * @param ferme the ferme to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ferme, or with status 400 (Bad Request) if the ferme has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fermes")
    @Timed
    public ResponseEntity<Ferme> createFerme(@Valid @RequestBody Ferme ferme) throws URISyntaxException {
        log.debug("REST request to save Ferme : {}", ferme);
        if (ferme.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ferme cannot already have an ID")).body(null);
        }
        Ferme result = fermeRepository.save(ferme);
        return ResponseEntity.created(new URI("/api/fermes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fermes : Updates an existing ferme.
     *
     * @param ferme the ferme to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ferme,
     * or with status 400 (Bad Request) if the ferme is not valid,
     * or with status 500 (Internal Server Error) if the ferme couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fermes")
    @Timed
    public ResponseEntity<Ferme> updateFerme(@Valid @RequestBody Ferme ferme) throws URISyntaxException {
        log.debug("REST request to update Ferme : {}", ferme);
        if (ferme.getId() == null) {
            return createFerme(ferme);
        }
        Ferme result = fermeRepository.save(ferme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ferme.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fermes : get all the fermes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fermes in body
     */
    @GetMapping("/fermes")
    @Timed
    public List<Ferme> getAllFermes() {
        log.debug("REST request to get all Fermes");
        List<Ferme> fermes = fermeRepository.findAll();
        return fermes;
    }

    /**
     * GET  /fermes/:id : get the "id" ferme.
     *
     * @param id the id of the ferme to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ferme, or with status 404 (Not Found)
     */
    @GetMapping("/fermes/{id}")
    @Timed
    public ResponseEntity<Ferme> getFerme(@PathVariable String id) {
        log.debug("REST request to get Ferme : {}", id);
        Ferme ferme = fermeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ferme));
    }

    /**
     * DELETE  /fermes/:id : delete the "id" ferme.
     *
     * @param id the id of the ferme to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fermes/{id}")
    @Timed
    public ResponseEntity<Void> deleteFerme(@PathVariable String id) {
        log.debug("REST request to delete Ferme : {}", id);
        fermeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
