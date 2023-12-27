package vn.huydtg.immunizationservice.service.dto;

import jakarta.validation.constraints.NotNull;
import vn.huydtg.immunizationservice.domain.enumeration.DistanceTimeType;

import java.io.Serializable;
import java.util.Objects;


public class InjectionDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer injectionTime;

    @NotNull
    private Integer distanceFromPrevious;

    @NotNull
    private DistanceTimeType distanceFromPreviousType;

    private AgeDTO age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInjectionTime() {
        return injectionTime;
    }

    public void setInjectionTime(Integer injectionTime) {
        this.injectionTime = injectionTime;
    }

    public Integer getDistanceFromPrevious() {
        return distanceFromPrevious;
    }

    public void setDistanceFromPrevious(Integer distanceFromPrevious) {
        this.distanceFromPrevious = distanceFromPrevious;
    }

    public DistanceTimeType getDistanceFromPreviousType() {
        return distanceFromPreviousType;
    }

    public void setDistanceFromPreviousType(DistanceTimeType distanceFromPreviousType) {
        this.distanceFromPreviousType = distanceFromPreviousType;
    }

    public AgeDTO getAge() {
        return age;
    }

    public void setAge(AgeDTO age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InjectionDTO)) {
            return false;
        }

        InjectionDTO injectionDTO = (InjectionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, injectionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "InjectionDTO{" +
            "id=" + getId() +
            ", injectionTime=" + getInjectionTime() +
            ", distanceFromPrevious=" + getDistanceFromPrevious() +
            ", distanceFromPreviousType='" + getDistanceFromPreviousType() + "'" +
            ", age=" + getAge() +
            "}";
    }
}
