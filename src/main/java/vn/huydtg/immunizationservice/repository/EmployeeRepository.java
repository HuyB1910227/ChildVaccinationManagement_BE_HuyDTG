package vn.huydtg.immunizationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.huydtg.immunizationservice.domain.Employee;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID>, JpaSpecificationExecutor<Employee> {
    @Query(value = "SELECT d.id, d.employeeId, d.fullName, d.phone FROM Employee d")
    List<Object[]> findAllEmployeesForSelect();

    Optional<Employee> findFirstByUserId(UUID userId);

    @Modifying
    @Query("UPDATE Employee p SET p.avatar = :avatar WHERE p.id = :employeeId")
    int updateAvatar(@Param("avatar") String avatar, @Param("employeeId") UUID employeeId);

    @Query(value = "SELECT e FROM Employee e JOIN e.user u JOIN u.roles r WHERE e.immunizationUnit.id = :immunizationUnitId AND r.name = 'PHYSICIAN'")
    List<Employee> finePhysicianInImmunizationUnit(UUID immunizationUnitId);
}
