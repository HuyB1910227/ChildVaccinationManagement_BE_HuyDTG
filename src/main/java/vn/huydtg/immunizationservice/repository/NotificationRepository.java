package vn.huydtg.immunizationservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.huydtg.immunizationservice.domain.Notification;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Notification> {

    @Query(value = "SELECT n.title, n.topic, n.message, n.sentDate FROM Notification n JOIN n.user WHERE n.status > 0 AND n.user.id = :userId ")
    List<Object[]> getAllNotificationSentForUerId(UUID userId);

    List<Notification> findAllByUserId(UUID userId);
}
