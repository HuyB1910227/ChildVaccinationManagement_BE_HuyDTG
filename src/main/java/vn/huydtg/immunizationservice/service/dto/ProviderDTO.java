package vn.huydtg.immunizationservice.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class ProviderDTO implements Serializable {


    private Long id;

    @NotNull
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProviderDTO)) {
            return false;
        }

        ProviderDTO providerDTO = (ProviderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, providerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }


    @Override
    public String toString() {
        return "ProviderDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
