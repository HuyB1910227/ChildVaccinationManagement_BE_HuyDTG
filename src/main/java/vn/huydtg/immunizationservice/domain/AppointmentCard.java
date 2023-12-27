package vn.huydtg.immunizationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

import java.time.Instant;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "appointment_card")
public class AppointmentCard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "appointment_date")
    private Instant appointmentDate;

    @Column(name = "appointment_date_confirmed")
    private Instant appointmentDateConfirmed;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "note")
    private String note;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appointmentCard")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "appointmentCard", "vaccineLot" }, allowSetters = true)
    private Set<Assignment> assignments = new HashSet<>();

    @JsonIgnoreProperties(value = { "appointmentCard", "patient" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "appointmentCard")
    private History history;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "histories", "appointmentCards", "nutritions", "customer" }, allowSetters = true)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "employees", "vaccineLots", "fixedSchedules", "appointmentCards" }, allowSetters = true)
    private ImmunizationUnit immunizationUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "appointmentCards", "immunizationUnit" }, allowSetters = true)
    private Employee employee;



    public Long getId() {
        return this.id;
    }

    public AppointmentCard id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getAppointmentDate() {
        return this.appointmentDate;
    }

    public AppointmentCard appointmentDate(Instant appointmentDate) {
        this.setAppointmentDate(appointmentDate);
        return this;
    }

    public void setAppointmentDate(Instant appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Instant getAppointmentDateConfirmed() {
        return this.appointmentDateConfirmed;
    }

    public AppointmentCard appointmentDateConfirmed(Instant appointmentDateConfirmed) {
        this.setAppointmentDateConfirmed(appointmentDateConfirmed);
        return this;
    }

    public void setAppointmentDateConfirmed(Instant appointmentDateConfirmed) {
        this.appointmentDateConfirmed = appointmentDateConfirmed;
    }

    public Integer getStatus() {
        return this.status;
    }

    public AppointmentCard status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNote() {
        return this.note;
    }

    public AppointmentCard note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Set<Assignment> getAssignments() {
        return this.assignments;
    }

    public void setAssignments(Set<Assignment> assignments) {
        if (this.assignments != null) {
            this.assignments.forEach(i -> i.setAppointmentCard(null));
        }
        if (assignments != null) {
            assignments.forEach(i -> i.setAppointmentCard(this));
        }
        this.assignments = assignments;
    }

    public AppointmentCard assignments(Set<Assignment> assignments) {
        this.setAssignments(assignments);
        return this;
    }

    public AppointmentCard addAssignment(Assignment assignment) {
        this.assignments.add(assignment);
        assignment.setAppointmentCard(this);
        return this;
    }

    public AppointmentCard removeAssignment(Assignment assignment) {
        this.assignments.remove(assignment);
        assignment.setAppointmentCard(null);
        return this;
    }

    public History getHistory() {
        return this.history;
    }

    public void setHistory(History history) {
        if (this.history != null) {
            this.history.setAppointmentCard(null);
        }
        if (history != null) {
            history.setAppointmentCard(this);
        }
        this.history = history;
    }

    public AppointmentCard history(History history) {
        this.setHistory(history);
        return this;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public AppointmentCard patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    public ImmunizationUnit getImmunizationUnit() {
        return this.immunizationUnit;
    }

    public void setImmunizationUnit(ImmunizationUnit immunizationUnit) {
        this.immunizationUnit = immunizationUnit;
    }

    public AppointmentCard immunizationUnit(ImmunizationUnit immunizationUnit) {
        this.setImmunizationUnit(immunizationUnit);
        return this;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public AppointmentCard employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppointmentCard)) {
            return false;
        }
        return id != null && id.equals(((AppointmentCard) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "AppointmentCard{" +
                "id=" + getId() +
                ", appointmentDate='" + getAppointmentDate() + "'" +
                ", appointmentDateConfirmed='" + getAppointmentDateConfirmed() + "'" +
                ", status=" + getStatus() +
                ", note='" + getNote() + "'" +
                "}";
    }
}
