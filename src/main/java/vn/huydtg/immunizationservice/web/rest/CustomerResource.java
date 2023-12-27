package vn.huydtg.immunizationservice.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import vn.huydtg.immunizationservice.domain.Customer;
import vn.huydtg.immunizationservice.security.SecurityUtils;
import vn.huydtg.immunizationservice.service.dto.*;
import vn.huydtg.immunizationservice.web.rest.utils.HeaderUtil;
import vn.huydtg.immunizationservice.web.rest.utils.PaginationUtil;
import vn.huydtg.immunizationservice.web.rest.utils.ResponseUtil;
import vn.huydtg.immunizationservice.repository.CustomerRepository;
import vn.huydtg.immunizationservice.service.CustomerQueryService;
import vn.huydtg.immunizationservice.service.CustomerService;
import vn.huydtg.immunizationservice.service.criteria.CustomerCriteria;
import vn.huydtg.immunizationservice.web.rest.errors.BadRequestAlertException;
import vn.huydtg.immunizationservice.web.rest.vm.CustomerVM;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;


@RestController
@RequestMapping("/api")
public class CustomerResource {


    private static final String ENTITY_NAME = "REST_CUSTOMER";

    @Value("${spring.application.name}")
    private String applicationName;
    private final CustomerService customerService;

    private final CustomerRepository customerRepository;

    private final CustomerQueryService customerQueryService;


    public CustomerResource(
        CustomerService customerService,
        CustomerRepository customerRepository,
        CustomerQueryService customerQueryService

    ) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.customerQueryService = customerQueryService;
    }


    @PostMapping("/customers")
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerVM customerVM) throws URISyntaxException {
        CustomerDTO result = customerService.save(customerVM);
        return ResponseEntity
            .created(new URI("/api/customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody CustomerDTO customerDTO
    ) throws URISyntaxException {
        if (customerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomerDTO result = customerService.update(customerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerDTO.getId().toString()))
            .body(result);
    }


    @PatchMapping(value = "/customers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CustomerDTO> partialUpdateCustomer(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody CustomerDTO customerDTO
    ) throws URISyntaxException {
        if (customerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomerDTO> result = customerService.partialUpdate(customerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerDTO.getId().toString())
        );
    }


    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(
        CustomerCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        Page<CustomerDTO> page = customerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/customers/count")
    public ResponseEntity<Long> countCustomers(CustomerCriteria criteria) {
        return ResponseEntity.ok().body(customerQueryService.countByCriteria(criteria));
    }


    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable UUID id) {
        Optional<CustomerDTO> customerDTO = customerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerDTO);
    }
    @GetMapping("/customers/select")
    public ResponseEntity<List<CustomerFSelectDTO>> getAllCustomerForSelect() {
        List<CustomerFSelectDTO> result = customerService.getCustomerFSelectDTO();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/customers/token/profile")
    public ResponseEntity<CustomerDTO> getAllPatientsByUserId() {
        UUID currentUserId = SecurityUtils.getUserId();
        Optional<Customer> customer = customerRepository.findFirstByUserId(currentUserId);
        Optional<CustomerDTO> customerDTO = customerService.findOne(customer.get().getId());
        return ResponseUtil.wrapOrNotFound(customerDTO);
    }

    @PostMapping("/customers/is-invalid-unique-value")
    public boolean isInvalidUniqueValue(@RequestBody CustomerDTO customerDTO) {
        if(customerDTO.getIdentityCard() != null) {
            Specification<Customer> spec = customerQueryService.hasIdentityCard(customerDTO.getIdentityCard());
            return customerRepository.exists(spec);
        }
        if (customerDTO.getPhone() != null) {
            Specification<Customer> spec = customerQueryService.hasPhone(customerDTO.getPhone());
            return customerRepository.exists(spec);
        }
        if(customerDTO.getEmail() != null) {
            Specification<Customer> spec = customerQueryService.hasEmail(customerDTO.getEmail());
            return customerRepository.exists(spec);
        }
        return false;
    }

    @GetMapping("/customers/{id}/account")
    public ResponseEntity<CustomerUserDTO> getAccountInformation(@PathVariable UUID id) {
        Optional<CustomerDTO> customerDTO = customerService.findOne(id);
        if (customerDTO.isEmpty()) {
            throw new BadRequestAlertException("Not found customer", ENTITY_NAME, "customernotexist");
        }

        UserDTO userDTO = customerDTO.get().getUser();
        CustomerUserDTO customerUserDTO = new CustomerUserDTO(customerDTO.get(), userDTO);
        return ResponseEntity.ok(customerUserDTO);
    }
}
