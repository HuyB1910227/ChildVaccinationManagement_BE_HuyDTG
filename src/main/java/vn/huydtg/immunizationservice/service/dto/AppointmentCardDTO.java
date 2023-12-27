package vn.huydtg.immunizationservice.service.dto;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


public class AppointmentCardDTO implements Serializable {

    private Long id;

    private Instant appointmentDate;

    private Instant appointmentDateConfirmed;

    @NotNull
    private Integer status;

    private String note;

    private PatientDTO patient;

    private ImmunizationUnitDTO immunizationUnit;

    private EmployeeDTO employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Instant appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Instant getAppointmentDateConfirmed() {
        return appointmentDateConfirmed;
    }

    public void setAppointmentDateConfirmed(Instant appointmentDateConfirmed) {
        this.appointmentDateConfirmed = appointmentDateConfirmed;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    public ImmunizationUnitDTO getImmunizationUnit() {
        return immunizationUnit;
    }

    public void setImmunizationUnit(ImmunizationUnitDTO immunizationUnit) {
        this.immunizationUnit = immunizationUnit;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppointmentCardDTO)) {
            return false;
        }

        AppointmentCardDTO appointmentCardDTO = (AppointmentCardDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appointmentCardDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }


    @Override
    public String toString() {
        return "AppointmentCardDTO{" +
                "id=" + getId() +
                ", appointmentDate='" + getAppointmentDate() + "'" +
                ", appointmentDateConfirmed='" + getAppointmentDateConfirmed() + "'" +
                ", status=" + getStatus() +
                ", note='" + getNote() + "'" +
                ", patient=" + getPatient() +
                ", immunizationUnit=" + getImmunizationUnit() +
                ", employee=" + getEmployee() +
                "}";
    }
}

