package vn.huydtg.immunizationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.huydtg.immunizationservice.domain.Customer;



import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {
    @Query(value = "SELECT d.id, d.fullName, d.identityCard, d.phone FROM Customer d")
    List<Object[]> findAllCustomersForSelect();
    Optional<Customer> findFirstByUserId(UUID userId);

    @Modifying
    @Query("UPDATE Customer p SET p.avatar = :avatar WHERE p.id = :customerId")
    int updateAvatar(@Param("avatar") String avatar, @Param("customerId") UUID customerId);

    @Query(value = "SELECT c FROM Customer c JOIN c.user u WHERE u.createdAt BETWEEN :fromDate AND :toDate")
    List<Customer> findCustomerByRange(Instant fromDate, Instant toDate);
}
