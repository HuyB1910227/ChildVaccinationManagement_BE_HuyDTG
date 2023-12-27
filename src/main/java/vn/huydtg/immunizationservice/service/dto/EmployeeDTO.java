package vn.huydtg.immunizationservice.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;


public class EmployeeDTO implements Serializable {

    private UUID id;

    @NotNull
    private String employeeId;

    @NotNull
    private String phone;

    @NotNull
    private String email;

    @NotNull
    private String fullName;

    @NotNull
    private String identityCard;

    @NotNull
    private String address;

    @NotNull
    private Long gender;

    @NotNull
    private LocalDate dateOfBirth;

    private String avatar;

    private UserDTO user;

    private ImmunizationUnitDTO immunizationUnit;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getGender() {
        return gender;
    }

    public void setGender(Long gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ImmunizationUnitDTO getImmunizationUnit() {
        return immunizationUnit;
    }

    public void setImmunizationUnit(ImmunizationUnitDTO immunizationUnit) {
        this.immunizationUnit = immunizationUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeDTO)) {
            return false;
        }

        EmployeeDTO employeeDTO = (EmployeeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, employeeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id='" + getId() + "'" +
                ", employeeId='" + getEmployeeId() + "'" +
                ", phone='" + getPhone() + "'" +
                ", email='" + getEmail() + "'" +
                ", fullName='" + getFullName() + "'" +
                ", identityCard='" + getIdentityCard() + "'" +
                ", address='" + getAddress() + "'" +
                ", gender=" + getGender() +
                ", dateOfBirth='" + getDateOfBirth() + "'" +
                ", avatar='" + getAvatar() + "'" +
                ", user=" + getUser() +
                ", immunizationUnit=" + getImmunizationUnit() +
                "}";
    }
}
