package sn.seysoo.web.rest;

import com.codahale.metrics.annotation.Timed;
import sn.seysoo.domain.FicheTracabilite;

import sn.seysoo.repository.FicheTracabiliteRepository;
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
 * REST controller for managing FicheTracabilite.
 */
@RestController
@RequestMapping("/api")
public class FicheTracabiliteResource {

    private final Logger log = LoggerFactory.getLogger(FicheTracabiliteResource.class);

    private static final String ENTITY_NAME = "ficheTracabilite";
        
    private final FicheTracabiliteRepository ficheTracabiliteRepository;

    public FicheTracabiliteResource(FicheTracabiliteRepository ficheTracabiliteRepository) {
        this.ficheTracabiliteRepository = ficheTracabiliteRepository;
    }

    /**
     * POST  /fiche-tracabilites : Create a new ficheTracabilite.
     *
     * @param ficheTracabilite the ficheTracabilite to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ficheTracabilite, or with status 400 (Bad Request) if the ficheTracabilite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fiche-tracabilites")
    @Timed
    public ResponseEntity<FicheTracabilite> createFicheTracabilite(@Valid @RequestBody FicheTracabilite ficheTracabilite) throws URISyntaxException {
        log.debug("REST request to save FicheTracabilite : {}", ficheTracabilite);
        if (ficheTracabilite.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ficheTracabilite cannot already have an ID")).body(null);
        }
        FicheTracabilite result = ficheTracabiliteRepository.save(ficheTracabilite);
        return ResponseEntity.created(new URI("/api/fiche-tracabilites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fiche-tracabilites : Updates an existing ficheTracabilite.
     *
     * @param ficheTracabilite the ficheTracabilite to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ficheTracabilite,
     * or with status 400 (Bad Request) if the ficheTracabilite is not valid,
     * or with status 500 (Internal Server Error) if the ficheTracabilite couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fiche-tracabilites")
    @Timed
    public ResponseEntity<FicheTracabilite> updateFicheTracabilite(@Valid @RequestBody FicheTracabilite ficheTracabilite) throws URISyntaxException {
        log.debug("REST request to update FicheTracabilite : {}", ficheTracabilite);
        if (ficheTracabilite.getId() == null) {
            return createFicheTracabilite(ficheTracabilite);
        }
        FicheTracabilite result = ficheTracabiliteRepository.save(ficheTracabilite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ficheTracabilite.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fiche-tracabilites : get all the ficheTracabilites.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ficheTracabilites in body
     */
    @GetMapping("/fiche-tracabilites")
    @Timed
    public List<FicheTracabilite> getAllFicheTracabilites() {
        log.debug("REST request to get all FicheTracabilites");
        List<FicheTracabilite> ficheTracabilites = ficheTracabiliteRepository.findAll();
        return ficheTracabilites;
    }

    /**
     * GET  /fiche-tracabilites/:id : get the "id" ficheTracabilite.
     *
     * @param id the id of the ficheTracabilite to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ficheTracabilite, or with status 404 (Not Found)
     */
    @GetMapping("/fiche-tracabilites/{id}")
    @Timed
    public ResponseEntity<FicheTracabilite> getFicheTracabilite(@PathVariable String id) {
        log.debug("REST request to get FicheTracabilite : {}", id);
        FicheTracabilite ficheTracabilite = ficheTracabiliteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ficheTracabilite));
    }

    /**
     * DELETE  /fiche-tracabilites/:id : delete the "id" ficheTracabilite.
     *
     * @param id the id of the ficheTracabilite to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fiche-tracabilites/{id}")
    @Timed
    public ResponseEntity<Void> deleteFicheTracabilite(@PathVariable String id) {
        log.debug("REST request to delete FicheTracabilite : {}", id);
        ficheTracabiliteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
