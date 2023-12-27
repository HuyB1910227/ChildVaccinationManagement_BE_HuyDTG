package vn.huydtg.immunizationservice.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import tech.jhipster.web.util.PaginationUtil;
import vn.huydtg.immunizationservice.repository.DiseaseRepository;
import vn.huydtg.immunizationservice.service.DiseaseQueryService;
import vn.huydtg.immunizationservice.service.DiseaseService;
import vn.huydtg.immunizationservice.service.criteria.DiseaseCriteria;
import vn.huydtg.immunizationservice.service.dto.DiseaseDTO;
import vn.huydtg.immunizationservice.service.dto.DiseaseFSelectDTO;
import vn.huydtg.immunizationservice.service.dto.DiseaseWithVaccineRelationshipDTO;
import vn.huydtg.immunizationservice.web.rest.errors.BadRequestAlertException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import vn.huydtg.immunizationservice.web.rest.utils.HeaderUtil;
import vn.huydtg.immunizationservice.web.rest.utils.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DiseaseResource {


    private static final String ENTITY_NAME = "REST_DISEASE";

    @Value("${spring.application.name}")
    private String applicationName;

    private final DiseaseService diseaseService;

    private final DiseaseRepository diseaseRepository;

    private final DiseaseQueryService diseaseQueryService;

    public DiseaseResource(DiseaseService diseaseService, DiseaseRepository diseaseRepository, DiseaseQueryService diseaseQueryService) {
        this.diseaseService = diseaseService;
        this.diseaseRepository = diseaseRepository;
        this.diseaseQueryService = diseaseQueryService;
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('MANAGER')")
    @PostMapping("/diseases")
    public ResponseEntity<DiseaseDTO> createDisease(@Valid @RequestBody DiseaseDTO diseaseDTO) throws URISyntaxException {
        if (diseaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new disease cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiseaseDTO result = diseaseService.save(diseaseDTO);
        return ResponseEntity
            .created(new URI("/api/diseases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('MANAGER')")
    @PutMapping("/diseases/{id}")
    public ResponseEntity<DiseaseDTO> updateDisease(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DiseaseDTO diseaseDTO
    ) throws URISyntaxException {
        if (diseaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diseaseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diseaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DiseaseDTO result = diseaseService.update(diseaseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diseaseDTO.getId().toString()))
            .body(result);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PatchMapping(value = "/diseases/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DiseaseDTO> partialUpdateDisease(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DiseaseDTO diseaseDTO
    ) throws URISyntaxException {
        if (diseaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diseaseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diseaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DiseaseDTO> result = diseaseService.partialUpdate(diseaseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diseaseDTO.getId().toString())
        );
    }

    @GetMapping("/diseases")
    public ResponseEntity<List<DiseaseDTO>> getAllDiseases(
        DiseaseCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {

        Page<DiseaseDTO> page = diseaseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/diseases/count")
    public ResponseEntity<Long> countDiseases(DiseaseCriteria criteria) {
        return ResponseEntity.ok().body(diseaseQueryService.countByCriteria(criteria));
    }

    @GetMapping("/diseases/{id}")
    public ResponseEntity<DiseaseDTO> getDisease(@PathVariable Long id) {
        Optional<DiseaseDTO> diseaseDTO = diseaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diseaseDTO);
    }

    @GetMapping("/diseases/{id}/with-vaccines")
    public ResponseEntity<DiseaseWithVaccineRelationshipDTO> getDiseaseWithVaccines(@PathVariable Long id) {
        Optional<DiseaseWithVaccineRelationshipDTO> diseaseDTO = diseaseService.findOneWithEagerRelationship(id);
        return ResponseUtil.wrapOrNotFound(diseaseDTO);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('MANAGER')")
    @DeleteMapping("/diseases/{id}")
    public ResponseEntity<Void> deleteDisease(@PathVariable Long id) {
        diseaseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/diseases/select")
    public ResponseEntity<List<DiseaseFSelectDTO>> getAllDiseaseForSelect() {
        List<DiseaseFSelectDTO> result = diseaseService.getDiseaseFSelectDTO();
        return ResponseEntity.ok().body(result);
    }
}
