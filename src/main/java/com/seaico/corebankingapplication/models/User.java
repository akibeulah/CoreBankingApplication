package com.seaico.corebankingapplication.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seaico.corebankingapplication.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String fullName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "userId", cascade = CascadeType.DETACH)
    @JsonIgnore
    private List<Activity> activities;
    @OneToMany(mappedBy = "userId", cascade = CascadeType.DETACH)
    @JsonIgnore
    private List<Account> accounts;
    @OneToMany(mappedBy = "userId", cascade = CascadeType.DETACH)
    @JsonIgnore
    private List<Pin> pins;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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

    public List<Activity> getActivities() {
        return activities.subList(0, Math.min(activities.size(), 5));
    }

    public List<Pin> getPins() {
        List<Pin> reversedPins = this.pins;
        Collections.reverse(reversedPins);
        return reversedPins;
    }

    public String getPin() {
        return getPins().get(0).getHashedPinCode();
    }
}
