package sn.seysoo.web.rest;

import com.codahale.metrics.annotation.Timed;
import sn.seysoo.domain.Betail;
import sn.seysoo.service.BetailService;
import sn.seysoo.web.rest.util.HeaderUtil;
import sn.seysoo.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Betail.
 */
@RestController
@RequestMapping("/api")
public class BetailResource {

    private final Logger log = LoggerFactory.getLogger(BetailResource.class);

    private static final String ENTITY_NAME = "betail";
        
    private final BetailService betailService;

    public BetailResource(BetailService betailService) {
        this.betailService = betailService;
    }

    /**
     * POST  /betails : Create a new betail.
     *
     * @param betail the betail to create
     * @return the ResponseEntity with status 201 (Created) and with body the new betail, or with status 400 (Bad Request) if the betail has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/betails")
    @Timed
    public ResponseEntity<Betail> createBetail(@RequestBody Betail betail) throws URISyntaxException {
        log.debug("REST request to save Betail : {}", betail);
        if (betail.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new betail cannot already have an ID")).body(null);
        }
        Betail result = betailService.save(betail);
        return ResponseEntity.created(new URI("/api/betails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /betails : Updates an existing betail.
     *
     * @param betail the betail to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated betail,
     * or with status 400 (Bad Request) if the betail is not valid,
     * or with status 500 (Internal Server Error) if the betail couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/betails")
    @Timed
    public ResponseEntity<Betail> updateBetail(@RequestBody Betail betail) throws URISyntaxException {
        log.debug("REST request to update Betail : {}", betail);
        if (betail.getId() == null) {
            return createBetail(betail);
        }
        Betail result = betailService.save(betail);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, betail.getId().toString()))
            .body(result);
    }

    /**
     * GET  /betails : get all the betails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of betails in body
     */
    @GetMapping("/betails")
    @Timed
    public ResponseEntity<List<Betail>> getAllBetails(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Betails");
        Page<Betail> page = betailService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/betails");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * GET  /betails : get all algo for the betails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of betails in body
     */
    @GetMapping("/betails/algo")
    @Timed
    public ResponseEntity<List<Betail>> getAllAlgo(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Betails");
        Page<Betail> page = betailService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/betails");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /betails/:id : get the "id" betail.
     *
     * @param id the id of the betail to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the betail, or with status 404 (Not Found)
     */
    @GetMapping("/betails/{id}")
    @Timed
    public ResponseEntity<Betail> getBetail(@PathVariable String id) {
        log.debug("REST request to get Betail : {}", id);
        Betail betail = betailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(betail));
    }

    /**
     * DELETE  /betails/:id : delete the "id" betail.
     *
     * @param id the id of the betail to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/betails/{id}")
    @Timed
    public ResponseEntity<Void> deleteBetail(@PathVariable String id) {
        log.debug("REST request to delete Betail : {}", id);
        betailService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
