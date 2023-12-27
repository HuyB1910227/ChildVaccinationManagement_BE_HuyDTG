package vn.huydtg.immunizationservice.service.dto;


import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class UserNormalDTO implements Serializable {

    private UUID id;

    @NotNull
    private String username;

    @NotNull
    private String phone;


    @NotNull
    private String password;

    @NotNull
    private Boolean isEnable;

    private Instant createdAt;

    private Instant updatedAt;

    private Set<RoleDTO> roles = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserNormalDTO)) {
            return false;
        }

        UserNormalDTO userNormalDTO = (UserNormalDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userNormalDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }


    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + getId() + "'" +
                ", username='" + getUsername() + "'" +
                ", phone='" + getPhone() + "'" +
                ", password='" + getPassword() + "'" +
                ", isEnable='" + getIsEnable() + "'" +
                ", createdAt='" + getCreatedAt() + "'" +
                ", updatedAt='" + getUpdatedAt() + "'" +
                ", roles=" + getRoles() +
                "}";
    }
}
