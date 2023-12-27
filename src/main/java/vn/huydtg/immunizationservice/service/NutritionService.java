package vn.huydtg.immunizationservice.service;

import vn.huydtg.immunizationservice.service.dto.NutritionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface NutritionService {

    NutritionDTO save(NutritionDTO nutritionDTO);


    NutritionDTO update(NutritionDTO nutritionDTO);


    Optional<NutritionDTO> partialUpdate(NutritionDTO nutritionDTO);


    Page<NutritionDTO> findAll(Pageable pageable);


    Optional<NutritionDTO> findOne(Long id);


    void delete(Long id);
}
