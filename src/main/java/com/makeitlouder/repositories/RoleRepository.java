package com.makeitlouder.repositories;

import com.makeitlouder.domain.security.Authority;
import com.makeitlouder.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(String name);
}
