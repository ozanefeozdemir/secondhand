package com.project.secondhand;

import com.project.secondhand.dto.UserDto;
import com.project.secondhand.model.User;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestSupport {
    public static final Long userId = 100L;

    public static List<User> generateUsers() {
        return IntStream.range(0, 5).mapToObj( i ->
                new User((long) i,
                        i + "@hotmail.com",
                        "firstName" + i,
                        "middleName" + i,
                        "lastName" + i,
                        new Random(2).nextBoolean())
                ).collect(Collectors.toList());
    }

    public static List<UserDto> generateUserDtoList(List<User> userList) {
        return userList.stream()
                .map( from -> new UserDto(from.getFirstName(), from.getMiddleName(), from.getLastName(), from.getEmail()))
                .collect(Collectors.toList());
    }

    public static User generateUser(String mail) {
        return new User(userId,
            "firstName" + userId,
            "middleName" + userId,
            "lastName" + userId,
            userId + mail,
            true);
    }

    public static UserDto generateUserDto(String mail) {

        return new UserDto("firstName" + userId,"middleName" + userId,"lastName" + userId,userId + mail);
    }
}
