package vn.huydtg.immunizationservice.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.huydtg.immunizationservice.service.dto.AppointmentCardDTO;
import vn.huydtg.immunizationservice.service.dto.AppointmentCardFSelectDTO;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface AppointmentCardService {
    AppointmentCardDTO save(AppointmentCardDTO appointmentCardDTO);


    AppointmentCardDTO update(AppointmentCardDTO appointmentCardDTO);


    Optional<AppointmentCardDTO> partialUpdate(AppointmentCardDTO appointmentCardDTO);


    Page<AppointmentCardDTO> findAll(Pageable pageable);


    List<AppointmentCardDTO> findAllWhereHistoryIsNull();


    Optional<AppointmentCardDTO> findOne(Long id);

    void delete(Long id);

    List<AppointmentCardFSelectDTO> getAppointmentCardFSelectDTO();

    List<AppointmentCardDTO> findAppointmentCardWScheduleExpectedFCustomer(UUID customerId, Instant fromDate, Instant toDate);

    List<AppointmentCardDTO> findAppointmentCardWScheduleRegisteredFCustomer(UUID customerId, Instant fromDate);
}
