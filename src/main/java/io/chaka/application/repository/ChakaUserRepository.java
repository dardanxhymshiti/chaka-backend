package io.chaka.application.repository;

import io.chaka.application.domain.ChakaUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ChakaUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChakaUserRepository extends JpaRepository<ChakaUser, Long> {

}
