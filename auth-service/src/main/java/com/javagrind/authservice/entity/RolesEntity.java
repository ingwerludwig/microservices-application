package com.javagrind.authservice.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "roles",schema="public")
@Entity
public class RolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq_gen")
    @SequenceGenerator(name = "role_id_seq_gen", sequenceName = "role_id_seq", allocationSize = 1)
    @Column(name ="role_id", nullable = false)
    @NotNull
    private Long role_id;

    @Enumerated(EnumType.STRING)
    private Role roleName;

    public RolesEntity(Role role){
        this.setRoleName(role);
    }
}
