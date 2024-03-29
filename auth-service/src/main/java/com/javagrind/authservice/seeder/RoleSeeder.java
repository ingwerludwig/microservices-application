package com.javagrind.authservice.seeder;
import com.javagrind.authservice.entity.Role;
import com.javagrind.authservice.entity.RolesEntity;
import com.javagrind.authservice.repositories.RoleRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RoleSeeder {
    private RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void seedRoles() {
        if (roleRepository.count() == 0) {
            for (Role role : Role.values()) {
                roleRepository.save(new RolesEntity(role));
            }
        }
    }
}