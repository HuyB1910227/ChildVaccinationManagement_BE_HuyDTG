package vn.huydtg.immunizationservice.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;


public class AssignmentDTO implements Serializable {

    private Long id;

    @NotNull
    private String route;

    @NotNull
    private Integer injectionTime;


    private Integer status;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Double dosage;

    private String note;


    private Instant injectionCompletionTime;

    private Instant nextAvailableInjectionDate;


    private AppointmentCardDTO appointmentCard;

    private VaccineLotDTO vaccineLot;

    private InjectionDTO injection;


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

    public AppointmentCardDTO getAppointmentCard() {
        return appointmentCard;
    }

    public void setAppointmentCard(AppointmentCardDTO appointmentCard) {
        this.appointmentCard = appointmentCard;
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

    public Instant getNextAvailableInjectionDate() {
        return nextAvailableInjectionDate;
    }

    public void setNextAvailableInjectionDate(Instant nextAvailableInjectionDate) {
        this.nextAvailableInjectionDate = nextAvailableInjectionDate;
    }

    public InjectionDTO getInjection() {
        return injection;
    }

    public void setInjection(InjectionDTO injection) {
        this.injection = injection;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssignmentDTO)) {
            return false;
        }

        AssignmentDTO assignmentDTO = (AssignmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, assignmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }


    @Override
    public String toString() {
        return "AssignmentDTO{" +
                "id=" + id +
                ", route='" + route + '\'' +
                ", injectionTime=" + injectionTime +
                ", status=" + status +
                ", price=" + price +
                ", injectionCompletionTime=" + injectionCompletionTime +
                ", nextAvailableInjectionDate=" + nextAvailableInjectionDate +
                ", appointmentCard=" + appointmentCard +
                ", vaccineLot=" + vaccineLot +
                ", dosage=" + dosage +
                ", note="   + note +
                '}';
    }
}
