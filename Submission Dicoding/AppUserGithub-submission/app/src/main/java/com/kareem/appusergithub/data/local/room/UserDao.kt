package com.kareem.appusergithub.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kareem.appusergithub.data.local.entity.UserEntity


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