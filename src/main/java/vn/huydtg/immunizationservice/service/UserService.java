package vn.huydtg.immunizationservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.huydtg.immunizationservice.service.dto.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserService {

    UserDTO save(UserDTO userDTO);

    UserDTO update(UserDTO userDTO);

    Optional<UserDTO> partialUpdate(UserDTO userDTO);

    Page<UserDTO> findAll(Pageable pageable);

    List<UserDTO> findAllWhereEmployeeIsNull();

    List<UserDTO> findAllWhereAdministratorIsNull();

    List<UserDTO> findAllWhereCustomerIsNull();

    List<UserDTO> findAllWhereCustomerAndEmployeeIsNull();

    List<UserDTO> findAllWhereCustomerAndAdministratorIsNull();

    List<UserDTO> findAllWhereEmployeeAndAdministratorIsNull();

    Page<UserDTO> findAllWithEagerRelationships(Pageable pageable);

    Optional<UserDTO> findOne(UUID id);

    Optional<UserDTO> changePasswordByToken(String oldPassword, String newPassword);

    Optional<UserDTO> changeUsernameByToken(String username);

}
