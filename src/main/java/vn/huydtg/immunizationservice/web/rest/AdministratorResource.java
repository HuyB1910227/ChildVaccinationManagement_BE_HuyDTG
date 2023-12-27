package vn.huydtg.immunizationservice.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.huydtg.immunizationservice.domain.Administrator;
import vn.huydtg.immunizationservice.security.SecurityUtils;
import vn.huydtg.immunizationservice.web.rest.utils.HeaderUtil;
import vn.huydtg.immunizationservice.web.rest.utils.ResponseUtil;
import vn.huydtg.immunizationservice.repository.AdministratorRepository;
import vn.huydtg.immunizationservice.service.AdministratorService;
import vn.huydtg.immunizationservice.service.dto.AdministratorDTO;
import vn.huydtg.immunizationservice.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AdministratorResource {


    private static final String ENTITY_NAME = "REST_ADMIN";

    private final AdministratorService administratorService;

    private final AdministratorRepository administratorRepository;

    @Value("${spring.application.name}")
    private String applicationName;

    public AdministratorResource(
        AdministratorService administratorService,
        AdministratorRepository administratorRepository
    ) {
        this.administratorService = administratorService;
        this.administratorRepository = administratorRepository;
    }


    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PostMapping("/administrators")
    public ResponseEntity<AdministratorDTO> createAdministrator(@Valid @RequestBody AdministratorDTO administratorDTO)
        throws URISyntaxException {
        if (administratorDTO.getId() != null) {
            throw new BadRequestAlertException("A new administrator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdministratorDTO result = administratorService.save(administratorDTO);
        return ResponseEntity
            .created(new URI("/api/administrators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PutMapping("/administrators/{id}")
    public ResponseEntity<AdministratorDTO> updateAdministrator(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody AdministratorDTO administratorDTO
    ) throws URISyntaxException {
        if (administratorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, administratorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!administratorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdministratorDTO result = administratorService.update(administratorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, administratorDTO.getId().toString()))
            .body(result);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PatchMapping(value = "/administrators/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdministratorDTO> partialUpdateAdministrator(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody AdministratorDTO administratorDTO
    ) throws URISyntaxException {
        if (administratorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, administratorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!administratorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdministratorDTO> result = administratorService.partialUpdate(administratorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, administratorDTO.getId().toString())
        );
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @GetMapping("/administrators/{id}")
    public ResponseEntity<AdministratorDTO> getAdministrator(@PathVariable UUID id) {

        Optional<AdministratorDTO> administratorDTO = administratorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(administratorDTO);
    }


    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @GetMapping("/administrators/token")
    public ResponseEntity<?> getAdministratorByToken() {

        UUID currentUserId = SecurityUtils.getUserId();

        Optional<AdministratorDTO> administratorDTO = administratorService.findOneByUserId(currentUserId);
        return ResponseUtil.wrapOrNotFound(administratorDTO);
//        return ResponseEntity.ok(currentUserId);
    }

    @PostMapping("/administrators/is-invalid-unique-value")
    public boolean isInvalidUniqueValue(@RequestBody AdministratorDTO administratorDTO) {

        if(administratorDTO.getIdentityCard() != null) {
            Specification<Administrator> spec = hasIdentityCard(administratorDTO.getIdentityCard());
            return administratorRepository.exists(spec);
        }

        if (administratorDTO.getPhone() != null) {
            Specification<Administrator> spec = hasPhone(administratorDTO.getPhone());
            return administratorRepository.exists(spec);
        }

        if(administratorDTO.getEmail() != null) {
            Specification<Administrator> spec = hasEmail(administratorDTO.getEmail());
            return administratorRepository.exists(spec);
        }


        return false;
    }

    public static Specification<Administrator> hasPhone(String phone) {
        return (root, query, criteriaBuilder) -> {
            if (phone == null || phone.isEmpty()) {
                return null;
            }
            return criteriaBuilder.equal(root.get("phone"), phone);
        };
    }

    public static Specification<Administrator> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null || email.isEmpty()) {
                return null;
            }
            return criteriaBuilder.equal(root.get("email"), email);
        };
    }

    public static Specification<Administrator> hasIdentityCard(String identityCard) {
        return (root, query, criteriaBuilder) -> {
            if (identityCard == null || identityCard.isEmpty()) {
                return null;
            }
            return criteriaBuilder.equal(root.get("identityCard"), identityCard);
        };
    }
}
