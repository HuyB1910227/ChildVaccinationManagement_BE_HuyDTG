package vn.huydtg.immunizationservice.repository;

import org.springframework.data.jpa.repository.Query;
import vn.huydtg.immunizationservice.domain.ImmunizationUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImmunizationUnitRepository extends JpaRepository<ImmunizationUnit, UUID>, JpaSpecificationExecutor<ImmunizationUnit> {

    Boolean findIsActiveById(UUID id);

    @Query(value = "SELECT d.id, d.name, d.address, d.isActive FROM ImmunizationUnit d")
    List<Object[]> findAllImmunizationUnitsFSelect();
}
