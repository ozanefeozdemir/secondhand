package com.project.secondhand.dto

/*
    private  String phoneNumber;
    private  String address;
    private  String city;
    private  String country;
    private  String postCode;
 */

data class UserDetailsDto(
    val phoneNumber: String,
    val address: String,
    val city: String,
    val country: String,
    val postCode: String,
) {
}