package com.perscolas.fintracker.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isAccountNonExpired;
    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isAccountNonLocked;
    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isCredentialsNonExpired;
    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isEnabled;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_access_role", joinColumns = @JoinColumn(name = "user_access_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

}