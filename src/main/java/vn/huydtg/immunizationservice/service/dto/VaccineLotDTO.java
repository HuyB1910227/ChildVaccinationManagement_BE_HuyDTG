package vn.huydtg.immunizationservice.service.dto;

import jakarta.validation.constraints.NotNull;
import vn.huydtg.immunizationservice.domain.enumeration.VaccinationType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;


public class VaccineLotDTO implements Serializable {

    private Long id;



    @NotNull
    private String lotCode;

    @NotNull
    private Integer quantity;

    @NotNull
    private Integer quantityUsed;

    @NotNull
    private Instant expiredDate;

    @NotNull
    private Instant transactionDate;

    @NotNull
    private Instant manufacturingDate;

    @NotNull
    private BigDecimal salePrice;

    private VaccineDTO vaccine;

    private ProviderDTO provider;

    private ImmunizationUnitDTO immunizationUnit;

    private Integer status;

    @NotNull
    private VaccinationType vaccinationType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getLotCode() {
        return lotCode;
    }

    public void setLotCode(String lotCode) {
        this.lotCode = lotCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(Integer quantityUsed) {
        this.quantityUsed = quantityUsed;
    }

    public Instant getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Instant expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Instant getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(Instant manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public VaccineDTO getVaccine() {
        return vaccine;
    }

    public void setVaccine(VaccineDTO vaccine) {
        this.vaccine = vaccine;
    }

    public ProviderDTO getProvider() {
        return provider;
    }

    public void setProvider(ProviderDTO provider) {
        this.provider = provider;
    }

    public ImmunizationUnitDTO getImmunizationUnit() {
        return immunizationUnit;
    }

    public void setImmunizationUnit(ImmunizationUnitDTO immunizationUnit) {
        this.immunizationUnit = immunizationUnit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public VaccinationType getVaccinationType() {
        return vaccinationType;
    }

    public void setVaccinationType(VaccinationType vaccinationType) {
        this.vaccinationType = vaccinationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VaccineLotDTO)) {
            return false;
        }

        VaccineLotDTO vaccineLotDTO = (VaccineLotDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vaccineLotDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }


    @Override
    public String toString() {
        return "VaccineLotDTO{" +
            "id=" + getId() +
            ", lotCode='" + getLotCode() + "'" +
            ", quantity=" + getQuantity() +
                ", quantityUsed=" + getQuantityUsed() +
            ", expiredDate='" + getExpiredDate() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", manufacturingDate='" + getManufacturingDate() + "'" +
            ", salePrice=" + getSalePrice() +
            ", vaccine=" + getVaccine() +
            ", provider=" + getProvider() +
            ", immunizationUnit=" + getImmunizationUnit() +
                ", status=" + getStatus() +
                ", vaccinationType=" + getVaccinationType() +
            "}";
    }
}
