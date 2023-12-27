package vn.huydtg.immunizationservice.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class PatientFCustomerDTO implements Serializable {

    private UUID id;

    @NotNull
    private String address;

    @NotNull
    private Integer gender;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private String fullName;

    private String avatar;

    public PatientFCustomerDTO() {
    }

    public PatientFCustomerDTO(UUID id, String address, Integer gender, LocalDate dateOfBirth, String fullName, String avatar) {
        this.id = id;
        this.address = address;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.fullName = fullName;
        this.avatar = avatar;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientFCustomerDTO that = (PatientFCustomerDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(address, that.address) && Objects.equals(gender, that.gender) && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(fullName, that.fullName) && Objects.equals(avatar, that.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, gender, dateOfBirth, fullName, avatar);
    }

    @Override
    public String toString() {
        return "PatientFCustomerDTO{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", fullName='" + fullName + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
