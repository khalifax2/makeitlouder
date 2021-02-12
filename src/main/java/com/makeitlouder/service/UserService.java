package com.makeitlouder.service;

import com.makeitlouder.shared.dto.UserDto;

import java.util.UUID;

public interface UserService {
    UserDto createUser(UserDto userDetails);
    UserDto getUserByEmail(String email);
    UserDto getUserById(UUID id);
    UserDto updateUser(UUID id, UserDto userDetails);
    void deleteUser(UUID id);
}
