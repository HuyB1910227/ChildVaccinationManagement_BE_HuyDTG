package vn.huydtg.immunizationservice.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


public class HistoryDTO implements Serializable {


    private Long id;

    @NotNull
    private Instant vaccinationDate;

    private String statusAfterInjection;

    private AppointmentCardDTO appointmentCard;

    private PatientDTO patient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getVaccinationDate() {
        return vaccinationDate;
    }

    public void setVaccinationDate(Instant vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }

    public String getStatusAfterInjection() {
        return statusAfterInjection;
    }

    public void setStatusAfterInjection(String statusAfterInjection) {
        this.statusAfterInjection = statusAfterInjection;
    }

    public AppointmentCardDTO getAppointmentCard() {
        return appointmentCard;
    }

    public void setAppointmentCard(AppointmentCardDTO appointmentCard) {
        this.appointmentCard = appointmentCard;
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
        if (!(o instanceof HistoryDTO)) {
            return false;
        }

        HistoryDTO historyDTO = (HistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, historyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "HistoryDTO{" +
            "id='" + getId() + "'" +
            ", vaccinationDate='" + getVaccinationDate() + "'" +
            ", statusAfterInjection='" + getStatusAfterInjection() + "'" +
            ", appointmentCard=" + getAppointmentCard() +
            ", patient=" + getPatient() +
            "}";
    }
}
