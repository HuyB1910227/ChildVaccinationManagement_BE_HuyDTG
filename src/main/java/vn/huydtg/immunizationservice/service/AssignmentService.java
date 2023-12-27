package vn.huydtg.immunizationservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.huydtg.immunizationservice.service.dto.AssignmentDTO;
import vn.huydtg.immunizationservice.service.dto.AssignmentFAppointmentCardDTO;

import java.util.List;
import java.util.Optional;

public interface AssignmentService {

    AssignmentDTO save(AssignmentDTO assignmentDTO);


    AssignmentDTO update(AssignmentDTO assignmentDTO);


    Optional<AssignmentDTO> partialUpdate(AssignmentDTO assignmentDTO);


    Page<AssignmentDTO> findAll(Pageable pageable);


    Optional<AssignmentDTO> findOne(Long id);


    List<AssignmentFAppointmentCardDTO> findAssignmentsByAppointmentCardId(Long appointmentCardId);

    void delete(Long id);
}
