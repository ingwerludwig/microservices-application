package com.javagrind.authservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class RolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NonNull
    @Column(name ="role_id", nullable = false)
    private Integer role_id;

    @Enumerated(EnumType.STRING)
    private Role roleName;

    public RolesEntity(Role role){
        this.setRoleName(role);
    }
}
