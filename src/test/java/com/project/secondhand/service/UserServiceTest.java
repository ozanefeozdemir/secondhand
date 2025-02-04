package com.project.secondhand.service;

import com.project.secondhand.TestSupport;
import com.project.secondhand.dto.CreateUserRequest;
import com.project.secondhand.dto.UpdateUserRequest;
import com.project.secondhand.dto.UserDto;
import com.project.secondhand.dto.UserDtoConverter;
import com.project.secondhand.exceptions.UserIsNotActiveException;
import com.project.secondhand.exceptions.UserNotFoundException;
import com.project.secondhand.model.User;
import com.project.secondhand.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest extends TestSupport {

    @Mock
    public UserRepository repository;
    @Mock
    public UserDtoConverter converter;
    @InjectMocks
    public UserService userService;


    @BeforeEach
    public void setUp() {
        converter = mock(UserDtoConverter.class);
        repository = mock(UserRepository.class);
        userService = new UserService(repository, converter);
    }

    @Test
    public void testGetAllUsers_itShouldReturnUserDtoList() {
        List<User> userList = generateUsers();
        List<UserDto> userDtoList = generateUserDtoList(userList);

        when(repository.findAll()).thenReturn(userList);
        when(converter.convert(userList)).thenReturn(userDtoList);

        List<UserDto> result = userService.getAllUsers();

        assertEquals(userDtoList, result);
        verify(repository).findAll();
        verify(converter).convert(userList);
    }

    @Test
    public void testGetUserByMail_whenUserMailExists_itShouldReturnDto() {
        String mail = "test@test.com";
        User user = generateUser(mail);
        UserDto userDto = generateUserDto(mail);

        when(repository.findByEmail(mail)).thenReturn(Optional.of(user));
        when(converter.convert(user)).thenReturn(userDto);

        UserDto result = userService.getUserByEmail(mail);

        assertEquals(userDto, result);
        verify(repository).findByEmail(mail);
        verify(converter).convert(user);
    }

    @Test
    public void testGetUserByMail_whenUserMailDoesNotExists_itShouldThorUserNotFoundException() {
        String mail = "test@test.com";
        when(repository.findByEmail(mail)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.getUserByEmail(mail)
        );

        verify(repository).findByEmail(mail);
        verifyNoInteractions(converter);
    }

    @Test
    public void testCreateUser_itShouldReturnCreatedUserDto() {
        String mail = "test@test.com";
        CreateUserRequest req = new CreateUserRequest("fname", "mname", "laname", mail);
        User user = new User(null, "fname", "mname", "laname", mail, false);// bu hata verirse id'siz bir User constructor oluşur
        User savedUser = new User(1L, "fname", "mname", "laname", mail, false);
        UserDto userDto = new UserDto("fname", "mname", "laname", mail);

        when(repository.save(user)).thenReturn(savedUser);
        when(converter.convert(savedUser)).thenReturn(userDto);

        UserDto result = userService.createUser(req);

        assertEquals(userDto, result);
        verify(repository).save(user);
        verify(converter).convert(savedUser);

    }

    @Test
    public void testUpdateUser_whenUserMailExistAndUserIsActive_itShouldReturnUpdatedUserDto() {
        String mail = "test@test.com";
        UpdateUserRequest req = new UpdateUserRequest("fname2", "mname2", "laname2");
        User user = new User(1L, "fname", "mname", "laname", mail, true);// bu hata verirse id'siz bir User constructor oluşur
        User updatedUser = new User(1L, "fname2", "mname2", "laname2", mail, true);
        User savedUser = new User(1L, "fname2", "mname2", "laname2", mail, true);
        UserDto userDto = new UserDto("fname2", "mname2", "laname2", mail);

        when(repository.findByEmail(mail)).thenReturn(Optional.of(user));
        when(repository.save(updatedUser)).thenReturn(savedUser);
        when(converter.convert(savedUser)).thenReturn(userDto);

        UserDto result = userService.updateUser(mail, req);

        assertEquals(userDto, result);
        verify(repository).findByEmail(mail);
        verify(repository).save(updatedUser);
        verify(converter).convert(savedUser);

    }

    @Test
    public void testUpdateUser_whenUserMailDoesNotExist_itShouldThorUserNotFoundException() {
        String mail = "test@test.com";
        UpdateUserRequest req = new UpdateUserRequest("fname2", "mname2", "laname2");

        when(repository.findByEmail(mail)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                userService.updateUser(mail, req));

        verify(repository).findByEmail(mail);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(converter);
    }

    @Test
    public void testUpdateUser_whenUserMailExistButIsNotActive_itShouldThorUserIsNotActiveException() {
        String mail = "test@test.com";
        UpdateUserRequest req = new UpdateUserRequest("fname2", "mname2", "laname2");
        User user = new User(1L, "fname", "mname", "laname", mail, false);// bu hata verirse id'siz bir User constructor oluşur

        when(repository.findByEmail(mail)).thenReturn(Optional.of(user));

        assertThrows(UserIsNotActiveException.class, () ->
                userService.updateUser(mail, req));

        verify(repository).findByEmail(mail);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(converter);
    }

    @Test
    public void testDeactivateUser_whenUserIdExists_itShouldUpdateUserByActiveFalse() {
        String mail = "test@test.com";
        User user = new User(userId, "fname", "mname", "laname", mail, true);// bu hata verirse id'siz bir User constructor oluşur
        User savedUser = new User(userId, "fname", "mname", "laname", mail, false);

        when(repository.findById(userId)).thenReturn(Optional.of(user));


        userService.deactivateUser(userId);

        verify(repository).findById(userId);
        verify(repository).save(user);
    }

    @Test
    public void testDeactivateUser_whenUserIdDoesNotExists_itShouldThrowUserNotFoundException() {

        when(repository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class , () -> userService.deactivateUser(userId));

        verify(repository).findById(userId);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testActivateUser_whenUserIdExists_itShouldUpdateUserByActiveTrue() {
        String mail = "test@test.com";
        User user = new User(userId, "fname", "mname", "laname", mail, false);// bu hata verirse id'siz bir User constructor oluşur
        User savedUser = new User(userId, "fname", "mname", "laname", mail, true);

        when(repository.findById(userId)).thenReturn(Optional.of(user));


        userService.activateUser(userId);

        verify(repository).findById(userId);
        verify(repository).save(user);
    }

    @Test
    public void testActivateUser_whenUserIdDoesNotExists_itShouldThrowUserNotFoundException() {

        when(repository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class , () -> userService.activateUser(userId));

        verify(repository).findById(userId);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testDeleteUser_whenUserIdExists_itShouldDeleteUser() {
        String mail = "test@test.com";
        User user = new User(userId, "fname", "mname", "laname", mail, false);// bu hata verirse id'siz bir User constructor oluşur

        when(repository.findById(userId)).thenReturn(Optional.of(user));


        userService.deleteUser(userId);

        verify(repository).findById(userId);
        verify(repository).deleteById(userId);
    }

    @Test
    public void testDeleteUser_whenUserIdDoesNotExists_itShouldThrowUserNotFoundException() {

        when(repository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class , () -> userService.deleteUser(userId));

        verify(repository).findById(userId);
        verifyNoMoreInteractions(repository);
    }

}