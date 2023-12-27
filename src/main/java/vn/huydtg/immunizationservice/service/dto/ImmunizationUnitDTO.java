package vn.huydtg.immunizationservice.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


public class ImmunizationUnitDTO implements Serializable {

    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
    private String operatingLicence;

    @NotNull
    private String hotline;

    @NotNull
    private Boolean isActive;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOperatingLicence() {
        return operatingLicence;
    }

    public void setOperatingLicence(String operatingLicence) {
        this.operatingLicence = operatingLicence;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImmunizationUnitDTO)) {
            return false;
        }

        ImmunizationUnitDTO immunizationUnitDTO = (ImmunizationUnitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, immunizationUnitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "ImmunizationUnitDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", operatingLicence='" + getOperatingLicence() + "'" +
            ", hotline='" + getHotline() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            "}";
    }
}
