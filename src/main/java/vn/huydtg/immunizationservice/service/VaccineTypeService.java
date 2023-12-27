package vn.huydtg.immunizationservice.service;

import vn.huydtg.immunizationservice.service.dto.VaccineTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.huydtg.immunizationservice.service.dto.VaccineTypeFSelectDTO;

import java.util.List;
import java.util.Optional;


public interface VaccineTypeService {

    VaccineTypeDTO save(VaccineTypeDTO vaccineTypeDTO);


    VaccineTypeDTO update(VaccineTypeDTO vaccineTypeDTO);


    Optional<VaccineTypeDTO> partialUpdate(VaccineTypeDTO vaccineTypeDTO);

    Page<VaccineTypeDTO> findAll(Pageable pageable);


    Optional<VaccineTypeDTO> findOne(Long id);

    void delete(Long id);

    List<VaccineTypeFSelectDTO> getVaccineTypesFSelectDTO();
}
