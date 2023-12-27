package vn.huydtg.immunizationservice.service.mapper;


import org.mapstruct.Mapper;
import vn.huydtg.immunizationservice.domain.Administrator;
import vn.huydtg.immunizationservice.service.dto.AdministratorDTO;


@Mapper(componentModel = "spring")
public interface AdministratorMapper extends EntityMapper<AdministratorDTO, Administrator> {

}
