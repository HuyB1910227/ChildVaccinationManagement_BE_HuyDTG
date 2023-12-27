package vn.huydtg.immunizationservice.repository;


import org.springframework.data.jpa.repository.Query;
import vn.huydtg.immunizationservice.domain.AppointmentCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Repository
public interface AppointmentCardRepository extends JpaRepository<AppointmentCard, Long>, JpaSpecificationExecutor<AppointmentCard> {
    @Query(value = "SELECT d.id, d.appointmentDateConfirmed, d.status FROM AppointmentCard d")
    List<Object[]> findAllAppointmentCardsForSelect();

    @Query(value = "SELECT ap FROM AppointmentCard ap JOIN ap.patient p WHERE p.customer.id =:customerId AND ap.appointmentDateConfirmed BETWEEN :fromDate AND :toDate AND ap.status > 0")
    List<AppointmentCard> findAppointmentCardConfirmedForCustomer(UUID customerId, Instant fromDate, Instant toDate);


    @Query(value = "SELECT ap FROM AppointmentCard ap JOIN ap.patient p WHERE p.customer.id =:customerId AND ap.appointmentDate > :fromDate AND ap.status = 0")
    List<AppointmentCard> findAppointmentCardRegisteredForCustomer(UUID customerId, Instant fromDate);

    @Query(value = "SELECT ap FROM AppointmentCard ap WHERE ap.appointmentDateConfirmed BETWEEN :fromDate AND :toDate AND ap.status > 0")
    List<AppointmentCard> findAppointmentCardWithDateRange(Instant fromDate, Instant toDate);


    @Query(value = "SELECT ap FROM AppointmentCard ap WHERE ap.appointmentDateConfirmed BETWEEN :fromDate AND :toDate AND ap.status BETWEEN :fromStatus AND :toStatus")
    List<AppointmentCard> findAppointmentCardWithDateAndStatusRange(Instant fromDate, Instant toDate, Integer fromStatus, Integer toStatus);
}

