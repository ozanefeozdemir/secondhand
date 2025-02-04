package com.project.secondhand.service;

import com.project.secondhand.dto.CreateUserDetailsRequest;
import com.project.secondhand.dto.UpdateUserDetailsRequest;
import com.project.secondhand.dto.UserDetailsDto;
import com.project.secondhand.dto.UserDetailsDtoConverter;
import com.project.secondhand.exceptions.UserDetailsNotFoundException;
import com.project.secondhand.model.User;
import com.project.secondhand.model.UserDetails;
import com.project.secondhand.repository.UserDetailsRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;
    private final UserService userService;
    private final UserDetailsDtoConverter converter;

    public UserDetailsService(UserDetailsRepository userDetailsRepository, UserService userService, UserDetailsDtoConverter converter) {
        this.userDetailsRepository = userDetailsRepository;
        this.userService = userService;
        this.converter = converter;
    }

    public UserDetailsDto createUserDetails(final CreateUserDetailsRequest request) {

        User user = userService.findUserById(request.getUserId());

        UserDetails userDetails = new UserDetails(request.getPhoneNumber(), request.getAddress(), request.getCity(), request.getCountry(), request.getPostCode(), user);
        return converter.convert(userDetailsRepository.save(userDetails));
    }

    public UserDetailsDto updateUserDetails(Long id, final UpdateUserDetailsRequest request) {
        UserDetails userDetails = findUserDetailsById(id);

        UserDetails updateUserDetails = new UserDetails(
                userDetails.getId(),
                request.getPhoneNumber(),
                request.getAddress(),
                request.getCity(),
                request.getCountry(),
                request.getPostCode(),
                userDetails.getUser()
        );

        return converter.convert(userDetailsRepository.save(updateUserDetails));
    }

    public void deleteUserDetails(Long id ){
        findUserDetailsById(id);
        userDetailsRepository.deleteById(id);
    }

    private UserDetails findUserDetailsById(final Long id) {
        return userDetailsRepository.findById(id).orElseThrow(() -> new UserDetailsNotFoundException("User not founded: " + id));
    }

}
