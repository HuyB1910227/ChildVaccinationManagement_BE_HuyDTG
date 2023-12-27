package vn.huydtg.immunizationservice.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import vn.huydtg.immunizationservice.domain.ImmunizationUnit;
import vn.huydtg.immunizationservice.repository.ImmunizationUnitRepository;
import vn.huydtg.immunizationservice.service.ImmunizationUnitQueryService;
import vn.huydtg.immunizationservice.service.ImmunizationUnitService;
import vn.huydtg.immunizationservice.service.criteria.ImmunizationUnitCriteria;
import vn.huydtg.immunizationservice.service.dto.ImmunizationUnitDTO;
import vn.huydtg.immunizationservice.service.dto.ImmunizationUnitFSelectDTO;
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
public class ImmunizationUnitResource {


    private static final String ENTITY_NAME = "REST_IMMUNIZATION_UNIT";

    @Value("${spring.application.name}")
    private String applicationName;

    private final ImmunizationUnitService immunizationUnitService;

    private final ImmunizationUnitRepository immunizationUnitRepository;

    private final ImmunizationUnitQueryService immunizationUnitQueryService;



    public ImmunizationUnitResource(
        ImmunizationUnitService immunizationUnitService,
        ImmunizationUnitRepository immunizationUnitRepository,
        ImmunizationUnitQueryService immunizationUnitQueryService

    ) {
        this.immunizationUnitService = immunizationUnitService;
        this.immunizationUnitRepository = immunizationUnitRepository;
        this.immunizationUnitQueryService = immunizationUnitQueryService;

    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PostMapping("/immunization-units")
    public ResponseEntity<ImmunizationUnitDTO> createImmunizationUnit(@Valid @RequestBody ImmunizationUnitDTO immunizationUnitDTO)
        throws URISyntaxException {
        if (immunizationUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new immunizationUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImmunizationUnitDTO result = immunizationUnitService.save(immunizationUnitDTO);
        return ResponseEntity
            .created(new URI("/api/immunization-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PutMapping("/immunization-units/{id}")
    public ResponseEntity<ImmunizationUnitDTO> updateImmunizationUnit(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ImmunizationUnitDTO immunizationUnitDTO
    ) throws URISyntaxException {
        if (immunizationUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, immunizationUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!immunizationUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ImmunizationUnitDTO result = immunizationUnitService.update(immunizationUnitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, immunizationUnitDTO.getId().toString()))
            .body(result);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('MANAGER')")
    @PatchMapping(value = "/immunization-units/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ImmunizationUnitDTO> partialUpdateImmunizationUnit(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ImmunizationUnitDTO immunizationUnitDTO
    ) throws URISyntaxException {
        if (immunizationUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, immunizationUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!immunizationUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ImmunizationUnitDTO> result = immunizationUnitService.partialUpdate(immunizationUnitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, immunizationUnitDTO.getId().toString())
        );
    }


    @GetMapping("/immunization-units")
    public ResponseEntity<List<ImmunizationUnitDTO>> getAllImmunizationUnits(
        ImmunizationUnitCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {

        Page<ImmunizationUnitDTO> page = immunizationUnitQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/immunization-units/count")
    public ResponseEntity<Long> countImmunizationUnits(ImmunizationUnitCriteria criteria) {
        return ResponseEntity.ok().body(immunizationUnitQueryService.countByCriteria(criteria));
    }


    @GetMapping("/immunization-units/{id}")
    public ResponseEntity<ImmunizationUnitDTO> getImmunizationUnit(@PathVariable UUID id) {
        Optional<ImmunizationUnitDTO> immunizationUnitDTO = immunizationUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(immunizationUnitDTO);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @DeleteMapping("/immunization-units/{id}")
    public ResponseEntity<Void> deleteImmunizationUnit(@PathVariable UUID id) {
        immunizationUnitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/immunization-units/select")
    public ResponseEntity<List<ImmunizationUnitFSelectDTO>> getAllImmunizationUnitForSelect() {
        List<ImmunizationUnitFSelectDTO> result = immunizationUnitService.getImmunizationUnitsFSelectDTO();
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/immunization-units/is-invalid-unique-value")
    public boolean isInvalidUniqueValue(@RequestBody ImmunizationUnitDTO immunizationUnitDTO) {
        if(immunizationUnitDTO.getHotline() != null) {
            Specification<ImmunizationUnit> spec = immunizationUnitQueryService.hasHotline(immunizationUnitDTO.getHotline());
            return immunizationUnitRepository.exists(spec);
        }
        return false;
    }
}
