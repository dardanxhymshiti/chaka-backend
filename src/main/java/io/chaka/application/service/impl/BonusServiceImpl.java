package io.chaka.application.service.impl;

import io.chaka.application.service.BonusService;
import io.chaka.application.domain.Bonus;
import io.chaka.application.repository.BonusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Bonus}.
 */
@Service
@Transactional
public class BonusServiceImpl implements BonusService {

    private final Logger log = LoggerFactory.getLogger(BonusServiceImpl.class);

    private final BonusRepository bonusRepository;

    public BonusServiceImpl(BonusRepository bonusRepository) {
        this.bonusRepository = bonusRepository;
    }

    /**
     * Save a bonus.
     *
     * @param bonus the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Bonus save(Bonus bonus) {
        log.debug("Request to save Bonus : {}", bonus);
        return bonusRepository.save(bonus);
    }

    /**
     * Get all the bonuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Bonus> findAll(Pageable pageable) {
        log.debug("Request to get all Bonuses");
        return bonusRepository.findAll(pageable);
    }


    /**
     * Get one bonus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Bonus> findOne(Long id) {
        log.debug("Request to get Bonus : {}", id);
        return bonusRepository.findById(id);
    }

    /**
     * Delete the bonus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bonus : {}", id);
        bonusRepository.deleteById(id);
    }
}
