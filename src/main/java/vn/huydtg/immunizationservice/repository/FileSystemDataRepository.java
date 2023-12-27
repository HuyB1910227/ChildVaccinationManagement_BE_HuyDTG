package vn.huydtg.immunizationservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.huydtg.immunizationservice.domain.FileSystemData;

import java.util.Optional;

@Repository
public interface FileSystemDataRepository extends JpaRepository<FileSystemData, Long>, JpaSpecificationExecutor<FileSystemData> {

    Optional<FileSystemData> findByName(String name);
}
