package com.makeitlouder.service;

import com.makeitlouder.domain.User;
import com.makeitlouder.shared.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDto createUser(UserDto userDetails);
    UserDto getUserByEmail(String email);
    UserDto getUserById(UUID id);
    List<UserDto> getUsers(int page, int limit);
    UserDto updateUser(UUID id, UserDto userDetails);
    void deleteUser(UUID id);
}
