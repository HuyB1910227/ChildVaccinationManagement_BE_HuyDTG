package vn.huydtg.immunizationservice.service.mapper;


import org.mapstruct.Mapper;
import vn.huydtg.immunizationservice.domain.Assignment;
import vn.huydtg.immunizationservice.service.dto.AssignmentDTO;


@Mapper(componentModel = "spring")
public interface AssignmentMapper extends EntityMapper<AssignmentDTO, Assignment> {

}
