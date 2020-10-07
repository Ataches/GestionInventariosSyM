package com.example.stockmanagementsym.logic

object User {
    private lateinit var name: String
    fun setUser(name:String){
        this.name = name
    }
    fun getUser(): String {
        return name
    }
}