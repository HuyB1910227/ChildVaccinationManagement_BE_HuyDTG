package vn.huydtg.immunizationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "assignment")
public class Assignment implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "route", nullable = false)
    private String route;

    @NotNull
    @Column(name = "injection_time", nullable = false)
    private Integer injectionTime;

    @Column(name = "status", nullable = true)
    private Integer status;

    @NotNull
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;


    @Column(name = "injection_completion_time", nullable = true)
    private Instant injectionCompletionTime;

    @Column(name = "next_available_injection_date", nullable = true)
    private Instant nextAvailableInjectionDate;

    @NotNull
    @Column(name = "dosage", nullable = false)
    private Double dosage;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "assignments", "history", "patient", "immunizationUnit", "employee" }, allowSetters = true)
    private AppointmentCard appointmentCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "assignments", "vaccine", "provider", "immunizationUnit" }, allowSetters = true)
    private VaccineLot vaccineLot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "assignments" }, allowSetters = true)
    private Injection injection;



    public Long getId() {
        return this.id;
    }

    public Assignment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoute() {
        return this.route;
    }

    public Assignment route(String route) {
        this.setRoute(route);
        return this;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Integer getInjectionTime() {
        return this.injectionTime;
    }

    public Assignment injectionTime(Integer injectionTime) {
        this.setInjectionTime(injectionTime);
        return this;
    }

    public void setInjectionTime(Integer injectionTime) {
        this.injectionTime = injectionTime;
    }

    public AppointmentCard getAppointmentCard() {
        return this.appointmentCard;
    }

    public void setAppointmentCard(AppointmentCard appointmentCard) {
        this.appointmentCard = appointmentCard;
    }

    public Assignment appointmentCard(AppointmentCard appointmentCard) {
        this.setAppointmentCard(appointmentCard);
        return this;
    }

    public VaccineLot getVaccineLot() {
        return this.vaccineLot;
    }

    public void setVaccineLot(VaccineLot vaccineLot) {
        this.vaccineLot = vaccineLot;
    }

    public Assignment vaccineLot(VaccineLot vaccineLot) {
        this.setVaccineLot(vaccineLot);
        return this;
    }


    public Integer getStatus() {
        return this.status;
    }

    public Assignment status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Assignment price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public Instant getInjectionCompletionTime() {
        return this.injectionCompletionTime;
    }

    public Assignment injectionCompletionTime(Instant injectionCompletionTime) {
        this.setInjectionCompletionTime(injectionCompletionTime);
        return this;
    }

    public void setInjectionCompletionTime(Instant injectionCompletionTime) {
        this.injectionCompletionTime = injectionCompletionTime;
    }

    public Instant getNextAvailableInjectionDate() {
        return this.nextAvailableInjectionDate;
    }

    public Assignment nextAvailableInjectionDate(Instant nextAvailableInjectionDate) {
        this.setNextAvailableInjectionDate(nextAvailableInjectionDate);
        return this;
    }

    public void setNextAvailableInjectionDate(Instant nextAvailableInjectionDate) {
        this.nextAvailableInjectionDate = nextAvailableInjectionDate;
    }


    public Injection getInjection() {
        return this.injection;
    }

    public void setInjection(Injection injection) {
        this.injection = injection;
    }

    public Assignment injection(Injection injection) {
        this.setInjection(injection);
        return this;
    }


    public Double getDosage() {
        return this.dosage;
    }

    public Assignment dosage(Double dosage) {
        this.setDosage(dosage);
        return this;
    }

    public void setDosage(Double dosage) {
        this.dosage = dosage;
    }

    public String getNote() {
        return this.note;
    }

    public Assignment note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Assignment)) {
            return false;
        }
        return id != null && id.equals(((Assignment) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Assignment{" +
            "id=" + getId() +
            ", route='" + getRoute() + "'" +
            ", injectionTime=" + getInjectionTime() +
                ", status=" + getStatus() +
                ", price=" + getPrice() +
                ", injectionCompletionTime=" + getInjectionCompletionTime() +
                ", nextAvailableInjectionDate=" + getNextAvailableInjectionDate() +
                ", dosage=" + getDosage() +
                ", note=" + getNote() +
            "}";
    }
}
