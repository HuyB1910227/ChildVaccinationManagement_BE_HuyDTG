package vn.huydtg.immunizationservice.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


public class NutritionDTO implements Serializable {

    private Long id;

    @NotNull
    private Double weight;

    @NotNull
    private Double height;

    private LocalDate measurementDate;

    private String status;

    private PatientDTO patient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public LocalDate getMeasurementDate() {
        return measurementDate;
    }

    public void setMeasurementDate(LocalDate measurementDate) {
        this.measurementDate = measurementDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NutritionDTO)) {
            return false;
        }

        NutritionDTO nutritionDTO = (NutritionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, nutritionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "NutritionDTO{" +
            "id=" + getId() +
            ", weight=" + getWeight() +
            ", height=" + getHeight() +
            ", measurementDate='" + getMeasurementDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", patient=" + getPatient() +
            "}";
    }
}
