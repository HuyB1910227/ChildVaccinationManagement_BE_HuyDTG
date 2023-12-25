package vn.huydtg.immunizationservice.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import vn.huydtg.immunizationservice.repository.AgeRepository;
import vn.huydtg.immunizationservice.service.AgeQueryService;
import vn.huydtg.immunizationservice.service.AgeService;
import vn.huydtg.immunizationservice.service.criteria.AgeCriteria;
import vn.huydtg.immunizationservice.service.dto.AgeDTO;
import vn.huydtg.immunizationservice.service.dto.AgeFSelectDTO;
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
public class AgeResource {


    private static final String ENTITY_NAME = "REST_AGE";

    @Value("${spring.application.name}")
    private String applicationName;

    private final AgeService ageService;

    private final AgeRepository ageRepository;

    private final AgeQueryService ageQueryService;

    public AgeResource(AgeService ageService, AgeRepository ageRepository, AgeQueryService ageQueryService) {
        this.ageService = ageService;
        this.ageRepository = ageRepository;
        this.ageQueryService = ageQueryService;
    }


    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PostMapping("/ages")
    public ResponseEntity<AgeDTO> createAge(@Valid @RequestBody AgeDTO ageDTO) throws URISyntaxException {
        if (ageDTO.getId() != null) {
            throw new BadRequestAlertException("A new age cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgeDTO result = ageService.save(ageDTO);
        return ResponseEntity.ok(result);

    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PutMapping("/ages/{id}")
    public ResponseEntity<AgeDTO> updateAge(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody AgeDTO ageDTO)
        throws URISyntaxException {
        if (ageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AgeDTO result = ageService.update(ageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ageDTO.getId().toString()))
            .body(result);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PatchMapping(value = "/ages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AgeDTO> partialUpdateAge(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AgeDTO ageDTO
    ) throws URISyntaxException {
        if (ageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AgeDTO> result = ageService.partialUpdate(ageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ageDTO.getId().toString())
        );
    }


    @GetMapping("/ages")
    public ResponseEntity<List<AgeDTO>> getAllAges(
        AgeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {

        Page<AgeDTO> page = ageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/ages/count")
    public ResponseEntity<Long> countAges(AgeCriteria criteria) {
        return ResponseEntity.ok().body(ageQueryService.countByCriteria(criteria));
    }

    @GetMapping("/ages/{id}")
    public ResponseEntity<AgeDTO> getAge(@PathVariable Long id) {
        Optional<AgeDTO> ageDTO = ageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ageDTO);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @DeleteMapping("/ages/{id}")
    public ResponseEntity<Void> deleteAge(@PathVariable Long id) {
        ageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/ages/by-vaccine/{vaccineId}")
    public ResponseEntity<List<AgeFSelectDTO>> getAgeByVaccine(@PathVariable UUID vaccineId) {
        List<AgeFSelectDTO> ageFSelectDTOList = ageService.findAllAgeByVaccine(vaccineId);
        return ResponseEntity.ok().body(ageFSelectDTOList);
    }
}
