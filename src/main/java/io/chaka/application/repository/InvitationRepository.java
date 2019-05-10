package io.chaka.application.repository;

import io.chaka.application.domain.Invitation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Invitation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

}
