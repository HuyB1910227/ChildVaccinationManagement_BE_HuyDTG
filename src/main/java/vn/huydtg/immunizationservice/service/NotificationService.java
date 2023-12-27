package vn.huydtg.immunizationservice.service;

import vn.huydtg.immunizationservice.service.dto.NotificationDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationService {

    NotificationDTO save(NotificationDTO notificationDTO);

    Optional<NotificationDTO> partialUpdate(NotificationDTO notificationDTO);

    Optional<NotificationDTO> findOne(Long id);

    List<NotificationDTO> findAllNotificationSentForUser(UUID userId);

    void delete(Long id);
}
