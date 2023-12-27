package vn.huydtg.immunizationservice.service.mapper;


import org.mapstruct.Mapper;
import vn.huydtg.immunizationservice.domain.Vaccine;
import vn.huydtg.immunizationservice.service.dto.VaccineDTO;

@Mapper(componentModel = "spring")
public interface VaccineMapper extends EntityMapper<VaccineDTO, Vaccine> {


}
