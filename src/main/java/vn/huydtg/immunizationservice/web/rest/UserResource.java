package vn.huydtg.immunizationservice.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import vn.huydtg.immunizationservice.domain.User;
import vn.huydtg.immunizationservice.security.SecurityUtils;
import vn.huydtg.immunizationservice.web.rest.utils.HeaderUtil;
import vn.huydtg.immunizationservice.web.rest.utils.PaginationUtil;
import vn.huydtg.immunizationservice.web.rest.utils.ResponseUtil;
import vn.huydtg.immunizationservice.repository.UserRepository;
import vn.huydtg.immunizationservice.service.UserQueryService;
import vn.huydtg.immunizationservice.service.UserService;
import vn.huydtg.immunizationservice.service.criteria.UserCriteria;
import vn.huydtg.immunizationservice.service.dto.UserDTO;
import vn.huydtg.immunizationservice.web.rest.errors.BadRequestAlertException;
import vn.huydtg.immunizationservice.web.rest.vm.ChangePasswordRequestVM;
import vn.huydtg.immunizationservice.web.rest.vm.ChangeUsernameRequestVM;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api")
public class UserResource {

    private static Logger logger = LoggerFactory.getLogger(UserResource.class);
    private static final String ENTITY_NAME = "REST_USER";

    @Value("${spring.application.name}")
    private String applicationName;
    private final UserService userService;

    private final UserRepository userRepository;

    private final UserQueryService userQueryService;



    public UserResource(UserService userService, UserRepository userRepository, UserQueryService userQueryService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userQueryService = userQueryService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {

        UserDTO result = userService.save(userDTO);
        return ResponseEntity
            .created(new URI("/api/users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody UserDTO userDTO
    ) throws URISyntaxException {
        if (userDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserDTO result = userService.update(userDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userDTO.getId().toString()))
            .body(result);
    }


    @PatchMapping(value = "/users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserDTO> partialUpdateUser(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody UserDTO userDTO
    ) throws URISyntaxException {
        if (userDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }


        Optional<UserDTO> result = userService.partialUpdate(userDTO);
//        logger.info(result.get().toString());
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userDTO.getId().toString())
        );
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(
        UserCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        Page<UserDTO> page = userQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/users/count")
    public ResponseEntity<Long> countUsers(UserCriteria criteria) {
        return ResponseEntity.ok().body(userQueryService.countByCriteria(criteria));
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID id) {
        Optional<UserDTO> userDTO = userService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userDTO);
    }

    @GetMapping("/users/token")
    public ResponseEntity<UserDTO> getUserByToken() {
        UUID currentUserId = SecurityUtils.getUserId();
        Optional<UserDTO> userDTO = userService.findOne(currentUserId);
        return ResponseUtil.wrapOrNotFound(userDTO);
    }


    @PostMapping("/users/is-invalid-unique-value")
    public boolean isInvalidUniqueValue(@RequestBody UserDTO userDTO) {
        if(userDTO.getUsername() != null) {
            Specification<User> spec = userQueryService.hasUsername(userDTO.getUsername());
            return userRepository.exists(spec);
        }
        if (userDTO.getPhone() != null) {
            Specification<User> spec = userQueryService.hasPhone(userDTO.getPhone());
            return userRepository.exists(spec);
        }
        return false;
    }

    @PostMapping("users/change-password/token")
    public ResponseEntity<UserDTO> changePasswordByTokenRequest(@Valid @RequestBody ChangePasswordRequestVM changePasswordRequestVM) {
        if(SecurityUtils.getUserId() == null) {
            throw new BadRequestAlertException("Invalid token", ENTITY_NAME, "usernotexist");
        }
        if (changePasswordRequestVM.getOldPassword().isEmpty() || changePasswordRequestVM.getNewPassword().isEmpty()) {
            throw new BadRequestAlertException("Bad request", ENTITY_NAME, "passwordnotfound");
        }
        Optional<UserDTO> userDTO = userService.changePasswordByToken(changePasswordRequestVM.getOldPassword(), changePasswordRequestVM.getNewPassword());
        return ResponseUtil.wrapOrNotFound(userDTO, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userDTO.get().getId().toString()));
    }

    @PostMapping("users/change-username/token")
    public ResponseEntity<UserDTO> changeUsernameByTokenRequest(@Valid @RequestBody ChangeUsernameRequestVM changeUsernameRequestVM) {
        if(SecurityUtils.getUserId() == null) {
            throw new BadRequestAlertException("Invalid token", ENTITY_NAME, "usernotexist");
        }
        if (changeUsernameRequestVM.getUsername().isEmpty()) {
            throw new BadRequestAlertException("Bad request", ENTITY_NAME, "usernamenotfound");
        }
        Optional<UserDTO> userDTO = userService.changeUsernameByToken(changeUsernameRequestVM.getUsername());
        return ResponseUtil.wrapOrNotFound(userDTO, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userDTO.get().getId().toString()));
    }


}
