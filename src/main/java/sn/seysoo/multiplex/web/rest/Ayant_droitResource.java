package sn.seysoo.multiplex.web.rest;

import com.codahale.metrics.annotation.Timed;
import sn.seysoo.multiplex.domain.Ayant_droit;

import sn.seysoo.multiplex.repository.Ayant_droitRepository;
import sn.seysoo.multiplex.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Ayant_droit.
 */
@RestController
@RequestMapping("/api")
public class Ayant_droitResource {

    private final Logger log = LoggerFactory.getLogger(Ayant_droitResource.class);
        
    @Inject
    private Ayant_droitRepository ayant_droitRepository;

    /**
     * POST  /ayant-droits : Create a new ayant_droit.
     *
     * @param ayant_droit the ayant_droit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ayant_droit, or with status 400 (Bad Request) if the ayant_droit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ayant-droits")
    @Timed
    public ResponseEntity<Ayant_droit> createAyant_droit(@Valid @RequestBody Ayant_droit ayant_droit) throws URISyntaxException {
        log.debug("REST request to save Ayant_droit : {}", ayant_droit);
        if (ayant_droit.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ayant_droit", "idexists", "A new ayant_droit cannot already have an ID")).body(null);
        }
        Ayant_droit result = ayant_droitRepository.save(ayant_droit);
        return ResponseEntity.created(new URI("/api/ayant-droits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ayant_droit", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ayant-droits : Updates an existing ayant_droit.
     *
     * @param ayant_droit the ayant_droit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ayant_droit,
     * or with status 400 (Bad Request) if the ayant_droit is not valid,
     * or with status 500 (Internal Server Error) if the ayant_droit couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ayant-droits")
    @Timed
    public ResponseEntity<Ayant_droit> updateAyant_droit(@Valid @RequestBody Ayant_droit ayant_droit) throws URISyntaxException {
        log.debug("REST request to update Ayant_droit : {}", ayant_droit);
        if (ayant_droit.getId() == null) {
            return createAyant_droit(ayant_droit);
        }
        Ayant_droit result = ayant_droitRepository.save(ayant_droit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ayant_droit", ayant_droit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ayant-droits : get all the ayant_droits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ayant_droits in body
     */
    @GetMapping("/ayant-droits")
    @Timed
    public List<Ayant_droit> getAllAyant_droits() {
        log.debug("REST request to get all Ayant_droits");
        List<Ayant_droit> ayant_droits = ayant_droitRepository.findAll();
        return ayant_droits;
    }

    /**
     * GET  /ayant-droits/:id : get the "id" ayant_droit.
     *
     * @param id the id of the ayant_droit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ayant_droit, or with status 404 (Not Found)
     */
    @GetMapping("/ayant-droits/{id}")
    @Timed
    public ResponseEntity<Ayant_droit> getAyant_droit(@PathVariable Long id) {
        log.debug("REST request to get Ayant_droit : {}", id);
        Ayant_droit ayant_droit = ayant_droitRepository.findOne(id);
        return Optional.ofNullable(ayant_droit)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ayant-droits/:id : delete the "id" ayant_droit.
     *
     * @param id the id of the ayant_droit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ayant-droits/{id}")
    @Timed
    public ResponseEntity<Void> deleteAyant_droit(@PathVariable Long id) {
        log.debug("REST request to delete Ayant_droit : {}", id);
        ayant_droitRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ayant_droit", id.toString())).build();
    }

}
