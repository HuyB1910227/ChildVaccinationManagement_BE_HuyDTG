package vn.huydtg.immunizationservice.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import vn.huydtg.immunizationservice.repository.AppointmentCardRepository;
import vn.huydtg.immunizationservice.repository.AssignmentRepository;
import vn.huydtg.immunizationservice.service.AssignmentQueryService;
import vn.huydtg.immunizationservice.service.AssignmentService;
import vn.huydtg.immunizationservice.service.VaccineLotService;
import vn.huydtg.immunizationservice.service.criteria.AssignmentCriteria;
import vn.huydtg.immunizationservice.service.dto.AssignmentDTO;
import vn.huydtg.immunizationservice.service.dto.AssignmentFAppointmentCardDTO;
import vn.huydtg.immunizationservice.service.dto.AssignmentInjectionForDiaryDTO;
import vn.huydtg.immunizationservice.service.dto.VaccineLotDTO;
import vn.huydtg.immunizationservice.service.mapper.AssignmentMapper;
import vn.huydtg.immunizationservice.web.rest.errors.BadRequestAlertException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import vn.huydtg.immunizationservice.web.rest.utils.HeaderUtil;
import vn.huydtg.immunizationservice.web.rest.utils.PaginationUtil;
import vn.huydtg.immunizationservice.web.rest.utils.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class AssignmentResource {

    Logger logger = Logger.getLogger(AssignmentResource.class.getName());
    private static final String ENTITY_NAME = "REST_ASSIGNMENT";

    @Value("${spring.application.name}")
    private String applicationName;

    private final AssignmentService assignmentService;

    private final AssignmentRepository assignmentRepository;

    private final AssignmentQueryService assignmentQueryService;


    private final AppointmentCardRepository appointmentCardRepository;


    private final VaccineLotService vaccineLotService;


    private final AssignmentMapper assignmentMapper;
    public AssignmentResource(
        AssignmentService assignmentService,
        AssignmentRepository assignmentRepository,
        AssignmentQueryService assignmentQueryService,
        AppointmentCardRepository appointmentCardRepository,
        AssignmentMapper assignmentMapper,
        VaccineLotService vaccineLotService
    ) {
        this.assignmentService = assignmentService;
        this.assignmentRepository = assignmentRepository;
        this.assignmentQueryService = assignmentQueryService;
        this.appointmentCardRepository = appointmentCardRepository;
        this.assignmentMapper = assignmentMapper;
        this.vaccineLotService = vaccineLotService;
    }


    @PostMapping("/assignments")
    public ResponseEntity<AssignmentDTO> createAssignment(@Valid @RequestBody AssignmentDTO assignmentDTO) throws URISyntaxException {
        if (assignmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new assignment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssignmentDTO result = assignmentService.save(assignmentDTO);
        return ResponseEntity
            .created(new URI("/api/assignments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/assignments/{id}")
    public ResponseEntity<AssignmentDTO> updateAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AssignmentDTO assignmentDTO
    ) throws URISyntaxException {
        if (assignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AssignmentDTO result = assignmentService.update(assignmentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assignmentDTO.getId().toString()))
            .body(result);
    }


    @PatchMapping(value = "/assignments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssignmentDTO> partialUpdateAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AssignmentDTO assignmentDTO
    ) throws URISyntaxException {
        if (assignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssignmentDTO> result = assignmentService.partialUpdate(assignmentDTO);
        if (result.isEmpty()) {
            throw new BadRequestAlertException("Etity update error", ENTITY_NAME, "partialupdateerror");
        }
        //if status == 1 => vaccine lot change quantity used
        if (assignmentDTO.getStatus() == 1) {
            logger.info("dô");
            VaccineLotDTO vaccineLotDTO = new VaccineLotDTO();
            vaccineLotDTO.setId(result.get().getVaccineLot().getId());
            vaccineLotDTO.setQuantityUsed(result.get().getVaccineLot().getQuantityUsed() + 1);
            logger.info("vaccine lot dto"  + vaccineLotDTO);
            Optional<VaccineLotDTO> updatedVaccineLot = vaccineLotService.partialUpdate(vaccineLotDTO);
            logger.info("vaccine lot updated dto"  + updatedVaccineLot);
            return ResponseUtil.wrapOrNotFound(
                    result,
                    HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assignmentDTO.getId().toString())
            );
        }


        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assignmentDTO.getId().toString())
        );
    }


    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentDTO>> getAllAssignments(
        AssignmentCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {

        Page<AssignmentDTO> page = assignmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/appointment-cards/{appointmentCardId}/assignments")
    public ResponseEntity<?> getAllAssignmentsByAppointmentCardId(@PathVariable Long appointmentCardId) {
        if (!appointmentCardRepository.existsById(appointmentCardId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "appointment-card-id-not-found");
        }

        List<AssignmentFAppointmentCardDTO> results = assignmentService.findAssignmentsByAppointmentCardId(appointmentCardId);
        return ResponseEntity.ok().body(results);
    }


    @GetMapping("/assignments/count")
    public ResponseEntity<Long> countAssignments(AssignmentCriteria criteria) {
        return ResponseEntity.ok().body(assignmentQueryService.countByCriteria(criteria));
    }


    @GetMapping("/assignments/{id}")
    public ResponseEntity<AssignmentDTO> getAssignment(@PathVariable Long id) {
        Optional<AssignmentDTO> assignmentDTO = assignmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assignmentDTO);
    }

    @DeleteMapping("/assignments/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        assignmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }


    @GetMapping("/assignments/injection-for-diary/by-patient/{patientId}")
    public ResponseEntity<?> getAssignment(@PathVariable UUID patientId) {
        List<Object[]> resultList = assignmentRepository.getAllAssigmentForInjectionDiary(patientId);
        List<AssignmentInjectionForDiaryDTO> assignmentInjectionForDiaryDTOList = new ArrayList<>();
        for (Object[] result : resultList) {
            if (result.length >= 2) {
                Long assignmentId = (Long) result[0];
                Long diseaseId = (Long) result[1];
                AssignmentInjectionForDiaryDTO assignmentInjectionForDiaryDTO = new AssignmentInjectionForDiaryDTO(assignmentId, diseaseId);
                assignmentInjectionForDiaryDTOList.add(assignmentInjectionForDiaryDTO);
            }
        }
        return ResponseEntity.ok().body(assignmentInjectionForDiaryDTOList);
    }
}
