package com.cfcici.`in`.project.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlin.collections.emptyList

class ApiDao(
    //gives UserApi class an HTTP client that it can use to communicate with your .NET API

    // Calls the API


    private val client: HttpClient = httpClient
) {

    suspend fun createUser(user: CreateUserRequest): Boolean {
        return try {
            //Use the Ktor HttpClient to send a POST request to my .NET API.
            val response = client.post("http://10.11.253.70:5214/api/users") {
                contentType(ContentType.Application.Json)
                setBody(user)//Put the user data inside the request body
            }
            println("Response status: ${response.status}")
            println("Response body: ${response.bodyAsText()}")
            response.status.isSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /*
    suspend fun getUserByUsernameApi(username: String): GetUserByUsernameApiUser? {
        return try {
            client.get("http://10.11.253.70:5214/api/users/GetUserByUsername"){
                parameter("usernameFromOutside", username)
            }.body<GetUserByUsernameApiUser>()
        } catch (e: Exception){
            e.printStackTrace()
            null
            /*
            Then you can retrieve both Suppose:
            val user = repository.getUserByUsernameRepo("parvathi")
            You can do:
            val username = user?.username
            val userId = user?.userId
            */
        }
    }*/

    suspend fun getUserByIdApi(userId: Int): ApiUser?{
        return try {
            client.get( "http://10.11.253.70:5214/api/users/GetUserInfo" ){
                parameter("userIdFromOutside", userId)
            }.body<ApiUser>()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    // save the edited car info
    suspend fun carEdit(car: ApiCar): Boolean {
        return try {
            val response = client.patch("http://10.11.253.70:5214/api/cars/CarEdit") {
                parameter("carIdFromOutside", car.userCarIdCar)
                contentType(ContentType.Application.Json)
                setBody(car)
            }
            println("Car edit status@@@@@@@@@@@: ${response.status}")
            response.status.isSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


    // get the car details
    suspend fun getCarsByBrandApi(userId: Int, carBrand: String): List<ApiCar> {
        return try {
            client.get("http://10.11.253.70:5214/api/cars/GetCarInfo") {
                parameter("userIdFromOutside", userId)
                parameter("carBrandFromOutside", carBrand)
            }.body<List<ApiCar>>()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }


    suspend fun emailExistsApi(email: String) : Boolean{
        return try {
            client.get("http://10.11.253.70:5214/api/users/EmailDuplicate"){
                parameter("emailFromOutside", email)
            }.body<EmailExistsApiUser>().emailExistApi//extracts the actual true/false value
        } catch (e: Exception){
            e.printStackTrace()
            false
        }
    }


    suspend fun usernameExistsApi(username: String) : Boolean{
        return try {
            client.get("http://10.11.253.70:5214/api/users/UsernameDuplicate"){
                parameter("usernameFromOutside", username)
            }.body<UsernameExistsApiUser>().usernameExistApi // if something is giving like value, true/false we should use this
        } catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    suspend fun usernameChange(userId: Int, username: String): String?{
        return  try {

            val response = client.patch("http://10.11.253.70:5214/api/users/UsernameChange"){
                parameter("userIdFromOutside", userId)
                parameter("NewUsernameFromOutside", username)
            }
            println("Error on Username Change Update in ApiDao ${response.status}")
            if (response.status.isSuccess()){
                response.body<UsernameChangeApiUser>().usernameChangeApi
            }else{null}
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun dobChange(userId: Int, dob: String): String?{
        return try{
            val response = client.patch ("http://10.11.253.70:5214/api/users/DobChange"){
                parameter("userIdFromOutside", userId)
                parameter("dobFromOutside", dob)
            }
            println("Error on DOB Change Update in ApiDao ${response.status}")
            if (response.status.isSuccess()){
                response.body<DobChangeApiUser>().dobChangeApi
            }else{null}

        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun profilePicture(username: String, userPhoto: String?): Boolean{
        return try {
            val response = client.patch("http://10.11.253.70:5214/api/users/PhotoUpdate"){
                parameter("usernameFromOutside",username)
                parameter("photoFromOutside", userPhoto)
            }
            println("Error on Photo Update ${response.status}")
            response.status.isSuccess()
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    suspend fun deleteUserApi(userId: Int): Boolean {
        return try {
            val response = client.delete("http://10.11.253.70:5214/api/users/DeleteUser") {
                parameter("deleteUserFromOutside", userId)
            }
            println("Delete status: ########## ${response.status}")
            response.status.isSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun deleteCarApi(carId: Int): Boolean {
        return try {
            val response = client.delete("http://10.11.253.70:5214/api/cars/DeleteCar") {
                parameter("deleteCarFromOutside", carId)
            }
            println("Delete Car status: ########## ${response.status}")
            response.status.isSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun loginCheckerApi(username: String, password: String): LoginResultApiUser? {
        return try {
            val response = client.post("http://10.11.253.70:5214/api/users/LoginChecker") {
                contentType(ContentType.Application.Json)
                setBody(LoginCheckerApiUser(username, password))
            }
            println("Login status: ${response.status}")
            if (response.status.isSuccess()) response.body<LoginResultApiUser>() else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    suspend fun themeUpdateApi(userId: Int, theme: String): Boolean{
        return try {
            val response = client.patch("http://10.11.253.70:5214/api/users/ThemeUpdate"){
                parameter("userIdFromOutside", userId)
                parameter("themeFromOutside", theme)
            }
            println("Error on Theme Update ${response.status}")
            response.status.isSuccess()
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }


    suspend fun darkModeApi(userId: Int, mode: Boolean): Boolean{
        return try {
            val response = client.patch("http://10.11.253.70:5214/api/users/DarkMode"){
                parameter("userIdFromOutside", userId)
                parameter("darkModeFromOutside", mode)
            }
            println("Error on Dark Mode Update ${response.status}")
            response.status.isSuccess()// if nothing is giving except 204/404/500 then we write this
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }


    suspend fun createCarApi(cars: ApiCar): Boolean {
        return try {
            //Use the Ktor HttpClient to send a POST request to my .NET API.
            val response = client.post("http://10.11.253.70:5214/api/cars") {
                contentType(ContentType.Application.Json)
                setBody(cars)//Put the user data inside the request body
            }
            println("Response status: ${response.status}")
            println("Response body: ${response.bodyAsText()}")
            response.status.isSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun totalCarUserOwnApi(userCarId: Int): Int?{
        return try {
            client.get("http://10.11.253.70:5214/api/cars/TotalCarUserOwns"){
                parameter("userIdFromOutside", userCarId)
            }.body<TotalCarUserOwnApiUser>().totalCars
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }


}