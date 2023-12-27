package vn.huydtg.immunizationservice.service.impl;

import vn.huydtg.immunizationservice.domain.Assignment;
import vn.huydtg.immunizationservice.repository.AssignmentRepository;
import vn.huydtg.immunizationservice.service.AssignmentService;
import vn.huydtg.immunizationservice.service.dto.AssignmentDTO;
import vn.huydtg.immunizationservice.service.dto.AssignmentFAppointmentCardDTO;
import vn.huydtg.immunizationservice.service.mapper.AssignmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

    private final Logger log = LoggerFactory.getLogger(AssignmentServiceImpl.class);

    private final AssignmentRepository assignmentRepository;

    private final AssignmentMapper assignmentMapper;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, AssignmentMapper assignmentMapper) {
        this.assignmentRepository = assignmentRepository;
        this.assignmentMapper = assignmentMapper;
    }

    @Override
    public AssignmentDTO save(AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentMapper.toEntity(assignmentDTO);
        assignment = assignmentRepository.save(assignment);
        return assignmentMapper.toDto(assignment);
    }

    @Override
    public AssignmentDTO update(AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentMapper.toEntity(assignmentDTO);
        assignment = assignmentRepository.save(assignment);
        return assignmentMapper.toDto(assignment);
    }

    @Override
    public Optional<AssignmentDTO> partialUpdate(AssignmentDTO assignmentDTO) {
        return assignmentRepository
            .findById(assignmentDTO.getId())
            .map(existingAssignment -> {
                assignmentMapper.partialUpdate(existingAssignment, assignmentDTO);

                return existingAssignment;
            })
            .map(assignmentRepository::save)
            .map(assignmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AssignmentDTO> findAll(Pageable pageable) {
        return assignmentRepository.findAll(pageable).map(assignmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AssignmentDTO> findOne(Long id) {
        return assignmentRepository.findById(id).map(assignmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        assignmentRepository.deleteById(id);
    }

    @Override
    public List<AssignmentFAppointmentCardDTO> findAssignmentsByAppointmentCardId(Long appointmentCardId) {
        List<AssignmentFAppointmentCardDTO> assignmentDTOs = assignmentRepository
                .findAllByAppointmentCardId(appointmentCardId)
                .stream()
                .map(assignmentMapper::toDto)
                .map(assignment -> {
                    AssignmentFAppointmentCardDTO assignmentDTO = new AssignmentFAppointmentCardDTO();
                    assignmentDTO.setId(assignment.getId());
                    assignmentDTO.setRoute(assignment.getRoute());
                    assignmentDTO.setVaccineLot(assignment.getVaccineLot());
                    assignmentDTO.setInjectionTime(assignment.getInjectionTime());
                    assignmentDTO.setStatus(assignment.getStatus());
                    assignmentDTO.setPrice(assignment.getPrice());
                    assignmentDTO.setInjectionCompletionTime(assignment.getInjectionCompletionTime());
                    assignmentDTO.setNote(assignment.getNote());
                    assignmentDTO.setNextAvailableInjectionDate(assignment.getNextAvailableInjectionDate());
                    assignmentDTO.setDosage(assignment.getDosage());
                    return assignmentDTO;
                })
                .collect(Collectors.toList());
        return assignmentDTOs;
    }
}
