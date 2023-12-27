package vn.huydtg.immunizationservice.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import vn.huydtg.immunizationservice.repository.NutritionRepository;
import vn.huydtg.immunizationservice.service.NutritionQueryService;
import vn.huydtg.immunizationservice.service.NutritionService;
import vn.huydtg.immunizationservice.service.criteria.NutritionCriteria;
import vn.huydtg.immunizationservice.service.dto.NutritionDTO;
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
public class NutritionResource {


    private static final String ENTITY_NAME = "REST_NUTRITION";

    @Value("${spring.application.name}")
    private String applicationName;

    private final NutritionService nutritionService;

    private final NutritionRepository nutritionRepository;

    private final NutritionQueryService nutritionQueryService;

    public NutritionResource(
        NutritionService nutritionService,
        NutritionRepository nutritionRepository,
        NutritionQueryService nutritionQueryService
    ) {
        this.nutritionService = nutritionService;
        this.nutritionRepository = nutritionRepository;
        this.nutritionQueryService = nutritionQueryService;
    }


    @PostMapping("/nutritions")
    public ResponseEntity<NutritionDTO> createNutrition(@Valid @RequestBody NutritionDTO nutritionDTO) throws URISyntaxException {
        if (nutritionDTO.getId() != null) {
            throw new BadRequestAlertException("A new nutrition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NutritionDTO result = nutritionService.save(nutritionDTO);
        return ResponseEntity
            .created(new URI("/api/nutritions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/nutritions/{id}")
    public ResponseEntity<NutritionDTO> updateNutrition(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NutritionDTO nutritionDTO
    ) throws URISyntaxException {
        if (nutritionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nutritionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nutritionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NutritionDTO result = nutritionService.update(nutritionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nutritionDTO.getId().toString()))
            .body(result);
    }


    @PatchMapping(value = "/nutritions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NutritionDTO> partialUpdateNutrition(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NutritionDTO nutritionDTO
    ) throws URISyntaxException {
        if (nutritionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nutritionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nutritionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NutritionDTO> result = nutritionService.partialUpdate(nutritionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nutritionDTO.getId().toString())
        );
    }


    @GetMapping("/nutritions")
    public ResponseEntity<List<NutritionDTO>> getAllNutritions(
        NutritionCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {

        Page<NutritionDTO> page = nutritionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/nutritions/count")
    public ResponseEntity<Long> countNutritions(NutritionCriteria criteria) {
        return ResponseEntity.ok().body(nutritionQueryService.countByCriteria(criteria));
    }


    @GetMapping("/nutritions/{id}")
    public ResponseEntity<NutritionDTO> getNutrition(@PathVariable Long id) {
        Optional<NutritionDTO> nutritionDTO = nutritionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nutritionDTO);
    }


    @DeleteMapping("/nutritions/{id}")
    public ResponseEntity<Void> deleteNutrition(@PathVariable Long id) {
        nutritionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/nutritions/all")
    public ResponseEntity<List<NutritionDTO>> getAllNutritionNoPage(
            NutritionCriteria criteria
    ) {
        List<NutritionDTO> results = nutritionQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(results);
    }

}
