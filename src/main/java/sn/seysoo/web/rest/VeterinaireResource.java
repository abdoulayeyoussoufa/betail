package sn.seysoo.web.rest;

import com.codahale.metrics.annotation.Timed;
import sn.seysoo.domain.Veterinaire;

import sn.seysoo.repository.VeterinaireRepository;
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
 * REST controller for managing Veterinaire.
 */
@RestController
@RequestMapping("/api")
public class VeterinaireResource {

    private final Logger log = LoggerFactory.getLogger(VeterinaireResource.class);

    private static final String ENTITY_NAME = "veterinaire";
        
    private final VeterinaireRepository veterinaireRepository;

    public VeterinaireResource(VeterinaireRepository veterinaireRepository) {
        this.veterinaireRepository = veterinaireRepository;
    }

    /**
     * POST  /veterinaires : Create a new veterinaire.
     *
     * @param veterinaire the veterinaire to create
     * @return the ResponseEntity with status 201 (Created) and with body the new veterinaire, or with status 400 (Bad Request) if the veterinaire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/veterinaires")
    @Timed
    public ResponseEntity<Veterinaire> createVeterinaire(@Valid @RequestBody Veterinaire veterinaire) throws URISyntaxException {
        log.debug("REST request to save Veterinaire : {}", veterinaire);
        if (veterinaire.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new veterinaire cannot already have an ID")).body(null);
        }
        Veterinaire result = veterinaireRepository.save(veterinaire);
        return ResponseEntity.created(new URI("/api/veterinaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /veterinaires : Updates an existing veterinaire.
     *
     * @param veterinaire the veterinaire to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated veterinaire,
     * or with status 400 (Bad Request) if the veterinaire is not valid,
     * or with status 500 (Internal Server Error) if the veterinaire couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/veterinaires")
    @Timed
    public ResponseEntity<Veterinaire> updateVeterinaire(@Valid @RequestBody Veterinaire veterinaire) throws URISyntaxException {
        log.debug("REST request to update Veterinaire : {}", veterinaire);
        if (veterinaire.getId() == null) {
            return createVeterinaire(veterinaire);
        }
        Veterinaire result = veterinaireRepository.save(veterinaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, veterinaire.getId().toString()))
            .body(result);
    }

    /**
     * GET  /veterinaires : get all the veterinaires.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of veterinaires in body
     */
    @GetMapping("/veterinaires")
    @Timed
    public List<Veterinaire> getAllVeterinaires() {
        log.debug("REST request to get all Veterinaires");
        List<Veterinaire> veterinaires = veterinaireRepository.findAll();
        return veterinaires;
    }

    /**
     * GET  /veterinaires/:id : get the "id" veterinaire.
     *
     * @param id the id of the veterinaire to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the veterinaire, or with status 404 (Not Found)
     */
    @GetMapping("/veterinaires/{id}")
    @Timed
    public ResponseEntity<Veterinaire> getVeterinaire(@PathVariable String id) {
        log.debug("REST request to get Veterinaire : {}", id);
        Veterinaire veterinaire = veterinaireRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(veterinaire));
    }

    /**
     * DELETE  /veterinaires/:id : delete the "id" veterinaire.
     *
     * @param id the id of the veterinaire to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/veterinaires/{id}")
    @Timed
    public ResponseEntity<Void> deleteVeterinaire(@PathVariable String id) {
        log.debug("REST request to delete Veterinaire : {}", id);
        veterinaireRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
