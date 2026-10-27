package com.cfcici.`in`.project.network

import kotlinx.serialization.Serializable

// ApiUser -> Receive data

//Eg:-
//CreateUserRequest = what you send to create a user.
// ApiUser = what you receive when reading a user.

@Serializable
data class CreateUserRequest(
    val username: String,
    val password: String,
    val date_Of_Birth: String,
    val email_Id: String
)

@Serializable
data class AddNewBrandApiUser(
    val Brand_Id: Int,
    val Name: String,
    val Logo: String ?= null
)
@Serializable // convert JSON to kotlin object
data class ApiCar (
    val Car_Id: Int = 0,
    val User_Id: Int,
    val Brand_Id: Int,
    //val Brand_Name: String,
    val Model_Name: String,
    val Colour: String,
    val Series: String? = null,
    val Year: String? = null,
    val Type_Of_Series: String? = null,
    val Collector_Number: String? = null,
    val Car_Photo: String
)

@Serializable
data class ApiUser(
    val user_Id: Int = 0,
    val username: String,
    val password: String,
    val date_Of_Birth: String,
    val email_Id: String,
    val profile_Picture : String? = null, // nullable, default to null
    val selected_Theme: String = "NEON_SPEEDWAY",
    val is_Dark_Mode: Boolean = true
)


// you can also avoid Serializable for total car
@Serializable
data class TotalCarUserOwnApiUser(val totalCarsApi: Int) // Remember "totalCars" name should match with C# function

@Serializable
data class LoginCheckerApiUser(val usernameLoginApi: String, val passwordLoginApi: String) // what you send

@Serializable
data class LoginResultApiUser(val userId: Int, val username: String, val accessToken: String, val tokenType: String, val expiresIn: Int)  // what you get back

/*

A data class like DeleteUserApiUser is only for reading a response body,
like { "emailExist": true }. Your delete endpoint returns NoContent,
which has no body, so there's nothing to convert. You only check .status.isSuccess().
You can delete that class.

@Serializable
data class DeleteUserApiUser(val userIdApi: Boolean)*/