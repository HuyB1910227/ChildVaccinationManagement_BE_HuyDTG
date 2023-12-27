package vn.huydtg.immunizationservice.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class AssignmentInjectionForDiaryDTO implements Serializable {

    private Long assignmentId;

    private Long diseaseId;

    public AssignmentInjectionForDiaryDTO() {
    }

    public AssignmentInjectionForDiaryDTO(Long assignmentId, Long diseaseId) {
        this.assignmentId = assignmentId;
        this.diseaseId = diseaseId;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Long getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Long diseaseId) {
        this.diseaseId = diseaseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentInjectionForDiaryDTO that = (AssignmentInjectionForDiaryDTO) o;
        return Objects.equals(assignmentId, that.assignmentId) && Objects.equals(diseaseId, that.diseaseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignmentId, diseaseId);
    }

    @Override
    public String toString() {
        return "AssignmentInjectionForDiaryDTO{" +
                "assignmentId=" + assignmentId +
                ", diseaseId=" + diseaseId +
                '}';
    }
}
