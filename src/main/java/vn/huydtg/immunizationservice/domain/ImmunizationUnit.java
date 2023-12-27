package vn.huydtg.immunizationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "immunization_unit")
public class ImmunizationUnit implements Serializable {


    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", length = 36, nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "operating_licence", nullable = false)
    private String operatingLicence;

    @NotNull
    @Column(name = "hotline", nullable = false)
    private String hotline;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "immunizationUnit")
    @JsonIgnoreProperties(value = { "user", "appointmentCards", "immunizationUnit" }, allowSetters = true)
    private Set<Employee> employees = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "immunizationUnit")
    @JsonIgnoreProperties(value = { "assignments", "vaccine", "provider", "immunizationUnit" }, allowSetters = true)
    private Set<VaccineLot> vaccineLots = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "immunizationUnit")
    @JsonIgnoreProperties(value = { "immunizationUnit" }, allowSetters = true)
    private Set<FixedSchedule> fixedSchedules = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "immunizationUnit")
    @JsonIgnoreProperties(value = { "assignments", "history", "patient", "immunizationUnit", "employee" }, allowSetters = true)
    private Set<AppointmentCard> appointmentCards = new HashSet<>();


    public UUID getId() {
        return this.id;
    }

    public ImmunizationUnit id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ImmunizationUnit name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public ImmunizationUnit address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOperatingLicence() {
        return this.operatingLicence;
    }

    public ImmunizationUnit operatingLicence(String operatingLicence) {
        this.setOperatingLicence(operatingLicence);
        return this;
    }

    public void setOperatingLicence(String operatingLicence) {
        this.operatingLicence = operatingLicence;
    }

    public String getHotline() {
        return this.hotline;
    }

    public ImmunizationUnit hotline(String hotline) {
        this.setHotline(hotline);
        return this;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ImmunizationUnit isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public ImmunizationUnit latitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public ImmunizationUnit longitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.setImmunizationUnit(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setImmunizationUnit(this));
        }
        this.employees = employees;
    }

    public ImmunizationUnit employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public ImmunizationUnit addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setImmunizationUnit(this);
        return this;
    }

    public ImmunizationUnit removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setImmunizationUnit(null);
        return this;
    }

    public Set<VaccineLot> getVaccineLots() {
        return this.vaccineLots;
    }

    public void setVaccineLots(Set<VaccineLot> vaccineLots) {
        if (this.vaccineLots != null) {
            this.vaccineLots.forEach(i -> i.setImmunizationUnit(null));
        }
        if (vaccineLots != null) {
            vaccineLots.forEach(i -> i.setImmunizationUnit(this));
        }
        this.vaccineLots = vaccineLots;
    }

    public ImmunizationUnit vaccineLots(Set<VaccineLot> vaccineLots) {
        this.setVaccineLots(vaccineLots);
        return this;
    }

    public ImmunizationUnit addVaccineLot(VaccineLot vaccineLot) {
        this.vaccineLots.add(vaccineLot);
        vaccineLot.setImmunizationUnit(this);
        return this;
    }

    public ImmunizationUnit removeVaccineLot(VaccineLot vaccineLot) {
        this.vaccineLots.remove(vaccineLot);
        vaccineLot.setImmunizationUnit(null);
        return this;
    }

    public Set<FixedSchedule> getFixedSchedules() {
        return this.fixedSchedules;
    }

    public void setFixedSchedules(Set<FixedSchedule> fixedSchedules) {
        if (this.fixedSchedules != null) {
            this.fixedSchedules.forEach(i -> i.setImmunizationUnit(null));
        }
        if (fixedSchedules != null) {
            fixedSchedules.forEach(i -> i.setImmunizationUnit(this));
        }
        this.fixedSchedules = fixedSchedules;
    }

    public ImmunizationUnit fixedSchedules(Set<FixedSchedule> fixedSchedules) {
        this.setFixedSchedules(fixedSchedules);
        return this;
    }

    public ImmunizationUnit addFixedSchedule(FixedSchedule fixedSchedule) {
        this.fixedSchedules.add(fixedSchedule);
        fixedSchedule.setImmunizationUnit(this);
        return this;
    }

    public ImmunizationUnit removeFixedSchedule(FixedSchedule fixedSchedule) {
        this.fixedSchedules.remove(fixedSchedule);
        fixedSchedule.setImmunizationUnit(null);
        return this;
    }

    public Set<AppointmentCard> getAppointmentCards() {
        return this.appointmentCards;
    }

    public void setAppointmentCards(Set<AppointmentCard> appointmentCards) {
        if (this.appointmentCards != null) {
            this.appointmentCards.forEach(i -> i.setImmunizationUnit(null));
        }
        if (appointmentCards != null) {
            appointmentCards.forEach(i -> i.setImmunizationUnit(this));
        }
        this.appointmentCards = appointmentCards;
    }

    public ImmunizationUnit appointmentCards(Set<AppointmentCard> appointmentCards) {
        this.setAppointmentCards(appointmentCards);
        return this;
    }

    public ImmunizationUnit addAppointmentCard(AppointmentCard appointmentCard) {
        this.appointmentCards.add(appointmentCard);
        appointmentCard.setImmunizationUnit(this);
        return this;
    }

    public ImmunizationUnit removeAppointmentCard(AppointmentCard appointmentCard) {
        this.appointmentCards.remove(appointmentCard);
        appointmentCard.setImmunizationUnit(null);
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImmunizationUnit)) {
            return false;
        }
        return id != null && id.equals(((ImmunizationUnit) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "ImmunizationUnit{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", operatingLicence='" + getOperatingLicence() + "'" +
            ", hotline='" + getHotline() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            "}";
    }
}
