package vn.huydtg.immunizationservice.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SuggestionDetailDTO implements Serializable {
    private List<AssignmentDTO> previousAssignments;

    private Optional<AssignmentDTO> currentAssignment;

    private DiseaseWithVaccineRelationshipDTO currentDisease;

    public SuggestionDetailDTO() {
    }

    public SuggestionDetailDTO(List<AssignmentDTO> previousAssignments, Optional<AssignmentDTO> currentAssignment, DiseaseWithVaccineRelationshipDTO currentDisease) {
        this.previousAssignments = previousAssignments;
        this.currentAssignment = currentAssignment;
        this.currentDisease = currentDisease;
    }

    public List<AssignmentDTO> getPreviousAssignments() {
        return previousAssignments;
    }

    public void setPreviousAssignments(List<AssignmentDTO> previousAssignments) {
        this.previousAssignments = previousAssignments;
    }

    public Optional<AssignmentDTO> getCurrentAssignment() {
        return currentAssignment;
    }

    public void setCurrentAssignment(Optional<AssignmentDTO> currentAssignment) {
        this.currentAssignment = currentAssignment;
    }


    public DiseaseWithVaccineRelationshipDTO getCurrentDisease() {
        return currentDisease;
    }

    public void setCurrentDisease(DiseaseWithVaccineRelationshipDTO currentDisease) {
        this.currentDisease = currentDisease;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuggestionDetailDTO that = (SuggestionDetailDTO) o;
        return Objects.equals(previousAssignments, that.previousAssignments) && Objects.equals(currentAssignment, that.currentAssignment) && Objects.equals(currentDisease, that.currentDisease);
    }

    @Override
    public int hashCode() {
        return Objects.hash(previousAssignments, currentAssignment, currentDisease);
    }

    @Override
    public String toString() {
        return "SuggestionDetailDTO{" +
                "previousAssignments=" + previousAssignments +
                ", currentAssignment=" + currentAssignment +
                ", currentDisease=" + currentDisease +
                '}';
    }
}
