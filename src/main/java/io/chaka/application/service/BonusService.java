package io.chaka.application.service;

import io.chaka.application.domain.Bonus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Bonus}.
 */
public interface BonusService {

    /**
     * Save a bonus.
     *
     * @param bonus the entity to save.
     * @return the persisted entity.
     */
    Bonus save(Bonus bonus);

    /**
     * Get all the bonuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Bonus> findAll(Pageable pageable);


    /**
     * Get the "id" bonus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Bonus> findOne(Long id);

    /**
     * Delete the "id" bonus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
