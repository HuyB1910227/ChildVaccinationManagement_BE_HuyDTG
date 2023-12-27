package vn.huydtg.immunizationservice.service.mapper;


import org.mapstruct.Mapper;
import vn.huydtg.immunizationservice.domain.Disease;
import vn.huydtg.immunizationservice.service.dto.DiseaseDTO;


@Mapper(componentModel = "spring")
public interface DiseaseMapper extends EntityMapper<DiseaseDTO, Disease> {}
