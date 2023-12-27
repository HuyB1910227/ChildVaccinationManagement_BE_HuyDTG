package vn.huydtg.immunizationservice.service.dto;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class ImmunizationUnitFSelectDTO implements Serializable {

    private UUID id;
    private String name;

    private String address;
    private Boolean isActive;



    public ImmunizationUnitFSelectDTO() {
    }

    public ImmunizationUnitFSelectDTO(UUID id, String name, String address, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.isActive = isActive;
    }

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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImmunizationUnitFSelectDTO that = (ImmunizationUnitFSelectDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, isActive);
    }

    @Override
    public String toString() {
        return "ImmunizationUnitFSelectDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
