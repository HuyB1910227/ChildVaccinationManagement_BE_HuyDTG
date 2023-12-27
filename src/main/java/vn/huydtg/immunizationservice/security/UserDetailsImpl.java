package vn.huydtg.immunizationservice.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.huydtg.immunizationservice.domain.User;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


public class UserDetailsImpl implements UserDetails {
    private UUID id;

    private String username;

    private String phone;

    private Boolean isEnable;

    @JsonIgnore
    private String password;

    private UUID immunizationUnitId;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(UUID id, String username, String phone, Boolean isEnable, String password, UUID immunizationUnitId, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.isEnable = isEnable;
        this.password = password;
        this.immunizationUnitId = immunizationUnitId;
        this.authorities = authorities;

    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        UUID immunizationUnitId = null;
        if (user.getEmployee()!=null){
           immunizationUnitId = user.getEmployee().getImmunizationUnit().getId();
        }
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPhone(),
                user.getIsEnable(),
                user.getPassword(),
                immunizationUnitId,
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public UUID getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public UUID getImmunizationUnitId() {
        return immunizationUnitId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
