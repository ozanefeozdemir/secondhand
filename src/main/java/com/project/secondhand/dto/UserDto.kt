package com.project.secondhand.dto

class UserDto(
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val email: String,
    val userDetails: List<UserDetailsDto>
) {
}