package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.dao.UserDao
import com.example.stockmanagementsym.logic.business.User
import java.lang.Exception

class UserLogic(private val userDao: UserDao) {

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

    fun confirmLogin(userName: String, password: String): Boolean {
        return (selectUser().any { it -> it.getUser() == userName }) &&
                (selectUser().any { it -> it.getPassword() == password })
    }
}