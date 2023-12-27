package vn.huydtg.immunizationservice.service.dto;

import java.io.Serializable;

public class EmployeeUserDTO implements Serializable {
    private EmployeeDTO employee;

    private UserDTO user;


    public EmployeeUserDTO() {
    }

    public EmployeeUserDTO(EmployeeDTO employee, UserDTO user) {
        this.employee = employee;
        this.user = user;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
