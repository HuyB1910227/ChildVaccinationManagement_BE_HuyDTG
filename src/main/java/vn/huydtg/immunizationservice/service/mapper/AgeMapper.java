package vn.huydtg.immunizationservice.service.mapper;


import org.mapstruct.Mapper;
import vn.huydtg.immunizationservice.domain.Age;
import vn.huydtg.immunizationservice.service.dto.AgeDTO;

@Mapper(componentModel = "spring")
public interface AgeMapper extends EntityMapper<AgeDTO, Age> {

}
