package com.project.secondhand.dto;

import com.project.secondhand.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserDtoConverter {

    private final UserDetailsDtoConverter converter;

    public UserDtoConverter(UserDetailsDtoConverter converter) {
        this.converter = converter;
    }

    public UserDto convert(User from) {
        return new UserDto(Objects.requireNonNull(from.getFirstName()), from.getMiddleName(), from.getLastName(), from.getEmail(), converter.convert(new ArrayList<>(from.getUserDetailsSet())));
    }

    public List<UserDto> convert(List<User> fromList) {
        return fromList.stream().map(this::convert).collect(Collectors.toList());
    }

}
