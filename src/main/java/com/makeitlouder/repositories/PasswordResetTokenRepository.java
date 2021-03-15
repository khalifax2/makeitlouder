package com.makeitlouder.repositories;

import com.makeitlouder.domain.security.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, UUID> {
    PasswordResetToken findByToken(String token);
}
