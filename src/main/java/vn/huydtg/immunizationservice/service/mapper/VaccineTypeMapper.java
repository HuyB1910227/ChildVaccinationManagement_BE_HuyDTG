package vn.huydtg.immunizationservice.service.mapper;


import org.mapstruct.Mapper;
import vn.huydtg.immunizationservice.domain.VaccineType;
import vn.huydtg.immunizationservice.service.dto.VaccineTypeDTO;


@Mapper(componentModel = "spring")
public interface VaccineTypeMapper extends EntityMapper<VaccineTypeDTO, VaccineType> {}
