package vn.huydtg.immunizationservice.service.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.huydtg.immunizationservice.domain.Employee;
import vn.huydtg.immunizationservice.service.dto.EmployeeDTO;
import vn.huydtg.immunizationservice.web.rest.vm.EmployeeVM;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EmployeeDTO employeeVMToEmployeeDTO(EmployeeVM employeeVM);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEmployeeFromEmployeeVM(EmployeeVM employeeVM, @MappingTarget Employee employee);
}
