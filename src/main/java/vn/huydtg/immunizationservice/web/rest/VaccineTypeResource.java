package vn.huydtg.immunizationservice.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import vn.huydtg.immunizationservice.repository.VaccineTypeRepository;
import vn.huydtg.immunizationservice.service.VaccineTypeQueryService;
import vn.huydtg.immunizationservice.service.VaccineTypeService;
import vn.huydtg.immunizationservice.service.criteria.VaccineTypeCriteria;
import vn.huydtg.immunizationservice.service.dto.VaccineTypeDTO;
import vn.huydtg.immunizationservice.service.dto.VaccineTypeFSelectDTO;
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


@RestController
@RequestMapping("/api")
public class VaccineTypeResource {


        private static final String ENTITY_NAME = "REST_VACCINE_TYPE";

    @Value("${spring.application.name}")
    private String applicationName;

    private final VaccineTypeService vaccineTypeService;

    private final VaccineTypeRepository vaccineTypeRepository;

    private final VaccineTypeQueryService vaccineTypeQueryService;

    public VaccineTypeResource(
        VaccineTypeService vaccineTypeService,
        VaccineTypeRepository vaccineTypeRepository,
        VaccineTypeQueryService vaccineTypeQueryService
    ) {
        this.vaccineTypeService = vaccineTypeService;
        this.vaccineTypeRepository = vaccineTypeRepository;
        this.vaccineTypeQueryService = vaccineTypeQueryService;
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PostMapping("/vaccine-types")
    public ResponseEntity<VaccineTypeDTO> createVaccineType(@Valid @RequestBody VaccineTypeDTO vaccineTypeDTO) throws URISyntaxException {
        if (vaccineTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new vaccineType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VaccineTypeDTO result = vaccineTypeService.save(vaccineTypeDTO);
        return ResponseEntity
            .created(new URI("/api/vaccine-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PutMapping("/vaccine-types/{id}")
    public ResponseEntity<VaccineTypeDTO> updateVaccineType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VaccineTypeDTO vaccineTypeDTO
    ) throws URISyntaxException {
        if (vaccineTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vaccineTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vaccineTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VaccineTypeDTO result = vaccineTypeService.update(vaccineTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vaccineTypeDTO.getId().toString()))
            .body(result);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PatchMapping(value = "/vaccine-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VaccineTypeDTO> partialUpdateVaccineType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VaccineTypeDTO vaccineTypeDTO
    ) throws URISyntaxException {
        if (vaccineTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vaccineTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vaccineTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VaccineTypeDTO> result = vaccineTypeService.partialUpdate(vaccineTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vaccineTypeDTO.getId().toString())
        );
    }


    @GetMapping("/vaccine-types")
    public ResponseEntity<List<VaccineTypeDTO>> getAllVaccineTypes(
        VaccineTypeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        Page<VaccineTypeDTO> page = vaccineTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/vaccine-types/count")
    public ResponseEntity<Long> countVaccineTypes(VaccineTypeCriteria criteria) {
        return ResponseEntity.ok().body(vaccineTypeQueryService.countByCriteria(criteria));
    }


    @GetMapping("/vaccine-types/{id}")
    public ResponseEntity<VaccineTypeDTO> getVaccineType(@PathVariable Long id) {
        Optional<VaccineTypeDTO> vaccineTypeDTO = vaccineTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vaccineTypeDTO);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @DeleteMapping("/vaccine-types/{id}")
    public ResponseEntity<Void> deleteVaccineType(@PathVariable Long id) {
        vaccineTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    @GetMapping("/vaccine-types/select")
    public ResponseEntity<List<VaccineTypeFSelectDTO>> getAllVaccineTypeForSelect() {
        List<VaccineTypeFSelectDTO> result = vaccineTypeService.getVaccineTypesFSelectDTO();
        return ResponseEntity.ok().body(result);
    }

}
