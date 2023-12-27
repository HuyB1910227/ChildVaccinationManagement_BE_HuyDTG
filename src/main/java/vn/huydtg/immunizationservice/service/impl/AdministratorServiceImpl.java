package vn.huydtg.immunizationservice.service.impl;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.huydtg.immunizationservice.domain.Administrator;
import vn.huydtg.immunizationservice.repository.AdministratorRepository;
import vn.huydtg.immunizationservice.service.AdministratorService;
import vn.huydtg.immunizationservice.service.dto.AdministratorDTO;
import vn.huydtg.immunizationservice.service.mapper.AdministratorMapper;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AdministratorServiceImpl implements AdministratorService {


    private final AdministratorRepository administratorRepository;

    private final AdministratorMapper administratorMapper;

    public AdministratorServiceImpl(AdministratorRepository administratorRepository, AdministratorMapper administratorMapper) {
        this.administratorRepository = administratorRepository;
        this.administratorMapper = administratorMapper;
    }

    @Override
    public AdministratorDTO save(AdministratorDTO administratorDTO) {
        Administrator administrator = administratorMapper.toEntity(administratorDTO);
        administrator = administratorRepository.save(administrator);
        return administratorMapper.toDto(administrator);
    }

    @Override
    public AdministratorDTO update(AdministratorDTO administratorDTO) {
        Administrator administrator = administratorMapper.toEntity(administratorDTO);
        administrator = administratorRepository.save(administrator);
        return administratorMapper.toDto(administrator);
    }

    @Override
    public Optional<AdministratorDTO> partialUpdate(AdministratorDTO administratorDTO) {
        return administratorRepository
            .findById(administratorDTO.getId())
            .map(existingAdministrator -> {
                administratorMapper.partialUpdate(existingAdministrator, administratorDTO);

                return existingAdministrator;
            })
            .map(administratorRepository::save)
            .map(administratorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdministratorDTO> findOne(UUID id) {
        return administratorRepository.findById(id).map(administratorMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AdministratorDTO> findOneByUserId(UUID userId) {
        return administratorRepository.findFirstByUserId(userId).map(administratorMapper::toDto);
    }

}
