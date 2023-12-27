package vn.huydtg.immunizationservice.service.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.huydtg.immunizationservice.domain.User;
import vn.huydtg.immunizationservice.service.dto.UserDTO;
import vn.huydtg.immunizationservice.web.rest.vm.CustomerVM;
import vn.huydtg.immunizationservice.web.rest.vm.EmployeeVM;


@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User>{
    UserDTO employeeVMToUserDTO(EmployeeVM employeeVM);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromEmployeeVM(EmployeeVM employeeVM, @MappingTarget User user);

    UserDTO customerVMToUserDTO(CustomerVM customerVM);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromCustomerVM(CustomerVM customerVM, @MappingTarget User user);

}
