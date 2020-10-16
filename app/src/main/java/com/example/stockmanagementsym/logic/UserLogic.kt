package com.example.stockmanagementsym.logic

import android.content.Context
import com.example.stockmanagementsym.data.dao.UserDao
import com.example.stockmanagementsym.logic.business.User
import java.lang.Exception

class UserLogic(private val userDao: UserDao) {

    private lateinit var context: Context

    fun setContext(context:Context){
        this.context = context
    }

    fun selectUser(): List<User> {
        return userDao.select()
    }

    fun insertUser(id:String,userName: String, password: String):Boolean{
        return try{
            userDao.insert(User(id,userName,password))
            true
        }catch (e:Exception){
            false
        }
    }

    fun confirmLogin(id:String,userName: String, password: String): Boolean {
        return (selectUser().any { it -> it.getUser() == userName }) &&
                (selectUser().any { it -> it.getPassword() == password })
    }
}