package sn.seysoo.web.rest;

import com.codahale.metrics.annotation.Timed;
import sn.seysoo.domain.FicheAnimal;

import sn.seysoo.repository.FicheAnimalRepository;
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
 * REST controller for managing FicheAnimal.
 */
@RestController
@RequestMapping("/api")
public class FicheAnimalResource {

    private final Logger log = LoggerFactory.getLogger(FicheAnimalResource.class);

    private static final String ENTITY_NAME = "ficheAnimal";
        
    private final FicheAnimalRepository ficheAnimalRepository;

    public FicheAnimalResource(FicheAnimalRepository ficheAnimalRepository) {
        this.ficheAnimalRepository = ficheAnimalRepository;
    }

    /**
     * POST  /fiche-animals : Create a new ficheAnimal.
     *
     * @param ficheAnimal the ficheAnimal to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ficheAnimal, or with status 400 (Bad Request) if the ficheAnimal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fiche-animals")
    @Timed
    public ResponseEntity<FicheAnimal> createFicheAnimal(@Valid @RequestBody FicheAnimal ficheAnimal) throws URISyntaxException {
        log.debug("REST request to save FicheAnimal : {}", ficheAnimal);
        if (ficheAnimal.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ficheAnimal cannot already have an ID")).body(null);
        }
        FicheAnimal result = ficheAnimalRepository.save(ficheAnimal);
        return ResponseEntity.created(new URI("/api/fiche-animals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fiche-animals : Updates an existing ficheAnimal.
     *
     * @param ficheAnimal the ficheAnimal to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ficheAnimal,
     * or with status 400 (Bad Request) if the ficheAnimal is not valid,
     * or with status 500 (Internal Server Error) if the ficheAnimal couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fiche-animals")
    @Timed
    public ResponseEntity<FicheAnimal> updateFicheAnimal(@Valid @RequestBody FicheAnimal ficheAnimal) throws URISyntaxException {
        log.debug("REST request to update FicheAnimal : {}", ficheAnimal);
        if (ficheAnimal.getId() == null) {
            return createFicheAnimal(ficheAnimal);
        }
        FicheAnimal result = ficheAnimalRepository.save(ficheAnimal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ficheAnimal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fiche-animals : get all the ficheAnimals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ficheAnimals in body
     */
    @GetMapping("/fiche-animals")
    @Timed
    public List<FicheAnimal> getAllFicheAnimals() {
        log.debug("REST request to get all FicheAnimals");
        List<FicheAnimal> ficheAnimals = ficheAnimalRepository.findAll();
        return ficheAnimals;
    }

    /**
     * GET  /fiche-animals/:id : get the "id" ficheAnimal.
     *
     * @param id the id of the ficheAnimal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ficheAnimal, or with status 404 (Not Found)
     */
    @GetMapping("/fiche-animals/{id}")
    @Timed
    public ResponseEntity<FicheAnimal> getFicheAnimal(@PathVariable String id) {
        log.debug("REST request to get FicheAnimal : {}", id);
        FicheAnimal ficheAnimal = ficheAnimalRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ficheAnimal));
    }

    /**
     * DELETE  /fiche-animals/:id : delete the "id" ficheAnimal.
     *
     * @param id the id of the ficheAnimal to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fiche-animals/{id}")
    @Timed
    public ResponseEntity<Void> deleteFicheAnimal(@PathVariable String id) {
        log.debug("REST request to delete FicheAnimal : {}", id);
        ficheAnimalRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
