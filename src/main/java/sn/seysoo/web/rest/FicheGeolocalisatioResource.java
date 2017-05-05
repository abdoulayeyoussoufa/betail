package sn.seysoo.web.rest;

import com.codahale.metrics.annotation.Timed;
import sn.seysoo.domain.FicheGeolocalisatio;

import sn.seysoo.repository.FicheGeolocalisatioRepository;
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
 * REST controller for managing FicheGeolocalisatio.
 */
@RestController
@RequestMapping("/api")
public class FicheGeolocalisatioResource {

    private final Logger log = LoggerFactory.getLogger(FicheGeolocalisatioResource.class);

    private static final String ENTITY_NAME = "ficheGeolocalisatio";
        
    private final FicheGeolocalisatioRepository ficheGeolocalisatioRepository;

    public FicheGeolocalisatioResource(FicheGeolocalisatioRepository ficheGeolocalisatioRepository) {
        this.ficheGeolocalisatioRepository = ficheGeolocalisatioRepository;
    }

    /**
     * POST  /fiche-geolocalisatios : Create a new ficheGeolocalisatio.
     *
     * @param ficheGeolocalisatio the ficheGeolocalisatio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ficheGeolocalisatio, or with status 400 (Bad Request) if the ficheGeolocalisatio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fiche-geolocalisatios")
    @Timed
    public ResponseEntity<FicheGeolocalisatio> createFicheGeolocalisatio(@Valid @RequestBody FicheGeolocalisatio ficheGeolocalisatio) throws URISyntaxException {
        log.debug("REST request to save FicheGeolocalisatio : {}", ficheGeolocalisatio);
        if (ficheGeolocalisatio.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ficheGeolocalisatio cannot already have an ID")).body(null);
        }
        FicheGeolocalisatio result = ficheGeolocalisatioRepository.save(ficheGeolocalisatio);
        return ResponseEntity.created(new URI("/api/fiche-geolocalisatios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fiche-geolocalisatios : Updates an existing ficheGeolocalisatio.
     *
     * @param ficheGeolocalisatio the ficheGeolocalisatio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ficheGeolocalisatio,
     * or with status 400 (Bad Request) if the ficheGeolocalisatio is not valid,
     * or with status 500 (Internal Server Error) if the ficheGeolocalisatio couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fiche-geolocalisatios")
    @Timed
    public ResponseEntity<FicheGeolocalisatio> updateFicheGeolocalisatio(@Valid @RequestBody FicheGeolocalisatio ficheGeolocalisatio) throws URISyntaxException {
        log.debug("REST request to update FicheGeolocalisatio : {}", ficheGeolocalisatio);
        if (ficheGeolocalisatio.getId() == null) {
            return createFicheGeolocalisatio(ficheGeolocalisatio);
        }
        FicheGeolocalisatio result = ficheGeolocalisatioRepository.save(ficheGeolocalisatio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ficheGeolocalisatio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fiche-geolocalisatios : get all the ficheGeolocalisatios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ficheGeolocalisatios in body
     */
    @GetMapping("/fiche-geolocalisatios")
    @Timed
    public List<FicheGeolocalisatio> getAllFicheGeolocalisatios() {
        log.debug("REST request to get all FicheGeolocalisatios");
        List<FicheGeolocalisatio> ficheGeolocalisatios = ficheGeolocalisatioRepository.findAll();
        return ficheGeolocalisatios;
    }

    /**
     * GET  /fiche-geolocalisatios/:id : get the "id" ficheGeolocalisatio.
     *
     * @param id the id of the ficheGeolocalisatio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ficheGeolocalisatio, or with status 404 (Not Found)
     */
    @GetMapping("/fiche-geolocalisatios/{id}")
    @Timed
    public ResponseEntity<FicheGeolocalisatio> getFicheGeolocalisatio(@PathVariable String id) {
        log.debug("REST request to get FicheGeolocalisatio : {}", id);
        FicheGeolocalisatio ficheGeolocalisatio = ficheGeolocalisatioRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ficheGeolocalisatio));
    }

    /**
     * DELETE  /fiche-geolocalisatios/:id : delete the "id" ficheGeolocalisatio.
     *
     * @param id the id of the ficheGeolocalisatio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fiche-geolocalisatios/{id}")
    @Timed
    public ResponseEntity<Void> deleteFicheGeolocalisatio(@PathVariable String id) {
        log.debug("REST request to delete FicheGeolocalisatio : {}", id);
        ficheGeolocalisatioRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
