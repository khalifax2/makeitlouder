package com.makeitlouder.service.impl;

import com.makeitlouder.domain.User;
import com.makeitlouder.domain.security.PasswordResetToken;
import com.makeitlouder.exceptions.UserServiceException;
import com.makeitlouder.repositories.PasswordResetTokenRepository;
import com.makeitlouder.repositories.UserRepository;
import com.makeitlouder.domain.security.UserPrincipal;
import com.makeitlouder.service.UserService;
import com.makeitlouder.shared.AmazonSES;
import com.makeitlouder.shared.Utils;
import com.makeitlouder.shared.dto.UserDto;
import com.makeitlouder.shared.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AmazonSES amazonSES;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> foundUser = userRepository.findByEmail(email);

        if (foundUser.isEmpty())
            throw new UsernameNotFoundException("Email: " + email + " not found!");

        return new UserPrincipal(foundUser.get());
    }

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
        user.setEmailVerificationToken(Utils.generateVerificationToken(user.getEmail()));
        user.getAddress().setUser(user);

        User createdUser = userRepository.save(user);
        UserDto savedUser = userMapper.UserEntityToUserDto(createdUser);

        amazonSES.verifyEmail(savedUser);

        return savedUser;
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> userDtoList = new ArrayList<>();

        if (page > 0) page -= 1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<User> userPage = userRepository.findAll(pageableRequest);
        List<User> users = userPage.getContent();

        for (User user : users) {
            UserDto userDto = userMapper.UserEntityToUserDto(user);
            userDtoList.add(userDto);
        }

        return userDtoList;
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


    @Override
    public boolean verifyEmailToken(String token) {
        boolean isVerified = false;

        User foundUser = userRepository.findUserByEmailVerificationToken(token).get();

        if (foundUser != null) {
            boolean hasTokenExpired = Utils.hasTokenExpired(token);
            if (!hasTokenExpired) {
                foundUser.setEmailVerificationToken(null);
                foundUser.setVerified(true);
                userRepository.save(foundUser);
                isVerified = true;
            }
        }

        return isVerified;
    }

    @Override
    public boolean requestPasswordReset(String email) {
        User foundUser = userRepository.findByEmail(email).get();

        if (foundUser == null) return false;

        String token = Utils.generatePasswordResetToken(foundUser.getId());

        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                                                                  .token(token)
                                                                  .user(foundUser).build();

        passwordResetTokenRepository.save(passwordResetToken);

        boolean sendPasswordResetToken = new AmazonSES().sendPasswordResetRequest(
                    foundUser.getFirstName(),
                    foundUser.getEmail(),
                    token
                );

        return sendPasswordResetToken;
    }

    @Override
    public boolean resetPassword(String password, String token) {
        boolean operationResult = false;

        if (Utils.hasTokenExpired(token)) return operationResult;

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        if (passwordResetToken != null) {
            String newEncodedPassword = bCryptPasswordEncoder.encode(password);

            User user = passwordResetToken.getUser();
            user.setEncryptedPassword(newEncodedPassword);
            User savedUser = userRepository.save(user);

            if (savedUser != null && savedUser.getEncryptedPassword().equals(newEncodedPassword)) {
                operationResult = true;
            }

            passwordResetTokenRepository.delete(passwordResetToken);

        } else {
            operationResult = false;
        }

        return operationResult;
    }
}
