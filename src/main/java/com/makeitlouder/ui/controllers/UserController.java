package com.makeitlouder.ui.controllers;

import com.makeitlouder.service.UserService;
import com.makeitlouder.shared.dto.UserDto;
import com.makeitlouder.shared.mappers.UserMapper;
import com.makeitlouder.ui.model.request.UserDetailsRequestModel;
import com.makeitlouder.ui.model.response.OperationalStatusModel;
import com.makeitlouder.ui.model.response.UserRest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public UserRest getUserById(@PathVariable UUID id) {
        UserDto foundUser = userService.getUserById(id);
        UserRest user = userMapper.UserDtoToUserRest(foundUser);
        return user;
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {

        UserDto userDto = userMapper.UserDetailsRequestToUserDto(userDetails);
        userDto.setRoles(new HashSet<>(Arrays.asList("ROLE_USER")));

        UserDto createdUser = userService.createUser(userDto);
        UserRest user = userMapper.UserDtoToUserRest(createdUser);

        return user;
    }

    @PutMapping("/{id}")
    public UserRest updateUser(@PathVariable UUID id, @RequestBody UserDetailsRequestModel userDetails) {
        UserDto userDto = userMapper.UserDetailsRequestToUserDto(userDetails);
        UserDto updatedUser = userService.updateUser(id, userDto);
        UserRest user = userMapper.UserDtoToUserRest(updatedUser);

        return user;
    }

    @DeleteMapping("/{id}")
    public OperationalStatusModel deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        OperationalStatusModel operationStatus = OperationalStatusModel
                .builder().operationName("DELETE").operationResult("SUCCESS").build();

        return operationStatus;
    }

}
