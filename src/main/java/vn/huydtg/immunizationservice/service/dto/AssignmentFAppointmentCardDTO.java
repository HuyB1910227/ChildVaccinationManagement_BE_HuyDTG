package vn.huydtg.immunizationservice.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;


public class AssignmentFAppointmentCardDTO implements Serializable {
    private Long id;

    @NotNull
    private String route;

    @NotNull
    private Integer injectionTime;

    private Integer status;


    private Double dosage;

    private String note;


    @NotNull
    private BigDecimal price;


    private Instant injectionCompletionTime;

    private Instant nextAvailableInjectionDate;

    private VaccineLotDTO vaccineLot;


    public AssignmentFAppointmentCardDTO() {
    }




    public AssignmentFAppointmentCardDTO(Long id, String route, Integer injectionTime, Integer status, Double dosage, String note, BigDecimal price, Instant injectionCompletionTime, Instant nextAvailableInjectionDate, VaccineLotDTO vaccineLot) {
        this.id = id;
        this.route = route;
        this.injectionTime = injectionTime;
        this.status = status;
        this.dosage = dosage;
        this.note = note;
        this.price = price;
        this.injectionCompletionTime = injectionCompletionTime;
        this.nextAvailableInjectionDate = nextAvailableInjectionDate;
        this.vaccineLot = vaccineLot;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Integer getInjectionTime() {
        return injectionTime;
    }

    public void setInjectionTime(Integer injectionTime) {
        this.injectionTime = injectionTime;
    }

    public VaccineLotDTO getVaccineLot() {
        return vaccineLot;
    }

    public void setVaccineLot(VaccineLotDTO vaccineLot) {
        this.vaccineLot = vaccineLot;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Instant getInjectionCompletionTime() {
        return injectionCompletionTime;
    }

    public void setInjectionCompletionTime(Instant injectionCompletionTime) {
        this.injectionCompletionTime = injectionCompletionTime;
    }

    public Double getDosage() {
        return dosage;
    }

    public void setDosage(Double dosage) {
        this.dosage = dosage;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getNextAvailableInjectionDate() {
        return nextAvailableInjectionDate;
    }

    public void setNextAvailableInjectionDate(Instant nextAvailableInjectionDate) {
        this.nextAvailableInjectionDate = nextAvailableInjectionDate;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentFAppointmentCardDTO that = (AssignmentFAppointmentCardDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(route, that.route) && Objects.equals(injectionTime, that.injectionTime) && Objects.equals(status, that.status) && Objects.equals(dosage, that.dosage) && Objects.equals(note, that.note) && Objects.equals(price, that.price) && Objects.equals(injectionCompletionTime, that.injectionCompletionTime) && Objects.equals(nextAvailableInjectionDate, that.nextAvailableInjectionDate) && Objects.equals(vaccineLot, that.vaccineLot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, route, injectionTime, status, dosage, note, price, injectionCompletionTime, nextAvailableInjectionDate, vaccineLot);
    }
}
