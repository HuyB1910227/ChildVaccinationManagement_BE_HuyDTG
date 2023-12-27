package vn.huydtg.immunizationservice.service.impl;

import vn.huydtg.immunizationservice.domain.Customer;
import vn.huydtg.immunizationservice.domain.History;
import vn.huydtg.immunizationservice.domain.Patient;
import vn.huydtg.immunizationservice.repository.CustomerRepository;
import vn.huydtg.immunizationservice.repository.HistoryRepository;
import vn.huydtg.immunizationservice.repository.PatientRepository;
import vn.huydtg.immunizationservice.security.SecurityUtils;
import vn.huydtg.immunizationservice.service.HistoryService;
import vn.huydtg.immunizationservice.service.dto.HistoryDTO;
import vn.huydtg.immunizationservice.service.dto.HistoryFCustomerDTO;
import vn.huydtg.immunizationservice.service.dto.PatientFCustomerDTO;
import vn.huydtg.immunizationservice.service.mapper.HistoryMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {


    private final HistoryRepository historyRepository;

    private final HistoryMapper historyMapper;

    private final CustomerRepository customerRepository;

    private final PatientRepository patientRepository;

    public HistoryServiceImpl(HistoryRepository historyRepository, HistoryMapper historyMapper, CustomerRepository customerRepository, PatientRepository patientRepository) {
        this.historyRepository = historyRepository;
        this.historyMapper = historyMapper;
        this.customerRepository = customerRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public HistoryDTO save(HistoryDTO historyDTO) {
        History history = historyMapper.toEntity(historyDTO);
        history = historyRepository.save(history);
        return historyMapper.toDto(history);
    }

    @Override
    public HistoryDTO update(HistoryDTO historyDTO) {
        History history = historyMapper.toEntity(historyDTO);
        history = historyRepository.save(history);
        return historyMapper.toDto(history);
    }

    @Override
    public Optional<HistoryDTO> partialUpdate(HistoryDTO historyDTO) {

        return historyRepository
            .findById(historyDTO.getId())
            .map(existingHistory -> {
                historyMapper.partialUpdate(existingHistory, historyDTO);

                return existingHistory;
            })
            .map(historyRepository::save)
            .map(historyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HistoryDTO> findAll(Pageable pageable) {
        return historyRepository.findAll(pageable).map(historyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HistoryDTO> findOne(Long id) {
        return historyRepository.findById(id).map(historyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        historyRepository.deleteById(id);
    }

    public  List<HistoryFCustomerDTO> getAllHistoriesByToken(){
        UUID currentUserId = SecurityUtils.getUserId();
        List<HistoryFCustomerDTO> result= new ArrayList<>();
        Optional<Customer> customer = customerRepository.findFirstByUserId(currentUserId);
        List<Patient> patients = new ArrayList<>();
        if (customer.isPresent()) {
            patients = patientRepository.findAllByCustomerId(customer.get().getId());
            result  = patients.stream()
                    .flatMap(patient -> historyRepository.findAllByPatientId(patient.getId()).stream())
                    .map(history -> {
                        PatientFCustomerDTO patientFCustomerDTO = new PatientFCustomerDTO(history.getPatient().getId(), history.getPatient().getAddress(), history.getPatient().getGender(), history.getPatient().getDateOfBirth(), history.getPatient().getFullName(), history.getPatient().getAvatar());
                        return new HistoryFCustomerDTO(history.getId(), history.getVaccinationDate(), history.getStatusAfterInjection(), patientFCustomerDTO);
                    }).collect(Collectors.toList());
        }
        return result;
    }
}
