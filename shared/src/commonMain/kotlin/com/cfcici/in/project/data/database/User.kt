package com.cfcici.`in`.project.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
    //indices = [Index(value = ["usernameUser"], unique = true)]) // it checks if the user is in the database before
data class User(//Parent of the UserCar
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    val usernameUser: String,
    val passwordUser: String,
    val dateOfBirthUser: String,
    val emailIdUser: String,
    val userPhotoUser: String? = null // nullable, default to null
)
@Entity(
    tableName = "UserOwnedCar",
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
    val modelUser: String,//
    val yearUser: Int?,
    val colourUser: String,//
    val seriesUser: String?,
    val typeOfSeriesUser: String?,
    val collectorNoUser: String?,
    val carPhotoUser: String//
)

@Entity(
    tableName = "UserSelectedCarBrand",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"], childColumns =["userIdFromUsers"],
            onDelete = ForeignKey.CASCADE),
        ], indices = [Index(value = ["userIdFromUsers"]
            // Why we don't need UserCar
            //Because selecting "HotWheels" doesn't depend on owning a particular car.
        //For example, the user can select HotWheels before owning any HotWheels car.
        //So UserSelectedCarBrand only needs to know: Which user selected which brand?
        )]
)
// You don't need user owned car list because you're already saving the owned cars in each brands in UserSelectedCarBrand

data class UserSelectedBrandCars(
    @PrimaryKey(autoGenerate = true)
    val userSelectedCarBrandId: Int = 0,
    val userIdFromUsers: Int,
    val selectedBrandName: String,
)

/*
UserSelectedCarBrand
userBrandId | userId | brandName
---------------------------------
1           | 5      | HotWheels
2           | 5      | MatchBox
3           | 5      | Tomica

UserOwnedCar
userCarId | userId | brand      | model
--------------------------------------------
1         | 5      | HotWheels  | Nissan Skyline
2         | 5      | HotWheels  | Toyota Supra
3         | 5      | MatchBox   | Ford Mustang

User
 │
 ├── selected brands
 │      ├── Hot Wheels
 │      └── MatchBox
 │
 └── owned cars
        ├── Hot Wheels → Nissan Skyline
        ├── Hot Wheels → Toyota Supra
        └── MatchBox → Ford Mustang
*/