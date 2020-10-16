package com.example.stockmanagementsym.logic.business

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class User(
    @NonNull @PrimaryKey @ColumnInfo(name = "id_customer")
    var idCustomer:String,
    private var user: String,
    private var password: String,
){

    fun getUser():String{
        return user
    }

    fun getPassword(): String {
        return password
    }
}