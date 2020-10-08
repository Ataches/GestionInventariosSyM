package com.example.stockmanagementsym.data

object User {
    private lateinit var name: String
    fun setUser(name:String){
        User.name = name
    }
    fun getUser(): String {
        return name
    }
}