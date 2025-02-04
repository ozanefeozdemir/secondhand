package com.project.secondhand.controller;


import com.project.secondhand.dto.CreateUserDetailsRequest;
import com.project.secondhand.dto.UpdateUserDetailsRequest;
import com.project.secondhand.dto.UserDetailsDto;
import com.project.secondhand.service.UserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/userdetails")
public class UserDetailsController {

    private final UserDetailsService userDetailsService;

    public UserDetailsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<UserDetailsDto> createUser(@RequestBody CreateUserDetailsRequest request) {
        return ResponseEntity.ok(userDetailsService.createUserDetails(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDetailsDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserDetailsRequest request) {
        return ResponseEntity.ok(userDetailsService.updateUserDetails(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

}
