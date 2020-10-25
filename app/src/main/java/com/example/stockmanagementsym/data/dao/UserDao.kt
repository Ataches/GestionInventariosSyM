package com.example.stockmanagementsym.data.dao

import androidx.room.*
import com.example.stockmanagementsym.logic.business.User

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * FROM USER")
    fun selectUserList(): List<User>

    @Query("SELECT * FROM USER WHERE :name = name AND :password = password")
    fun selectUser(name: String, password: String): User
}

