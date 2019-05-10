package io.chaka.application.service;

import io.chaka.application.domain.ChakaUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ChakaUser}.
 */
public interface ChakaUserService {

    /**
     * Save a chakaUser.
     *
     * @param chakaUser the entity to save.
     * @return the persisted entity.
     */
    ChakaUser save(ChakaUser chakaUser);

    /**
     * Get all the chakaUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChakaUser> findAll(Pageable pageable);


    /**
     * Get the "id" chakaUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChakaUser> findOne(Long id);

    /**
     * Delete the "id" chakaUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
