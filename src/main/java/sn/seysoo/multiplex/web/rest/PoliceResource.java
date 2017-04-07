package sn.seysoo.multiplex.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonView;

import sn.seysoo.multiplex.domain.Police;
import sn.seysoo.multiplex.repository.PoliceRepository;
import sn.seysoo.multiplex.service.PoliceService;
import sn.seysoo.multiplex.web.rest.util.HeaderUtil;
import sn.seysoo.multiplex.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * REST controller for managing Police.
 */
@RestController
@RequestMapping("/api")
public class PoliceResource {

    private final Logger log = LoggerFactory.getLogger(PoliceResource.class);
        
    @Inject
    private PoliceService policeService;
    
    @Inject
    private PoliceRepository policeRepository;

    /**
     * POST  /police : Create a new police.
     *
     * @param police the police to create
     * @return the ResponseEntity with status 201 (Created) and with body the new police, or with status 400 (Bad Request) if the police has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/police")
    @Timed
    public ResponseEntity<Police> createPolice(@Valid @RequestBody Police police) throws URISyntaxException {
        log.debug("REST request to save Police : {}", police);
        if (police.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("police", "idexists", "A new police cannot already have an ID")).body(null);
        }
        Police result = policeService.save(police);
        return ResponseEntity.created(new URI("/api/police/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("police", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /police : Updates an existing police.
     *
     * @param police the police to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated police,
     * or with status 400 (Bad Request) if the police is not valid,
     * or with status 500 (Internal Server Error) if the police couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/police")
    @Timed
    public ResponseEntity<Police> updatePolice(@Valid @RequestBody Police police) throws URISyntaxException {
        log.debug("REST request to update Police : {}", police);
        if (police.getId() == null) {
            return createPolice(police);
        }
        Police result = policeService.save(police);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("police", police.getId().toString()))
            .body(result);
    }

    /**
     * GET  /police : get all the police.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of police in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/police")
    @Timed
    public ResponseEntity<List<Police>> getAllPolice(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Police");
        Page<Police> page = policeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/police");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
	 * GET  /employes-datatable -> get all the employes for datatables.
	 */
	@JsonView(DataTablesOutput.View.class)
	@RequestMapping(value = "/police-datatable",
	method = RequestMethod.GET,
	produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<DataTablesOutput<Police>> getAllForDatatable(DataTablesInput input) throws URISyntaxException {
		log.debug("REST request to get all Polices");
		
		DataTablesOutput<Police> output = null;
		output = policeRepository.findAll(input);
		
		return ResponseEntity.ok(output);
	}
    /**
     * GET  /police/:id : get the "id" police.
     *
     * @param id the id of the police to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the police, or with status 404 (Not Found)
     */
    @GetMapping("/police/{id}")
    @Timed
    public ResponseEntity<Police> getPolice(@PathVariable Long id) {
        log.debug("REST request to get Police : {}", id);
        Police police = policeService.findOne(id);
        return Optional.ofNullable(police)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /police/:id : delete the "id" police.
     *
     * @param id the id of the police to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/police/{id}")
    @Timed
    public ResponseEntity<Void> deletePolice(@PathVariable Long id) {
        log.debug("REST request to delete Police : {}", id);
        policeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("police", id.toString())).build();
    }

}
