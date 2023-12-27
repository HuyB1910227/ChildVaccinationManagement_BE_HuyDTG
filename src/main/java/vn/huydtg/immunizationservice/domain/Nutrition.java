package vn.huydtg.immunizationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(name = "nutrition")
public class Nutrition implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Double weight;

    @NotNull
    @Column(name = "height", nullable = false)
    private Double height;

    @Column(name = "measurement_date")
    private LocalDate measurementDate;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "histories", "appointmentCards", "nutritions", "customer" }, allowSetters = true)
    private Patient patient;


    public Long getId() {
        return this.id;
    }

    public Nutrition id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getWeight() {
        return this.weight;
    }

    public Nutrition weight(Double weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return this.height;
    }

    public Nutrition height(Double height) {
        this.setHeight(height);
        return this;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public LocalDate getMeasurementDate() {
        return this.measurementDate;
    }

    public Nutrition measurementDate(LocalDate measurementDate) {
        this.setMeasurementDate(measurementDate);
        return this;
    }

    public void setMeasurementDate(LocalDate measurementDate) {
        this.measurementDate = measurementDate;
    }

    public String getStatus() {
        return this.status;
    }

    public Nutrition status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Nutrition patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nutrition)) {
            return false;
        }
        return id != null && id.equals(((Nutrition) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    @Override
    public String toString() {
        return "Nutrition{" +
            "id=" + getId() +
            ", weight=" + getWeight() +
            ", height=" + getHeight() +
            ", measurementDate='" + getMeasurementDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
