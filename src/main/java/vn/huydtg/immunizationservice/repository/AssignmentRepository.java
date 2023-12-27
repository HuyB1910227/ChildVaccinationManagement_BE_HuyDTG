package vn.huydtg.immunizationservice.repository;

import org.springframework.data.jpa.repository.Query;
import vn.huydtg.immunizationservice.domain.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import java.util.List;

import java.util.UUID;


@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long>, JpaSpecificationExecutor<Assignment> {


    List<Assignment> findAllByAppointmentCardId(Long appointmentCardId);

    @Query(value = "SELECT DISTINCT a.id, d.id FROM Assignment a JOIN a.appointmentCard ac JOIN a.vaccineLot vl JOIN vl.vaccine v JOIN v.diseases d WHERE a.status = 1 AND ac.patient.id = :patientId")
    List<Object[]> getAllAssigmentForInjectionDiary(UUID patientId);

    @Query(value = "SELECT DISTINCT a.id, d.id FROM Assignment a JOIN a.appointmentCard ac JOIN a.vaccineLot vl JOIN vl.vaccine v JOIN v.diseases d WHERE ac.id = :appointmentCardId AND d.id = :diseaseId")
    List<Object[]> getAssignmentForCurrentAppointmentCardAndDisease(Long appointmentCardId, Long diseaseId);


    @Query(value = "SELECT DISTINCT a.id, d.id FROM Assignment a JOIN a.appointmentCard ac JOIN a.vaccineLot vl JOIN vl.vaccine v JOIN v.diseases d WHERE ac.id = :appointmentCardId")
    List<Object[]> getAssignmentForCurrentAppointmentCard(Long appointmentCardId);

    @Query(value = "SELECT a.id, d.id FROM Assignment a JOIN a.appointmentCard ac JOIN a.vaccineLot vl JOIN vl.vaccine v JOIN v.diseases d JOIN ac.patient p WHERE p.id = :patientId AND a.status = 1 AND d.id = :diseaseId ORDER BY a.injectionCompletionTime DESC LIMIT 1")
    Object[] getTheLastAssignmentOfDiseaseByPatientIdAndDiseaseId(UUID patientId, Long diseaseId);


    @Query(value = "SELECT DISTINCT a.id, a.injection.id, a.injectionCompletionTime, a.injectionTime FROM Assignment a JOIN a.appointmentCard ac JOIN a.vaccineLot vl JOIN vl.vaccine v JOIN v.diseases d WHERE a.status = 1 AND ac.patient.id = :patientId AND v.id = :vaccineId")
    List<Object[]> getAllAssigmentByPatientAndVaccine(UUID patientId, UUID vaccineId);
}
