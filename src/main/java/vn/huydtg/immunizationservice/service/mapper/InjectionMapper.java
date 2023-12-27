package vn.huydtg.immunizationservice.service.mapper;


import org.mapstruct.Mapper;
import vn.huydtg.immunizationservice.domain.Injection;
import vn.huydtg.immunizationservice.service.dto.InjectionDTO;


@Mapper(componentModel = "spring")
public interface InjectionMapper extends EntityMapper<InjectionDTO, Injection> {

}
