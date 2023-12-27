package vn.huydtg.immunizationservice.service;

import vn.huydtg.immunizationservice.service.dto.RoleDTO;

import java.util.List;
import java.util.Optional;


public interface RoleService {

    RoleDTO save(RoleDTO roleDTO);


    RoleDTO update(RoleDTO roleDTO);


    Optional<RoleDTO> partialUpdate(RoleDTO roleDTO);

    Optional<RoleDTO> findOne(Long id);

    List<RoleDTO> findAll();
    void delete(Long id);
}
