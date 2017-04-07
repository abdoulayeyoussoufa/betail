package sn.seysoo.multiplex.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;

import sn.seysoo.multiplex.domain.Assure;
import sn.seysoo.multiplex.domain.Police;
import sn.seysoo.multiplex.repository.AssureRepository;
import sn.seysoo.multiplex.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Assure.
 */
@RestController
@RequestMapping("/api")
public class AssureResource {

    private final Logger log = LoggerFactory.getLogger(AssureResource.class);
        
    @Inject
    private AssureRepository assureRepository;

    /**
     * POST  /assures : Create a new assure.
     *
     * @param assure the assure to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assure, or with status 400 (Bad Request) if the assure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assures")
    @Timed
    public ResponseEntity<Assure> createAssure(@Valid @RequestBody Assure assure) throws URISyntaxException {
        log.debug("REST request to save Assure : {}", assure);
        if (assure.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("assure", "idexists", "A new assure cannot already have an ID")).body(null);
        }
        Assure result = assureRepository.save(assure);
        return ResponseEntity.created(new URI("/api/assures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("assure", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assures : Updates an existing assure.
     *
     * @param assure the assure to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assure,
     * or with status 400 (Bad Request) if the assure is not valid,
     * or with status 500 (Internal Server Error) if the assure couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/assures")
    @Timed
    public ResponseEntity<Assure> updateAssure(@Valid @RequestBody Assure assure) throws URISyntaxException {
        log.debug("REST request to update Assure : {}", assure);
        if (assure.getId() == null) {
            return createAssure(assure);
        }
        Assure result = assureRepository.save(assure);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assure", assure.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assures : get all the assures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of assures in body
     */
    @GetMapping("/assures")
    @Timed
    public List<Assure> getAllAssures() {
        log.debug("REST request to get all Assures");
        List<Assure> assures = assureRepository.findAll();
        return assures;
    }

    /**
     * GET  /assures/:id : get the "id" assure.
     *
     * @param id the id of the assure to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assure, or with status 404 (Not Found)
     */
    @GetMapping("/assures/{id}")
    @Timed
    public ResponseEntity<Assure> getAssure(@PathVariable Long id) {
        log.debug("REST request to get Assure : {}", id);
        Assure assure = assureRepository.findOne(id);
        return Optional.ofNullable(assure)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * lister les données d'un tableau avec un datatable
     * 
     * 
     * */
    
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(value = "/assure-datatable",
	method = RequestMethod.GET,
	produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<DataTablesOutput<Assure>> getAllForDatatable(DataTablesInput input) throws URISyntaxException {
		log.debug("REST request to get all Assures");
		
		DataTablesOutput<Assure> output = null;
		output = assureRepository.findAll(input);
		
		return ResponseEntity.ok(output);
	}

    /**
     * DELETE  /assures/:id : delete the "id" assure.
     *
     * @param id the id of the assure to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/assures/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssure(@PathVariable Long id) {
        log.debug("REST request to delete Assure : {}", id);
        assureRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("assure", id.toString())).build();
    }

}
