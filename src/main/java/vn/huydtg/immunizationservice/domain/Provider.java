package vn.huydtg.immunizationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "provider")
public class Provider implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "provider")
    @JsonIgnoreProperties(value = { "assignments", "vaccine", "provider", "immunizationUnit" }, allowSetters = true)
    private Set<VaccineLot> vaccineLots = new HashSet<>();


    public Long getId() {
        return this.id;
    }

    public Provider id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Provider name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<VaccineLot> getVaccineLots() {
        return this.vaccineLots;
    }

    public void setVaccineLots(Set<VaccineLot> vaccineLots) {
        if (this.vaccineLots != null) {
            this.vaccineLots.forEach(i -> i.setProvider(null));
        }
        if (vaccineLots != null) {
            vaccineLots.forEach(i -> i.setProvider(this));
        }
        this.vaccineLots = vaccineLots;
    }

    public Provider vaccineLots(Set<VaccineLot> vaccineLots) {
        this.setVaccineLots(vaccineLots);
        return this;
    }

    public Provider addVaccineLot(VaccineLot vaccineLot) {
        this.vaccineLots.add(vaccineLot);
        vaccineLot.setProvider(this);
        return this;
    }

    public Provider removeVaccineLot(VaccineLot vaccineLot) {
        this.vaccineLots.remove(vaccineLot);
        vaccineLot.setProvider(null);
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Provider)) {
            return false;
        }
        return id != null && id.equals(((Provider) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    @Override
    public String toString() {
        return "Provider{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
