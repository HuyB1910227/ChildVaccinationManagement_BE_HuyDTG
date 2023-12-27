package vn.huydtg.immunizationservice.repository;

import org.springframework.data.jpa.repository.Query;
import vn.huydtg.immunizationservice.domain.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VaccineTypeRepository extends JpaRepository<VaccineType, Long>, JpaSpecificationExecutor<VaccineType> {
    @Query(value = "SELECT d.id, d.name FROM VaccineType d")
    List<Object[]> findAllVaccineTypesForSelect();
}
