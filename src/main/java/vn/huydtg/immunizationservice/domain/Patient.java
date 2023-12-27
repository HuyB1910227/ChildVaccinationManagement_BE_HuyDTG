package vn.huydtg.immunizationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "patient")
public class Patient implements Serializable {



    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", length = 36, nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "gender", nullable = false)
    private Integer gender;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "avatar")
    private String avatar;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "appointmentCard", "patient" }, allowSetters = true)
    private Set<History> histories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assignments", "history", "patient", "immunizationUnit", "employee" }, allowSetters = true)
    private Set<AppointmentCard> appointmentCards = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "patient" }, allowSetters = true)
    private Set<Nutrition> nutritions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "patients" }, allowSetters = true)
    private Customer customer;


    public UUID getId() {
        return this.id;
    }

    public Patient id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public Patient address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getGender() {
        return this.gender;
    }

    public Patient gender(Integer gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Patient dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Patient fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public Patient avatar(String avatar) {
        this.setAvatar(avatar);
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<History> getHistories() {
        return this.histories;
    }

    public void setHistories(Set<History> histories) {
        if (this.histories != null) {
            this.histories.forEach(i -> i.setPatient(null));
        }
        if (histories != null) {
            histories.forEach(i -> i.setPatient(this));
        }
        this.histories = histories;
    }

    public Patient histories(Set<History> histories) {
        this.setHistories(histories);
        return this;
    }

    public Patient addHistory(History history) {
        this.histories.add(history);
        history.setPatient(this);
        return this;
    }

    public Patient removeHistory(History history) {
        this.histories.remove(history);
        history.setPatient(null);
        return this;
    }

    public Set<AppointmentCard> getAppointmentCards() {
        return this.appointmentCards;
    }

    public void setAppointmentCards(Set<AppointmentCard> appointmentCards) {
        if (this.appointmentCards != null) {
            this.appointmentCards.forEach(i -> i.setPatient(null));
        }
        if (appointmentCards != null) {
            appointmentCards.forEach(i -> i.setPatient(this));
        }
        this.appointmentCards = appointmentCards;
    }

    public Patient appointmentCards(Set<AppointmentCard> appointmentCards) {
        this.setAppointmentCards(appointmentCards);
        return this;
    }

    public Patient addAppointmentCard(AppointmentCard appointmentCard) {
        this.appointmentCards.add(appointmentCard);
        appointmentCard.setPatient(this);
        return this;
    }

    public Patient removeAppointmentCard(AppointmentCard appointmentCard) {
        this.appointmentCards.remove(appointmentCard);
        appointmentCard.setPatient(null);
        return this;
    }

    public Set<Nutrition> getNutritions() {
        return this.nutritions;
    }

    public void setNutritions(Set<Nutrition> nutritions) {
        if (this.nutritions != null) {
            this.nutritions.forEach(i -> i.setPatient(null));
        }
        if (nutritions != null) {
            nutritions.forEach(i -> i.setPatient(this));
        }
        this.nutritions = nutritions;
    }

    public Patient nutritions(Set<Nutrition> nutritions) {
        this.setNutritions(nutritions);
        return this;
    }

    public Patient addNutrition(Nutrition nutrition) {
        this.nutritions.add(nutrition);
        nutrition.setPatient(this);
        return this;
    }

    public Patient removeNutrition(Nutrition nutrition) {
        this.nutritions.remove(nutrition);
        nutrition.setPatient(null);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Patient customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient)) {
            return false;
        }
        return id != null && id.equals(((Patient) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    @Override
    public String toString() {
        return "Patient{" +
                "id=" + getId() +
                ", address='" + getAddress() + "'" +
                ", gender=" + getGender() +
                ", dateOfBirth='" + getDateOfBirth() + "'" +
                ", fullName='" + getFullName() + "'" +
                ", avatar='" + getAvatar() + "'" +
                "}";
    }
}
