package vn.huydtg.immunizationservice.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class CustomerFSelectDTO implements Serializable {

    private UUID id;

    @NotNull
    private String phone;

    @NotNull
    private String fullName;

    @NotNull
    private String identityCard;

    public CustomerFSelectDTO() {
    }

    public CustomerFSelectDTO(UUID id, String phone, String fullName, String identityCard) {
        this.id = id;
        this.phone = phone;
        this.fullName = fullName;
        this.identityCard = identityCard;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerFSelectDTO that = (CustomerFSelectDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(phone, that.phone) && Objects.equals(fullName, that.fullName) && Objects.equals(identityCard, that.identityCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phone, fullName, identityCard);
    }
}
