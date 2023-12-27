package vn.huydtg.immunizationservice.repository;

import org.springframework.data.jpa.repository.Query;
import vn.huydtg.immunizationservice.domain.VaccineLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;

import java.util.UUID;


@Repository
public interface VaccineLotRepository extends JpaRepository<VaccineLot, Long>, JpaSpecificationExecutor<VaccineLot> {

    @Query(value = "SELECT DISTINCT  v.id, v.name, d.vaccinationType FROM VaccineLot d JOIN d.vaccine v " +
            "WHERE d.status = 1 AND d.immunizationUnit.id = :immunizationUnitId AND d.quantity > d.quantityUsed AND d.expiredDate > CURRENT_TIMESTAMP")
    List<Object[]> findVaccineLotAvailableByImmunizationUnitId(@Param("immunizationUnitId") UUID immunizationUnitId);
 }
