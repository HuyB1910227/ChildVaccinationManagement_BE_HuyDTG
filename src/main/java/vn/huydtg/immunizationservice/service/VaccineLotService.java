package vn.huydtg.immunizationservice.service;

import vn.huydtg.immunizationservice.service.dto.VaccineAvailableInImmunizationUnitDTO;
import vn.huydtg.immunizationservice.service.dto.VaccineLotDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface VaccineLotService {

    VaccineLotDTO save(VaccineLotDTO vaccineLotDTO);


    VaccineLotDTO update(VaccineLotDTO vaccineLotDTO);


    Optional<VaccineLotDTO> partialUpdate(VaccineLotDTO vaccineLotDTO);


    Page<VaccineLotDTO> findAll(Pageable pageable);


    Optional<VaccineLotDTO> findOne(Long id);


    void delete(Long id);

    List<VaccineAvailableInImmunizationUnitDTO> getVaccineAvailableByImmunizationUnitId(UUID immunizationUnitId);
}
