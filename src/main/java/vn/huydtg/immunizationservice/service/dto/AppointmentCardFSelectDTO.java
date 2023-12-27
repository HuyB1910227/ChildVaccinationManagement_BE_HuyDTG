package vn.huydtg.immunizationservice.service.dto;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class AppointmentCardFSelectDTO implements Serializable {

    private Long id;

    private Instant appointmentDateConfirmed;

    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentCardFSelectDTO that = (AppointmentCardFSelectDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(appointmentDateConfirmed, that.appointmentDateConfirmed) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appointmentDateConfirmed, status);
    }

    @Override
    public String toString() {
        return "AppointmentCardFSelectDTO{" +
                "id=" + id +
                ", appointmentDateConfirmed=" + appointmentDateConfirmed +
                ", status=" + status +
                '}';
    }
}
