package vn.huydtg.immunizationservice.repository;

import org.springframework.data.jpa.repository.Query;
import vn.huydtg.immunizationservice.domain.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long>, JpaSpecificationExecutor<Provider> {
    @Query(value = "SELECT d.id, d.name FROM Provider d")
    List<Object[]> findAllProvidersForSelect();
}
