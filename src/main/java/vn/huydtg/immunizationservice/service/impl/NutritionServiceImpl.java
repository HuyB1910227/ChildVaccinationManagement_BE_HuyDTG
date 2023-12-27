package vn.huydtg.immunizationservice.service.impl;

import vn.huydtg.immunizationservice.domain.Nutrition;
import vn.huydtg.immunizationservice.repository.NutritionRepository;
import vn.huydtg.immunizationservice.service.NutritionService;
import vn.huydtg.immunizationservice.service.dto.NutritionDTO;
import vn.huydtg.immunizationservice.service.mapper.NutritionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class NutritionServiceImpl implements NutritionService {


    private final NutritionRepository nutritionRepository;

    private final NutritionMapper nutritionMapper;

    public NutritionServiceImpl(NutritionRepository nutritionRepository, NutritionMapper nutritionMapper) {
        this.nutritionRepository = nutritionRepository;
        this.nutritionMapper = nutritionMapper;
    }

    @Override
    public NutritionDTO save(NutritionDTO nutritionDTO) {
        Nutrition nutrition = nutritionMapper.toEntity(nutritionDTO);
        nutrition = nutritionRepository.save(nutrition);
        return nutritionMapper.toDto(nutrition);
    }

    @Override
    public NutritionDTO update(NutritionDTO nutritionDTO) {
        Nutrition nutrition = nutritionMapper.toEntity(nutritionDTO);
        nutrition = nutritionRepository.save(nutrition);
        return nutritionMapper.toDto(nutrition);
    }

    @Override
    public Optional<NutritionDTO> partialUpdate(NutritionDTO nutritionDTO) {

        return nutritionRepository
            .findById(nutritionDTO.getId())
            .map(existingNutrition -> {
                nutritionMapper.partialUpdate(existingNutrition, nutritionDTO);

                return existingNutrition;
            })
            .map(nutritionRepository::save)
            .map(nutritionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NutritionDTO> findAll(Pageable pageable) {
        return nutritionRepository.findAll(pageable).map(nutritionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NutritionDTO> findOne(Long id) {
        return nutritionRepository.findById(id).map(nutritionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        nutritionRepository.deleteById(id);
    }
}
