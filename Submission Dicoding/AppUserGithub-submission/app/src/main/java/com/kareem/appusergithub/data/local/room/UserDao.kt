package com.kareem.appusergithub.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kareem.appusergithub.data.local.entity.UserEntity


@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getListStar(): LiveData<List<UserEntity>>

    @Query("SELECT count(*) FROM user WHERE user.id = :id")
    suspend fun getStarUser(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Query("DELETE FROM  user WHERE user.id = :id")
    suspend fun deleteAll(id: Int): Int
}