package sn.seysoo.web.rest;

import com.codahale.metrics.annotation.Timed;
import sn.seysoo.domain.PartenaireBancaire;

import sn.seysoo.repository.PartenaireBancaireRepository;
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
 * REST controller for managing PartenaireBancaire.
 */
@RestController
@RequestMapping("/api")
public class PartenaireBancaireResource {

    private final Logger log = LoggerFactory.getLogger(PartenaireBancaireResource.class);

    private static final String ENTITY_NAME = "partenaireBancaire";
        
    private final PartenaireBancaireRepository partenaireBancaireRepository;

    public PartenaireBancaireResource(PartenaireBancaireRepository partenaireBancaireRepository) {
        this.partenaireBancaireRepository = partenaireBancaireRepository;
    }

    /**
     * POST  /partenaire-bancaires : Create a new partenaireBancaire.
     *
     * @param partenaireBancaire the partenaireBancaire to create
     * @return the ResponseEntity with status 201 (Created) and with body the new partenaireBancaire, or with status 400 (Bad Request) if the partenaireBancaire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/partenaire-bancaires")
    @Timed
    public ResponseEntity<PartenaireBancaire> createPartenaireBancaire(@Valid @RequestBody PartenaireBancaire partenaireBancaire) throws URISyntaxException {
        log.debug("REST request to save PartenaireBancaire : {}", partenaireBancaire);
        if (partenaireBancaire.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new partenaireBancaire cannot already have an ID")).body(null);
        }
        PartenaireBancaire result = partenaireBancaireRepository.save(partenaireBancaire);
        return ResponseEntity.created(new URI("/api/partenaire-bancaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /partenaire-bancaires : Updates an existing partenaireBancaire.
     *
     * @param partenaireBancaire the partenaireBancaire to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated partenaireBancaire,
     * or with status 400 (Bad Request) if the partenaireBancaire is not valid,
     * or with status 500 (Internal Server Error) if the partenaireBancaire couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/partenaire-bancaires")
    @Timed
    public ResponseEntity<PartenaireBancaire> updatePartenaireBancaire(@Valid @RequestBody PartenaireBancaire partenaireBancaire) throws URISyntaxException {
        log.debug("REST request to update PartenaireBancaire : {}", partenaireBancaire);
        if (partenaireBancaire.getId() == null) {
            return createPartenaireBancaire(partenaireBancaire);
        }
        PartenaireBancaire result = partenaireBancaireRepository.save(partenaireBancaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, partenaireBancaire.getId().toString()))
            .body(result);
    }

    /**
     * GET  /partenaire-bancaires : get all the partenaireBancaires.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of partenaireBancaires in body
     */
    @GetMapping("/partenaire-bancaires")
    @Timed
    public List<PartenaireBancaire> getAllPartenaireBancaires() {
        log.debug("REST request to get all PartenaireBancaires");
        List<PartenaireBancaire> partenaireBancaires = partenaireBancaireRepository.findAll();
        return partenaireBancaires;
    }

    /**
     * GET  /partenaire-bancaires/:id : get the "id" partenaireBancaire.
     *
     * @param id the id of the partenaireBancaire to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the partenaireBancaire, or with status 404 (Not Found)
     */
    @GetMapping("/partenaire-bancaires/{id}")
    @Timed
    public ResponseEntity<PartenaireBancaire> getPartenaireBancaire(@PathVariable String id) {
        log.debug("REST request to get PartenaireBancaire : {}", id);
        PartenaireBancaire partenaireBancaire = partenaireBancaireRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(partenaireBancaire));
    }

    /**
     * DELETE  /partenaire-bancaires/:id : delete the "id" partenaireBancaire.
     *
     * @param id the id of the partenaireBancaire to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/partenaire-bancaires/{id}")
    @Timed
    public ResponseEntity<Void> deletePartenaireBancaire(@PathVariable String id) {
        log.debug("REST request to delete PartenaireBancaire : {}", id);
        partenaireBancaireRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
