package com.project.secondhand.service;

import com.project.secondhand.dto.CreateUserRequest;
import com.project.secondhand.dto.UpdateUserRequest;
import com.project.secondhand.dto.UserDto;
import com.project.secondhand.dto.UserDtoConverter;
import com.project.secondhand.exceptions.UserIsNotActiveException;
import com.project.secondhand.exceptions.UserNotFoundException;
import com.project.secondhand.model.User;
import com.project.secondhand.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserDtoConverter userDtoConverter;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    public UserService(UserRepository userRepository, UserDtoConverter userDtoConverter) {
        this.userRepository = userRepository;
        this.userDtoConverter = userDtoConverter;
    }

    private User findUserByEmail(final String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User not found: %s", email)));
    }

    protected User findUserById(final Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User not found: %s", id)));
    }

    private void changeActivateUser(final Long id, final Boolean activate) {
        User user = findUserById(id);

        if (user.getIsActive() == activate) {
            throw new IllegalStateException("User is already in the desired state.");
        }

        user.setIsActive(activate);
        userRepository.save(user);
    }



    public List<UserDto> getAllUsers() {
        return userDtoConverter.convert(userRepository.findAll());
    }

    public UserDto getUserByEmail(final String email) {
        User user = findUserByEmail(email);
        if (user == null) {
            // Loglama ve hata mesajı eklenebilir
            throw new UserNotFoundException("User not found: " + email);
        }
        return userDtoConverter.convert(user);
    }

    public UserDto createUser(final CreateUserRequest createUserRequest) {
        User user = new User(null,
                createUserRequest.getFirstName(),
                createUserRequest.getMiddleName(),
                createUserRequest.getLastName(),
                createUserRequest.getEmail(),
                false);
        return userDtoConverter.convert(userRepository.save(user));
    }


    public void deactivateUser(final Long id) {
        changeActivateUser(id, false);
    }

    public void activateUser(final Long id ) {
        changeActivateUser(id, true);
    }

    public void deleteUser(Long id) {
        findUserById(id);
        userRepository.deleteById(id);

    }

    public UserDto updateUser(String email, UpdateUserRequest updateUserRequest) {
        User user = findUserByEmail(email);
        if(!user.getIsActive()){
            log.warn(String.format("User is not active, user mail: %s", user.getEmail()));
            throw new UserIsNotActiveException(String.format("User is not active: %s", user.getEmail()));
        }

        User updatedUser = new User(
                user.getId(),
                updateUserRequest.getFirstName(),
                updateUserRequest.getMiddleName(),
                updateUserRequest.getLastName(),
                user.getEmail(),
                user.getIsActive()
        );

        return userDtoConverter.convert(userRepository.save(updatedUser));
    }

}
