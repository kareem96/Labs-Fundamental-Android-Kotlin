package com.kareem.appusergithub.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kareem.appusergithub.data.model.UserItems


@Database(entities = [UserItems::class],  exportSchema = false, version = 1,)
abstract class UserDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
    companion object{
        @Volatile
        private var instance: UserDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): UserDatabase {
            if (instance == null){
                synchronized(UserDatabase::class.java){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "User.db"
                    ).build()
                }
            }
            return instance as UserDatabase
        }
    }
}