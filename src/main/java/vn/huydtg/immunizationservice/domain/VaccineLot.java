package vn.huydtg.immunizationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import vn.huydtg.immunizationservice.domain.enumeration.VaccinationType;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vaccine_lot")
public class VaccineLot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "lot_code", nullable = false)
    private String lotCode;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "quantity_used", nullable = false)
    private Integer quantityUsed;

    @NotNull
    @Column(name = "expired_date", nullable = false)
    private Instant expiredDate;

    @NotNull
    @Column(name = "transaction_date", nullable = false)
    private Instant transactionDate;

    @NotNull
    @Column(name = "manufacturing_date", nullable = false)
    private Instant manufacturingDate;

    @NotNull
    @Column(name = "sale_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal salePrice;


    @Column(name = "status", nullable = true)
    private Integer status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "vaccination_type", nullable = false)
    private VaccinationType vaccinationType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vaccineLot")
    @JsonIgnoreProperties(value = { "appointmentCard", "vaccineLot" }, allowSetters = true)
    private Set<Assignment> assignments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "vaccineLots", "ages", "diseases", "vaccineType" }, allowSetters = true)
    private Vaccine vaccine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "vaccineLots" }, allowSetters = true)
    private Provider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "employees", "vaccineLots", "fixedSchedules", "appointmentCards" }, allowSetters = true)
    private ImmunizationUnit immunizationUnit;


    public Long getId() {
        return this.id;
    }

    public VaccineLot id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getLotCode() {
        return this.lotCode;
    }

    public VaccineLot lotCode(String lotCode) {
        this.setLotCode(lotCode);
        return this;
    }

    public void setLotCode(String lotCode) {
        this.lotCode = lotCode;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public VaccineLot quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantityUsed() {
        return this.quantityUsed;
    }

    public VaccineLot quantityUsed(Integer quantityUsed) {
        this.setQuantityUsed(quantityUsed);
        return this;
    }

    public void setQuantityUsed(Integer quantityUsed) {
        this.quantityUsed = quantityUsed;
    }

    public Instant getExpiredDate() {
        return this.expiredDate;
    }

    public VaccineLot expiredDate(Instant expiredDate) {
        this.setExpiredDate(expiredDate);
        return this;
    }

    public void setExpiredDate(Instant expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Instant getTransactionDate() {
        return this.transactionDate;
    }

    public VaccineLot transactionDate(Instant transactionDate) {
        this.setTransactionDate(transactionDate);
        return this;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Instant getManufacturingDate() {
        return this.manufacturingDate;
    }

    public VaccineLot manufacturingDate(Instant manufacturingDate) {
        this.setManufacturingDate(manufacturingDate);
        return this;
    }

    public void setManufacturingDate(Instant manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public BigDecimal getSalePrice() {
        return this.salePrice;
    }

    public VaccineLot salePrice(BigDecimal salePrice) {
        this.setSalePrice(salePrice);
        return this;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Set<Assignment> getAssignments() {
        return this.assignments;
    }

    public void setAssignments(Set<Assignment> assignments) {
        if (this.assignments != null) {
            this.assignments.forEach(i -> i.setVaccineLot(null));
        }
        if (assignments != null) {
            assignments.forEach(i -> i.setVaccineLot(this));
        }
        this.assignments = assignments;
    }

    public VaccineLot assignments(Set<Assignment> assignments) {
        this.setAssignments(assignments);
        return this;
    }

    public VaccineLot addAssignment(Assignment assignment) {
        this.assignments.add(assignment);
        assignment.setVaccineLot(this);
        return this;
    }

    public VaccineLot removeAssignment(Assignment assignment) {
        this.assignments.remove(assignment);
        assignment.setVaccineLot(null);
        return this;
    }

    public Vaccine getVaccine() {
        return this.vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public VaccineLot vaccine(Vaccine vaccine) {
        this.setVaccine(vaccine);
        return this;
    }

    public Provider getProvider() {
        return this.provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public VaccineLot provider(Provider provider) {
        this.setProvider(provider);
        return this;
    }

    public ImmunizationUnit getImmunizationUnit() {
        return this.immunizationUnit;
    }

    public void setImmunizationUnit(ImmunizationUnit immunizationUnit) {
        this.immunizationUnit = immunizationUnit;
    }

    public VaccineLot immunizationUnit(ImmunizationUnit immunizationUnit) {
        this.setImmunizationUnit(immunizationUnit);
        return this;
    }

    public Integer getStatus() {
        return this.status;
    }

    public VaccineLot status(Integer status) {
        this.setStatus(status);
        return this;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }


    public VaccinationType getVaccinationType() {
        return this.vaccinationType;
    }

    public VaccineLot vaccinationType(VaccinationType vaccinationType) {
        this.setVaccinationType(vaccinationType);
        return this;
    }

    public void setVaccinationType(VaccinationType vaccinationType) {
        this.vaccinationType = vaccinationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VaccineLot)) {
            return false;
        }
        return id != null && id.equals(((VaccineLot) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    @Override
    public String toString() {
        return "VaccineLot{" +
            "id=" + getId() +
            ", lotCode='" + getLotCode() + "'" +
            ", quantity=" + getQuantity() + ", quantityUsed=" + getQuantityUsed() +
            ", expiredDate='" + getExpiredDate() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", manufacturingDate='" + getManufacturingDate() + "'" +
            ", salePrice=" + getSalePrice() + ", status=" + getStatus() + ", vaccinationType=" + getVaccinationType() +
            "}";
    }
}
