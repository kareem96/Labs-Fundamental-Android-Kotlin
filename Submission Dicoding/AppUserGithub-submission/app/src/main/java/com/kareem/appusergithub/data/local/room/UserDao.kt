package com.kareem.appusergithub.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kareem.appusergithub.data.local.entity.UserEntity


@Dao
interface UserDao {
    @Query("SELECT * FROM USER ORDER BY username ASC")
     fun getUser(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM USER where bookmarked = 1")
     fun getBookmarkedUser(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insertUser(user: List<UserEntity>)

    @Update
     fun updateUser(user: UserEntity)

    @Query("DELETE FROM USER where bookmarked = 0")
     fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM USER WHERE id = :id AND bookmarked = 1)")
    fun isNewBookmarkedUser(id: Int):Boolean
}