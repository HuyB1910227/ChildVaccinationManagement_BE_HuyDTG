package vn.huydtg.immunizationservice.service.impl;

import vn.huydtg.immunizationservice.domain.Disease;
import vn.huydtg.immunizationservice.repository.DiseaseRepository;
import vn.huydtg.immunizationservice.service.DiseaseService;
import vn.huydtg.immunizationservice.service.dto.DiseaseDTO;
import vn.huydtg.immunizationservice.service.dto.DiseaseFSelectDTO;
import vn.huydtg.immunizationservice.service.dto.DiseaseWithVaccineRelationshipDTO;
import vn.huydtg.immunizationservice.service.mapper.DiseaseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.huydtg.immunizationservice.service.mapper.VaccineMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class DiseaseServiceImpl implements DiseaseService {


    private final DiseaseRepository diseaseRepository;

    private final DiseaseMapper diseaseMapper;

    private final VaccineMapper vaccineMapper;

    public DiseaseServiceImpl(DiseaseRepository diseaseRepository, DiseaseMapper diseaseMapper, VaccineMapper vaccineMapper) {
        this.diseaseRepository = diseaseRepository;
        this.diseaseMapper = diseaseMapper;
        this.vaccineMapper = vaccineMapper;
    }

    @Override
    public DiseaseDTO save(DiseaseDTO diseaseDTO) {
        Disease disease = diseaseMapper.toEntity(diseaseDTO);
        disease = diseaseRepository.save(disease);
        return diseaseMapper.toDto(disease);
    }

    @Override
    public DiseaseDTO update(DiseaseDTO diseaseDTO) {
        Disease disease = diseaseMapper.toEntity(diseaseDTO);
        disease = diseaseRepository.save(disease);
        return diseaseMapper.toDto(disease);
    }

    @Override
    public Optional<DiseaseDTO> partialUpdate(DiseaseDTO diseaseDTO) {

        return diseaseRepository
            .findById(diseaseDTO.getId())
            .map(existingDisease -> {
                diseaseMapper.partialUpdate(existingDisease, diseaseDTO);

                return existingDisease;
            })
            .map(diseaseRepository::save)
            .map(diseaseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DiseaseDTO> findAll(Pageable pageable) {
        return diseaseRepository.findAll(pageable).map(diseaseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DiseaseDTO> findOne(Long id) {
        return diseaseRepository.findById(id).map(diseaseMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DiseaseWithVaccineRelationshipDTO> findOneWithEagerRelationship(Long id) {
        return diseaseRepository.findOneWithEagerRelationships(id).map(
                disease -> {
                    DiseaseWithVaccineRelationshipDTO diseaseWithVaccineRelationshipDTO = new DiseaseWithVaccineRelationshipDTO();
                    diseaseWithVaccineRelationshipDTO.setId(disease.getId());
                    diseaseWithVaccineRelationshipDTO.setName(disease.getName());
                    diseaseWithVaccineRelationshipDTO.setDescription(disease.getDescription());
                    diseaseWithVaccineRelationshipDTO.setVaccines(disease.getVaccines().stream().map(vaccineMapper::toDto).collect(Collectors.toSet()));
                    return diseaseWithVaccineRelationshipDTO;
                }
        );
    }

    @Override
    public void delete(Long id) {
        diseaseRepository.deleteById(id);
    }

    @Override
    public List<DiseaseFSelectDTO> getDiseaseFSelectDTO() {
        List<Object[]> resultList = diseaseRepository.findAllDiseasesForSelect();
        List<DiseaseFSelectDTO> diseaseDTOList = new ArrayList<>();
        for (Object[] result : resultList) {
            if (result.length >= 2) {
                Long id = (Long) result[0];
                String name = (String) result[1];
                DiseaseFSelectDTO diseaseDTO = new DiseaseFSelectDTO();
                diseaseDTO.setId(id);
                diseaseDTO.setName(name);
                diseaseDTOList.add(diseaseDTO);
            }
        }
        return diseaseDTOList;
    }

}
