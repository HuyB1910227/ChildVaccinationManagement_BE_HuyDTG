package vn.huydtg.immunizationservice.service.mapper;


import org.mapstruct.Mapper;
import vn.huydtg.immunizationservice.domain.ImmunizationUnit;
import vn.huydtg.immunizationservice.service.dto.ImmunizationUnitDTO;

@Mapper(componentModel = "spring")
public interface ImmunizationUnitMapper extends EntityMapper<ImmunizationUnitDTO, ImmunizationUnit> {}
