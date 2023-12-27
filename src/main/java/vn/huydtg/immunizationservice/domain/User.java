package vn.huydtg.immunizationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", length = 36, nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "is_enable", nullable = false)
    private Boolean isEnable;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;


    public UUID getId() {
        return this.id;
    }

    public User id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public User username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return this.phone;
    }

    public User phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return this.password;
    }

    public User password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsEnable() {
        return this.isEnable;
    }

    public User isEnable(Boolean isEnable) {
        this.setIsEnable(isEnable);
        return this;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }



    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public User createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public User updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", username='" + getUsername() + "'" +
                ", phone='" + getPhone() + "'" +
                ", password='" + getPassword() + "'" +
                ", isEnable='" + getIsEnable() + "'" +
                ", createdAt='" + getCreatedAt() + "'" +
                ", updatedAt='" + getUpdatedAt() + "'" +
                "}";
    }

    //Relationships
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "rel_user__role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "users" }, allowSetters = true)
    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User roles(Set<Role> roles) {
        this.setRoles(roles);
        return this;
    }

    public User addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
        return this;
    }

    public User removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
        return this;
    }

    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Employee employee;

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        if (this.employee != null) {
            this.employee.setUser(null);
        }
        if (employee != null) {
            employee.setUser(this);
        }
        this.employee = employee;
    }

    public User employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }


    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Administrator administrator;

    public Administrator getAdministrator() {
        return this.administrator;
    }

    public void setAdministrator(Administrator administrator) {
        if (this.administrator != null) {
            this.administrator.setUser(null);
        }
        if (administrator != null) {
            administrator.setUser(this);
        }
        this.administrator = administrator;
    }

    public User administrator(Administrator administrator) {
        this.setAdministrator(administrator);
        return this;
    }


    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Customer customer;

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        if (this.customer != null) {
            this.customer.setUser(null);
        }
        if (customer != null) {
            customer.setUser(this);
        }
        this.customer = customer;
    }

    public User customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }


    //add relationship to Notification Token and Notification
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private Set<NotificationToken> notificationTokens = new HashSet<>();


    public Set<NotificationToken> getNotificationTokens() {
        return this.notificationTokens;
    }

    public void setNotificationTokens(Set<NotificationToken> notificationTokens) {
        if (this.notificationTokens != null) {
            this.notificationTokens.forEach(i -> i.setUser(null));
        }
        if (notificationTokens != null) {
            notificationTokens.forEach(i -> i.setUser(this));
        }
        this.notificationTokens = notificationTokens;
    }

    public User notificationTokens(Set<NotificationToken> notificationTokens) {
        this.setNotificationTokens(notificationTokens);
        return this;
    }

    public User addNotificationToken(NotificationToken notificationToken) {
        this.notificationTokens.add(notificationToken);
        notificationToken.setUser(this);
        return this;
    }

    public User removeNotificationToken(NotificationToken notificationToken) {
        this.notificationTokens.remove(notificationToken);
        notificationToken.setUser(null);
        return this;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private Set<Notification> notifications = new HashSet<>();


    public Set<Notification> getNotifications() {
        return this.notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        if (this.notifications != null) {
            this.notifications.forEach(i -> i.setUser(null));
        }
        if (notifications != null) {
            notifications.forEach(i -> i.setUser(this));
        }
        this.notifications = notifications;
    }

    public User notifications(Set<Notification> notifications) {
        this.setNotifications(notifications);
        return this;
    }

    public User addNotification(Notification notification) {
        this.notifications.add(notification);
        notification.setUser(this);
        return this;
    }

    public User removeNotification(Notification notification) {
        this.notifications.remove(notification);
        notification.setUser(null);
        return this;
    }


}
