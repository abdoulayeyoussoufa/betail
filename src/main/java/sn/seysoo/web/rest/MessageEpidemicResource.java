package sn.seysoo.web.rest;

import com.codahale.metrics.annotation.Timed;
import sn.seysoo.domain.MessageEpidemic;

import sn.seysoo.repository.MessageEpidemicRepository;
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
 * REST controller for managing MessageEpidemic.
 */
@RestController
@RequestMapping("/api")
public class MessageEpidemicResource {

    private final Logger log = LoggerFactory.getLogger(MessageEpidemicResource.class);

    private static final String ENTITY_NAME = "messageEpidemic";
        
    private final MessageEpidemicRepository messageEpidemicRepository;

    public MessageEpidemicResource(MessageEpidemicRepository messageEpidemicRepository) {
        this.messageEpidemicRepository = messageEpidemicRepository;
    }

    /**
     * POST  /message-epidemics : Create a new messageEpidemic.
     *
     * @param messageEpidemic the messageEpidemic to create
     * @return the ResponseEntity with status 201 (Created) and with body the new messageEpidemic, or with status 400 (Bad Request) if the messageEpidemic has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/message-epidemics")
    @Timed
    public ResponseEntity<MessageEpidemic> createMessageEpidemic(@Valid @RequestBody MessageEpidemic messageEpidemic) throws URISyntaxException {
        log.debug("REST request to save MessageEpidemic : {}", messageEpidemic);
        if (messageEpidemic.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new messageEpidemic cannot already have an ID")).body(null);
        }
        MessageEpidemic result = messageEpidemicRepository.save(messageEpidemic);
        return ResponseEntity.created(new URI("/api/message-epidemics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /message-epidemics : Updates an existing messageEpidemic.
     *
     * @param messageEpidemic the messageEpidemic to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated messageEpidemic,
     * or with status 400 (Bad Request) if the messageEpidemic is not valid,
     * or with status 500 (Internal Server Error) if the messageEpidemic couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/message-epidemics")
    @Timed
    public ResponseEntity<MessageEpidemic> updateMessageEpidemic(@Valid @RequestBody MessageEpidemic messageEpidemic) throws URISyntaxException {
        log.debug("REST request to update MessageEpidemic : {}", messageEpidemic);
        if (messageEpidemic.getId() == null) {
            return createMessageEpidemic(messageEpidemic);
        }
        MessageEpidemic result = messageEpidemicRepository.save(messageEpidemic);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, messageEpidemic.getId().toString()))
            .body(result);
    }

    /**
     * GET  /message-epidemics : get all the messageEpidemics.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of messageEpidemics in body
     */
    @GetMapping("/message-epidemics")
    @Timed
    public List<MessageEpidemic> getAllMessageEpidemics() {
        log.debug("REST request to get all MessageEpidemics");
        List<MessageEpidemic> messageEpidemics = messageEpidemicRepository.findAll();
        return messageEpidemics;
    }

    /**
     * GET  /message-epidemics/:id : get the "id" messageEpidemic.
     *
     * @param id the id of the messageEpidemic to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the messageEpidemic, or with status 404 (Not Found)
     */
    @GetMapping("/message-epidemics/{id}")
    @Timed
    public ResponseEntity<MessageEpidemic> getMessageEpidemic(@PathVariable String id) {
        log.debug("REST request to get MessageEpidemic : {}", id);
        MessageEpidemic messageEpidemic = messageEpidemicRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(messageEpidemic));
    }

    /**
     * DELETE  /message-epidemics/:id : delete the "id" messageEpidemic.
     *
     * @param id the id of the messageEpidemic to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/message-epidemics/{id}")
    @Timed
    public ResponseEntity<Void> deleteMessageEpidemic(@PathVariable String id) {
        log.debug("REST request to delete MessageEpidemic : {}", id);
        messageEpidemicRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
