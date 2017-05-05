package sn.seysoo.web.rest;

import com.codahale.metrics.annotation.Timed;
import sn.seysoo.domain.Eleveur;

import sn.seysoo.repository.EleveurRepository;
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
 * REST controller for managing Eleveur.
 */
@RestController
@RequestMapping("/api")
public class EleveurResource {

    private final Logger log = LoggerFactory.getLogger(EleveurResource.class);

    private static final String ENTITY_NAME = "eleveur";
        
    private final EleveurRepository eleveurRepository;

    public EleveurResource(EleveurRepository eleveurRepository) {
        this.eleveurRepository = eleveurRepository;
    }

    /**
     * POST  /eleveurs : Create a new eleveur.
     *
     * @param eleveur the eleveur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eleveur, or with status 400 (Bad Request) if the eleveur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/eleveurs")
    @Timed
    public ResponseEntity<Eleveur> createEleveur(@Valid @RequestBody Eleveur eleveur) throws URISyntaxException {
        log.debug("REST request to save Eleveur : {}", eleveur);
        if (eleveur.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new eleveur cannot already have an ID")).body(null);
        }
        Eleveur result = eleveurRepository.save(eleveur);
        return ResponseEntity.created(new URI("/api/eleveurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eleveurs : Updates an existing eleveur.
     *
     * @param eleveur the eleveur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eleveur,
     * or with status 400 (Bad Request) if the eleveur is not valid,
     * or with status 500 (Internal Server Error) if the eleveur couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/eleveurs")
    @Timed
    public ResponseEntity<Eleveur> updateEleveur(@Valid @RequestBody Eleveur eleveur) throws URISyntaxException {
        log.debug("REST request to update Eleveur : {}", eleveur);
        if (eleveur.getId() == null) {
            return createEleveur(eleveur);
        }
        Eleveur result = eleveurRepository.save(eleveur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eleveur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eleveurs : get all the eleveurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eleveurs in body
     */
    @GetMapping("/eleveurs")
    @Timed
    public List<Eleveur> getAllEleveurs() {
        log.debug("REST request to get all Eleveurs");
        List<Eleveur> eleveurs = eleveurRepository.findAll();
        return eleveurs;
    }

    /**
     * GET  /eleveurs/:id : get the "id" eleveur.
     *
     * @param id the id of the eleveur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eleveur, or with status 404 (Not Found)
     */
    @GetMapping("/eleveurs/{id}")
    @Timed
    public ResponseEntity<Eleveur> getEleveur(@PathVariable String id) {
        log.debug("REST request to get Eleveur : {}", id);
        Eleveur eleveur = eleveurRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eleveur));
    }

    /**
     * DELETE  /eleveurs/:id : delete the "id" eleveur.
     *
     * @param id the id of the eleveur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/eleveurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteEleveur(@PathVariable String id) {
        log.debug("REST request to delete Eleveur : {}", id);
        eleveurRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
