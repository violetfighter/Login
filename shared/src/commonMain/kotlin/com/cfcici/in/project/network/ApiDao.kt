package com.cfcici.`in`.project.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlin.collections.emptyList

enum class ForgotPasswordResult { SUCCESS, EMAIL_NOT_FOUND, ERROR }
class ApiDao(
    //gives UserApi class an HTTP client that it can use to communicate with your .NET API

    // Calls the API

    private val client: HttpClient = httpClient
) {

    suspend fun uploadImage(bytes: ByteArray): String? = try {
        val response = client.submitFormWithBinaryData(
            url = "http://10.11.253.70:5214/api/images/Upload",
            formData = formData {
                append("file", bytes, Headers.build {
                    append(HttpHeaders.ContentType, "image/jpeg")
                    // Ktor automatically adds form-data; name="file".
                    // Only pass filename here to avoid duplicate name="file" headers:
                    append(HttpHeaders.ContentDisposition, "filename=\"photo.jpg\"")
                })
            }
        )
        if (response.status.isSuccess()) {
            response.bodyAsText().trim() // Trim quote marks or trailing whitespace
        } else {
            println("Upload failed with status: ${response.status}")
            null
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }


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
            }.body<Boolean>()//extracts the actual true/false value || server sends just true or false
        } catch (e: Exception){
            e.printStackTrace()
            false
        }
    }


    suspend fun usernameExistsApi(username: String) : Boolean{
        return try {
            client.get("http://10.11.253.70:5214/api/users/UsernameDuplicate"){
                parameter("usernameFromOutside", username)
            }.body<Boolean>() // if something is giving like value, true/false we should use this
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
                username
                //response.body<UsernameChangeApiUser>().usernameChangeApi
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
                dob
                //response.body<DobChangeApiUser>().dobChangeApi
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
            println("Delete Car @@@@@ $carId status: ########## ${response.status} and body ${response.bodyAsText()}")
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

    suspend fun newPasswordApi(email: String, newPassword: String): Boolean{
        return try {
            val response = client.patch("http://10.11.253.70:5214/api/users/NewPassword"){
                parameter("userEmailFromOutside", email)
                parameter("newPasswordFromOutside", newPassword)
            }
            response.status.isSuccess()
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

    suspend fun totalCarUserOwnApi(userIdCar: Int): Int?{
        return try {
            val response = client.get("http://10.11.253.70:5214/api/cars/TotalCarUserOwns"){
                parameter("userIdFromOutside", userIdCar)
            }.body<TotalCarUserOwnApiUser>()
            println("Total Car Update ${response.totalCarsApi}")
            response.totalCarsApi
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun forgotPassword(email: String): ForgotPasswordResult {
        return try {
            val response = client.post("http://10.11.253.70:5214/api/users/ForgotPassword") {
                parameter("userEmailFromOutside", email)
            }
            when {
                response.status.isSuccess() -> ForgotPasswordResult.SUCCESS
                response.status == HttpStatusCode.NotFound -> ForgotPasswordResult.EMAIL_NOT_FOUND
                else -> ForgotPasswordResult.ERROR
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ForgotPasswordResult.ERROR
        }
    }

    suspend fun verifyPassword(email: String, code: String): Boolean{
        return try {
            val response = client.get("http://10.11.253.70:5214/api/passwords/VerifingCode"){
                parameter("emailFromOutside", email)
                parameter("codeFromOutside", code)
            }
            println("VERIFY RESPONSE: ${response.status} ${response.bodyAsText()}")
            response.status.isSuccess()
        } catch (e: Exception){
            e.printStackTrace()
            false
        }
    }


}