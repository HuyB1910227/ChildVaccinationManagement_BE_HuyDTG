package vn.huydtg.immunizationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import vn.huydtg.immunizationservice.domain.enumeration.DistanceTimeType;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "injection")
public class Injection implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "injection_time", nullable = false)
    private Integer injectionTime;

    @NotNull
    @Column(name = "distance_from_previous", nullable = false)
    private Integer distanceFromPrevious;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "distance_from_previous_type", nullable = false)
    private DistanceTimeType distanceFromPreviousType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "injections", "vaccine" }, allowSetters = true)
    private Age age;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "injection")
    @JsonIgnoreProperties(value = { "appointmentCard", "vaccineLot", "injection" }, allowSetters = true)
    private Set<Assignment> assignments = new HashSet<>();


    public Long getId() {
        return this.id;
    }

    public Injection id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInjectionTime() {
        return this.injectionTime;
    }

    public Injection injectionTime(Integer injectionTime) {
        this.setInjectionTime(injectionTime);
        return this;
    }

    public void setInjectionTime(Integer injectionTime) {
        this.injectionTime = injectionTime;
    }

    public Integer getDistanceFromPrevious() {
        return this.distanceFromPrevious;
    }

    public Injection distanceFromPrevious(Integer distanceFromPrevious) {
        this.setDistanceFromPrevious(distanceFromPrevious);
        return this;
    }

    public void setDistanceFromPrevious(Integer distanceFromPrevious) {
        this.distanceFromPrevious = distanceFromPrevious;
    }

    public DistanceTimeType getDistanceFromPreviousType() {
        return this.distanceFromPreviousType;
    }

    public Injection distanceFromPreviousType(DistanceTimeType distanceFromPreviousType) {
        this.setDistanceFromPreviousType(distanceFromPreviousType);
        return this;
    }

    public void setDistanceFromPreviousType(DistanceTimeType distanceFromPreviousType) {
        this.distanceFromPreviousType = distanceFromPreviousType;
    }

    public Age getAge() {
        return this.age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public Injection age(Age age) {
        this.setAge(age);
        return this;
    }


    public Set<Assignment> getAssignments() {
        return this.assignments;
    }

    public void setAssignments(Set<Assignment> assignments) {
        if (this.assignments != null) {
            this.assignments.forEach(i -> i.setInjection(null));
        }
        if (assignments != null) {
            assignments.forEach(i -> i.setInjection(this));
        }
        this.assignments = assignments;
    }

    public Injection assignments(Set<Assignment> assignments) {
        this.setAssignments(assignments);
        return this;
    }

    public Injection addAssignment(Assignment assignment) {
        this.assignments.add(assignment);
        assignment.setInjection(this);
        return this;
    }

    public Injection removeAssignment(Assignment assignment) {
        this.assignments.remove(assignment);
        assignment.setInjection(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Injection)) {
            return false;
        }
        return id != null && id.equals(((Injection) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Injection{" +
            "id=" + getId() +
            ", injectionTime=" + getInjectionTime() +
            ", distanceFromPrevious=" + getDistanceFromPrevious() +
            ", distanceFromPreviousType='" + getDistanceFromPreviousType() + "'" +
            "}";
    }
}
