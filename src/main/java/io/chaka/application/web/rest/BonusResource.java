package io.chaka.application.web.rest;

import io.chaka.application.domain.Bonus;
import io.chaka.application.service.BonusService;
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
 * REST controller for managing {@link io.chaka.application.domain.Bonus}.
 */
@RestController
@RequestMapping("/api")
public class BonusResource {

    private final Logger log = LoggerFactory.getLogger(BonusResource.class);

    private static final String ENTITY_NAME = "bonus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BonusService bonusService;

    public BonusResource(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    /**
     * {@code POST  /bonuses} : Create a new bonus.
     *
     * @param bonus the bonus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bonus, or with status {@code 400 (Bad Request)} if the bonus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bonuses")
    public ResponseEntity<Bonus> createBonus(@RequestBody Bonus bonus) throws URISyntaxException {
        log.debug("REST request to save Bonus : {}", bonus);
        if (bonus.getId() != null) {
            throw new BadRequestAlertException("A new bonus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bonus result = bonusService.save(bonus);
        return ResponseEntity.created(new URI("/api/bonuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bonuses} : Updates an existing bonus.
     *
     * @param bonus the bonus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonus,
     * or with status {@code 400 (Bad Request)} if the bonus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bonus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bonuses")
    public ResponseEntity<Bonus> updateBonus(@RequestBody Bonus bonus) throws URISyntaxException {
        log.debug("REST request to update Bonus : {}", bonus);
        if (bonus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bonus result = bonusService.save(bonus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bonus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bonuses} : get all the bonuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bonuses in body.
     */
    @GetMapping("/bonuses")
    public ResponseEntity<List<Bonus>> getAllBonuses(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Bonuses");
        Page<Bonus> page = bonusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bonuses/:id} : get the "id" bonus.
     *
     * @param id the id of the bonus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bonus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bonuses/{id}")
    public ResponseEntity<Bonus> getBonus(@PathVariable Long id) {
        log.debug("REST request to get Bonus : {}", id);
        Optional<Bonus> bonus = bonusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bonus);
    }

    /**
     * {@code DELETE  /bonuses/:id} : delete the "id" bonus.
     *
     * @param id the id of the bonus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bonuses/{id}")
    public ResponseEntity<Void> deleteBonus(@PathVariable Long id) {
        log.debug("REST request to delete Bonus : {}", id);
        bonusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
