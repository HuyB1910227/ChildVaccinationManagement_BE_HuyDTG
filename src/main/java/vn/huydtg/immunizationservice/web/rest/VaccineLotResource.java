package vn.huydtg.immunizationservice.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.UUIDFilter;
import vn.huydtg.immunizationservice.repository.VaccineLotRepository;
import vn.huydtg.immunizationservice.security.SecurityUtils;
import vn.huydtg.immunizationservice.service.VaccineLotQueryService;
import vn.huydtg.immunizationservice.service.VaccineLotService;
import vn.huydtg.immunizationservice.service.criteria.VaccineLotCriteria;
import vn.huydtg.immunizationservice.service.dto.VaccineAvailableInImmunizationUnitDTO;
import vn.huydtg.immunizationservice.service.dto.VaccineLotDTO;
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
public class VaccineLotResource {


    private static final String ENTITY_NAME = "REST_VACCINE_LOT";

    @Value("${spring.application.name}")
    private String applicationName;

    private final VaccineLotService vaccineLotService;

    private final VaccineLotRepository vaccineLotRepository;

    private final VaccineLotQueryService vaccineLotQueryService;

    public VaccineLotResource(
        VaccineLotService vaccineLotService,
        VaccineLotRepository vaccineLotRepository,
        VaccineLotQueryService vaccineLotQueryService
    ) {
        this.vaccineLotService = vaccineLotService;
        this.vaccineLotRepository = vaccineLotRepository;
        this.vaccineLotQueryService = vaccineLotQueryService;
    }

    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMINISTRATOR')")
    @PostMapping("/vaccine-lots")
    public ResponseEntity<VaccineLotDTO> createVaccineLot(@Valid @RequestBody VaccineLotDTO vaccineLotDTO) throws URISyntaxException {
        if (vaccineLotDTO.getId() != null) {
            throw new BadRequestAlertException("A new vaccineLot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VaccineLotDTO result = vaccineLotService.save(vaccineLotDTO);
        return ResponseEntity
            .created(new URI("/api/vaccine-lots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMINISTRATOR')")
    @PutMapping("/vaccine-lots/{id}")
    public ResponseEntity<VaccineLotDTO> updateVaccineLot(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VaccineLotDTO vaccineLotDTO
    ) throws URISyntaxException {
        if (vaccineLotDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vaccineLotDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vaccineLotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VaccineLotDTO result = vaccineLotService.update(vaccineLotDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vaccineLotDTO.getId().toString()))
            .body(result);
    }

    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMINISTRATOR')")
    @PatchMapping(value = "/vaccine-lots/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VaccineLotDTO> partialUpdateVaccineLot(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VaccineLotDTO vaccineLotDTO
    ) throws URISyntaxException {
        if (vaccineLotDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vaccineLotDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vaccineLotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VaccineLotDTO> result = vaccineLotService.partialUpdate(vaccineLotDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vaccineLotDTO.getId().toString())
        );
    }


    @GetMapping("/vaccine-lots")
    public ResponseEntity<List<VaccineLotDTO>> getAllVaccineLots(
        VaccineLotCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        boolean isEmployee = SecurityUtils.hasCurrentUserAnyOfAuthorities("MANAGER", "PHYSICIAN", "STAFF");
        if(isEmployee) {
            UUID currentUserImmunizationUnitId = SecurityUtils.getImmunizationUnitId();
            Filter<UUID> immunizationUnitIdFilter = new UUIDFilter().setEquals(currentUserImmunizationUnitId);
            criteria.setImmunizationUnitId((UUIDFilter) immunizationUnitIdFilter);
        }
        Page<VaccineLotDTO> page = vaccineLotQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/vaccine-lots/count")
    public ResponseEntity<Long> countVaccineLots(VaccineLotCriteria criteria) {
        return ResponseEntity.ok().body(vaccineLotQueryService.countByCriteria(criteria));
    }


    @GetMapping("/vaccine-lots/{id}")
    public ResponseEntity<VaccineLotDTO> getVaccineLot(@PathVariable Long id) {
        Optional<VaccineLotDTO> vaccineLotDTO = vaccineLotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vaccineLotDTO);
    }


    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMINISTRATOR')")
    @DeleteMapping("/vaccine-lots/{id}")
    public ResponseEntity<Void> deleteVaccineLot(@PathVariable Long id) {
        vaccineLotService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/vaccine-lots/vaccine-available/by-immunization-unit/{immunizationUnitId}")
    public ResponseEntity<List<VaccineAvailableInImmunizationUnitDTO>> getVaccineAvailableByImmunizationUnitId(@PathVariable UUID immunizationUnitId) {
        List<VaccineAvailableInImmunizationUnitDTO> result = vaccineLotService.getVaccineAvailableByImmunizationUnitId(immunizationUnitId);
        return ResponseEntity.ok().body(result);
    }

}
