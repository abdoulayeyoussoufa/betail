package sn.seysoo.web.rest;

import com.codahale.metrics.annotation.Timed;
import sn.seysoo.domain.Maladie;

import sn.seysoo.repository.MaladieRepository;
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
 * REST controller for managing Maladie.
 */
@RestController
@RequestMapping("/api")
public class MaladieResource {

    private final Logger log = LoggerFactory.getLogger(MaladieResource.class);

    private static final String ENTITY_NAME = "maladie";
        
    private final MaladieRepository maladieRepository;

    public MaladieResource(MaladieRepository maladieRepository) {
        this.maladieRepository = maladieRepository;
    }

    /**
     * POST  /maladies : Create a new maladie.
     *
     * @param maladie the maladie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new maladie, or with status 400 (Bad Request) if the maladie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/maladies")
    @Timed
    public ResponseEntity<Maladie> createMaladie(@Valid @RequestBody Maladie maladie) throws URISyntaxException {
        log.debug("REST request to save Maladie : {}", maladie);
        if (maladie.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new maladie cannot already have an ID")).body(null);
        }
        Maladie result = maladieRepository.save(maladie);
        return ResponseEntity.created(new URI("/api/maladies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /maladies : Updates an existing maladie.
     *
     * @param maladie the maladie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated maladie,
     * or with status 400 (Bad Request) if the maladie is not valid,
     * or with status 500 (Internal Server Error) if the maladie couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/maladies")
    @Timed
    public ResponseEntity<Maladie> updateMaladie(@Valid @RequestBody Maladie maladie) throws URISyntaxException {
        log.debug("REST request to update Maladie : {}", maladie);
        if (maladie.getId() == null) {
            return createMaladie(maladie);
        }
        Maladie result = maladieRepository.save(maladie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, maladie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /maladies : get all the maladies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of maladies in body
     */
    @GetMapping("/maladies")
    @Timed
    public List<Maladie> getAllMaladies() {
        log.debug("REST request to get all Maladies");
        List<Maladie> maladies = maladieRepository.findAll();
        return maladies;
    }

    /**
     * GET  /maladies/:id : get the "id" maladie.
     *
     * @param id the id of the maladie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the maladie, or with status 404 (Not Found)
     */
    @GetMapping("/maladies/{id}")
    @Timed
    public ResponseEntity<Maladie> getMaladie(@PathVariable String id) {
        log.debug("REST request to get Maladie : {}", id);
        Maladie maladie = maladieRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(maladie));
    }

    /**
     * DELETE  /maladies/:id : delete the "id" maladie.
     *
     * @param id the id of the maladie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/maladies/{id}")
    @Timed
    public ResponseEntity<Void> deleteMaladie(@PathVariable String id) {
        log.debug("REST request to delete Maladie : {}", id);
        maladieRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
