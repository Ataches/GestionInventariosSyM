package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.dao.UserDao
import com.example.stockmanagementsym.logic.business.User
import kotlinx.coroutines.*


class UserLogic(private val userDao: UserDao) {

    private var userList: List<User> = listOf()

    suspend fun selectUserList(): List<User> {
        withContext(Dispatchers.IO) {
            userList = userDao.select()
        }
        return userList
    }
    suspend fun insertUser(userName: String, password: String):Boolean{
        return try{
            withContext(Dispatchers.IO) {
                userDao.insert(User(userName, password))
            }
            true
        }catch (e: Exception){
            false
        }
    }

    suspend fun confirmLogin(userName: String, password: String):Boolean {
        withContext(Dispatchers.IO) {
            //insertUser(userName, password)
            selectUserList()
        }
        return (userList.any { it.getUser() == userName }) &&
                (userList.any { it.getPassword() == password })
    }
}
