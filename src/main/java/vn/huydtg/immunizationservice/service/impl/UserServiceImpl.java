package vn.huydtg.immunizationservice.service.impl;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.huydtg.immunizationservice.domain.Administrator;
import vn.huydtg.immunizationservice.domain.Customer;
import vn.huydtg.immunizationservice.domain.Employee;
import vn.huydtg.immunizationservice.domain.User;
import vn.huydtg.immunizationservice.repository.AdministratorRepository;
import vn.huydtg.immunizationservice.repository.CustomerRepository;
import vn.huydtg.immunizationservice.repository.EmployeeRepository;
import vn.huydtg.immunizationservice.repository.UserRepository;
import vn.huydtg.immunizationservice.security.SecurityUtils;
import vn.huydtg.immunizationservice.service.AdministratorService;
import vn.huydtg.immunizationservice.service.CustomerService;
import vn.huydtg.immunizationservice.service.EmployeeService;
import vn.huydtg.immunizationservice.service.UserService;
import vn.huydtg.immunizationservice.service.dto.AdministratorDTO;
import vn.huydtg.immunizationservice.service.dto.CustomerDTO;
import vn.huydtg.immunizationservice.service.dto.EmployeeDTO;
import vn.huydtg.immunizationservice.service.dto.UserDTO;
import vn.huydtg.immunizationservice.service.mapper.UserMapper;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
@Transactional
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private PasswordEncoder passwordEncoder;

    private CustomerRepository customerRepository;

    private AdministratorRepository administratorRepository;

    private EmployeeRepository employeeRepository;

    private CustomerService customerService;

    private AdministratorService administratorService;

    private EmployeeService employeeService;



    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, CustomerRepository customerRepository, AdministratorRepository administratorRepository, EmployeeRepository employeeRepository, CustomerService customerService, AdministratorService administratorService, EmployeeService employeeService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.administratorRepository = administratorRepository;
        this.employeeRepository = employeeRepository;
        this.customerService = customerService;
        this.administratorService = administratorService;
        this.employeeService = employeeService;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userMapper.toEntity(userDTO);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public Optional<UserDTO> partialUpdate(UserDTO userDTO) {
        userDTO.setUpdatedAt(Instant.now());
        Optional<UserDTO> result1 = userRepository
            .findById(userDTO.getId())
            .map(existingUser -> {
                userMapper.partialUpdate(existingUser, userDTO);
                return existingUser;
            })
            .map(userRepository::save)
            .map(userMapper::toDto);
        if (result1.isPresent() && userDTO.getPhone() != null) {
            Optional<Customer> customer = customerRepository.findFirstByUserId(result1.get().getId());
            Optional<Employee> employee = employeeRepository.findFirstByUserId(result1.get().getId());
            Optional<Administrator> administrator = administratorRepository.findFirstByUserId(result1.get().getId());

            if (customer.isPresent()) {
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setId(customer.get().getId());
                customerDTO.setPhone(result1.get().getPhone());
                customerService.partialUpdate(customerDTO);
            }
            if (employee.isPresent()) {
                EmployeeDTO employeeDTO = new EmployeeDTO();
                employeeDTO.setId(employee.get().getId());
                employeeDTO.setPhone(result1.get().getPhone());
                employeeService.partialUpdate(employeeDTO);
            }
            if (administrator.isPresent()) {
                AdministratorDTO administratorDTO = new AdministratorDTO();
                administratorDTO.setId(administrator.get().getId());
                administratorDTO.setPhone(result1.get().getPhone());
                administratorService.partialUpdate(administratorDTO);
            }
        }
        return result1;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    public Page<UserDTO> findAllWithEagerRelationships(Pageable pageable) {
        return userRepository.findAllWithEagerRelationships(pageable).map(userMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAllWhereEmployeeIsNull() {
        return StreamSupport
            .stream(userRepository.findAll().spliterator(), false)
            .filter(user -> user.getEmployee() == null)
            .map(userMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAllWhereAdministratorIsNull() {
        return StreamSupport
            .stream(userRepository.findAll().spliterator(), false)
            .filter(user -> user.getAdministrator() == null)
            .map(userMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAllWhereCustomerIsNull() {
        return StreamSupport
            .stream(userRepository.findAll().spliterator(), false)
            .filter(user -> user.getCustomer() == null)
            .map(userMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAllWhereCustomerAndEmployeeIsNull() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .filter(user -> (user.getCustomer() == null && user.getEmployee() == null) )
                .map(userMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAllWhereCustomerAndAdministratorIsNull() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .filter(user -> (user.getCustomer() == null && user.getAdministrator() == null) )
                .map(userMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAllWhereEmployeeAndAdministratorIsNull() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .filter(user -> (user.getEmployee() == null && user.getAdministrator() == null) )
                .map(userMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findOne(UUID id) {
        return userRepository.findOneWithEagerRelationships(id).map(userMapper::toDto);
    }

    @Override
    public Optional<UserDTO> changePasswordByToken(String oldPassword, String newPassword) {
        UUID userId = SecurityUtils.getUserId();
        Optional<User> optionalUser = Optional.empty();
        if (userId != null) {
           optionalUser = userRepository.findById(userId);
        }
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return Optional.ofNullable(userMapper.toDto(user));
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> changeUsernameByToken(String username) {
        UUID userId = SecurityUtils.getUserId();
        Optional<User> optionalUser = Optional.empty();
        if (userId != null) {
            optionalUser = userRepository.findById(userId);
        }
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(username);
            userRepository.save(user);
            return Optional.ofNullable(userMapper.toDto(user));
        }
        return Optional.empty();
    }
}
