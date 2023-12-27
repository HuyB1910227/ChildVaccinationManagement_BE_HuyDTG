package vn.huydtg.immunizationservice.service.mapper;


import org.mapstruct.Mapper;
import vn.huydtg.immunizationservice.domain.Provider;
import vn.huydtg.immunizationservice.service.dto.ProviderDTO;


@Mapper(componentModel = "spring")
public interface ProviderMapper extends EntityMapper<ProviderDTO, Provider> {}
