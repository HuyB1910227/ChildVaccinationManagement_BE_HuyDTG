package vn.huydtg.immunizationservice.service;

import vn.huydtg.immunizationservice.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientService {

    PatientDTO save(PatientDTO patientDTO);


    PatientDTO update(PatientDTO patientDTO);


    Optional<PatientDTO> partialUpdate(PatientDTO patientDTO);


    Page<PatientDTO> findAll(Pageable pageable);


    Optional<PatientDTO> findOne(UUID id);


    void delete(UUID id);

    List<PatientFSelectDTO> getPatientFSelectDTO();

    List<PatientFCustomerDTO> findPatientsByCustomerId(UUID customerId);


}
