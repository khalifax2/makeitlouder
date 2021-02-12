package com.makeitlouder.service.impl;

import com.makeitlouder.domain.User;
import com.makeitlouder.exceptions.UserServiceException;
import com.makeitlouder.repositories.UserRepository;
import com.makeitlouder.service.UserService;
import com.makeitlouder.shared.dto.UserDto;
import com.makeitlouder.shared.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto getUserByEmail(String email) throws UsernameNotFoundException {
        Optional<User> foundUser = userRepository.findByEmail(email);

        if (foundUser.isEmpty()) throw new UsernameNotFoundException(email);

        UserDto user = userMapper.UserEntityToUserDto(foundUser.get());

        return user;
    }

    @Override
    public UserDto getUserById(UUID id) throws UserServiceException {
        Optional<User> foundUser = userRepository.findById(id);

        if (foundUser.isEmpty()) throw new UserServiceException("User not found!");

        UserDto user = userMapper.UserEntityToUserDto(foundUser.get());

        return user;
    }

    @Override
    public UserDto createUser(UserDto userDetails) throws UserServiceException {
        Optional<User> foundUser = userRepository.findByEmail(userDetails.getEmail());

        if (foundUser.isPresent()) throw new UserServiceException("User already Exists!");

        User user = userMapper.UserDtoToUserEntity(userDetails);

        user.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        user.getAddress().setUser(user);

        User createdUser = userRepository.save(user);
        UserDto savedUser = userMapper.UserEntityToUserDto(createdUser);

        return savedUser;
    }

    @Override
    public UserDto updateUser(UUID id, UserDto userDetails) throws UserServiceException {
        Optional<User> foundUser = userRepository.findById(id);

        if (foundUser.isEmpty()) throw new UserServiceException("User not found!");

        foundUser.get().setFirstName(userDetails.getFirstName());
        foundUser.get().setLastName(userDetails.getLastName());

        User updatedUser = userRepository.save(foundUser.get());
        UserDto user = userMapper.UserEntityToUserDto(updatedUser);

        return user;
    }

    @Override
    public void deleteUser(UUID id) throws UserServiceException {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty()) throw new UserServiceException("User not found!");
        userRepository.delete(foundUser.get());
    }
}
