package vn.huydtg.immunizationservice.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;


public class AdministratorDTO implements Serializable {


    private UUID id;

    @NotNull
    private String phone;

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

    @NotNull
    private String email;

    @NotNull
    private String hotline;

    private UserDTO user;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdministratorDTO)) {
            return false;
        }

        AdministratorDTO administratorDTO = (AdministratorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, administratorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "AdministratorDTO{" +
            "id='" + getId() + "'" +
            ", phone='" + getPhone() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", identityCard='" + getIdentityCard() + "'" +
            ", address='" + getAddress() + "'" +
            ", gender=" + getGender() +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", email='" + getEmail() + "'" +
            ", hotline='" + getHotline() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
