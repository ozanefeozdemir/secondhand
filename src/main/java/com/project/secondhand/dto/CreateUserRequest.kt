package com.project.secondhand.dto

data class CreateUserRequest(
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val email: String,
) {
}