package vn.huydtg.immunizationservice.service;

import vn.huydtg.immunizationservice.service.dto.ImmunizationUnitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.huydtg.immunizationservice.service.dto.ImmunizationUnitFSelectDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ImmunizationUnitService {

    ImmunizationUnitDTO save(ImmunizationUnitDTO immunizationUnitDTO);


    ImmunizationUnitDTO update(ImmunizationUnitDTO immunizationUnitDTO);


    Optional<ImmunizationUnitDTO> partialUpdate(ImmunizationUnitDTO immunizationUnitDTO);


    Page<ImmunizationUnitDTO> findAll(Pageable pageable);


    Optional<ImmunizationUnitDTO> findOne(UUID id);


    void delete(UUID id);

    List<ImmunizationUnitFSelectDTO> getImmunizationUnitsFSelectDTO();
}
