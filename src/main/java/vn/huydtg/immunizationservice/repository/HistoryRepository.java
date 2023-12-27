package vn.huydtg.immunizationservice.repository;

import vn.huydtg.immunizationservice.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface HistoryRepository extends JpaRepository<History, Long>, JpaSpecificationExecutor<History> {
    List<History> findAllByPatientId(UUID patientId);
}
