package com.virtualpsychcare.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;

    private String password;

    @Column(name = "is_enable")
    private boolean isEnable;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;

    @Column(name = "acconut_non_expired")
    private boolean acconutNonExpired;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;
}
