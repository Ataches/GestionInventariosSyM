package com.example.stockmanagementsym.data.dao

import androidx.room.*
import com.example.stockmanagementsym.logic.business.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Update
    suspend fun update(user: User)

    @Query("SELECT * FROM USER")
    suspend fun select(): List<User>


    @Query("DELETE FROM USER")
    suspend fun deleteAll()

}

