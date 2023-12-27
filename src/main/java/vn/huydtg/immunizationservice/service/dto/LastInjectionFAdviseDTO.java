package vn.huydtg.immunizationservice.service.dto;

import java.io.Serializable;
import java.time.Instant;

public class LastInjectionFAdviseDTO implements Serializable {

    private Long assignmentId;

    private Long injectionId;

    private Instant injectionCompletionTime;

    private Integer injectionTime;

    public LastInjectionFAdviseDTO(Long assignmentId, Long injectionId, Instant injectionCompletionTime, Integer injectionTime) {
        this.assignmentId = assignmentId;
        this.injectionId = injectionId;
        this.injectionCompletionTime = injectionCompletionTime;
        this.injectionTime = injectionTime;
    }

    public LastInjectionFAdviseDTO() {
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Long getInjectionId() {
        return injectionId;
    }

    public void setInjectionId(Long injectionId) {
        this.injectionId = injectionId;
    }

    public Instant getInjectionCompletionTime() {
        return injectionCompletionTime;
    }

    public void setInjectionCompletionTime(Instant injectionCompletionTime) {
        this.injectionCompletionTime = injectionCompletionTime;
    }

    public Integer getInjectionTime() {
        return injectionTime;
    }

    public void setInjectionTime(Integer injectionTime) {
        this.injectionTime = injectionTime;
    }

    @Override
    public String toString() {
        return "InjectionFAdviseDTO{" +
                "assignmentId=" + assignmentId +
                ", injectionId=" + injectionId +
                ", injectionCompletionTime=" + injectionCompletionTime +
                ", injectionTime=" + injectionTime +
                '}';
    }
}
