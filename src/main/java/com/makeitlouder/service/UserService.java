package com.makeitlouder.service;

import com.makeitlouder.domain.User;
import com.makeitlouder.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDetails);
    UserDto getUserByEmail(String email);
    UserDto getUserById(UUID id);
    List<UserDto> getUsers(int page, int limit);
    UserDto updateUser(UUID id, UserDto userDetails);
    void deleteUser(UUID id);
    boolean verifyEmailToken(String token);
    boolean requestPasswordReset(String email);
    boolean resetPassword(String password, String token);
}
