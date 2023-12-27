package vn.huydtg.immunizationservice.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class HistoryFCustomerDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant vaccinationDate;

    private String statusAfterInjection;

    private PatientFCustomerDTO patientGeneralInformation;


    public HistoryFCustomerDTO() {
    }

    public HistoryFCustomerDTO(Long id, Instant vaccinationDate, String statusAfterInjection, PatientFCustomerDTO patientGeneralInformation) {
        this.id = id;
        this.vaccinationDate = vaccinationDate;
        this.statusAfterInjection = statusAfterInjection;
        this.patientGeneralInformation = patientGeneralInformation;
    }

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

    public PatientFCustomerDTO getPatientGeneralInformation() {
        return patientGeneralInformation;
    }

    public void setPatientGeneralInformation(PatientFCustomerDTO patientGeneralInformation) {
        this.patientGeneralInformation = patientGeneralInformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryFCustomerDTO that = (HistoryFCustomerDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(vaccinationDate, that.vaccinationDate) && Objects.equals(statusAfterInjection, that.statusAfterInjection) && Objects.equals(patientGeneralInformation, that.patientGeneralInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vaccinationDate, statusAfterInjection, patientGeneralInformation);
    }

    @Override
    public String toString() {
        return "HistoryFCustomerDTO{" +
                "id=" + id +
                ", vaccinationDate=" + vaccinationDate +
                ", statusAfterInjection='" + statusAfterInjection + '\'' +
                ", patientGeneralInformation=" + patientGeneralInformation +
                '}';
    }
}
