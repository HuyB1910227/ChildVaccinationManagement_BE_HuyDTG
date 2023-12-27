package vn.huydtg.immunizationservice.service.mapper;

import org.mapstruct.Mapper;
import vn.huydtg.immunizationservice.domain.Patient;
import vn.huydtg.immunizationservice.service.dto.PatientDTO;

@Mapper(componentModel = "spring")
public interface PatientMapper extends EntityMapper<PatientDTO, Patient> {

}
