package vn.huydtg.immunizationservice.service.mapper;


import org.mapstruct.Mapper;
import vn.huydtg.immunizationservice.domain.VaccineLot;
import vn.huydtg.immunizationservice.service.dto.VaccineLotDTO;


@Mapper(componentModel = "spring")
public interface VaccineLotMapper extends EntityMapper<VaccineLotDTO, VaccineLot> {

}
