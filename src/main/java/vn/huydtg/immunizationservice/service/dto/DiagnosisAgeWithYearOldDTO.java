package vn.huydtg.immunizationservice.service.dto;

import vn.huydtg.immunizationservice.domain.enumeration.AgeType;

import java.io.Serializable;

public class DiagnosisAgeWithYearOldDTO implements Serializable {

    private Long id;

    private Double minAge;

    private Double maxAge;

    private AgeType ageType;



    public DiagnosisAgeWithYearOldDTO() {
    }

    public DiagnosisAgeWithYearOldDTO(Long id, Double minAge, Double maxAge, AgeType ageType) {
        this.id = id;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.ageType = ageType;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMinAge() {
        return minAge;
    }

    public void setMinAge(Double minAge) {
        this.minAge = minAge;
    }

    public Double getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Double maxAge) {
        this.maxAge = maxAge;
    }

    public AgeType getAgeType() {
        return ageType;
    }

    public void setAgeType(AgeType ageType) {
        this.ageType = ageType;
    }




}
