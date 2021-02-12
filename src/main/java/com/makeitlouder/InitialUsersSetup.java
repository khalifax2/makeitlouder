package com.makeitlouder;

import com.makeitlouder.domain.User;
import com.makeitlouder.domain.security.Authority;
import com.makeitlouder.domain.security.Role;
import com.makeitlouder.repositories.RoleRepository;
import com.makeitlouder.repositories.AuthorityRepository;
import com.makeitlouder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class InitialUsersSetup {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("APPLICATION READY EVENT STARTED...");

        Authority readAuthority = createAuthority("READ_AUTHORITY");
        Authority writeAuthority = createAuthority("WRITE_AUTHORITY");
        Authority deleteAuthority = createAuthority("DELETE_AUTHORITY");

        createRole("ROLE_USER", new HashSet<>(Arrays.asList(readAuthority, writeAuthority)));
        Role roleAdmin = createRole("ROLE_ADMIN", new HashSet<>(Arrays.asList(readAuthority, writeAuthority, deleteAuthority)));

        if (roleAdmin == null) return;

        User adminUser = new User();
        adminUser.setFirstName("Chupapi");
        adminUser.setLastName("Karpalov");
        adminUser.setEmail("admin@gmail.com");
        adminUser.setEmailVerificationStatus(true);
        adminUser.setId(UUID.randomUUID());
        adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("123"));
        adminUser.setRoles(new HashSet<>(Arrays.asList(roleAdmin)));

        userRepository.save(adminUser);
    }

    private Authority createAuthority(String name) {
        Authority authority = authorityRepository.findByName(name);

        if (authority == null) {
            authority = Authority.builder().name(name).build();
            authorityRepository.save(authority);
        }
        return authority;
    }

    private Role createRole(String name, Set<Authority> authorities) {
        Role role = roleRepository.findByName(name);

        if (role == null) {
            role = Role.builder().name(name).build();
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;
    }

}
