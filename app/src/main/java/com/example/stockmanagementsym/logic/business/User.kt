package com.example.stockmanagementsym.logic.business

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class User(
    private var user: String,
    private var password: String,
){
    @PrimaryKey(autoGenerate = true)
    @NonNull @ColumnInfo(name = "id_user")
    var idSale:Long = 0L

    fun getUser():String{
        return user
    }

    fun getPassword(): String {
        return password
    }
}