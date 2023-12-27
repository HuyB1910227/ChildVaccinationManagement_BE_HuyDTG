package vn.huydtg.immunizationservice.repository;

import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.huydtg.immunizationservice.domain.NotificationToken;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationTokenRepository extends JpaRepository<NotificationToken, Long>, JpaSpecificationExecutor<NotificationToken> {

    Optional<NotificationToken> findFirstByRegistrationTokenAndUserId(String registrationId, UUID userId);


    List<NotificationToken> findAllByUserId(UUID userId);
}
