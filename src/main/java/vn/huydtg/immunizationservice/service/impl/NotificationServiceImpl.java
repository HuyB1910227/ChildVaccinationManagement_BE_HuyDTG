package vn.huydtg.immunizationservice.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.huydtg.immunizationservice.domain.Notification;
import vn.huydtg.immunizationservice.repository.NotificationRepository;
import vn.huydtg.immunizationservice.service.NotificationService;
import vn.huydtg.immunizationservice.service.dto.NotificationDTO;
import vn.huydtg.immunizationservice.service.mapper.NotificationMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }


    @Override
    public NotificationDTO save(NotificationDTO notificationDTO) {
        Notification notification = notificationMapper.toEntity(notificationDTO);
        notification = notificationRepository.save(notification);
        return notificationMapper.toDto(notification);
    }

    @Override
    public Optional<NotificationDTO> partialUpdate(NotificationDTO notificationDTO) {
        return notificationRepository.findById(notificationDTO.getId()).map(existingNotification -> {
            notificationMapper.partialUpdate(existingNotification, notificationDTO);
            return existingNotification;
        }).map(notificationRepository::save).map(notificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotificationDTO> findOne(Long id) {
        return notificationRepository.findById(id).map(notificationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }


    @Override
    public List<NotificationDTO> findAllNotificationSentForUser(UUID userId) {
        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        List<Object[]> objects = notificationRepository.getAllNotificationSentForUerId(userId);
        for (Object[] object : objects) {
            if(object.length >= 2) {
                String title = (String) object[0];
                String topic = (String) object[1];
                String message = (String) object[2];
                LocalDate sentDate = (LocalDate) object[3];
                NotificationDTO noti = new NotificationDTO();
                noti.setTitle(title);
                noti.setTopic(topic);
                noti.setMessage(message);
                noti.setSentDate(sentDate);
                notificationDTOList.add(noti);
             }
        }
        return notificationDTOList;
    }
}
