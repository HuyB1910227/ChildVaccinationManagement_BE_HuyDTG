package vn.huydtg.immunizationservice.web.rest;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.exceptions.BadPasswordException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.UUIDFilter;
import vn.huydtg.immunizationservice.domain.AppointmentCard;
import vn.huydtg.immunizationservice.domain.Assignment;
import vn.huydtg.immunizationservice.domain.Customer;
import vn.huydtg.immunizationservice.repository.AppointmentCardRepository;
import vn.huydtg.immunizationservice.repository.AssignmentRepository;
import vn.huydtg.immunizationservice.repository.CustomerRepository;
import vn.huydtg.immunizationservice.security.SecurityUtils;
import vn.huydtg.immunizationservice.service.AppointmentCardQueryService;
import vn.huydtg.immunizationservice.service.AppointmentCardService;
import vn.huydtg.immunizationservice.service.EmployeeService;
import vn.huydtg.immunizationservice.service.criteria.AppointmentCardCriteria;
import vn.huydtg.immunizationservice.service.dto.*;
import vn.huydtg.immunizationservice.service.mapper.AppointmentCardMapper;
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
import vn.huydtg.immunizationservice.web.rest.utils.PDFUtils;
import vn.huydtg.immunizationservice.web.rest.utils.PaginationUtil;
import vn.huydtg.immunizationservice.web.rest.utils.ResponseUtil;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AppointmentCardResource {


    private static final String ENTITY_NAME = "REST_APPOINTMENT";

    @Value("${spring.application.name}")
    private String applicationName;

    private final AppointmentCardService appointmentCardService;

    private final AppointmentCardRepository appointmentCardRepository;

    private final AppointmentCardMapper appointmentCardMapper;

    private final AppointmentCardQueryService appointmentCardQueryService;

    private final EmployeeService employeeService;

    private final CustomerRepository customerRepository;

    private final AssignmentRepository assignmentRepository;

    private final AssignmentMapper assignmentMapper;

    Logger logger = LoggerFactory.getLogger(AppointmentCardResource.class);
    public AppointmentCardResource(
        AppointmentCardService appointmentCardService,
        AppointmentCardRepository appointmentCardRepository,
        AppointmentCardQueryService appointmentCardQueryService,
        EmployeeService employeeService,
        AppointmentCardMapper appointmentCardMapper,
        CustomerRepository customerRepository,
        AssignmentRepository assignmentRepository,
        AssignmentMapper assignmentMapper
    ) {
        this.appointmentCardService = appointmentCardService;
        this.appointmentCardRepository = appointmentCardRepository;
        this.appointmentCardQueryService = appointmentCardQueryService;
        this.employeeService = employeeService;
        this.appointmentCardMapper = appointmentCardMapper;
        this.customerRepository = customerRepository;
        this.assignmentRepository = assignmentRepository;
        this.assignmentMapper = assignmentMapper;
    }


    @PostMapping("/appointment-cards")
    public ResponseEntity<AppointmentCardDTO> createAppointmentCard(@Valid @RequestBody AppointmentCardDTO appointmentCardDTO)
        throws URISyntaxException {
        if (appointmentCardDTO.getId() != null) {
            throw new BadRequestAlertException("A new appointmentCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppointmentCardDTO result = appointmentCardService.save(appointmentCardDTO);
        return ResponseEntity
            .created(new URI("/api/appointment-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/appointment-cards/{id}")
    public ResponseEntity<AppointmentCardDTO> updateAppointmentCard(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AppointmentCardDTO appointmentCardDTO
    ) throws URISyntaxException {
        if (appointmentCardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appointmentCardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appointmentCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        if (appointmentCardDTO.getEmployee() == null) {

        }
        if(appointmentCardDTO.getStatus() < 1) {
            UUID currentUserId = SecurityUtils.getUserId();
            Optional<EmployeeDTO> employeeDTO = employeeService.findOneByUserId(currentUserId);
            appointmentCardDTO.setEmployee(employeeDTO.orElse(null));
        }
        AppointmentCardDTO result = appointmentCardService.update(appointmentCardDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appointmentCardDTO.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/appointment-cards/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppointmentCardDTO> partialUpdateAppointmentCard(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AppointmentCardDTO appointmentCardDTO
    ) throws URISyntaxException {
        if (appointmentCardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appointmentCardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appointmentCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppointmentCardDTO> result = appointmentCardService.partialUpdate(appointmentCardDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appointmentCardDTO.getId().toString())
        );
    }



    @PatchMapping(value = "/appointment-cards/complete-diagnosis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppointmentCardDTO> partialUpdateDiagnosisAppointmentCard(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody AppointmentCardDTO appointmentCardDTO
    ) throws URISyntaxException {
        if (appointmentCardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appointmentCardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appointmentCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        //status = 2 => save employee id
        Optional<AppointmentCardDTO> selectedAppointmentCardDTO = appointmentCardRepository.findById(id).map(appointmentCardMapper::toDto);
        if (selectedAppointmentCardDTO.isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        AppointmentCardDTO inputDTO = selectedAppointmentCardDTO.get();
        inputDTO.setStatus(appointmentCardDTO.getStatus());
        if(appointmentCardDTO.getStatus() != null && appointmentCardDTO.getStatus() < 3) {
            UUID currentUserId = SecurityUtils.getUserId();
            Optional<EmployeeDTO> employeeDTO = employeeService.findOneByUserId(currentUserId);
            if (employeeDTO.isPresent()) {
                EmployeeDTO newEmployee = new EmployeeDTO();
                newEmployee.setId(employeeDTO.get().getId());
                inputDTO.setEmployee(newEmployee);
            }
        }
        logger.info(inputDTO.toString());
        AppointmentCardDTO result = appointmentCardService.update(inputDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appointmentCardDTO.getId().toString()))
                .body(result);
    }


    @GetMapping("/appointment-cards")
    public ResponseEntity<List<AppointmentCardDTO>> getAllAppointmentCardsInImmunizationUnit(
        AppointmentCardCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {

        boolean isEmployee = SecurityUtils.hasCurrentUserAnyOfAuthorities("MANAGER", "PHYSICIAN", "STAFF");
        if(isEmployee) {
            UUID currentUserImmunizationUnitId = SecurityUtils.getImmunizationUnitId();
            Filter<UUID> immunizationUnitIdFilter = new UUIDFilter().setEquals(currentUserImmunizationUnitId);
            criteria.setImmunizationUnitId((UUIDFilter) immunizationUnitIdFilter);
        }
        Page<AppointmentCardDTO> page = appointmentCardQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/appointment-cards/normal")
    public ResponseEntity<List<AppointmentCardDTO>> getAllAppointmentCards(
            AppointmentCardCriteria criteria,
            @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        Page<AppointmentCardDTO> page = appointmentCardQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/appointment-cards/count")
    public ResponseEntity<Long> countAppointmentCards(AppointmentCardCriteria criteria) {
        return ResponseEntity.ok().body(appointmentCardQueryService.countByCriteria(criteria));
    }


    @GetMapping("/appointment-cards/{id}")
    public ResponseEntity<AppointmentCardDTO> getAppointmentCard(@PathVariable Long id) {
        Optional<AppointmentCardDTO> appointmentCardDTO = appointmentCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appointmentCardDTO);
    }

    @DeleteMapping("/appointment-cards/{id}")
    public ResponseEntity<Void> deleteAppointmentCard(@PathVariable Long id) {
        appointmentCardService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    @GetMapping("/appointment-cards/select")
    public ResponseEntity<List<AppointmentCardFSelectDTO>> getAllAppointmentCardForSelect() {
        List<AppointmentCardFSelectDTO> result = appointmentCardService.getAppointmentCardFSelectDTO();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/appointment-cards/schedule-expected")
    public ResponseEntity<List<AppointmentCardDTO>> getAllAppointmentCardExpectedForCustomer() {
        UUID currentUserId = SecurityUtils.getUserId();
        Optional<Customer> currentCustomer = customerRepository.findFirstByUserId(currentUserId);
        if (currentCustomer.isEmpty()) {
            throw new BadRequestAlertException("Not found customer", "REST_CUSTOMER", "customerdoesnotexist");
        }
        Instant startOfToday = Instant.now().truncatedTo(ChronoUnit.DAYS);
        Instant endOfNextWeek = startOfToday.plus(7, ChronoUnit.DAYS);
        List<AppointmentCardDTO> result = appointmentCardService.findAppointmentCardWScheduleExpectedFCustomer(currentCustomer.get().getId(), startOfToday, endOfNextWeek);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/appointment-cards/schedule-registered")
    public ResponseEntity<List<AppointmentCardDTO>> getAllAppointmentCardRegisteredForCustomer() {
        UUID currentUserId = SecurityUtils.getUserId();
        Optional<Customer> currentCustomer = customerRepository.findFirstByUserId(currentUserId);
        if (currentCustomer.isEmpty()) {
            throw new BadRequestAlertException("Not found customer", "REST_CUSTOMER", "customerdoesnotexist");
        }
        Instant startOfToday = Instant.now().truncatedTo(ChronoUnit.DAYS);
        List<AppointmentCardDTO> result = appointmentCardService.findAppointmentCardWScheduleRegisteredFCustomer(currentCustomer.get().getId(), startOfToday);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/appointment-cards/appointment-card-pdf/{appointmentCardId}")
    public ResponseEntity<byte[]> exportAssignmentPdf(@PathVariable Long appointmentCardId) throws IOException, DocumentException {

        Optional<AppointmentCardDTO> appointmentCardDTO = appointmentCardService.findOne(appointmentCardId);
        if (appointmentCardDTO.isEmpty()) {
            throw new BadRequestAlertException("Not found appointment card", ENTITY_NAME, "notfoundappointmentcard");
        }
        List<Assignment> assignments = assignmentRepository.findAllByAppointmentCardId(appointmentCardId);
        List<AssignmentDTO> assignmentDTOList = assignments.stream().map(assignmentMapper::toDto).toList();
        if (assignmentDTOList.isEmpty()) {
            throw new BadRequestAlertException("Empty assignment list", ENTITY_NAME, "emptyassignmentlist");
        }
        UUID currentUserId = SecurityUtils.getUserId();
        Optional<EmployeeDTO> employeeDTO = employeeService.findOneByUserId(currentUserId);
        if (employeeDTO.isEmpty()) {
            throw new BadRequestAlertException("Not found employee", ENTITY_NAME, "notfoundemployee");
        }
        ByteArrayOutputStream pdfStream = PDFUtils.generateAssignmentPdfStream(appointmentCardDTO.get(), assignmentDTOList, employeeDTO.get());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=query_results.pdf");
        headers.setContentLength(pdfStream.size());
        return new ResponseEntity<>(pdfStream.toByteArray(), headers, HttpStatus.OK);
    }

}
