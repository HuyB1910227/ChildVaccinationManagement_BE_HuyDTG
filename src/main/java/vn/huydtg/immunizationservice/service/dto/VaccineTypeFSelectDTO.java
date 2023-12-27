package vn.huydtg.immunizationservice.service.dto;

import java.io.Serializable;

public class VaccineTypeFSelectDTO implements Serializable {

    private Long id;
    private String name;

    public VaccineTypeFSelectDTO() {
    }

    public VaccineTypeFSelectDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
