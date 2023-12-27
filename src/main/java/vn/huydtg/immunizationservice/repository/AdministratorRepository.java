package vn.huydtg.immunizationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.huydtg.immunizationservice.domain.Administrator;



import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, UUID>, JpaSpecificationExecutor<Administrator> {

    Optional<Administrator> findFirstByUserId(UUID userId);

    @Modifying
    @Query("UPDATE Administrator p SET p.avatar = :avatar WHERE p.id = :administratorId")
    int updateAvatar(@Param("avatar") String avatar, @Param("administratorId") UUID administratorId);
}
