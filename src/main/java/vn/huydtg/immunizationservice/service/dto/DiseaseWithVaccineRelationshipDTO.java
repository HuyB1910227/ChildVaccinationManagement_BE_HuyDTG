package vn.huydtg.immunizationservice.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class DiseaseWithVaccineRelationshipDTO implements Serializable {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    private Set<VaccineDTO> vaccines;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<VaccineDTO> getVaccines() {
        return vaccines;
    }

    public void setVaccines(Set<VaccineDTO> vaccines) {
        this.vaccines = vaccines;
    }

    public DiseaseWithVaccineRelationshipDTO() {
    }

    public DiseaseWithVaccineRelationshipDTO(Long id, String name, String description, Set<VaccineDTO> vaccines) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.vaccines = vaccines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiseaseWithVaccineRelationshipDTO that = (DiseaseWithVaccineRelationshipDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(vaccines, that.vaccines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, vaccines);
    }

    @Override
    public String toString() {
        return "DiseaseWithVaccineRelationshipDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", vaccines=" + vaccines +
                '}';
    }
}
