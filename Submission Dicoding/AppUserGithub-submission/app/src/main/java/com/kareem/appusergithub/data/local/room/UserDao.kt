package com.kareem.appusergithub.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getListBookmark(): LiveData<List<UserEntity>>

    @Query("SELECT EXISTS(SELECT * FROM user where username = :username AND bookmarked = 1)")
    suspend fun getBookmarkedUser(username:String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun deleteAll(user: UserEntity)
}