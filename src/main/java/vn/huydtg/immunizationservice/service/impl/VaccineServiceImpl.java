package vn.huydtg.immunizationservice.service.impl;

import vn.huydtg.immunizationservice.domain.Vaccine;
import vn.huydtg.immunizationservice.repository.VaccineRepository;
import vn.huydtg.immunizationservice.service.VaccineService;
import vn.huydtg.immunizationservice.service.dto.VaccineDTO;
import vn.huydtg.immunizationservice.service.dto.VaccineFSelectDTO;
import vn.huydtg.immunizationservice.service.mapper.VaccineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class VaccineServiceImpl implements VaccineService {


    private final VaccineRepository vaccineRepository;

    private final VaccineMapper vaccineMapper;

    public VaccineServiceImpl(VaccineRepository vaccineRepository, VaccineMapper vaccineMapper) {
        this.vaccineRepository = vaccineRepository;
        this.vaccineMapper = vaccineMapper;
    }

    @Override
    public VaccineDTO save(VaccineDTO vaccineDTO) {
        Vaccine vaccine = vaccineMapper.toEntity(vaccineDTO);
        vaccine = vaccineRepository.save(vaccine);
        return vaccineMapper.toDto(vaccine);
    }

    @Override
    public VaccineDTO update(VaccineDTO vaccineDTO) {
        Vaccine vaccine = vaccineMapper.toEntity(vaccineDTO);
        vaccine = vaccineRepository.save(vaccine);
        return vaccineMapper.toDto(vaccine);
    }

    @Override
    public Optional<VaccineDTO> partialUpdate(VaccineDTO vaccineDTO) {

        return vaccineRepository
            .findById(vaccineDTO.getId())
            .map(existingVaccine -> {
                vaccineMapper.partialUpdate(existingVaccine, vaccineDTO);

                return existingVaccine;
            })
            .map(vaccineRepository::save)
            .map(vaccineMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VaccineDTO> findAll(Pageable pageable) {
        return vaccineRepository.findAll(pageable).map(vaccineMapper::toDto);
    }

    public Page<VaccineDTO> findAllWithEagerRelationships(Pageable pageable) {
        return vaccineRepository.findAllWithEagerRelationships(pageable).map(vaccineMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VaccineDTO> findOne(UUID id) {
        return vaccineRepository.findOneWithEagerRelationships(id).map(vaccineMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        vaccineRepository.deleteById(id);
    }

    @Override
    public List<VaccineFSelectDTO> getVaccineFSelectDTO() {
        List<Object[]> resultList = vaccineRepository.findAllVaccinesForSelect();
        List<VaccineFSelectDTO> vaccineFSelectDTOList = new ArrayList<>();
        for (Object[] result : resultList) {
            if (result.length >= 2) {
                UUID id = (UUID) result[0];
                String name = (String) result[1];
                VaccineFSelectDTO vaccineFSelectDTO = new VaccineFSelectDTO();
                vaccineFSelectDTO.setId(id);
                vaccineFSelectDTO.setName(name);
                vaccineFSelectDTOList.add(vaccineFSelectDTO);
            }
        }
        return vaccineFSelectDTOList;
    }
}
