package vn.huydtg.immunizationservice.service.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.huydtg.immunizationservice.domain.Customer;
import vn.huydtg.immunizationservice.domain.NotificationToken;
import vn.huydtg.immunizationservice.repository.CustomerRepository;
import vn.huydtg.immunizationservice.repository.NotificationRepository;
import vn.huydtg.immunizationservice.repository.NotificationTokenRepository;
import vn.huydtg.immunizationservice.service.NotificationService;
import vn.huydtg.immunizationservice.service.dto.NotificationDTO;
import vn.huydtg.immunizationservice.service.dto.UserDTO;
import vn.huydtg.immunizationservice.web.rest.NotificationForCustomerResource;
import vn.huydtg.immunizationservice.web.rest.errors.BadRequestAlertException;
import vn.huydtg.immunizationservice.web.rest.vm.NotificationForCustomerVM;
import vn.huydtg.immunizationservice.web.rest.vm.PushNotificationRequest;

import java.util.List;
import java.util.Optional;


@Service
public class PushNotificationForCustomerService {

    private NotificationService notificationService;

    private NotificationRepository notificationRepository;

    private CustomerRepository customerRepository;

    private PushNotificationService pushNotificationService;

    private NotificationTokenRepository notificationTokenRepository;

    Logger logger = LoggerFactory.getLogger(NotificationForCustomerResource.class);


    public PushNotificationForCustomerService(NotificationService notificationService, NotificationRepository notificationRepository, CustomerRepository customerRepository, PushNotificationService pushNotificationService, NotificationTokenRepository notificationTokenRepository) {
        this.notificationService = notificationService;
        this.notificationRepository = notificationRepository;
        this.customerRepository = customerRepository;
        this.pushNotificationService = pushNotificationService;
        this.notificationTokenRepository = notificationTokenRepository;
    }

    public NotificationDTO createAppointmentNotificationsForSchedule(NotificationForCustomerVM notificationForCustomerVM) {
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
            return null;
        }

        for (NotificationToken notificationToken : notificationTokenList) {
            PushNotificationRequest pushNotificationRequest = new PushNotificationRequest(data.getTitle(), data.getMessage(), data.getTopic(), notificationToken.getRegistrationToken());
            logger.info("sent to " + pushNotificationRequest.getToken());
            pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
        }
        return result;
    }
}
