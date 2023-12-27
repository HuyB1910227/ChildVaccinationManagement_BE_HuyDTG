package vn.huydtg.immunizationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    public Long getId() {
        return this.id;
    }

    public Role id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Role name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        return id != null && id.equals(((Role) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                "}";
    }


    //relationship

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "roles", "employee", "administrator", "customer" }, allowSetters = true)
    private Set<User> users = new HashSet<>();

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        if (this.users != null) {
            this.users.forEach(i -> i.removeRole(this));
        }
        if (users != null) {
            users.forEach(i -> i.addRole(this));
        }
        this.users = users;
    }

    public Role users(Set<User> users) {
        this.setUsers(users);
        return this;
    }

    public Role addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Role removeUser(User user) {
        this.users.remove(user);
        return this;
    }
}
