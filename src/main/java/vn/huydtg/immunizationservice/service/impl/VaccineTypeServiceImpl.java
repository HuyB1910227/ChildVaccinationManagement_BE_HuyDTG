package vn.huydtg.immunizationservice.service.impl;

import vn.huydtg.immunizationservice.domain.VaccineType;
import vn.huydtg.immunizationservice.repository.VaccineTypeRepository;
import vn.huydtg.immunizationservice.service.VaccineTypeService;
import vn.huydtg.immunizationservice.service.dto.VaccineTypeDTO;
import vn.huydtg.immunizationservice.service.dto.VaccineTypeFSelectDTO;
import vn.huydtg.immunizationservice.service.mapper.VaccineTypeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class VaccineTypeServiceImpl implements VaccineTypeService {


    private final VaccineTypeRepository vaccineTypeRepository;

    private final VaccineTypeMapper vaccineTypeMapper;

    public VaccineTypeServiceImpl(VaccineTypeRepository vaccineTypeRepository, VaccineTypeMapper vaccineTypeMapper) {
        this.vaccineTypeRepository = vaccineTypeRepository;
        this.vaccineTypeMapper = vaccineTypeMapper;
    }

    @Override
    public VaccineTypeDTO save(VaccineTypeDTO vaccineTypeDTO) {
        VaccineType vaccineType = vaccineTypeMapper.toEntity(vaccineTypeDTO);
        vaccineType = vaccineTypeRepository.save(vaccineType);
        return vaccineTypeMapper.toDto(vaccineType);
    }

    @Override
    public VaccineTypeDTO update(VaccineTypeDTO vaccineTypeDTO) {
        VaccineType vaccineType = vaccineTypeMapper.toEntity(vaccineTypeDTO);
        vaccineType = vaccineTypeRepository.save(vaccineType);
        return vaccineTypeMapper.toDto(vaccineType);
    }

    @Override
    public Optional<VaccineTypeDTO> partialUpdate(VaccineTypeDTO vaccineTypeDTO) {

        return vaccineTypeRepository
            .findById(vaccineTypeDTO.getId())
            .map(existingVaccineType -> {
                vaccineTypeMapper.partialUpdate(existingVaccineType, vaccineTypeDTO);

                return existingVaccineType;
            })
            .map(vaccineTypeRepository::save)
            .map(vaccineTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VaccineTypeDTO> findAll(Pageable pageable) {
        return vaccineTypeRepository.findAll(pageable).map(vaccineTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VaccineTypeDTO> findOne(Long id) {
        return vaccineTypeRepository.findById(id).map(vaccineTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        vaccineTypeRepository.deleteById(id);
    }

    @Override
    public List<VaccineTypeFSelectDTO> getVaccineTypesFSelectDTO() {
        List<Object[]> resultList = vaccineTypeRepository.findAllVaccineTypesForSelect();
        List<VaccineTypeFSelectDTO> vaccineTypeFSelectDTOList = new ArrayList<>();
        for (Object[] result : resultList) {
            if (result.length >= 2) {
                Long id = (Long) result[0];
                String name = (String) result[1];
                VaccineTypeFSelectDTO vaccineTypeFSelectDTO = new VaccineTypeFSelectDTO();
                vaccineTypeFSelectDTO.setId(id);
                vaccineTypeFSelectDTO.setName(name);
                vaccineTypeFSelectDTOList.add(vaccineTypeFSelectDTO);
            }
        }
        return vaccineTypeFSelectDTOList;
    }
}
