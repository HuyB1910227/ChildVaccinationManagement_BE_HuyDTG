package vn.huydtg.immunizationservice.service.dto;

import java.io.Serializable;
import java.util.UUID;

public class VaccineFSelectDTO implements Serializable {

    private UUID Id;

    private String name;

    public VaccineFSelectDTO() {
    }

    public VaccineFSelectDTO(UUID id, String name) {
        Id = id;
        this.name = name;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
