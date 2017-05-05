package sn.seysoo.web.rest;

import com.codahale.metrics.annotation.Timed;
import sn.seysoo.domain.AvisPerte;

import sn.seysoo.repository.AvisPerteRepository;
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
 * REST controller for managing AvisPerte.
 */
@RestController
@RequestMapping("/api")
public class AvisPerteResource {

    private final Logger log = LoggerFactory.getLogger(AvisPerteResource.class);

    private static final String ENTITY_NAME = "avisPerte";
        
    private final AvisPerteRepository avisPerteRepository;

    public AvisPerteResource(AvisPerteRepository avisPerteRepository) {
        this.avisPerteRepository = avisPerteRepository;
    }

    /**
     * POST  /avis-pertes : Create a new avisPerte.
     *
     * @param avisPerte the avisPerte to create
     * @return the ResponseEntity with status 201 (Created) and with body the new avisPerte, or with status 400 (Bad Request) if the avisPerte has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/avis-pertes")
    @Timed
    public ResponseEntity<AvisPerte> createAvisPerte(@Valid @RequestBody AvisPerte avisPerte) throws URISyntaxException {
        log.debug("REST request to save AvisPerte : {}", avisPerte);
        if (avisPerte.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new avisPerte cannot already have an ID")).body(null);
        }
        AvisPerte result = avisPerteRepository.save(avisPerte);
        return ResponseEntity.created(new URI("/api/avis-pertes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /avis-pertes : Updates an existing avisPerte.
     *
     * @param avisPerte the avisPerte to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated avisPerte,
     * or with status 400 (Bad Request) if the avisPerte is not valid,
     * or with status 500 (Internal Server Error) if the avisPerte couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/avis-pertes")
    @Timed
    public ResponseEntity<AvisPerte> updateAvisPerte(@Valid @RequestBody AvisPerte avisPerte) throws URISyntaxException {
        log.debug("REST request to update AvisPerte : {}", avisPerte);
        if (avisPerte.getId() == null) {
            return createAvisPerte(avisPerte);
        }
        AvisPerte result = avisPerteRepository.save(avisPerte);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, avisPerte.getId().toString()))
            .body(result);
    }

    /**
     * GET  /avis-pertes : get all the avisPertes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of avisPertes in body
     */
    @GetMapping("/avis-pertes")
    @Timed
    public List<AvisPerte> getAllAvisPertes() {
        log.debug("REST request to get all AvisPertes");
        List<AvisPerte> avisPertes = avisPerteRepository.findAll();
        return avisPertes;
    }

    /**
     * GET  /avis-pertes/:id : get the "id" avisPerte.
     *
     * @param id the id of the avisPerte to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the avisPerte, or with status 404 (Not Found)
     */
    @GetMapping("/avis-pertes/{id}")
    @Timed
    public ResponseEntity<AvisPerte> getAvisPerte(@PathVariable String id) {
        log.debug("REST request to get AvisPerte : {}", id);
        AvisPerte avisPerte = avisPerteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(avisPerte));
    }

    /**
     * DELETE  /avis-pertes/:id : delete the "id" avisPerte.
     *
     * @param id the id of the avisPerte to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/avis-pertes/{id}")
    @Timed
    public ResponseEntity<Void> deleteAvisPerte(@PathVariable String id) {
        log.debug("REST request to delete AvisPerte : {}", id);
        avisPerteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
