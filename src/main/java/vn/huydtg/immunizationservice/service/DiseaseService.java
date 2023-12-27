package vn.huydtg.immunizationservice.service;

import vn.huydtg.immunizationservice.service.dto.DiseaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.huydtg.immunizationservice.service.dto.DiseaseFSelectDTO;
import vn.huydtg.immunizationservice.service.dto.DiseaseWithVaccineRelationshipDTO;

import java.util.List;
import java.util.Optional;


public interface DiseaseService {

    DiseaseDTO save(DiseaseDTO diseaseDTO);


    DiseaseDTO update(DiseaseDTO diseaseDTO);


    Optional<DiseaseDTO> partialUpdate(DiseaseDTO diseaseDTO);


    Page<DiseaseDTO> findAll(Pageable pageable);


    Optional<DiseaseDTO> findOne(Long id);

    Optional<DiseaseWithVaccineRelationshipDTO> findOneWithEagerRelationship(Long id);

    void delete(Long id);

    List<DiseaseFSelectDTO> getDiseaseFSelectDTO();
}
