package sn.seysoo.web.rest;

import com.codahale.metrics.annotation.Timed;
import sn.seysoo.domain.CarnetSante;

import sn.seysoo.repository.CarnetSanteRepository;
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
 * REST controller for managing CarnetSante.
 */
@RestController
@RequestMapping("/api")
public class CarnetSanteResource {

    private final Logger log = LoggerFactory.getLogger(CarnetSanteResource.class);

    private static final String ENTITY_NAME = "carnetSante";
        
    private final CarnetSanteRepository carnetSanteRepository;

    public CarnetSanteResource(CarnetSanteRepository carnetSanteRepository) {
        this.carnetSanteRepository = carnetSanteRepository;
    }

    /**
     * POST  /carnet-santes : Create a new carnetSante.
     *
     * @param carnetSante the carnetSante to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carnetSante, or with status 400 (Bad Request) if the carnetSante has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/carnet-santes")
    @Timed
    public ResponseEntity<CarnetSante> createCarnetSante(@Valid @RequestBody CarnetSante carnetSante) throws URISyntaxException {
        log.debug("REST request to save CarnetSante : {}", carnetSante);
        if (carnetSante.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new carnetSante cannot already have an ID")).body(null);
        }
        CarnetSante result = carnetSanteRepository.save(carnetSante);
        return ResponseEntity.created(new URI("/api/carnet-santes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carnet-santes : Updates an existing carnetSante.
     *
     * @param carnetSante the carnetSante to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carnetSante,
     * or with status 400 (Bad Request) if the carnetSante is not valid,
     * or with status 500 (Internal Server Error) if the carnetSante couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/carnet-santes")
    @Timed
    public ResponseEntity<CarnetSante> updateCarnetSante(@Valid @RequestBody CarnetSante carnetSante) throws URISyntaxException {
        log.debug("REST request to update CarnetSante : {}", carnetSante);
        if (carnetSante.getId() == null) {
            return createCarnetSante(carnetSante);
        }
        CarnetSante result = carnetSanteRepository.save(carnetSante);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carnetSante.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carnet-santes : get all the carnetSantes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of carnetSantes in body
     */
    @GetMapping("/carnet-santes")
    @Timed
    public List<CarnetSante> getAllCarnetSantes() {
        log.debug("REST request to get all CarnetSantes");
        List<CarnetSante> carnetSantes = carnetSanteRepository.findAll();
        return carnetSantes;
    }

    /**
     * GET  /carnet-santes/:id : get the "id" carnetSante.
     *
     * @param id the id of the carnetSante to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carnetSante, or with status 404 (Not Found)
     */
    @GetMapping("/carnet-santes/{id}")
    @Timed
    public ResponseEntity<CarnetSante> getCarnetSante(@PathVariable String id) {
        log.debug("REST request to get CarnetSante : {}", id);
        CarnetSante carnetSante = carnetSanteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(carnetSante));
    }

    /**
     * DELETE  /carnet-santes/:id : delete the "id" carnetSante.
     *
     * @param id the id of the carnetSante to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/carnet-santes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCarnetSante(@PathVariable String id) {
        log.debug("REST request to delete CarnetSante : {}", id);
        carnetSanteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
