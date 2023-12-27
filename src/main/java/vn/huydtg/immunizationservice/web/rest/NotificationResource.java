package vn.huydtg.immunizationservice.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.huydtg.immunizationservice.repository.NotificationRepository;
import vn.huydtg.immunizationservice.security.SecurityUtils;
import vn.huydtg.immunizationservice.service.NotificationService;
import vn.huydtg.immunizationservice.service.dto.NotificationDTO;
import vn.huydtg.immunizationservice.web.rest.errors.BadRequestAlertException;
import vn.huydtg.immunizationservice.web.rest.utils.HeaderUtil;
import vn.huydtg.immunizationservice.web.rest.utils.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class NotificationResource {

    @Value("${spring.application.name}")
    private String applicationName;
    private static final String ENTITY_NAME = "REST_NOTIFICATION";

    private final NotificationService notificationService;

    private final NotificationRepository notificationRepository;


    public NotificationResource(NotificationService notificationService, NotificationRepository notificationRepository) {
        this.notificationService = notificationService;
        this.notificationRepository = notificationRepository;
    }

    @PostMapping("/notifications")
    public ResponseEntity<NotificationDTO> createNotification(@Valid @RequestBody NotificationDTO notificationDTO) throws URISyntaxException {
        if (notificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new notification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotificationDTO result = notificationService.save(notificationDTO);
        return ResponseEntity
                .created(new URI("/api/notifications/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PatchMapping(value = "/notifications/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NotificationDTO> partialUpdateNotification(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody NotificationDTO notificationDTO
    ) throws URISyntaxException {
        if (notificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notificationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NotificationDTO> result = notificationService.partialUpdate(notificationDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notificationDTO.getId().toString())
        );
    }

    @GetMapping("/notifications/{id}")
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable Long id) {
        Optional<NotificationDTO> notificationDTO = notificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notificationDTO);
    }

    @GetMapping("/notifications/by-token")
    public ResponseEntity<List<NotificationDTO>> getNotificationByToken() {
        UUID currentUserId = SecurityUtils.getUserId();
        List<NotificationDTO> notificationDTOS = notificationService.findAllNotificationSentForUser(currentUserId);
        return ResponseEntity.ok().body(notificationDTOS);
    }


    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }


}
