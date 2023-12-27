package vn.huydtg.immunizationservice.service.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;


import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class NotificationTokenDTO implements Serializable {


    private Long id;

    @NotNull
    private String registrationToken;

    private Instant createdDate;

    @JsonIgnore
    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationTokenDTO)) {
            return false;
        }

        NotificationTokenDTO notificationTokenDTO = (NotificationTokenDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, notificationTokenDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "NotificationTokenDTO{" +
                "id=" + id +
                ", registrationToken='" + registrationToken + '\'' +
                ", createdDate=" + createdDate +
                ", user=" + user +
                '}';
    }
}
