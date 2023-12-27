package vn.huydtg.immunizationservice.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.huydtg.immunizationservice.service.dto.EmployeeDTO;
import vn.huydtg.immunizationservice.service.dto.EmployeeFSelectDTO;
import vn.huydtg.immunizationservice.web.rest.vm.EmployeeVM;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeService {

    EmployeeDTO save(EmployeeVM employeeVM);


    EmployeeDTO update(EmployeeDTO employeeDTO);

    Optional<EmployeeDTO> partialUpdate(EmployeeDTO employeeDTO);

    Page<EmployeeDTO> findAll(Pageable pageable);

    Optional<EmployeeDTO> findOne(UUID id);

    List<EmployeeFSelectDTO> getEmployeeFSelectDTO();

    Optional<EmployeeDTO> findOneByUserId(UUID userId);

}
