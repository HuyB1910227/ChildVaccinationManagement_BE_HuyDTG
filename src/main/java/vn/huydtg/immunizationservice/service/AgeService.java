package vn.huydtg.immunizationservice.service;

import vn.huydtg.immunizationservice.service.dto.AgeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.huydtg.immunizationservice.service.dto.AgeFSelectDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface AgeService {

    AgeDTO save(AgeDTO ageDTO);


    AgeDTO update(AgeDTO ageDTO);


    Optional<AgeDTO> partialUpdate(AgeDTO ageDTO);


    Page<AgeDTO> findAll(Pageable pageable);


    Optional<AgeDTO> findOne(Long id);


    void delete(Long id);

    List<AgeFSelectDTO> findAllAgeByVaccine(UUID vaccineId);
}
