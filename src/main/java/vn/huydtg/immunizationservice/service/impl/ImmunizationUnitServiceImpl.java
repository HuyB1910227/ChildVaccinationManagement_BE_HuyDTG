package vn.huydtg.immunizationservice.service.impl;

import vn.huydtg.immunizationservice.domain.ImmunizationUnit;
import vn.huydtg.immunizationservice.repository.ImmunizationUnitRepository;
import vn.huydtg.immunizationservice.service.ImmunizationUnitService;
import vn.huydtg.immunizationservice.service.dto.ImmunizationUnitDTO;
import vn.huydtg.immunizationservice.service.dto.ImmunizationUnitFSelectDTO;
import vn.huydtg.immunizationservice.service.mapper.ImmunizationUnitMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ImmunizationUnitServiceImpl implements ImmunizationUnitService {


    private final ImmunizationUnitRepository immunizationUnitRepository;

    private final ImmunizationUnitMapper immunizationUnitMapper;

    public ImmunizationUnitServiceImpl(
        ImmunizationUnitRepository immunizationUnitRepository,
        ImmunizationUnitMapper immunizationUnitMapper
    ) {
        this.immunizationUnitRepository = immunizationUnitRepository;
        this.immunizationUnitMapper = immunizationUnitMapper;
    }

    @Override
    public ImmunizationUnitDTO save(ImmunizationUnitDTO immunizationUnitDTO) {
        ImmunizationUnit immunizationUnit = immunizationUnitMapper.toEntity(immunizationUnitDTO);
        immunizationUnit = immunizationUnitRepository.save(immunizationUnit);
        return immunizationUnitMapper.toDto(immunizationUnit);
    }

    @Override
    public ImmunizationUnitDTO update(ImmunizationUnitDTO immunizationUnitDTO) {
        ImmunizationUnit immunizationUnit = immunizationUnitMapper.toEntity(immunizationUnitDTO);
        immunizationUnit = immunizationUnitRepository.save(immunizationUnit);
        return immunizationUnitMapper.toDto(immunizationUnit);
    }

    @Override
    public Optional<ImmunizationUnitDTO> partialUpdate(ImmunizationUnitDTO immunizationUnitDTO) {

        return immunizationUnitRepository
            .findById(immunizationUnitDTO.getId())
            .map(existingImmunizationUnit -> {
                immunizationUnitMapper.partialUpdate(existingImmunizationUnit, immunizationUnitDTO);

                return existingImmunizationUnit;
            })
            .map(immunizationUnitRepository::save)
            .map(immunizationUnitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImmunizationUnitDTO> findAll(Pageable pageable) {
        return immunizationUnitRepository.findAll(pageable).map(immunizationUnitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ImmunizationUnitDTO> findOne(UUID id) {
        return immunizationUnitRepository.findById(id).map(immunizationUnitMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        immunizationUnitRepository.deleteById(id);
    }


    @Override
    public List<ImmunizationUnitFSelectDTO> getImmunizationUnitsFSelectDTO() {
        List<Object[]> resultList = immunizationUnitRepository.findAllImmunizationUnitsFSelect();
        List<ImmunizationUnitFSelectDTO> immunizationUnitsDTOList = new ArrayList<>();
        for (Object[] result : resultList) {
            if (result.length >= 4) {
                UUID id = (UUID) result[0];
                String name = (String) result[1];
                String address = (String) result[2];
                Boolean isActive = (Boolean) result[3];
                ImmunizationUnitFSelectDTO immunizationUnitFSelectDTO = new ImmunizationUnitFSelectDTO();
                immunizationUnitFSelectDTO.setId(id);
                immunizationUnitFSelectDTO.setName(name);
                immunizationUnitFSelectDTO.setAddress(address);
                immunizationUnitFSelectDTO.setIsActive(isActive);
                immunizationUnitsDTOList.add(immunizationUnitFSelectDTO);
            }
        }
        return immunizationUnitsDTOList;
    }
}
