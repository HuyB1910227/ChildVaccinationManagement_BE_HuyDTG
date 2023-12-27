package vn.huydtg.immunizationservice.repository;

import vn.huydtg.immunizationservice.domain.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface NutritionRepository extends JpaRepository<Nutrition, Long>, JpaSpecificationExecutor<Nutrition> {}
