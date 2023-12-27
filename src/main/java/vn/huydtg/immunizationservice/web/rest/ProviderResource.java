package vn.huydtg.immunizationservice.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import vn.huydtg.immunizationservice.repository.ProviderRepository;
import vn.huydtg.immunizationservice.service.ProviderQueryService;
import vn.huydtg.immunizationservice.service.ProviderService;
import vn.huydtg.immunizationservice.service.criteria.ProviderCriteria;
import vn.huydtg.immunizationservice.service.dto.ProviderDTO;
import vn.huydtg.immunizationservice.service.dto.ProviderFSelectDTO;
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
public class ProviderResource {


    private static final String ENTITY_NAME = "REST_PROVIDER";

    @Value("${spring.application.name}")
    private String applicationName;

    private final ProviderService providerService;

    private final ProviderRepository providerRepository;

    private final ProviderQueryService providerQueryService;

    public ProviderResource(
        ProviderService providerService,
        ProviderRepository providerRepository,
        ProviderQueryService providerQueryService
    ) {
        this.providerService = providerService;
        this.providerRepository = providerRepository;
        this.providerQueryService = providerQueryService;
    }



    @PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('MANAGER')")
    @PostMapping("/providers")
    public ResponseEntity<ProviderDTO> createProvider(@Valid @RequestBody ProviderDTO providerDTO) throws URISyntaxException {
        if (providerDTO.getId() != null) {
            throw new BadRequestAlertException("A new provider cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProviderDTO result = providerService.save(providerDTO);
        return ResponseEntity
            .created(new URI("/api/providers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('MANAGER')")
    @PutMapping("/providers/{id}")
    public ResponseEntity<ProviderDTO> updateProvider(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProviderDTO providerDTO
    ) throws URISyntaxException {
        if (providerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, providerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!providerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProviderDTO result = providerService.update(providerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, providerDTO.getId().toString()))
            .body(result);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('MANAGER')")
    @PatchMapping(value = "/providers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProviderDTO> partialUpdateProvider(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProviderDTO providerDTO
    ) throws URISyntaxException {
        if (providerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, providerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!providerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProviderDTO> result = providerService.partialUpdate(providerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, providerDTO.getId().toString())
        );
    }

    @GetMapping("/providers")
    public ResponseEntity<List<ProviderDTO>> getAllProviders(
        ProviderCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        Page<ProviderDTO> page = providerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/providers/count")
    public ResponseEntity<Long> countProviders(ProviderCriteria criteria) {
        return ResponseEntity.ok().body(providerQueryService.countByCriteria(criteria));
    }


    @GetMapping("/providers/{id}")
    public ResponseEntity<ProviderDTO> getProvider(@PathVariable Long id) {
        Optional<ProviderDTO> providerDTO = providerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(providerDTO);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('MANAGER')")
    @DeleteMapping("/providers/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        providerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }


    @GetMapping("/providers/select")
    public ResponseEntity<List<ProviderFSelectDTO>> getAllProviderForSelect() {
        List<ProviderFSelectDTO> result = providerService.getProviderFSelectDTO();
        return ResponseEntity.ok().body(result);
    }
}
