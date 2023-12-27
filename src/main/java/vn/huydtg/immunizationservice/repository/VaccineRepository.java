package vn.huydtg.immunizationservice.repository;

import org.springframework.data.jpa.repository.Query;
import vn.huydtg.immunizationservice.domain.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface VaccineRepository
    extends VaccineRepositoryWithBagRelationships, JpaRepository<Vaccine, UUID>, JpaSpecificationExecutor<Vaccine> {
    default Optional<Vaccine> findOneWithEagerRelationships(UUID id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Vaccine> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Vaccine> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    @Query(value = "SELECT d.id, d.name FROM Vaccine d")
    List<Object[]> findAllVaccinesForSelect();
}
