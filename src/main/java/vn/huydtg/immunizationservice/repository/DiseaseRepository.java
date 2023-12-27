package vn.huydtg.immunizationservice.repository;


import org.springframework.data.jpa.repository.Query;
import vn.huydtg.immunizationservice.domain.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public interface DiseaseRepository extends DiseaseRepositoryWithBagRelationship, JpaRepository<Disease, Long>, JpaSpecificationExecutor<Disease> {
    @Query(value = "SELECT d.id, d.name FROM Disease d")
    List<Object[]> findAllDiseasesForSelect();


    default Optional<Disease> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }
}
