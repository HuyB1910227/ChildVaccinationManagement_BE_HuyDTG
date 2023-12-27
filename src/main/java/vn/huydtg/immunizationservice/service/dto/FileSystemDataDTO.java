package vn.huydtg.immunizationservice.service.dto;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
public class FileSystemDataDTO implements Serializable {

    private Long id;

    @NotNull
    private String filePath;

    @NotNull
    private String name;

    @NotNull
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileSystemDataDTO that = (FileSystemDataDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FileSystemDataDTO{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
