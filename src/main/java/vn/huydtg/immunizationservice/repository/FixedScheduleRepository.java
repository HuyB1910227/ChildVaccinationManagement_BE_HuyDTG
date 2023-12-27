package vn.huydtg.immunizationservice.repository;

import org.springframework.data.jpa.repository.Query;
import vn.huydtg.immunizationservice.domain.FixedSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.huydtg.immunizationservice.domain.enumeration.VaccinationType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Repository
public interface FixedScheduleRepository extends JpaRepository<FixedSchedule, Long>, JpaSpecificationExecutor<FixedSchedule> {

    @Query(value = "SELECT f FROM FixedSchedule f WHERE f.immunizationUnit.id = :immunizationUnitId " +
            "AND f.workingDay = :workingDay AND :startTime <= f.endTime AND :endTime >= f.startTime " +
            "AND (f.workingWeek = :workingWeek OR f.workingWeek = 'ALL') AND f.vaccinationType = :vaccinationType")
    List<FixedSchedule> findAllFixedSchedulesWithWorkingWeek(String workingDay, String workingWeek, Instant startTime, Instant endTime, UUID immunizationUnitId, VaccinationType vaccinationType);


    List<FixedSchedule> findAllByIsEnableAndImmunizationUnitId(boolean status, UUID immunizationUnitId);
    List<FixedSchedule> findAllByIsEnableAndImmunizationUnitIdAndVaccinationType(boolean status, UUID immunizationUnitId, VaccinationType vaccinationType);

    @Query(value = "SELECT f FROM FixedSchedule f WHERE f.startTime <= :beginTime AND (f.workingWeek = :week OR f.workingWeek = 'ALL') AND (f.workingDay = :workingDay1 OR f.workingDay = :workingDay2)")
    List<FixedSchedule> findAllEligibleFixedSchedule(String workingDay1, String workingDay2, String week, Instant beginTime);
}
