package vn.huydtg.immunizationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "disease")
public class Disease implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "diseases")
    @JsonIgnoreProperties(value = { "vaccineLots", "ages", "diseases", "vaccineType" }, allowSetters = true)
    private Set<Vaccine> vaccines = new HashSet<>();


    public Long getId() {
        return this.id;
    }

    public Disease id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Disease name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Disease description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Vaccine> getVaccines() {
        return this.vaccines;
    }

    public void setVaccines(Set<Vaccine> vaccines) {
        if (this.vaccines != null) {
            this.vaccines.forEach(i -> i.removeDisease(this));
        }
        if (vaccines != null) {
            vaccines.forEach(i -> i.addDisease(this));
        }
        this.vaccines = vaccines;
    }

    public Disease vaccines(Set<Vaccine> vaccines) {
        this.setVaccines(vaccines);
        return this;
    }

    public Disease addVaccine(Vaccine vaccine) {
        this.vaccines.add(vaccine);
        vaccine.getDiseases().add(this);
        return this;
    }

    public Disease removeVaccine(Vaccine vaccine) {
        this.vaccines.remove(vaccine);
        vaccine.getDiseases().remove(this);
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Disease)) {
            return false;
        }
        return id != null && id.equals(((Disease) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Disease{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
