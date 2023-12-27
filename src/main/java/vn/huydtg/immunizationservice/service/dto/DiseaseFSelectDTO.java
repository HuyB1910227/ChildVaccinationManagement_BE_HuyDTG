package vn.huydtg.immunizationservice.service.dto;

import jakarta.persistence.Column;

import java.io.Serializable;


public class DiseaseFSelectDTO implements Serializable {

    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    public DiseaseFSelectDTO() {
    }

    public DiseaseFSelectDTO(Long id, String name) {
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

    @Override
    public String toString() {
        return "DiseaseFSelectDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
