package vn.huydtg.immunizationservice.service.impl;

import vn.huydtg.immunizationservice.domain.Patient;
import vn.huydtg.immunizationservice.repository.PatientRepository;
import vn.huydtg.immunizationservice.service.PatientService;
import vn.huydtg.immunizationservice.service.dto.*;
import vn.huydtg.immunizationservice.service.mapper.PatientMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional
public class PatientServiceImpl implements PatientService {


    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public PatientDTO save(PatientDTO patientDTO) {
        Patient patient = patientMapper.toEntity(patientDTO);
        patient = patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    @Override
    public PatientDTO update(PatientDTO patientDTO) {
        Patient patient = patientMapper.toEntity(patientDTO);
        patient = patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    @Override
    public Optional<PatientDTO> partialUpdate(PatientDTO patientDTO) {

        return patientRepository
            .findById(patientDTO.getId())
            .map(existingPatient -> {
                patientMapper.partialUpdate(existingPatient, patientDTO);

                return existingPatient;
            })
            .map(patientRepository::save)
            .map(patientMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientDTO> findAll(Pageable pageable) {
        return patientRepository.findAll(pageable).map(patientMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientDTO> findOne(UUID id) {
        return patientRepository.findById(id).map(patientMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        patientRepository.deleteById(id);
    }

    @Override
    public List<PatientFSelectDTO> getPatientFSelectDTO() {
        List<Object[]> resultList = patientRepository.findAllPatientsForSelect();
        List<PatientFSelectDTO> patientFSelectDTOArrayList = new ArrayList<>();
        for (Object[] result : resultList) {
            if (result.length >= 3) {
                UUID id = (UUID) result[0];

                String fullName = (String) result[1];
                LocalDate dateOfBirth = (LocalDate) result[2];
                PatientFSelectDTO patientFSelectDTO = new PatientFSelectDTO();
                patientFSelectDTO.setId(id);
                patientFSelectDTO.setFullName(fullName);
                patientFSelectDTO.setDateOfBirth(dateOfBirth);
                patientFSelectDTOArrayList.add(patientFSelectDTO);
            }
        }
        return patientFSelectDTOArrayList;
    }

    @Override
    public List<PatientFCustomerDTO> findPatientsByCustomerId(UUID customerId){
        List<PatientFCustomerDTO> patientFCustomerDTOS = patientRepository
                .findAllByCustomerId(customerId)
                .stream()
                .map(patientMapper::toDto)
                .map(patientDTO -> {
                    PatientFCustomerDTO patientFCustomerDTO =
                            new PatientFCustomerDTO(patientDTO.getId(),
                                    patientDTO.getAddress(),
                                    patientDTO.getGender(),
                                    patientDTO.getDateOfBirth(),
                                    patientDTO.getFullName(),
                                    patientDTO.getAvatar());
                    return patientFCustomerDTO;
                })
                .collect(Collectors.toList());
        return patientFCustomerDTOS;
    }
}
