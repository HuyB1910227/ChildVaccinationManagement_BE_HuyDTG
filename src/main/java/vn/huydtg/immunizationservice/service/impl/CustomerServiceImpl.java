package vn.huydtg.immunizationservice.service.impl;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.huydtg.immunizationservice.domain.Customer;
import vn.huydtg.immunizationservice.domain.User;
import vn.huydtg.immunizationservice.repository.CustomerRepository;
import vn.huydtg.immunizationservice.repository.UserRepository;
import vn.huydtg.immunizationservice.service.CustomerService;
import vn.huydtg.immunizationservice.service.dto.CustomerDTO;
import vn.huydtg.immunizationservice.service.dto.CustomerFSelectDTO;
import vn.huydtg.immunizationservice.service.dto.UserDTO;
import vn.huydtg.immunizationservice.service.mapper.CustomerMapper;
import vn.huydtg.immunizationservice.service.mapper.UserMapper;
import vn.huydtg.immunizationservice.web.rest.vm.CustomerVM;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper,
                               UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder
    ) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CustomerDTO save(CustomerVM customerVM) {
        User user = new User();
        userMapper.updateUserFromCustomerVM(customerVM, user);
        user.isEnable(true);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        user.setPassword(passwordEncoder.encode(customerVM.getPassword()));
        user = userRepository.save(user);
        Optional<User> createdUser = userRepository.findByUsername(customerVM.getUsername());
        if (createdUser.isPresent()) {
            UserDTO userDTO = userMapper.toDto(createdUser.get());
            User savedUser = userMapper.toEntity(userDTO);
            Customer customer = new Customer();
            customerMapper.updateCustomerFromCustomerVM(customerVM, customer);
            customer.setUser(savedUser);
            customer = customerRepository.save(customer);
            return customerMapper.toDto(customer);
        } else {
            throw new RuntimeException("User not found for username: " + customerVM.getUsername());
        }
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    @Override
    public Optional<CustomerDTO> partialUpdate(CustomerDTO customerDTO) {
        return customerRepository
            .findById(customerDTO.getId())
            .map(existingCustomer -> {
                customerMapper.partialUpdate(existingCustomer, customerDTO);

                return existingCustomer;
            })
            .map(customerRepository::save)
            .map(customerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerDTO> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable).map(customerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerDTO> findOne(UUID id) {
        return customerRepository.findById(id).map(customerMapper::toDto);
    }



    @Override
    public List<CustomerFSelectDTO> getCustomerFSelectDTO() {
        List<Object[]> resultList = customerRepository.findAllCustomersForSelect();
        List<CustomerFSelectDTO> customerFSelectDTOList = new ArrayList<>();
        for (Object[] result : resultList) {
            if (result.length >= 4) {
                UUID id = (UUID) result[0];
                String fullName = (String) result[1];
                String identityCard = (String) result[2];
                String phone = (String) result[3];
                CustomerFSelectDTO customerFSelectDTO = new CustomerFSelectDTO();
                customerFSelectDTO.setId(id);
                customerFSelectDTO.setFullName(fullName);
                customerFSelectDTO.setIdentityCard(identityCard);
                customerFSelectDTO.setPhone(phone);
                customerFSelectDTOList.add(customerFSelectDTO);
            }
        }
        return customerFSelectDTOList;
    }
}
