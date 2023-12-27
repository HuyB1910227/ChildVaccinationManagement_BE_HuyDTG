package vn.huydtg.immunizationservice.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.huydtg.immunizationservice.domain.Customer;
import vn.huydtg.immunizationservice.domain.Notification;
import vn.huydtg.immunizationservice.domain.NotificationToken;
import vn.huydtg.immunizationservice.repository.CustomerRepository;
import vn.huydtg.immunizationservice.repository.NotificationRepository;
import vn.huydtg.immunizationservice.repository.NotificationTokenRepository;
import vn.huydtg.immunizationservice.repository.UserRepository;
import vn.huydtg.immunizationservice.security.SecurityUtils;
import vn.huydtg.immunizationservice.service.NotificationService;
import vn.huydtg.immunizationservice.service.dto.CustomerDTO;
import vn.huydtg.immunizationservice.service.dto.NotificationDTO;
import vn.huydtg.immunizationservice.service.dto.UserDTO;
import vn.huydtg.immunizationservice.service.notification.PushNotificationService;
import vn.huydtg.immunizationservice.web.rest.errors.BadRequestAlertException;
import vn.huydtg.immunizationservice.web.rest.utils.HeaderUtil;
import vn.huydtg.immunizationservice.web.rest.utils.ResponseUtil;
import vn.huydtg.immunizationservice.web.rest.vm.NotificationForCustomerVM;
import vn.huydtg.immunizationservice.web.rest.vm.PushNotificationRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
@RestController
@RequestMapping("/api")
public class NotificationForCustomerResource {

    @Value("${spring.application.name}")
    private String applicationName;
    private static final String ENTITY_NAME = "REST_NOTIFICATION";

    private final NotificationService notificationService;

    private final NotificationRepository notificationRepository;

    private final CustomerRepository customerRepository;

    private PushNotificationService pushNotificationService;

    private NotificationTokenRepository notificationTokenRepository;

    Logger logger = LoggerFactory.getLogger(NotificationForCustomerResource.class);


    public NotificationForCustomerResource(NotificationService notificationService, NotificationRepository notificationRepository, CustomerRepository customerRepository, PushNotificationService pushNotificationService, NotificationTokenRepository notificationTokenRepository) {
        this.notificationService = notificationService;
        this.notificationRepository = notificationRepository;
        this.customerRepository = customerRepository;
        this.pushNotificationService = pushNotificationService;
        this.notificationTokenRepository = notificationTokenRepository;
    }



    @PostMapping("/notifications/for-customer")
    public ResponseEntity<NotificationDTO> createNotificationForCustomer(@Valid @RequestBody NotificationForCustomerVM notificationForCustomerVM) throws URISyntaxException {
//
        Optional<Customer> customer = customerRepository.findById(notificationForCustomerVM.getCustomerId());
        if (customer.isEmpty()) {
            throw new BadRequestAlertException("Not Found Customer", "REST_CUSTOMER", "notfoundcustomer");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(customer.get().getUser().getId());
        NotificationDTO data = new NotificationDTO();
        data.setTitle(notificationForCustomerVM.getTitle());
        data.setTopic(notificationForCustomerVM.getTopic());
        data.setMessage(notificationForCustomerVM.getMessage());
        data.setStatus(1);
        data.setSentDate(notificationForCustomerVM.getSentDate());
        data.setUser(userDTO);
        NotificationDTO result = notificationService.save(data);

        List<NotificationToken> notificationTokenList = notificationTokenRepository.findAllByUserId(userDTO.getId());

        if (notificationTokenList.isEmpty()) {
            logger.info("not found notification list");
            return ResponseEntity
                    .created(new URI("/api/notifications/for-customer" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);
        }

        for (NotificationToken notificationToken : notificationTokenList) {
            PushNotificationRequest pushNotificationRequest = new PushNotificationRequest(data.getTitle(), data.getMessage(), data.getTopic(), notificationToken.getRegistrationToken());
            logger.info("sent to " + pushNotificationRequest.getToken());
            pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
        }
        return ResponseEntity
                .created(new URI("/api/notifications/for-customer" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);


    }
}
