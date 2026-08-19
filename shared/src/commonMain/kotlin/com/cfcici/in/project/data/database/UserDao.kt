package com.cfcici.`in`.project.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getAll(): List<User>

    @Insert
    suspend fun insert(vararg user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    suspend fun updateCarEdit(userCar: UserCar)

    @Update
    suspend fun updateProfileEdit(user: User)

    //Asynchronously check my user table for this email and return true if it exists, otherwise return false
    // Means look inside  the user table and find the email already exist
    @Query("SELECT * FROM Users WHERE emailIdUser = :emailUserDao LIMIT 1")// email is a parameter
    suspend fun getEmailIdDuplication(emailUserDao: String): User?

    @Query("SELECT * FROM Users WHERE usernameUser = :usernameUserDao LIMIT 1")
    suspend fun getUserByUsername(usernameUserDao: String): User?

    //AND guarantees that both conditions have to be true for the same row at the same time,
    // so there's no way for SQL to accidentally pair a username from one row with a password from a different row.
    // Either one single row satisfies both conditions together, or no row does — there's no in-between, no mixing.
    @Query("SELECT * FROM Users WHERE usernameUser = :username AND passwordUser = :password LIMIT 1")
    suspend fun loginChecker(username: String, password: String): User?

    //########################################################################################################################################################################

    @Insert
    suspend fun insertUserOwnedCar(vararg userCar: UserCar)

    @Query("""SELECT * FROM UserOwnedCar WHERE userIdUser = :userIdDao""")
    suspend fun getUserOwnedCar(userIdDao: Int): List<UserCar>

    @Delete
    suspend fun deleteUserOwnedCar(userCar: UserCar)

    @Query("""SELECT * FROM UserOwnedCar WHERE userIdUser = :userIdDao AND brandUser = :brandCarsDao""")
    suspend fun getUserOwnedCarsByBrands(userIdDao: Int, brandCarsDao: String): List<UserCar>

/*in SQLLite, || means join like +
% -> anything before or before the search text. that we put on both side so it will give the models that have that particular name on it
the reason we used userIdUser = :userIdDao, when the app used by many people and search particular model it might show other people's mode car too we don't need that to happen*/
    @Query("""SELECT * FROM UserOwnedCar WHERE userIdUser = :userIdDao AND modelUser LIKE '%' || :modelUserDao || '%' """)
    suspend fun getCarOnSearchBar(userIdDao:Int, modelUserDao: String): List<UserCar>

    //########################################################################################################################################################################

    @Insert// insert the selected brands
    suspend fun insertSelectedCarBrand(selectedBrandDao: UserSelectedBrandCars)

    // to get the selected car brands
    @Query("""SELECT * FROM UserSelectedCarBrand WHERE userIdFromUsers = :userIdDao""")
    suspend fun getSelectedCarBrands(userIdDao: Int): List<UserSelectedBrandCars>

    @Query("""DELETE FROM UserOwnedCar WHERE userIdUser = :userIdDao AND brandUser = :selectedBrandDao""")
    suspend fun deleteUserOwnedCarsByBrand(userIdDao: Int, selectedBrandDao: String)

    @Query("""DELETE FROM UserSelectedCarBrand WHERE userIdFromUsers = :userIdDao AND selectedBrandName = :selectedBrandDao""")
    suspend fun deleteSelectedBrand(userIdDao: Int, selectedBrandDao: String)

}