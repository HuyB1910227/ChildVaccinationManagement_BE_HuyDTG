package vn.huydtg.immunizationservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.huydtg.immunizationservice.domain.Age;
import vn.huydtg.immunizationservice.domain.enumeration.AgeType;

import java.util.List;
import java.util.UUID;


@Repository
public interface AgeRepository extends JpaRepository<Age, Long>, JpaSpecificationExecutor<Age> {
    Age findByMinAgeAndMinAgeTypeAndMaxAgeAndMaxAgeTypeAndVaccineId(Integer minAge, AgeType minAgeType, Integer maxAge, AgeType maxAgeType, UUID vaccineId);

    List<Age> findAllByVaccineId(UUID vaccineId);
}
