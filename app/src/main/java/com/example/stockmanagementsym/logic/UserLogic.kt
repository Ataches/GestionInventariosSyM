package com.example.stockmanagementsym.logic

import android.widget.Toast
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import com.example.stockmanagementsym.data.dao.UserDao
import com.example.stockmanagementsym.logic.business.User
import kotlinx.coroutines.*


class UserLogic(private val userDao: UserDao) {

    private var userList: List<User> = listOf()

    fun selectUser(){
        userList = userDao.select()
    }
    fun insertUser(userName: String, password: String):Boolean{
        return try{
            userDao.insert(User(userName, password))
            true
        }catch (e: Exception){
            false
        }
    }

    suspend fun confirmLogin(userName: String, password: String):Boolean {
        GlobalScope.launch(
            Dispatchers.IO
        ) {
            insertUser(userName, password)
            selectUser()
        }
        delay(100)
        return (userList.any { it.getUser() == userName }) &&
                (userList.any { it.getPassword() == password })
    }
}
