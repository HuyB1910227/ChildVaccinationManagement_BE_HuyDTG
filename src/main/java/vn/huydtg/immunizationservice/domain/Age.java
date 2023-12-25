package vn.huydtg.immunizationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import vn.huydtg.immunizationservice.domain.enumeration.AgeType;
import vn.huydtg.immunizationservice.domain.enumeration.RequestType;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "age")
public class Age implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "min_age", nullable = false)
    private Integer minAge;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "min_age_type", nullable = false)
    private AgeType minAgeType;

    @Column(name = "max_age")
    private Integer maxAge;

    @Enumerated(EnumType.STRING)
    @Column(name = "max_age_type")
    private AgeType maxAgeType;


    @Column(name = "note")
    private String note;

    @NotNull
    @Column(name = "request_type")
    @Enumerated(EnumType.STRING)
    private RequestType requestType;



    //relationships
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "age")
    @JsonIgnoreProperties(value = { "age" }, allowSetters = true)
    private Set<Injection> injections = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "vaccineLots", "ages", "diseases", "vaccineType" }, allowSetters = true)
    private Vaccine vaccine;


    public Long getId() {
        return this.id;
    }

    public Age id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMinAge() {
        return this.minAge;
    }

    public Age minAge(Integer minAge) {
        this.setMinAge(minAge);
        return this;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public AgeType getMinAgeType() {
        return this.minAgeType;
    }

    public Age minAgeType(AgeType minAgeType) {
        this.setMinAgeType(minAgeType);
        return this;
    }

    public void setMinAgeType(AgeType minAgeType) {
        this.minAgeType = minAgeType;
    }

    public Integer getMaxAge() {
        return this.maxAge;
    }

    public Age maxAge(Integer maxAge) {
        this.setMaxAge(maxAge);
        return this;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public AgeType getMaxAgeType() {
        return this.maxAgeType;
    }

    public Age maxAgeType(AgeType maxAgeType) {
        this.setMaxAgeType(maxAgeType);
        return this;
    }

    public void setMaxAgeType(AgeType maxAgeType) {
        this.maxAgeType = maxAgeType;
    }

    public Set<Injection> getInjections() {
        return this.injections;
    }

    public void setInjections(Set<Injection> injections) {
        if (this.injections != null) {
            this.injections.forEach(i -> i.setAge(null));
        }
        if (injections != null) {
            injections.forEach(i -> i.setAge(this));
        }
        this.injections = injections;
    }

    public Age injections(Set<Injection> injections) {
        this.setInjections(injections);
        return this;
    }

    public Age addInjection(Injection injection) {
        this.injections.add(injection);
        injection.setAge(this);
        return this;
    }

    public Age removeInjection(Injection injection) {
        this.injections.remove(injection);
        injection.setAge(null);
        return this;
    }

    public Vaccine getVaccine() {
        return this.vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public Age vaccine(Vaccine vaccine) {
        this.setVaccine(vaccine);
        return this;
    }

    public String getNote() {
        return this.note;
    }

    public Age note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public RequestType getRequestType() {
        return this.requestType;
    }

    public Age requestType(RequestType requestType) {
        this.setRequestType(requestType);
        return this;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Age)) {
            return false;
        }
        return id != null && id.equals(((Age) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Age{" +
            "id=" + getId() +
            ", minAge=" + getMinAge() +
            ", minAgeType='" + getMinAgeType() + "'" +
            ", maxAge=" + getMaxAge() +
            ", maxAgeType='" + getMaxAgeType() + "'" +
                ", note='" + getNote() + "'" +
                ", requestType='" + getRequestType() + "'" +
            "}";
    }
}
