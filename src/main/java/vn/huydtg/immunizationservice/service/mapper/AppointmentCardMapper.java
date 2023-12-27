package vn.huydtg.immunizationservice.service.mapper;


import org.mapstruct.Mapper;
import vn.huydtg.immunizationservice.domain.AppointmentCard;
import vn.huydtg.immunizationservice.service.dto.AppointmentCardDTO;

@Mapper(componentModel = "spring")
public interface AppointmentCardMapper extends EntityMapper<AppointmentCardDTO, AppointmentCard> {

}
