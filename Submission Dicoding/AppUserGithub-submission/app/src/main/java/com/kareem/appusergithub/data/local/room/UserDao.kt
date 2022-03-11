package com.kareem.appusergithub.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getListStar(): LiveData<List<UserEntity>>

    @Query("SELECT EXISTS(SELECT * FROM user where username = :username AND star = 1)")
    suspend fun getStarUser(username:String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun deleteAll(user: UserEntity)
}