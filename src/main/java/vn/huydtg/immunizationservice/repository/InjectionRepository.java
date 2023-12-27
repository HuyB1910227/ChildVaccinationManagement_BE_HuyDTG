package vn.huydtg.immunizationservice.repository;

import vn.huydtg.immunizationservice.domain.Injection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InjectionRepository extends JpaRepository<Injection, Long>, JpaSpecificationExecutor<Injection> {


    List<Injection> findAllByAgeId(Long ageId);
}
