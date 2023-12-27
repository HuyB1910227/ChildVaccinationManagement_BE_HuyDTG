package vn.huydtg.immunizationservice.service;

import vn.huydtg.immunizationservice.service.dto.VaccineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.huydtg.immunizationservice.service.dto.VaccineFSelectDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VaccineService {

    VaccineDTO save(VaccineDTO vaccineDTO);


    VaccineDTO update(VaccineDTO vaccineDTO);


    Optional<VaccineDTO> partialUpdate(VaccineDTO vaccineDTO);


    Page<VaccineDTO> findAll(Pageable pageable);


    Page<VaccineDTO> findAllWithEagerRelationships(Pageable pageable);


    Optional<VaccineDTO> findOne(UUID id);


    void delete(UUID id);

    List<VaccineFSelectDTO> getVaccineFSelectDTO();
}
