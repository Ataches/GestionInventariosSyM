package com.example.stockmanagementsym.model.data

object User {
    private lateinit var name: String
    fun setUser(name:String){
        User.name = name
    }
    fun getUser(): String {
        return name
    }
}