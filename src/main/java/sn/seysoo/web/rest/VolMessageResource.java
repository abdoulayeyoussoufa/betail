package sn.seysoo.web.rest;

import com.codahale.metrics.annotation.Timed;
import sn.seysoo.domain.VolMessage;

import sn.seysoo.repository.VolMessageRepository;
import sn.seysoo.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VolMessage.
 */
@RestController
@RequestMapping("/api")
public class VolMessageResource {

    private final Logger log = LoggerFactory.getLogger(VolMessageResource.class);

    private static final String ENTITY_NAME = "volMessage";
        
    private final VolMessageRepository volMessageRepository;

    public VolMessageResource(VolMessageRepository volMessageRepository) {
        this.volMessageRepository = volMessageRepository;
    }

    /**
     * POST  /vol-messages : Create a new volMessage.
     *
     * @param volMessage the volMessage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new volMessage, or with status 400 (Bad Request) if the volMessage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vol-messages")
    @Timed
    public ResponseEntity<VolMessage> createVolMessage(@RequestBody VolMessage volMessage) throws URISyntaxException {
        log.debug("REST request to save VolMessage : {}", volMessage);
        if (volMessage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new volMessage cannot already have an ID")).body(null);
        }
        VolMessage result = volMessageRepository.save(volMessage);
        return ResponseEntity.created(new URI("/api/vol-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vol-messages : Updates an existing volMessage.
     *
     * @param volMessage the volMessage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated volMessage,
     * or with status 400 (Bad Request) if the volMessage is not valid,
     * or with status 500 (Internal Server Error) if the volMessage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vol-messages")
    @Timed
    public ResponseEntity<VolMessage> updateVolMessage(@RequestBody VolMessage volMessage) throws URISyntaxException {
        log.debug("REST request to update VolMessage : {}", volMessage);
        if (volMessage.getId() == null) {
            return createVolMessage(volMessage);
        }
        VolMessage result = volMessageRepository.save(volMessage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, volMessage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vol-messages : get all the volMessages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of volMessages in body
     */
    @GetMapping("/vol-messages")
    @Timed
    public List<VolMessage> getAllVolMessages() {
        log.debug("REST request to get all VolMessages");
        List<VolMessage> volMessages = volMessageRepository.findAll();
        return volMessages;
    }

    /**
     * GET  /vol-messages/:id : get the "id" volMessage.
     *
     * @param id the id of the volMessage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the volMessage, or with status 404 (Not Found)
     */
    @GetMapping("/vol-messages/{id}")
    @Timed
    public ResponseEntity<VolMessage> getVolMessage(@PathVariable String id) {
        log.debug("REST request to get VolMessage : {}", id);
        VolMessage volMessage = volMessageRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(volMessage));
    }

    /**
     * DELETE  /vol-messages/:id : delete the "id" volMessage.
     *
     * @param id the id of the volMessage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vol-messages/{id}")
    @Timed
    public ResponseEntity<Void> deleteVolMessage(@PathVariable String id) {
        log.debug("REST request to delete VolMessage : {}", id);
        volMessageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
