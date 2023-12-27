package vn.huydtg.immunizationservice.service.mapper;


import org.mapstruct.Mapper;
import vn.huydtg.immunizationservice.domain.FixedSchedule;
import vn.huydtg.immunizationservice.service.dto.FixedScheduleDTO;


@Mapper(componentModel = "spring")
public interface FixedScheduleMapper extends EntityMapper<FixedScheduleDTO, FixedSchedule> {

}
