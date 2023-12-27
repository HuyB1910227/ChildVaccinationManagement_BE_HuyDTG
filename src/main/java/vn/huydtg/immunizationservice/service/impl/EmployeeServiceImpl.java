package vn.huydtg.immunizationservice.service.impl;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.huydtg.immunizationservice.domain.Employee;
import vn.huydtg.immunizationservice.domain.ImmunizationUnit;
import vn.huydtg.immunizationservice.domain.User;
import vn.huydtg.immunizationservice.repository.EmployeeRepository;
import vn.huydtg.immunizationservice.repository.ImmunizationUnitRepository;
import vn.huydtg.immunizationservice.repository.UserRepository;
import vn.huydtg.immunizationservice.service.EmployeeService;
import vn.huydtg.immunizationservice.service.dto.*;
import vn.huydtg.immunizationservice.service.mapper.EmployeeMapper;
import vn.huydtg.immunizationservice.service.mapper.UserMapper;
import vn.huydtg.immunizationservice.web.rest.vm.EmployeeVM;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final UserRepository userRepository;

    private final ImmunizationUnitRepository immunizationUnitRepository;

    private final UserMapper userMapper;

    private PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, UserRepository userRepository,
                               UserMapper userMapper, PasswordEncoder passwordEncoder, ImmunizationUnitRepository immunizationUnitRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.immunizationUnitRepository = immunizationUnitRepository;
    }

    @Override
    public EmployeeDTO save(EmployeeVM employeeVM) {
        User user = new User();

        userMapper.updateUserFromEmployeeVM(employeeVM, user);
        user.isEnable(employeeVM.getIsEnable());
        user.setId(UUID.fromString("0492b8a2-ffbb-45ed-b710-8405f2c48158"));
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        user.setPassword(passwordEncoder.encode(employeeVM.getPassword()));
        user = userRepository.save(user);
        Optional<User> createdUser = userRepository.findByUsername(employeeVM.getUsername());

        if (createdUser.isPresent()) {
            if(immunizationUnitRepository.existsById(employeeVM.getImmunizationUnitId())) {
                ImmunizationUnit immunizationUnit;
                immunizationUnit = immunizationUnitRepository.findById(employeeVM.getImmunizationUnitId()).get();
                UserDTO userDTO = userMapper.toDto(createdUser.get());
                User savedUser = userMapper.toEntity(userDTO);
                Employee employee = new Employee();
                employeeMapper.updateEmployeeFromEmployeeVM(employeeVM, employee);
                employee.setId(UUID.fromString("0492b8a2-ffbb-45ed-b710-8405f2c48158"));
                employee.setUser(savedUser);
                employee.setImmunizationUnit(immunizationUnit);
                employee = employeeRepository.save(employee);
                return employeeMapper.toDto(employee);
            } else {
                throw new RuntimeException("Immunization Unit not found for id: " + employeeVM.getImmunizationUnitId());
            }

        } else {
            throw new RuntimeException("User not found for username: " + employeeVM.getUsername());
        }

    }





    @Override
    public EmployeeDTO update(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Override
    public Optional<EmployeeDTO> partialUpdate(EmployeeDTO employeeDTO) {

        return employeeRepository
            .findById(employeeDTO.getId())
            .map(existingEmployee -> {
                employeeMapper.partialUpdate(existingEmployee, employeeDTO);

                return existingEmployee;
            })
            .map(employeeRepository::save)
            .map(employeeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(employeeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> findOne(UUID id) {
        return employeeRepository.findById(id).map(employeeMapper::toDto);
    }



    @Override
    public List<EmployeeFSelectDTO> getEmployeeFSelectDTO() {
        List<Object[]> resultList = employeeRepository.findAllEmployeesForSelect();
        List<EmployeeFSelectDTO> employeeFSelectDTOArrayList = new ArrayList<>();
        for (Object[] result : resultList) {
            if (result.length >= 4) {
                UUID id = (UUID) result[0];
                String employeeId = (String) result[1];
                String fullName = (String) result[2];
                String phone = (String) result[3];
                EmployeeFSelectDTO employeeFSelectDTO = new EmployeeFSelectDTO();
                employeeFSelectDTO.setId(id);
                employeeFSelectDTO.setEmployeeId(employeeId);
                employeeFSelectDTO.setFullName(fullName);
                employeeFSelectDTO.setPhone(phone);
                employeeFSelectDTOArrayList.add(employeeFSelectDTO);
            }
        }
        return employeeFSelectDTOArrayList;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> findOneByUserId(UUID userId) {
        return employeeRepository.findFirstByUserId(userId).map(employeeMapper::toDto);
    }
}
