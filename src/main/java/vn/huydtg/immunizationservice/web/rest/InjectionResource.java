package vn.huydtg.immunizationservice.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import vn.huydtg.immunizationservice.repository.InjectionRepository;
import vn.huydtg.immunizationservice.service.InjectionQueryService;
import vn.huydtg.immunizationservice.service.InjectionService;
import vn.huydtg.immunizationservice.service.criteria.InjectionCriteria;
import vn.huydtg.immunizationservice.service.dto.InjectionDTO;
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
public class InjectionResource {


    private static final String ENTITY_NAME = "REST_INJECTION";

    @Value("${spring.application.name}")
    private String applicationName;

    private final InjectionService injectionService;

    private final InjectionRepository injectionRepository;

    private final InjectionQueryService injectionQueryService;

    public InjectionResource(
        InjectionService injectionService,
        InjectionRepository injectionRepository,
        InjectionQueryService injectionQueryService
    ) {
        this.injectionService = injectionService;
        this.injectionRepository = injectionRepository;
        this.injectionQueryService = injectionQueryService;
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PostMapping("/injections")
    public ResponseEntity<InjectionDTO> createInjection(@Valid @RequestBody InjectionDTO injectionDTO) throws URISyntaxException {
        if (injectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new injection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InjectionDTO result = injectionService.save(injectionDTO);
        return ResponseEntity
            .created(new URI("/api/injections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PutMapping("/injections/{id}")
    public ResponseEntity<InjectionDTO> updateInjection(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InjectionDTO injectionDTO
    ) throws URISyntaxException {
        if (injectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, injectionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!injectionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InjectionDTO result = injectionService.update(injectionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, injectionDTO.getId().toString()))
            .body(result);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PatchMapping(value = "/injections/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InjectionDTO> partialUpdateInjection(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InjectionDTO injectionDTO
    ) throws URISyntaxException {
        if (injectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, injectionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!injectionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InjectionDTO> result = injectionService.partialUpdate(injectionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, injectionDTO.getId().toString())
        );
    }

    @GetMapping("/injections")
    public ResponseEntity<List<InjectionDTO>> getAllInjections(
        InjectionCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {

        Page<InjectionDTO> page = injectionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/injections/count")
    public ResponseEntity<Long> countInjections(InjectionCriteria criteria) {
        return ResponseEntity.ok().body(injectionQueryService.countByCriteria(criteria));
    }

    @GetMapping("/injections/{id}")
    public ResponseEntity<InjectionDTO> getInjection(@PathVariable Long id) {
        Optional<InjectionDTO> injectionDTO = injectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(injectionDTO);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @DeleteMapping("/injections/{id}")
    public ResponseEntity<Void> deleteInjection(@PathVariable Long id) {
        injectionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }


}
