package vn.huydtg.immunizationservice.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import vn.huydtg.immunizationservice.repository.HistoryRepository;
import vn.huydtg.immunizationservice.service.HistoryQueryService;
import vn.huydtg.immunizationservice.service.HistoryService;
import vn.huydtg.immunizationservice.service.criteria.HistoryCriteria;
import vn.huydtg.immunizationservice.service.dto.HistoryDTO;
import vn.huydtg.immunizationservice.service.dto.HistoryFCustomerDTO;
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
public class HistoryResource {


    private static final String ENTITY_NAME = "REST_HISTORY";

    @Value("${spring.application.name}")
    private String applicationName;

    private final HistoryService historyService;

    private final HistoryRepository historyRepository;

    private final HistoryQueryService historyQueryService;

    public HistoryResource(HistoryService historyService, HistoryRepository historyRepository, HistoryQueryService historyQueryService) {
        this.historyService = historyService;
        this.historyRepository = historyRepository;
        this.historyQueryService = historyQueryService;
    }


    @PostMapping("/histories")
    public ResponseEntity<HistoryDTO> createHistory(@Valid @RequestBody HistoryDTO historyDTO) throws URISyntaxException {
        if (historyDTO.getId() != null) {
            throw new BadRequestAlertException("A new history cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HistoryDTO result = historyService.save(historyDTO);
        return ResponseEntity
            .created(new URI("/api/histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/histories/{id}")
    public ResponseEntity<HistoryDTO> updateHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HistoryDTO historyDTO
    ) throws URISyntaxException {
        if (historyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HistoryDTO result = historyService.update(historyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historyDTO.getId().toString()))
            .body(result);
    }


    @PatchMapping(value = "/histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HistoryDTO> partialUpdateHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HistoryDTO historyDTO
    ) throws URISyntaxException {
        if (historyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HistoryDTO> result = historyService.partialUpdate(historyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historyDTO.getId().toString())
        );
    }


    @GetMapping("/histories")
    public ResponseEntity<List<HistoryDTO>> getAllHistories(
        HistoryCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {

        Page<HistoryDTO> page = historyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/histories/count")
    public ResponseEntity<Long> countHistories(HistoryCriteria criteria) {
        return ResponseEntity.ok().body(historyQueryService.countByCriteria(criteria));
    }


    @GetMapping("/histories/{id}")
    public ResponseEntity<HistoryDTO> getHistory(@PathVariable Long id) {
        Optional<HistoryDTO> historyDTO = historyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(historyDTO);
    }

    @DeleteMapping("/histories/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Long id) {
        historyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/customers/token/histories")
    public ResponseEntity<List<HistoryFCustomerDTO>> getAllHistoriesByToken() {
        List<HistoryFCustomerDTO> result = historyService.getAllHistoriesByToken();
        return ResponseEntity.ok().body(result);
    }
}
