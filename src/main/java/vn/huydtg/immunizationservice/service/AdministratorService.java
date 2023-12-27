package vn.huydtg.immunizationservice.service;

import vn.huydtg.immunizationservice.service.dto.AdministratorDTO;

import java.util.Optional;
import java.util.UUID;

public interface AdministratorService {

    AdministratorDTO save(AdministratorDTO administratorDTO);

    AdministratorDTO update(AdministratorDTO administratorDTO);

    Optional<AdministratorDTO> partialUpdate(AdministratorDTO administratorDTO);
    Optional<AdministratorDTO> findOne(UUID id);

    Optional<AdministratorDTO> findOneByUserId(UUID userId);
}
