package vn.huydtg.immunizationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "history")
public class History implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "vaccination_date", nullable = false)
    private Instant vaccinationDate;

    @Column(name = "status_after_injection")
    private String statusAfterInjection;

    @JsonIgnoreProperties(value = { "assignments", "history", "patient", "immunizationUnit", "employee" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private AppointmentCard appointmentCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "histories", "appointmentCards", "nutritions", "customer" }, allowSetters = true)
    private Patient patient;


    public Long getId() {
        return this.id;
    }

    public History id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getVaccinationDate() {
        return this.vaccinationDate;
    }

    public History vaccinationDate(Instant vaccinationDate) {
        this.setVaccinationDate(vaccinationDate);
        return this;
    }

    public void setVaccinationDate(Instant vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }

    public String getStatusAfterInjection() {
        return this.statusAfterInjection;
    }

    public History statusAfterInjection(String statusAfterInjection) {
        this.setStatusAfterInjection(statusAfterInjection);
        return this;
    }

    public void setStatusAfterInjection(String statusAfterInjection) {
        this.statusAfterInjection = statusAfterInjection;
    }

    public AppointmentCard getAppointmentCard() {
        return this.appointmentCard;
    }

    public void setAppointmentCard(AppointmentCard appointmentCard) {
        this.appointmentCard = appointmentCard;
    }

    public History appointmentCard(AppointmentCard appointmentCard) {
        this.setAppointmentCard(appointmentCard);
        return this;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public History patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof History)) {
            return false;
        }
        return id != null && id.equals(((History) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + getId() +
                ", vaccinationDate='" + getVaccinationDate() + "'" +
                ", statusAfterInjection='" + getStatusAfterInjection() + "'" +
                "}";
    }
}
