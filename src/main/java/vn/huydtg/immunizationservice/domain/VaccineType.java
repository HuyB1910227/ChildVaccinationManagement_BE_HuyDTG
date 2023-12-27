package vn.huydtg.immunizationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vaccine_type")
public class VaccineType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vaccineType")
    @JsonIgnoreProperties(value = { "vaccineLots", "ages", "diseases", "vaccineType" }, allowSetters = true)
    private Set<Vaccine> vaccines = new HashSet<>();


    public Long getId() {
        return this.id;
    }

    public VaccineType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public VaccineType name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Vaccine> getVaccines() {
        return this.vaccines;
    }

    public void setVaccines(Set<Vaccine> vaccines) {
        if (this.vaccines != null) {
            this.vaccines.forEach(i -> i.setVaccineType(null));
        }
        if (vaccines != null) {
            vaccines.forEach(i -> i.setVaccineType(this));
        }
        this.vaccines = vaccines;
    }

    public VaccineType vaccines(Set<Vaccine> vaccines) {
        this.setVaccines(vaccines);
        return this;
    }

    public VaccineType addVaccine(Vaccine vaccine) {
        this.vaccines.add(vaccine);
        vaccine.setVaccineType(this);
        return this;
    }

    public VaccineType removeVaccine(Vaccine vaccine) {
        this.vaccines.remove(vaccine);
        vaccine.setVaccineType(null);
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VaccineType)) {
            return false;
        }
        return id != null && id.equals(((VaccineType) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "VaccineType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
