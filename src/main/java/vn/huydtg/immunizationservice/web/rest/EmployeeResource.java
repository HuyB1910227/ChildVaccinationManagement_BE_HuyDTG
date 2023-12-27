package vn.huydtg.immunizationservice.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.UUIDFilter;
import vn.huydtg.immunizationservice.domain.Employee;
import vn.huydtg.immunizationservice.repository.EmployeeRepository;
import vn.huydtg.immunizationservice.security.SecurityUtils;
import vn.huydtg.immunizationservice.service.EmployeeQueryService;
import vn.huydtg.immunizationservice.service.EmployeeService;
import vn.huydtg.immunizationservice.service.criteria.EmployeeCriteria;
import vn.huydtg.immunizationservice.service.dto.*;
import vn.huydtg.immunizationservice.service.mapper.EmployeeMapper;
import vn.huydtg.immunizationservice.web.rest.errors.BadRequestAlertException;
import vn.huydtg.immunizationservice.web.rest.utils.HeaderUtil;
import vn.huydtg.immunizationservice.web.rest.utils.PaginationUtil;
import vn.huydtg.immunizationservice.web.rest.utils.ResponseUtil;
import vn.huydtg.immunizationservice.web.rest.vm.EmployeeVM;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class EmployeeResource {

    private static final String ENTITY_NAME = "REST_EMPLOYEE";

    @Value("${spring.application.name}")
    private String applicationName;

    private final EmployeeService employeeService;

    private final EmployeeRepository employeeRepository;

    private final EmployeeQueryService employeeQueryService;

    private final EmployeeMapper employeeMapper;


    Logger logger = Logger.getLogger(EmployeeResource.class.getName());

    public EmployeeResource(
        EmployeeService employeeService,
        EmployeeRepository employeeRepository,
        EmployeeQueryService employeeQueryService,
        EmployeeMapper employeeMapper
    ) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.employeeQueryService = employeeQueryService;
        this.employeeMapper = employeeMapper;
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'MANAGER')")
    @PostMapping("/employees")
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeVM employeeVM) throws URISyntaxException {

        EmployeeDTO result = employeeService.save(employeeVM);
        return ResponseEntity
            .created(new URI("/api/employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'MANAGER', 'STAFF')")
    @PutMapping("/employees/{id}")
    public ResponseEntity<?> updateEmployee(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody EmployeeNormalDTO employeeNormalDTO
    ) throws URISyntaxException {
        if (employeeNormalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeNormalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employeeNormalDTO.getId());
        employeeDTO.setEmployeeId(employeeNormalDTO.getEmployeeId());
        employeeDTO.setPhone(employeeNormalDTO.getPhone());
        employeeDTO.setEmail(employeeNormalDTO.getEmail());
        employeeDTO.setFullName(employeeNormalDTO.getFullName());
        employeeDTO.setIdentityCard(employeeNormalDTO.getIdentityCard());
        employeeDTO.setAddress(employeeNormalDTO.getAddress());
        employeeDTO.setGender(employeeNormalDTO.getGender());
        employeeDTO.setDateOfBirth(employeeNormalDTO.getDateOfBirth());
        employeeDTO.setAvatar(employeeNormalDTO.getAvatar());
        employeeDTO.setImmunizationUnit(employeeNormalDTO.getImmunizationUnit());
        employeeDTO.setUser(employeeNormalDTO.getUser());
        EmployeeDTO result = employeeService.update(employeeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeDTO.getId().toString()))
            .body(result);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'MANAGER', 'STAFF')")
    @PatchMapping(value = "/employees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeDTO> partialUpdateEmployee(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody EmployeeDTO employeeDTO
    ) throws URISyntaxException {
        if (employeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeDTO> result = employeeService.partialUpdate(employeeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeDTO.getId().toString())
        );
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'MANAGER')")
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(
        EmployeeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        boolean isEmployee = SecurityUtils.hasCurrentUserAnyOfAuthorities("MANAGER", "PHYSICIAN", "STAFF");
        if(isEmployee) {
            UUID currentUserImmunizationUnitId = SecurityUtils.getImmunizationUnitId();
            Filter<UUID> immunizationUnitIdFilter = new UUIDFilter().setEquals(currentUserImmunizationUnitId);
            criteria.setImmunizationUnitId((UUIDFilter) immunizationUnitIdFilter);
        }
        Page<EmployeeDTO> page = employeeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'MANAGER')")
    @GetMapping("/employees/count")
    public ResponseEntity<Long> countEmployees(EmployeeCriteria criteria) {
        return ResponseEntity.ok().body(employeeQueryService.countByCriteria(criteria));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'MANAGER')")
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable UUID id) {
        Optional<EmployeeDTO> employeeDTO = employeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeDTO);
    }

    @GetMapping("/employees/select")
    public ResponseEntity<List<EmployeeFSelectDTO>> getAllEmployeeForSelect() {
        List<EmployeeFSelectDTO> result = employeeService.getEmployeeFSelectDTO();
        return ResponseEntity.ok().body(result);
    }


    @GetMapping("/employees/token")
    public ResponseEntity<EmployeeDTO> getAdministratorByToken() {
        UUID currentUserId = SecurityUtils.getUserId();
        Optional<EmployeeDTO> employeeDTO = employeeService.findOneByUserId(currentUserId);
        return ResponseUtil.wrapOrNotFound(employeeDTO);
    }
    @PostMapping("/employees/is-invalid-unique-value")
    public boolean isInvalidUniqueValue(@RequestBody EmployeeDTO employeeDTO) {
        logger.info("employee: " + employeeDTO);

        if(employeeDTO.getIdentityCard() != null) {
            Specification<Employee> spec = employeeQueryService.hasIdentityCard(employeeDTO.getIdentityCard());
            return employeeRepository.exists(spec);
        }

        //not use !employeeDTO.getPhone().isEmpty() &&  in string
        if (employeeDTO.getPhone() != null) {
            Specification<Employee> spec = employeeQueryService.hasPhone(employeeDTO.getPhone());
            return employeeRepository.exists(spec);
        }

        if(employeeDTO.getEmail() != null) {
            Specification<Employee> spec = employeeQueryService.hasEmail(employeeDTO.getEmail());
            return employeeRepository.exists(spec);
        }


       return false;
    }

    @GetMapping("/employees/{id}/account")
    public ResponseEntity<EmployeeUserDTO> getAccountInformation(@PathVariable UUID id) {
        Optional<EmployeeDTO> employeeDTO = employeeService.findOne(id);
        if (employeeDTO.isEmpty()) {
            throw new BadRequestAlertException("Not found employee", ENTITY_NAME, "employeenotexist");
        }

        UserDTO userDTO = employeeDTO.get().getUser();
        EmployeeUserDTO employeeUserDTO = new EmployeeUserDTO(employeeDTO.get(), userDTO);

        return ResponseEntity.ok(employeeUserDTO);
    }

    @GetMapping("/employees/find-physician/by-token")
    public ResponseEntity<?> getPhysicianInImmunizationUnit() {
        UUID currentImmunizationUnitId = SecurityUtils.getImmunizationUnitId();
        if (currentImmunizationUnitId == null) {
            throw new BadRequestAlertException("Not found immunization unit", ENTITY_NAME, "immunizationunitnotexist");
        }
        List<EmployeeFSelectDTO> employeeFSelectDTOS = employeeRepository.finePhysicianInImmunizationUnit(currentImmunizationUnitId).stream().map(
                (employee -> {
                    EmployeeFSelectDTO employeeFSelectDTO = new EmployeeFSelectDTO();
                    employeeFSelectDTO.setId(employee.getId());
                    employeeFSelectDTO.setEmployeeId(employee.getEmployeeId());
                    employeeFSelectDTO.setFullName(employee.getFullName());
                    employeeFSelectDTO.setPhone(employee.getPhone());
                    return employeeFSelectDTO;
                })
        ).toList();
        return ResponseEntity.ok(employeeFSelectDTOS);
    }

}
