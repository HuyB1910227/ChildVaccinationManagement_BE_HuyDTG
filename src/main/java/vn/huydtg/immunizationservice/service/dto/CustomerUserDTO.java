package vn.huydtg.immunizationservice.service.dto;

import java.io.Serializable;

public class CustomerUserDTO implements Serializable {

    private CustomerDTO customer;

    private UserDTO user;

    public CustomerUserDTO() {
    }

    public CustomerUserDTO(CustomerDTO customer, UserDTO user) {
        this.customer = customer;
        this.user = user;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
