package vn.huydtg.immunizationservice.service.mapper;


import org.mapstruct.Mapper;
import vn.huydtg.immunizationservice.domain.Nutrition;
import vn.huydtg.immunizationservice.service.dto.NutritionDTO;


@Mapper(componentModel = "spring")
public interface NutritionMapper extends EntityMapper<NutritionDTO, Nutrition> {

}
