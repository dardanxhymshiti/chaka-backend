package io.chaka.application.web.rest;

import io.chaka.application.domain.ChakaUser;
import io.chaka.application.service.ChakaUserService;
import io.chaka.application.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.chaka.application.domain.ChakaUser}.
 */
@RestController
@RequestMapping("/api")
public class ChakaUserResource {

    private final Logger log = LoggerFactory.getLogger(ChakaUserResource.class);

    private static final String ENTITY_NAME = "chakaUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChakaUserService chakaUserService;

    public ChakaUserResource(ChakaUserService chakaUserService) {
        this.chakaUserService = chakaUserService;
    }

    /**
     * {@code POST  /chaka-users} : Create a new chakaUser.
     *
     * @param chakaUser the chakaUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chakaUser, or with status {@code 400 (Bad Request)} if the chakaUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chaka-users")
    public ResponseEntity<ChakaUser> createChakaUser(@RequestBody ChakaUser chakaUser) throws URISyntaxException {
        log.debug("REST request to save ChakaUser : {}", chakaUser);
        if (chakaUser.getId() != null) {
            throw new BadRequestAlertException("A new chakaUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChakaUser result = chakaUserService.save(chakaUser);
        return ResponseEntity.created(new URI("/api/chaka-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chaka-users} : Updates an existing chakaUser.
     *
     * @param chakaUser the chakaUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chakaUser,
     * or with status {@code 400 (Bad Request)} if the chakaUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chakaUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chaka-users")
    public ResponseEntity<ChakaUser> updateChakaUser(@RequestBody ChakaUser chakaUser) throws URISyntaxException {
        log.debug("REST request to update ChakaUser : {}", chakaUser);
        if (chakaUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChakaUser result = chakaUserService.save(chakaUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chakaUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /chaka-users} : get all the chakaUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chakaUsers in body.
     */
    @GetMapping("/chaka-users")
    public ResponseEntity<List<ChakaUser>> getAllChakaUsers(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ChakaUsers");
        Page<ChakaUser> page = chakaUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /chaka-users/:id} : get the "id" chakaUser.
     *
     * @param id the id of the chakaUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chakaUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chaka-users/{id}")
    public ResponseEntity<ChakaUser> getChakaUser(@PathVariable Long id) {
        log.debug("REST request to get ChakaUser : {}", id);
        Optional<ChakaUser> chakaUser = chakaUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chakaUser);
    }

    /**
     * {@code DELETE  /chaka-users/:id} : delete the "id" chakaUser.
     *
     * @param id the id of the chakaUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chaka-users/{id}")
    public ResponseEntity<Void> deleteChakaUser(@PathVariable Long id) {
        log.debug("REST request to delete ChakaUser : {}", id);
        chakaUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
