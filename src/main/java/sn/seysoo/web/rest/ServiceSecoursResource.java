package sn.seysoo.web.rest;

import com.codahale.metrics.annotation.Timed;
import sn.seysoo.domain.ServiceSecours;

import sn.seysoo.repository.ServiceSecoursRepository;
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
 * REST controller for managing ServiceSecours.
 */
@RestController
@RequestMapping("/api")
public class ServiceSecoursResource {

    private final Logger log = LoggerFactory.getLogger(ServiceSecoursResource.class);

    private static final String ENTITY_NAME = "serviceSecours";
        
    private final ServiceSecoursRepository serviceSecoursRepository;

    public ServiceSecoursResource(ServiceSecoursRepository serviceSecoursRepository) {
        this.serviceSecoursRepository = serviceSecoursRepository;
    }

    /**
     * POST  /service-secours : Create a new serviceSecours.
     *
     * @param serviceSecours the serviceSecours to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceSecours, or with status 400 (Bad Request) if the serviceSecours has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/service-secours")
    @Timed
    public ResponseEntity<ServiceSecours> createServiceSecours(@Valid @RequestBody ServiceSecours serviceSecours) throws URISyntaxException {
        log.debug("REST request to save ServiceSecours : {}", serviceSecours);
        if (serviceSecours.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new serviceSecours cannot already have an ID")).body(null);
        }
        ServiceSecours result = serviceSecoursRepository.save(serviceSecours);
        return ResponseEntity.created(new URI("/api/service-secours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-secours : Updates an existing serviceSecours.
     *
     * @param serviceSecours the serviceSecours to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceSecours,
     * or with status 400 (Bad Request) if the serviceSecours is not valid,
     * or with status 500 (Internal Server Error) if the serviceSecours couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/service-secours")
    @Timed
    public ResponseEntity<ServiceSecours> updateServiceSecours(@Valid @RequestBody ServiceSecours serviceSecours) throws URISyntaxException {
        log.debug("REST request to update ServiceSecours : {}", serviceSecours);
        if (serviceSecours.getId() == null) {
            return createServiceSecours(serviceSecours);
        }
        ServiceSecours result = serviceSecoursRepository.save(serviceSecours);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviceSecours.getId().toString()))
            .body(result);
    }

    /**
     * GET  /service-secours : get all the serviceSecours.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of serviceSecours in body
     */
    @GetMapping("/service-secours")
    @Timed
    public List<ServiceSecours> getAllServiceSecours() {
        log.debug("REST request to get all ServiceSecours");
        List<ServiceSecours> serviceSecours = serviceSecoursRepository.findAll();
        return serviceSecours;
    }

    /**
     * GET  /service-secours/:id : get the "id" serviceSecours.
     *
     * @param id the id of the serviceSecours to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceSecours, or with status 404 (Not Found)
     */
    @GetMapping("/service-secours/{id}")
    @Timed
    public ResponseEntity<ServiceSecours> getServiceSecours(@PathVariable String id) {
        log.debug("REST request to get ServiceSecours : {}", id);
        ServiceSecours serviceSecours = serviceSecoursRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(serviceSecours));
    }

    /**
     * DELETE  /service-secours/:id : delete the "id" serviceSecours.
     *
     * @param id the id of the serviceSecours to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/service-secours/{id}")
    @Timed
    public ResponseEntity<Void> deleteServiceSecours(@PathVariable String id) {
        log.debug("REST request to delete ServiceSecours : {}", id);
        serviceSecoursRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
