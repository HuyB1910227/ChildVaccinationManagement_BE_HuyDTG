package vn.huydtg.immunizationservice.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

public class UserPatchDTO implements Serializable {

    private UUID id;

    @NotNull
    private String username;

    @NotNull
    private String phone;
    @NotNull
    private Boolean isEnable;


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

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    public UserPatchDTO() {
    }

    public UserPatchDTO(UUID id, String username, String phone, Boolean isEnable) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.isEnable = isEnable;
    }
}
