package vn.huydtg.immunizationservice.service.dto;
import java.io.Serializable;
import java.util.Set;

public class SuggestionForSelectDTO implements Serializable {

    private DiseaseFSelectDTO diseaseFSelectDTO;

    private Set<Long> assigmentIds;

    private String possibilityOfInjectionStatus;

    public SuggestionForSelectDTO() {
    }

    public SuggestionForSelectDTO(DiseaseFSelectDTO diseaseFSelectDTO, Set<Long> assigmentIds, String possibilityOfInjectionStatus) {
        this.diseaseFSelectDTO = diseaseFSelectDTO;
        this.assigmentIds = assigmentIds;
        this.possibilityOfInjectionStatus = possibilityOfInjectionStatus;
    }



    public DiseaseFSelectDTO getDiseaseFSelectDTO() {
        return diseaseFSelectDTO;
    }

    public void setDiseaseFSelectDTO(DiseaseFSelectDTO diseaseFSelectDTO) {
        this.diseaseFSelectDTO = diseaseFSelectDTO;
    }

    public Set<Long> getAssigmentIds() {
        return assigmentIds;
    }

    public void setAssigmentIds(Set<Long> assigmentIds) {
        this.assigmentIds = assigmentIds;
    }



    public String getPossibilityOfInjectionStatus() {
        return possibilityOfInjectionStatus;
    }

    public void setPossibilityOfInjectionStatus(String possibilityOfInjectionStatus) {
        this.possibilityOfInjectionStatus = possibilityOfInjectionStatus;
    }
}
