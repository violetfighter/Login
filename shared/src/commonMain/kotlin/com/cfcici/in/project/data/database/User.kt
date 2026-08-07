package com.cfcici.`in`.project.data.database

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users")
    //indices = [Index(value = ["usernameUser"], unique = true)]) // it checks if the user is in the database before
data class User(//Parent of the UserCar
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    val usernameUser: String,
    val passwordUser: String,
    val dateOfBirthUser: String,
    val emailIdUser: String,
)
@Entity(
    tableName = "UserCar",
    foreignKeys =
        [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"], childColumns = ["userIdUser"], // take the UserCar.userId and make sure it corresponds to User.userId
            onDelete = ForeignKey.CASCADE
        )], indices = [Index(value = ["userIdUser"])]
    )

data class  UserCar(// Child of the User
    @PrimaryKey(autoGenerate = true)
    val userCarIdUser: Int = 0,

    val userIdUser: Int,// foreign key

    val brandUser: String,
    val modelUser: String,
    val yearUser: Int?,
    val colourUser: String,
    val seriesUser: String?,
    val typeOfSeriesUser: String?,
    val collectorNoUser: String?,
    val photoUser: String
)