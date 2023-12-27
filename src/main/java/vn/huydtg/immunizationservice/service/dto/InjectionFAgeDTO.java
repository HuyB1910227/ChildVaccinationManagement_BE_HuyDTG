package vn.huydtg.immunizationservice.service.dto;

import vn.huydtg.immunizationservice.domain.enumeration.DistanceTimeType;

import java.io.Serializable;

public class InjectionFAgeDTO implements Serializable {

    private Long id;


    private Integer injectionTime;


    private Integer distanceFromPrevious;


    private DistanceTimeType distanceFromPreviousType;

    public InjectionFAgeDTO() {
    }

    public InjectionFAgeDTO(Long id, Integer injectionTime, Integer distanceFromPrevious, DistanceTimeType distanceFromPreviousType) {
        this.id = id;
        this.injectionTime = injectionTime;
        this.distanceFromPrevious = distanceFromPrevious;
        this.distanceFromPreviousType = distanceFromPreviousType;
    }

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

    @Override
    public String toString() {
        return "InjectionFAgeDTO{" +
                "id=" + id +
                ", injectionTime=" + injectionTime +
                ", distanceFromPrevious=" + distanceFromPrevious +
                ", distanceFromPreviousType=" + distanceFromPreviousType +
                '}';
    }
}
