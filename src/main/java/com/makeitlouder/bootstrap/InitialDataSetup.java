package com.makeitlouder.bootstrap;

import com.makeitlouder.domain.Address;
import com.makeitlouder.domain.Pet;
import com.makeitlouder.domain.User;
import com.makeitlouder.domain.enumerated.Gender;
import com.makeitlouder.domain.enumerated.Status;
import com.makeitlouder.domain.enumerated.Type;
import com.makeitlouder.domain.security.Authority;
import com.makeitlouder.domain.security.Role;
import com.makeitlouder.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class InitialDataSetup {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PetRepository petRepository;



    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("APPLICATION READY EVENT STARTED...");

        Authority readAuthority = createAuthority("READ_AUTHORITY");
        Authority writeAuthority = createAuthority("WRITE_AUTHORITY");
        Authority deleteAuthority = createAuthority("DELETE_AUTHORITY");

        Role roleUser = createRole("ROLE_USER", new HashSet<>(Arrays.asList(readAuthority, writeAuthority)));
        Role roleAdmin = createRole("ROLE_ADMIN", new HashSet<>(Arrays.asList(readAuthority, writeAuthority, deleteAuthority)));

        if (roleAdmin == null) return;

        User adminUser = User.builder()
            .id(UUID.randomUUID())
            .firstName("Chupapi")
            .lastName("Karpalov")
            .email("admin@gmail.com")
            .isVerified(true)
            .encryptedPassword(bCryptPasswordEncoder.encode("123"))
            .roles(new HashSet<>(Arrays.asList(roleAdmin))).build();

        userRepository.save(adminUser);

        Address johnAddress = Address.builder()
                .street("Ariest St.")
                .city("WoyWoy")
                .state("NSW Sydney")
                .postalCode("4411")
                .build();

        User john = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .isVerified(true)
                .encryptedPassword(bCryptPasswordEncoder.encode("123"))
                .roles(new HashSet<>(Arrays.asList(roleUser)))
                .address(johnAddress)
                .build();

        john.getAddress().setUser(john);

        userRepository.save(john);

        Address janeAddress = Address.builder()
                .street("Gallant 7")
                .city("Lesure")
                .state("Brisbane")
                .postalCode("2222")
                .build();

        User jane = User.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .isVerified(true)
                .encryptedPassword(bCryptPasswordEncoder.encode("123"))
                .roles(new HashSet<>(Arrays.asList(roleUser)))
                .address(janeAddress)
                .build();

        jane.getAddress().setUser(jane);

        userRepository.save(jane);

        Pet slurpee = Pet.builder()
                .name("slurpee")
                .imagePath("/images/cardImg.png")
                .petType(Type.DOG)
                .petStatus(Status.AVAILABLE)
                .gender(Gender.FEMALE).build();

        Pet tyler = Pet.builder()
                .name("tyler")
                .imagePath("/images/cardImg.png")
                .petType(Type.DOG)
                .petStatus(Status.AVAILABLE)
                .gender(Gender.MALE).build();

        Pet cassey = Pet.builder()
                .name("cassey")
                .imagePath("/images/cardImg.png")
                .petType(Type.DOG)
                .petStatus(Status.AVAILABLE)
                .gender(Gender.FEMALE).build();

        Pet eklay = Pet.builder()
                .name("eklay")
                .imagePath("/images/cardImg.png")
                .petType(Type.CAT)
                .petStatus(Status.AVAILABLE)
                .gender(Gender.FEMALE).build();

        Pet doro = Pet.builder()
                .name("doro")
                .imagePath("/images/cardImg.png")
                .petType(Type.CAT)
                .petStatus(Status.AVAILABLE)
                .gender(Gender.MALE).build();

        Pet ponyang = Pet.builder()
                .name("ponyang")
                .imagePath("/images/cardImg.png")
                .petType(Type.CAT)
                .petStatus(Status.AVAILABLE)
                .gender(Gender.FEMALE).build();

        petRepository.save(slurpee);
        petRepository.save(tyler);
        petRepository.save(cassey);
        petRepository.save(eklay);
        petRepository.save(doro);
        petRepository.save(ponyang);


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
