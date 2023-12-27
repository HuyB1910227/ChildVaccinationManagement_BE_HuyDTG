package vn.huydtg.immunizationservice.service.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.huydtg.immunizationservice.domain.Customer;
import vn.huydtg.immunizationservice.service.dto.CustomerDTO;
import vn.huydtg.immunizationservice.web.rest.vm.CustomerVM;


@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {
    CustomerDTO customerVMToCustomerDTO(CustomerVM customerVM);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCustomerFromCustomerVM(CustomerVM customerVM, @MappingTarget Customer customer);
}
