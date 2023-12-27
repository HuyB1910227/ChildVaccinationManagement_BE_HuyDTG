package vn.huydtg.immunizationservice.service.dto;

import vn.huydtg.immunizationservice.domain.enumeration.AgeType;
import vn.huydtg.immunizationservice.domain.enumeration.RequestType;

import java.util.List;

public class AgeFSelectDTO {

    private Long id;

    private Integer minAge;

    private AgeType minAgeType;

    private Integer maxAge;

    private AgeType maxAgeType;

    private RequestType requestType;

    private String note;

    private List<InjectionFAgeDTO> injections;


    public AgeFSelectDTO() {
    }




    public AgeFSelectDTO(Long id, Integer minAge, AgeType minAgeType, Integer maxAge, AgeType maxAgeType, RequestType requestType, String note, List<InjectionFAgeDTO> injections) {
        this.id = id;
        this.minAge = minAge;
        this.minAgeType = minAgeType;
        this.maxAge = maxAge;
        this.maxAgeType = maxAgeType;
        this.requestType = requestType;
        this.note = note;
        this.injections = injections;
    }

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

    public List<InjectionFAgeDTO> getInjections() {
        return injections;
    }

    public void setInjections(List<InjectionFAgeDTO> injections) {
        this.injections = injections;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "AgeFSelectDTO{" +
                "id=" + id +
                ", minAge=" + minAge +
                ", minAgeType=" + minAgeType +
                ", maxAge=" + maxAge +
                ", maxAgeType=" + maxAgeType +
                ", injections=" + injections +
                '}';
    }
}
