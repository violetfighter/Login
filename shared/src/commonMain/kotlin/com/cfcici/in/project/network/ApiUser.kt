package com.cfcici.`in`.project.network

import kotlinx.serialization.Serializable

// ApiUser -> Receive data

//Eg:-
//CreateUserRequest = what you send to create a user.
// ApiUser = what you receive when reading a user.

@Serializable
data class CreateUserRequest(
    val usernameUser: String,
    val passwordUser: String,
    val dateOfBirthUser: String,
    val emailIdUser: String
)

@Serializable // convert JSON to kotlin object
data class ApiCar (
    val userIdCar: Int,
    val userCarIdCar: Int = 0,
    val brandCar: String,
    val modelCar: String,
    val colourCar: String,
    val seriesCar: String? = null,
    val yearCar: String? = null,
    val typeOfSeriesCar: String? = null,
    val collectorNoCar: String? = null,
    val carPhotoCar: String
)

@Serializable
data class ApiUser(
    val userId: Int = 0,
    val usernameUser: String,
    val passwordUser: String,
    val dateOfBirthUser: String,
    val emailIdUser: String,
    val userPhotoUser: String? = null, // nullable, default to null
    val selectedTheme: String = "NEON_SPEEDWAY",
    val isDarkMode: Boolean = true
)

@Serializable
data class TotalCarUserOwnApiUser(val totalCars: Int) // Remember "totalCars" name should match with C# function
@Serializable
data class EmailExistsApiUser(val emailExistApi: Boolean)

@Serializable
data class UsernameExistsApiUser(val usernameExistApi: Boolean)

@Serializable
data class UsernameChangeApiUser(val usernameChangeApi: String)

@Serializable
data class DobChangeApiUser(val dobChangeApi: String)

@Serializable
data class LoginCheckerApiUser(val usernameLoginApi: String, val passwordLoginApi: String) // what you send

@Serializable
data class LoginResultApiUser(val userId: Int, val usernameUser: String)  // what you get back

/*

A data class like DeleteUserApiUser is only for reading a response body,
like { "emailExist": true }. Your delete endpoint returns NoContent,
which has no body, so there's nothing to convert. You only check .status.isSuccess().
You can delete that class.

@Serializable
data class DeleteUserApiUser(val userIdApi: Boolean)*/