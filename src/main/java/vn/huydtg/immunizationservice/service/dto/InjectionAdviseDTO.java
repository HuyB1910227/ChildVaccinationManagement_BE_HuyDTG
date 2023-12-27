package vn.huydtg.immunizationservice.service.dto;

import java.io.Serializable;
import java.util.List;

public class InjectionAdviseDTO implements Serializable {

    private Long injectionIdAdvise;

    private Integer injectionTimeIdAdvise;

    private List<LastInjectionFAdviseDTO> lastInjections;

    public InjectionAdviseDTO() {
    }

    public InjectionAdviseDTO(Long injectionIdAdvise, Integer injectionTimeIdAdvise, List<LastInjectionFAdviseDTO> lastInjections) {
        this.injectionIdAdvise = injectionIdAdvise;
        this.injectionTimeIdAdvise = injectionTimeIdAdvise;
        this.lastInjections = lastInjections;
    }

    public Long getInjectionIdAdvise() {
        return injectionIdAdvise;
    }

    public void setInjectionIdAdvise(Long injectionIdAdvise) {
        this.injectionIdAdvise = injectionIdAdvise;
    }

    public Integer getInjectionTimeIdAdvise() {
        return injectionTimeIdAdvise;
    }

    public void setInjectionTimeIdAdvise(Integer injectionTimeIdAdvise) {
        this.injectionTimeIdAdvise = injectionTimeIdAdvise;
    }

    public List<LastInjectionFAdviseDTO> getLastInjections() {
        return lastInjections;
    }

    public void setLastInjections(List<LastInjectionFAdviseDTO> lastInjections) {
        this.lastInjections = lastInjections;
    }

    @Override
    public String toString() {
        return "InjectionAdviseDTO{" +
                "injectionIdAdvise=" + injectionIdAdvise +
                ", injectionTimeIdAdvise=" + injectionTimeIdAdvise +
                ", lastInjections=" + lastInjections +
                '}';
    }
}
