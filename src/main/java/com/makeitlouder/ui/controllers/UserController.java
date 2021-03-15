package com.makeitlouder.ui.controllers;

import com.makeitlouder.security.SecurityConstants;
import com.makeitlouder.service.UserService;
import com.makeitlouder.shared.dto.UserDto;
import com.makeitlouder.shared.mappers.UserMapper;
import com.makeitlouder.ui.model.request.PasswordResetModel;
import com.makeitlouder.ui.model.request.PasswordResetRequestModel;
import com.makeitlouder.ui.model.request.UserDetailsRequestModel;
import com.makeitlouder.ui.model.response.OperationalStatusModel;
import com.makeitlouder.ui.model.response.UserRest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "2") int limit
    ) {
        List<UserRest> userList = new ArrayList<>();
        List<UserDto> foundUsers = userService.getUsers(page, limit);

        for (UserDto userDto : foundUsers) {
            UserRest user = userMapper.UserDtoToUserRest(userDto);
            userList.add(user);
        }

        return userList;
    }

    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    @PutMapping("/{id}")
    public UserRest updateUser(@PathVariable UUID id, @RequestBody UserDetailsRequestModel userDetails) {
        UserDto userDto = userMapper.UserDetailsRequestToUserDto(userDetails);
        UserDto updatedUser = userService.updateUser(id, userDto);
        UserRest user = userMapper.UserDtoToUserRest(updatedUser);

        return user;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public OperationalStatusModel deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        OperationalStatusModel operationStatus = OperationalStatusModel
                .builder().operationName("DELETE").operationResult("SUCCESS").build();

        return operationStatus;
    }


    @GetMapping("/email-verification")
    public OperationalStatusModel verifyEmailToken(@RequestParam(value = "token") String token) {
        OperationalStatusModel operation = new OperationalStatusModel();

        boolean isVerified = userService.verifyEmailToken(token);
        operation.setOperationName("VERIFY_EMAIL");

        if (!isVerified) {
            operation.setOperationResult("ERROR");
        } else {
            operation.setOperationResult("SUCCESS");
        }

        return operation;
    }

    @PostMapping("/password-reset-request")
    public OperationalStatusModel requestPasswordReset(@RequestBody PasswordResetRequestModel passwordReset) {
        OperationalStatusModel operation = new OperationalStatusModel();

        boolean operationResult = userService.requestPasswordReset(passwordReset.getEmail());
        operation.setOperationName("PASSWORD_RESET_REQUEST");

        if (!operationResult) {
            operation.setOperationResult("ERROR");
        } else {
            operation.setOperationResult("SUCCESS");
        }

        return operation;
    }


    @PostMapping("/password-reset")
    public OperationalStatusModel resetPassword(@RequestBody PasswordResetModel passwordReset) {
        OperationalStatusModel operation = new OperationalStatusModel();

        boolean operationResult = userService.resetPassword(passwordReset.getPassword(), passwordReset.getToken());

        operation.setOperationName("PASSWORD_RESET_REQUEST");

        if (!operationResult) {
            operation.setOperationResult("ERROR");
        } else {
            operation.setOperationResult("SUCCESS");
        }

        return operation;
    }

}
