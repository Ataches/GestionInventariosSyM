package com.example.stockmanagementsym.logic.business

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(
    private var name: String,
    private var password: String,
    private var privileges: String
){
    @PrimaryKey(autoGenerate = true)
    @NonNull @ColumnInfo(name = "id_user")
    var idSale:Long = 0L

    fun getName():String{
        return name
    }

    fun getPassword(): String {
        return password
    }

    fun getPrivileges(): String {
        return privileges
    }
}