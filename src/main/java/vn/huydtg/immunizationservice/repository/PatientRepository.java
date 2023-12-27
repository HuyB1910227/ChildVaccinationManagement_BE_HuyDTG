package vn.huydtg.immunizationservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.huydtg.immunizationservice.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID>, JpaSpecificationExecutor<Patient> {
    @Query(value = "SELECT d.id, d.fullName, d.dateOfBirth FROM Patient d")
    List<Object[]> findAllPatientsForSelect();

    List<Patient> findAllByCustomerId(UUID customerId);


    @Modifying
    @Query("UPDATE Patient p SET p.avatar = :avatar WHERE p.id = :patientId")
    int updateAvatar(@Param("avatar") String avatar, @Param("patientId") UUID patientId);



}
