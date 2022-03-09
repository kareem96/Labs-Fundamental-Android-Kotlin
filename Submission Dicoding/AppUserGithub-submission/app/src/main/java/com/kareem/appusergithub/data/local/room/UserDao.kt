package com.kareem.appusergithub.data.local.room

import androidx.room.*
import com.kareem.appusergithub.data.model.UserItems


@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY username ASC")
     suspend fun getListBookmark(): List<UserItems>

    @Query("SELECT * FROM user where bookmarked = :username")
    suspend fun getBookmarkedUser(username:String): UserItems?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserItems)

    @Delete
    suspend fun deleteAll(user: UserItems)
}