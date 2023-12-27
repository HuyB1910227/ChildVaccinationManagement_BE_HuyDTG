package vn.huydtg.immunizationservice.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;



@Entity
@Table(name = "file_system_data")
public class FileSystemData extends AbstractAuditingTimeEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "file_path", nullable = false)
    private String filePath;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @Override
    public Long getId() {
        return id;
    }

    public FileSystemData id(Long id) {
        this.setId(id);
        return this;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return this.filePath;
    }
    public FileSystemData filePath(String filePath) {
        this.setFilePath(filePath);
        return this;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public String getName() {
        return this.name;
    }
    public FileSystemData name(String name) {
        this.setName(name);
        return this;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }
    public FileSystemData type(String type) {
        this.setType(type);
        return this;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileSystemData)) {
            return false;
        }
        return id != null && id.equals(((FileSystemData) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "FileSystemData{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", created_date='" + getCreatedDate() + '\'' +
                ", last_modified_date='" + getLastModifiedDate() + '\'' +
                '}';
    }
}
