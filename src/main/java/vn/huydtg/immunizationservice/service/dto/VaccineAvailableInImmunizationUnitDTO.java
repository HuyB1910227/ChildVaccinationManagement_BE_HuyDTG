package vn.huydtg.immunizationservice.service.dto;

import vn.huydtg.immunizationservice.domain.enumeration.VaccinationType;

import java.util.Objects;
import java.util.UUID;

public class VaccineAvailableInImmunizationUnitDTO {

    private UUID id;

    private String name;

    private VaccinationType vaccinationType;


    public VaccineAvailableInImmunizationUnitDTO() {
    }

    public VaccineAvailableInImmunizationUnitDTO(UUID id, String name, VaccinationType vaccinationType) {
        this.id = id;
        this.name = name;
        this.vaccinationType = vaccinationType;
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

    public VaccinationType getVaccinationType() {
        return vaccinationType;
    }

    public void setVaccinationType(VaccinationType vaccinationType) {
        this.vaccinationType = vaccinationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VaccineAvailableInImmunizationUnitDTO that = (VaccineAvailableInImmunizationUnitDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && vaccinationType == that.vaccinationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, vaccinationType);
    }

    @Override
    public String toString() {
        return "VaccineAvailableInImmunizationUnitDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", vaccinationType=" + vaccinationType +
                '}';
    }
}
