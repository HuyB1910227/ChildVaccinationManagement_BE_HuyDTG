package vn.huydtg.immunizationservice.service.dto;

import jakarta.validation.constraints.NotNull;
import vn.huydtg.immunizationservice.domain.enumeration.AgeType;
import vn.huydtg.immunizationservice.domain.enumeration.RequestType;

import java.io.Serializable;
import java.util.Objects;



public class AgeDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer minAge;

    @NotNull
    private AgeType minAgeType;

    private Integer maxAge;

    private AgeType maxAgeType;

    private String note;

    @NotNull
    private RequestType requestType;

    private VaccineDTO vaccine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public AgeType getMinAgeType() {
        return minAgeType;
    }

    public void setMinAgeType(AgeType minAgeType) {
        this.minAgeType = minAgeType;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public AgeType getMaxAgeType() {
        return maxAgeType;
    }

    public void setMaxAgeType(AgeType maxAgeType) {
        this.maxAgeType = maxAgeType;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }



    public VaccineDTO getVaccine() {
        return vaccine;
    }

    public void setVaccine(VaccineDTO vaccine) {
        this.vaccine = vaccine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgeDTO)) {
            return false;
        }

        AgeDTO ageDTO = (AgeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "AgeDTO{" +
            "id=" + getId() +
            ", minAge=" + getMinAge() +
            ", minAgeType='" + getMinAgeType() + "'" +
            ", maxAge=" + getMaxAge() +
            ", maxAgeType='" + getMaxAgeType() + "'" +
            ", vaccine=" + getVaccine() +
                ", note=" + getNote() +
                ", requestType=" + getRequestType() +
            "}";
    }
}
