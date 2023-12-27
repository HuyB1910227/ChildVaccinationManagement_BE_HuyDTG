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
@Table(name = "vaccine")
public class Vaccine implements Serializable {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", length = 36, nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "dosage", nullable = false)
    private Double dosage;

    @NotNull
    @Column(name = "common_reaction", nullable = false)
    private String commonReaction;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "contraindication", nullable = false)
    private String contraindication;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vaccine")
    @JsonIgnoreProperties(value = { "assignments", "vaccine", "provider", "immunizationUnit" }, allowSetters = true)
    private Set<VaccineLot> vaccineLots = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vaccine")
    @JsonIgnoreProperties(value = { "injections", "vaccine" }, allowSetters = true)
    private Set<Age> ages = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_vaccine__disease",
        joinColumns = @JoinColumn(name = "vaccine_id"),
        inverseJoinColumns = @JoinColumn(name = "disease_id")
    )
    @JsonIgnoreProperties(value = { "vaccines" }, allowSetters = true)
    private Set<Disease> diseases = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "vaccines" }, allowSetters = true)
    private VaccineType vaccineType;


    public UUID getId() {
        return this.id;
    }

    public Vaccine id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Vaccine name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDosage() {
        return this.dosage;
    }

    public Vaccine dosage(Double dosage) {
        this.setDosage(dosage);
        return this;
    }

    public void setDosage(Double dosage) {
        this.dosage = dosage;
    }

    public String getCommonReaction() {
        return this.commonReaction;
    }

    public Vaccine commonReaction(String commonReaction) {
        this.setCommonReaction(commonReaction);
        return this;
    }

    public void setCommonReaction(String commonReaction) {
        this.commonReaction = commonReaction;
    }

    public Set<VaccineLot> getVaccineLots() {
        return this.vaccineLots;
    }

    public void setVaccineLots(Set<VaccineLot> vaccineLots) {
        if (this.vaccineLots != null) {
            this.vaccineLots.forEach(i -> i.setVaccine(null));
        }
        if (vaccineLots != null) {
            vaccineLots.forEach(i -> i.setVaccine(this));
        }
        this.vaccineLots = vaccineLots;
    }

    public Vaccine vaccineLots(Set<VaccineLot> vaccineLots) {
        this.setVaccineLots(vaccineLots);
        return this;
    }

    public Vaccine addVaccineLot(VaccineLot vaccineLot) {
        this.vaccineLots.add(vaccineLot);
        vaccineLot.setVaccine(this);
        return this;
    }

    public Vaccine removeVaccineLot(VaccineLot vaccineLot) {
        this.vaccineLots.remove(vaccineLot);
        vaccineLot.setVaccine(null);
        return this;
    }

    public Set<Age> getAges() {
        return this.ages;
    }

    public void setAges(Set<Age> ages) {
        if (this.ages != null) {
            this.ages.forEach(i -> i.setVaccine(null));
        }
        if (ages != null) {
            ages.forEach(i -> i.setVaccine(this));
        }
        this.ages = ages;
    }

    public Vaccine ages(Set<Age> ages) {
        this.setAges(ages);
        return this;
    }

    public Vaccine addAge(Age age) {
        this.ages.add(age);
        age.setVaccine(this);
        return this;
    }

    public Vaccine removeAge(Age age) {
        this.ages.remove(age);
        age.setVaccine(null);
        return this;
    }

    public Set<Disease> getDiseases() {
        return this.diseases;
    }

    public void setDiseases(Set<Disease> diseases) {
        this.diseases = diseases;
    }

    public Vaccine diseases(Set<Disease> diseases) {
        this.setDiseases(diseases);
        return this;
    }

    public Vaccine addDisease(Disease disease) {
        this.diseases.add(disease);
        disease.getVaccines().add(this);
        return this;
    }

    public Vaccine removeDisease(Disease disease) {
        this.diseases.remove(disease);
        disease.getVaccines().remove(this);
        return this;
    }

    public VaccineType getVaccineType() {
        return this.vaccineType;
    }

    public void setVaccineType(VaccineType vaccineType) {
        this.vaccineType = vaccineType;
    }

    public Vaccine vaccineType(VaccineType vaccineType) {
        this.setVaccineType(vaccineType);
        return this;
    }


    public String getDescription() {
        return this.description;
    }

    public Vaccine description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getContraindication() {
        return this.contraindication;
    }

    public Vaccine contraindication(String contraindication) {
        this.setContraindication(contraindication);
        return this;
    }

    public void setContraindication(String contraindication) {
        this.contraindication = contraindication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vaccine)) {
            return false;
        }
        return id != null && id.equals(((Vaccine) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Vaccine{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dosage=" + getDosage() +
            ", commonReaction='" + getCommonReaction() + "'" +
                ", description='" + getDescription() + "'" +
                ", contraindication='" + getContraindication() + "'" +
            "}";
    }
}
