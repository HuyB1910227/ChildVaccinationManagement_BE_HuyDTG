package vn.huydtg.immunizationservice.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


public class VaccineDTO implements Serializable {

    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private Double dosage;

    @NotNull
    private String commonReaction;

    @NotNull
    private String description;

    @NotNull
    private String contraindication;

    private Set<DiseaseDTO> diseases = new HashSet<>();

    private VaccineTypeDTO vaccineType;

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

    public Double getDosage() {
        return dosage;
    }

    public void setDosage(Double dosage) {
        this.dosage = dosage;
    }

    public String getCommonReaction() {
        return commonReaction;
    }

    public void setCommonReaction(String commonReaction) {
        this.commonReaction = commonReaction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getContraindication() {
        return contraindication;
    }

    public void setContraindication(String contraindication) {
        this.contraindication = contraindication;
    }


    public Set<DiseaseDTO> getDiseases() {
        return diseases;
    }

    public void setDiseases(Set<DiseaseDTO> diseases) {
        this.diseases = diseases;
    }

    public VaccineTypeDTO getVaccineType() {
        return vaccineType;
    }

    public void setVaccineType(VaccineTypeDTO vaccineType) {
        this.vaccineType = vaccineType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VaccineDTO)) {
            return false;
        }

        VaccineDTO vaccineDTO = (VaccineDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vaccineDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "VaccineDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", dosage=" + getDosage() +
            ", commonReaction='" + getCommonReaction() + "'" +
                ", description='" + getDescription() + "'" +
                ", contraindication='" + getContraindication() + "'" +
            ", diseases=" + getDiseases() +
            ", vaccineType=" + getVaccineType() +
            "}";
    }
}
