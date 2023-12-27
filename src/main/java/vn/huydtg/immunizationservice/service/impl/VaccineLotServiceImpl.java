package vn.huydtg.immunizationservice.service.impl;

import vn.huydtg.immunizationservice.domain.VaccineLot;
import vn.huydtg.immunizationservice.domain.enumeration.VaccinationType;
import vn.huydtg.immunizationservice.repository.VaccineLotRepository;
import vn.huydtg.immunizationservice.service.VaccineLotService;
import vn.huydtg.immunizationservice.service.dto.VaccineAvailableInImmunizationUnitDTO;
import vn.huydtg.immunizationservice.service.dto.VaccineLotDTO;
import vn.huydtg.immunizationservice.service.mapper.VaccineLotMapper;
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
public class VaccineLotServiceImpl implements VaccineLotService {


    private final VaccineLotRepository vaccineLotRepository;

    private final VaccineLotMapper vaccineLotMapper;

    public VaccineLotServiceImpl(VaccineLotRepository vaccineLotRepository, VaccineLotMapper vaccineLotMapper) {
        this.vaccineLotRepository = vaccineLotRepository;
        this.vaccineLotMapper = vaccineLotMapper;
    }

    @Override
    public VaccineLotDTO save(VaccineLotDTO vaccineLotDTO) {
        VaccineLot vaccineLot = vaccineLotMapper.toEntity(vaccineLotDTO);
        vaccineLot = vaccineLotRepository.save(vaccineLot);
        return vaccineLotMapper.toDto(vaccineLot);
    }

    @Override
    public VaccineLotDTO update(VaccineLotDTO vaccineLotDTO) {
        VaccineLot vaccineLot = vaccineLotMapper.toEntity(vaccineLotDTO);
        vaccineLot = vaccineLotRepository.save(vaccineLot);
        return vaccineLotMapper.toDto(vaccineLot);
    }

    @Override
    public Optional<VaccineLotDTO> partialUpdate(VaccineLotDTO vaccineLotDTO) {

        return vaccineLotRepository
            .findById(vaccineLotDTO.getId())
            .map(existingVaccineLot -> {
                vaccineLotMapper.partialUpdate(existingVaccineLot, vaccineLotDTO);

                return existingVaccineLot;
            })
            .map(vaccineLotRepository::save)
            .map(vaccineLotMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VaccineLotDTO> findAll(Pageable pageable) {
        return vaccineLotRepository.findAll(pageable).map(vaccineLotMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VaccineLotDTO> findOne(Long id) {
        return vaccineLotRepository.findById(id).map(vaccineLotMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        vaccineLotRepository.deleteById(id);
    }

    @Override
    public List<VaccineAvailableInImmunizationUnitDTO> getVaccineAvailableByImmunizationUnitId(UUID immunizationUnitId) {
        List<Object[]> resultList = vaccineLotRepository.findVaccineLotAvailableByImmunizationUnitId(immunizationUnitId);
        List<VaccineAvailableInImmunizationUnitDTO> vaccineAvailableInImmunizationUnitDTOList = new ArrayList<>();
        for (Object[] result : resultList) {
            if (result.length >= 3) {
                UUID vaccineId = (UUID) result[0];
                String vaccineName = (String) result[1];
                VaccinationType vaccinationType = (VaccinationType) result[2];
                VaccineAvailableInImmunizationUnitDTO vaccineAvailableInImmunizationUnitDTO = new VaccineAvailableInImmunizationUnitDTO(vaccineId, vaccineName, vaccinationType);
                vaccineAvailableInImmunizationUnitDTOList.add(vaccineAvailableInImmunizationUnitDTO);
            }
        }
        return vaccineAvailableInImmunizationUnitDTOList;
    }
}
