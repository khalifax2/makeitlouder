package com.makeitlouder.shared.mappers;

import com.makeitlouder.domain.security.Role;
import com.makeitlouder.repositories.RoleRepository;
import com.makeitlouder.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Transient;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final RoleRepository roleRepository;

    public Set<Role> roleStringToRoleEntity(Set<String> roles) {
        Set<Role> rolesEntity = new HashSet<>();
        for (String role : roles) {
            Role roleEntity = roleRepository.findByName(role);
            rolesEntity.add(roleEntity);
        }
        return rolesEntity;
    }

    public Set<String> roleEntityToRoleString(Set<Role> roles) {
        Set<String> newRoles = new HashSet<>();
        for (Role role : roles) {
            Role roleEntity = roleRepository.findByName(role.getName());
            newRoles.add(roleEntity.getName());
        }
        return newRoles;
    }

}
