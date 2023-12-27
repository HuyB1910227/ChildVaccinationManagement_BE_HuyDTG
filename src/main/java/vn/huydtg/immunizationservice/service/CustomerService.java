package vn.huydtg.immunizationservice.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.huydtg.immunizationservice.service.dto.CustomerDTO;
import vn.huydtg.immunizationservice.service.dto.CustomerFSelectDTO;
import vn.huydtg.immunizationservice.web.rest.vm.CustomerVM;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {



    CustomerDTO save (CustomerVM customerVM);

    CustomerDTO update(CustomerDTO customerDTO);



    Optional<CustomerDTO> partialUpdate(CustomerDTO customerDTO);

    Page<CustomerDTO> findAll(Pageable pageable);

    Optional<CustomerDTO> findOne(UUID id);

    List<CustomerFSelectDTO> getCustomerFSelectDTO();
}
