package vn.huydtg.immunizationservice.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import vn.huydtg.immunizationservice.domain.Customer;
import vn.huydtg.immunizationservice.repository.CustomerRepository;
import vn.huydtg.immunizationservice.repository.PatientRepository;
import vn.huydtg.immunizationservice.security.SecurityUtils;
import vn.huydtg.immunizationservice.service.PatientQueryService;
import vn.huydtg.immunizationservice.service.PatientService;
import vn.huydtg.immunizationservice.service.criteria.PatientCriteria;
import vn.huydtg.immunizationservice.service.dto.*;
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

@RestController
@RequestMapping("/api")
public class PatientResource {


    private static final String ENTITY_NAME = "REST_PATIENT";

    @Value("${spring.application.name}")
    private String applicationName;

    private final PatientService patientService;

    private final PatientRepository patientRepository;

    private final PatientQueryService patientQueryService;

    private final CustomerRepository customerRepository;

    public PatientResource(PatientService patientService, PatientRepository patientRepository, PatientQueryService patientQueryService, CustomerRepository customerRepository) {
        this.patientService = patientService;
        this.patientRepository = patientRepository;
        this.patientQueryService = patientQueryService;
        this.customerRepository = customerRepository;
    }

    @PostMapping("/patients")
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO patientDTO) throws URISyntaxException {
        if (patientDTO.getId() != null) {
            throw new BadRequestAlertException("A new patient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientDTO result = patientService.save(patientDTO);
        return ResponseEntity
            .created(new URI("/api/patients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/patients/{id}")
    public ResponseEntity<PatientDTO> updatePatient(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody PatientDTO patientDTO
    ) throws URISyntaxException {
        if (patientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patientDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PatientDTO result = patientService.update(patientDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patientDTO.getId().toString()))
            .body(result);
    }


    @PatchMapping(value = "/patients/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PatientDTO> partialUpdatePatient(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody PatientDTO patientDTO
    ) throws URISyntaxException {
        if (patientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patientDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PatientDTO> result = patientService.partialUpdate(patientDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patientDTO.getId().toString())
        );
    }


    @GetMapping("/patients")
    public ResponseEntity<List<PatientDTO>> getAllPatients(
        PatientCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {

        Page<PatientDTO> page = patientQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/patients/count")
    public ResponseEntity<Long> countPatients(PatientCriteria criteria) {
        return ResponseEntity.ok().body(patientQueryService.countByCriteria(criteria));
    }


    @GetMapping("/patients/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable UUID id) {
        Optional<PatientDTO> patientDTO = patientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patientDTO);
    }


    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/patients/select")
    public ResponseEntity<List<PatientFSelectDTO>> getAllPatientForSelect() {
        List<PatientFSelectDTO> result = patientService.getPatientFSelectDTO();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/customers/{customerId}/patients")
    public ResponseEntity<?> getAllPatientsByCustomerId(@PathVariable UUID customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "customer-id-not-found");
        }
        List<PatientFCustomerDTO> results = patientService.findPatientsByCustomerId(customerId);
        return ResponseEntity.ok().body(results);
    }
    @GetMapping("/customers/token/patients")
    public ResponseEntity<List<PatientFCustomerDTO>> getAllPatientsByUserId() {
        UUID currentUserId = SecurityUtils.getUserId();
        Optional<Customer> customer = customerRepository.findFirstByUserId(currentUserId);
        List<PatientFCustomerDTO> results = new ArrayList<>();
        if (customer.isPresent()) {
            results = patientService.findPatientsByCustomerId(customer.get().getId());
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }

}
