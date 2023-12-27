package vn.huydtg.immunizationservice.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class EmployeeFSelectDTO implements Serializable {
    private UUID id;
    @NotNull
    private String employeeId;

    @NotNull
    private String phone;

    @NotNull
    private String fullName;

    public EmployeeFSelectDTO() {
    }

    public EmployeeFSelectDTO(UUID id, String employeeId, String phone, String fullName) {
        this.id = id;
        this.employeeId = employeeId;
        this.phone = phone;
        this.fullName = fullName;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeFSelectDTO that = (EmployeeFSelectDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(employeeId, that.employeeId) && Objects.equals(phone, that.phone) && Objects.equals(fullName, that.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeId, phone, fullName);
    }

    @Override
    public String toString() {
        return "EmployeeFSelectDTO{" +
                "id=" + id +
                ", employeeId='" + employeeId + '\'' +
                ", phone='" + phone + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
