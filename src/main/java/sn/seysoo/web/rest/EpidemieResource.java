package sn.seysoo.web.rest;

import com.codahale.metrics.annotation.Timed;
import sn.seysoo.domain.Epidemie;

import sn.seysoo.repository.EpidemieRepository;
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
 * REST controller for managing Epidemie.
 */
@RestController
@RequestMapping("/api")
public class EpidemieResource {

    private final Logger log = LoggerFactory.getLogger(EpidemieResource.class);

    private static final String ENTITY_NAME = "epidemie";
        
    private final EpidemieRepository epidemieRepository;

    public EpidemieResource(EpidemieRepository epidemieRepository) {
        this.epidemieRepository = epidemieRepository;
    }

    /**
     * POST  /epidemies : Create a new epidemie.
     *
     * @param epidemie the epidemie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new epidemie, or with status 400 (Bad Request) if the epidemie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/epidemies")
    @Timed
    public ResponseEntity<Epidemie> createEpidemie(@Valid @RequestBody Epidemie epidemie) throws URISyntaxException {
        log.debug("REST request to save Epidemie : {}", epidemie);
        if (epidemie.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new epidemie cannot already have an ID")).body(null);
        }
        Epidemie result = epidemieRepository.save(epidemie);
        return ResponseEntity.created(new URI("/api/epidemies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /epidemies : Updates an existing epidemie.
     *
     * @param epidemie the epidemie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated epidemie,
     * or with status 400 (Bad Request) if the epidemie is not valid,
     * or with status 500 (Internal Server Error) if the epidemie couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/epidemies")
    @Timed
    public ResponseEntity<Epidemie> updateEpidemie(@Valid @RequestBody Epidemie epidemie) throws URISyntaxException {
        log.debug("REST request to update Epidemie : {}", epidemie);
        if (epidemie.getId() == null) {
            return createEpidemie(epidemie);
        }
        Epidemie result = epidemieRepository.save(epidemie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, epidemie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /epidemies : get all the epidemies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of epidemies in body
     */
    @GetMapping("/epidemies")
    @Timed
    public List<Epidemie> getAllEpidemies() {
        log.debug("REST request to get all Epidemies");
        List<Epidemie> epidemies = epidemieRepository.findAll();
        return epidemies;
    }

    /**
     * GET  /epidemies/:id : get the "id" epidemie.
     *
     * @param id the id of the epidemie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the epidemie, or with status 404 (Not Found)
     */
    @GetMapping("/epidemies/{id}")
    @Timed
    public ResponseEntity<Epidemie> getEpidemie(@PathVariable String id) {
        log.debug("REST request to get Epidemie : {}", id);
        Epidemie epidemie = epidemieRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(epidemie));
    }

    /**
     * DELETE  /epidemies/:id : delete the "id" epidemie.
     *
     * @param id the id of the epidemie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/epidemies/{id}")
    @Timed
    public ResponseEntity<Void> deleteEpidemie(@PathVariable String id) {
        log.debug("REST request to delete Epidemie : {}", id);
        epidemieRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
