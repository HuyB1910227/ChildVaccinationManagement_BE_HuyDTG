package vn.huydtg.immunizationservice.service.mapper;

import org.mapstruct.Mapper;
import vn.huydtg.immunizationservice.domain.Role;
import vn.huydtg.immunizationservice.service.dto.RoleDTO;


@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDTO, Role>{
}
