package vn.huydtg.immunizationservice.service.mapper;

import org.mapstruct.Mapper;
import vn.huydtg.immunizationservice.domain.FileSystemData;
import vn.huydtg.immunizationservice.service.dto.FileSystemDataDTO;


@Mapper(componentModel = "spring")
public interface FileSystemDataMapper extends EntityMapper<FileSystemDataDTO, FileSystemData> {

}
