package io.chaka.application.service.impl;

import io.chaka.application.service.ChakaUserService;
import io.chaka.application.domain.ChakaUser;
import io.chaka.application.repository.ChakaUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ChakaUser}.
 */
@Service
@Transactional
public class ChakaUserServiceImpl implements ChakaUserService {

    private final Logger log = LoggerFactory.getLogger(ChakaUserServiceImpl.class);

    private final ChakaUserRepository chakaUserRepository;

    public ChakaUserServiceImpl(ChakaUserRepository chakaUserRepository) {
        this.chakaUserRepository = chakaUserRepository;
    }

    /**
     * Save a chakaUser.
     *
     * @param chakaUser the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ChakaUser save(ChakaUser chakaUser) {
        log.debug("Request to save ChakaUser : {}", chakaUser);
        return chakaUserRepository.save(chakaUser);
    }

    /**
     * Get all the chakaUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ChakaUser> findAll(Pageable pageable) {
        log.debug("Request to get all ChakaUsers");
        return chakaUserRepository.findAll(pageable);
    }


    /**
     * Get one chakaUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ChakaUser> findOne(Long id) {
        log.debug("Request to get ChakaUser : {}", id);
        return chakaUserRepository.findById(id);
    }

    /**
     * Delete the chakaUser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChakaUser : {}", id);
        chakaUserRepository.deleteById(id);
    }
}
