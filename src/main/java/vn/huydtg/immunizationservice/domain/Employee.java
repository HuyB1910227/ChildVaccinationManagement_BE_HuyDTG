package vn.huydtg.immunizationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", length = 36, nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "employee_id", nullable = false)
    private String employeeId;

    @NotNull
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotNull
    @Column(name = "identity_card", nullable = false, unique = true)
    private String identityCard;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "gender", nullable = false)
    private Long gender;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "avatar")
    private String avatar;


    public UUID getId() {
        return this.id;
    }

    public Employee id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public Employee employeeId(String employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPhone() {
        return this.phone;
    }

    public Employee phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public Employee email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Employee fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdentityCard() {
        return this.identityCard;
    }

    public Employee identityCard(String identityCard) {
        this.setIdentityCard(identityCard);
        return this;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getAddress() {
        return this.address;
    }

    public Employee address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getGender() {
        return this.gender;
    }

    public Employee gender(Long gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Long gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Employee dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public Employee avatar(String avatar) {
        this.setAvatar(avatar);
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }






    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", employeeId='" + employeeId + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", identityCard='" + identityCard + '\'' +
                ", address='" + address + '\'' +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", avatar='" + avatar + '\'' +
                ", user=" + user +
                ", immunizationUnit=" + immunizationUnit +
                ", appointmentCards=" + appointmentCards +
                '}';
    }

    //relationships
    @JsonIgnoreProperties(value = { "roles", "employee", "administrator", "customer" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User user;
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Employee user(User user) {
        this.setUser(user);
        return this;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "employees", "vaccineLots", "fixedSchedules", "appointmentCards" }, allowSetters = true)
    private ImmunizationUnit immunizationUnit;

    public ImmunizationUnit getImmunizationUnit() {
        return this.immunizationUnit;
    }

    public void setImmunizationUnit(ImmunizationUnit immunizationUnit) {
        this.immunizationUnit = immunizationUnit;
    }

    public Employee immunizationUnit(ImmunizationUnit immunizationUnit) {
        this.setImmunizationUnit(immunizationUnit);
        return this;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    @JsonIgnoreProperties(value = { "assignments", "history", "patient", "immunizationUnit", "employee" }, allowSetters = true)
    private Set<AppointmentCard> appointmentCards = new HashSet<>();


    public Set<AppointmentCard> getAppointmentCards() {
        return this.appointmentCards;
    }

    public void setAppointmentCards(Set<AppointmentCard> appointmentCards) {
        if (this.appointmentCards != null) {
            this.appointmentCards.forEach(i -> i.setEmployee(null));
        }
        if (appointmentCards != null) {
            appointmentCards.forEach(i -> i.setEmployee(this));
        }
        this.appointmentCards = appointmentCards;
    }

    public Employee appointmentCards(Set<AppointmentCard> appointmentCards) {
        this.setAppointmentCards(appointmentCards);
        return this;
    }

    public Employee addAppointmentCard(AppointmentCard appointmentCard) {
        this.appointmentCards.add(appointmentCard);
        appointmentCard.setEmployee(this);
        return this;
    }

    public Employee removeAppointmentCard(AppointmentCard appointmentCard) {
        this.appointmentCards.remove(appointmentCard);
        appointmentCard.setEmployee(null);
        return this;
    }

}
