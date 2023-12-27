package vn.huydtg.immunizationservice.service.mapper;


import org.mapstruct.Mapper;
import vn.huydtg.immunizationservice.domain.History;
import vn.huydtg.immunizationservice.service.dto.HistoryDTO;


@Mapper(componentModel = "spring")
public interface HistoryMapper extends EntityMapper<HistoryDTO, History> {

}
