package vn.huydtg.immunizationservice.web.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import vn.huydtg.immunizationservice.repository.VaccineRepository;
import vn.huydtg.immunizationservice.service.VaccineQueryService;
import vn.huydtg.immunizationservice.service.VaccineService;
import vn.huydtg.immunizationservice.service.criteria.VaccineCriteria;
import vn.huydtg.immunizationservice.service.dto.VaccineDTO;
import vn.huydtg.immunizationservice.service.dto.VaccineFSelectDTO;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api")
public class VaccineResource {


    private static final String ENTITY_NAME = "REST_VACCINE";

    @Value("${spring.application.name}")
    private String applicationName;

    private final VaccineService vaccineService;

    private final VaccineRepository vaccineRepository;

    private final VaccineQueryService vaccineQueryService;

    public VaccineResource(VaccineService vaccineService, VaccineRepository vaccineRepository, VaccineQueryService vaccineQueryService) {
        this.vaccineService = vaccineService;
        this.vaccineRepository = vaccineRepository;
        this.vaccineQueryService = vaccineQueryService;
    }


    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/vaccines")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<VaccineDTO> createVaccine(@Valid @RequestBody VaccineDTO vaccineDTO) throws URISyntaxException {
        if (vaccineDTO.getId() != null) {
            throw new BadRequestAlertException("A new vaccine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VaccineDTO result = vaccineService.save(vaccineDTO);
        return ResponseEntity
            .created(new URI("/api/vaccines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PutMapping("/vaccines/{id}")
    public ResponseEntity<VaccineDTO> updateVaccine(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody VaccineDTO vaccineDTO
    ) throws URISyntaxException {
        if (vaccineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vaccineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vaccineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VaccineDTO result = vaccineService.update(vaccineDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vaccineDTO.getId().toString()))
            .body(result);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PatchMapping(value = "/vaccines/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VaccineDTO> partialUpdateVaccine(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody VaccineDTO vaccineDTO
    ) throws URISyntaxException {
        if (vaccineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vaccineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vaccineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VaccineDTO> result = vaccineService.partialUpdate(vaccineDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vaccineDTO.getId().toString())
        );
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/vaccines")
    public ResponseEntity<List<VaccineDTO>> getAllVaccines(
        VaccineCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {

        Page<VaccineDTO> page = vaccineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/vaccines/count")
    public ResponseEntity<Long> countVaccines(VaccineCriteria criteria) {
        return ResponseEntity.ok().body(vaccineQueryService.countByCriteria(criteria));
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/vaccines/{id}")
    public ResponseEntity<VaccineDTO> getVaccine(@PathVariable UUID id) {
        Optional<VaccineDTO> vaccineDTO = vaccineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vaccineDTO);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/vaccines/{id}")
    public ResponseEntity<Void> deleteVaccine(@PathVariable UUID id) {
        vaccineService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }


    @GetMapping("/vaccines/select")
    public ResponseEntity<List<VaccineFSelectDTO>> getAllVaccineForSelect() {
        List<VaccineFSelectDTO> result = vaccineService.getVaccineFSelectDTO();
        return ResponseEntity.ok().body(result);
    }
}
