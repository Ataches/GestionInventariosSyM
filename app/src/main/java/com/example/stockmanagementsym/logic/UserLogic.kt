package com.example.stockmanagementsym.logic

import android.util.Log
import com.example.stockmanagementsym.data.dao.UserDao
import com.example.stockmanagementsym.logic.business.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserLogic(
    private var userDao: UserDao
){

    private suspend fun selectUserList(): List<User> {
        return userDao.select()
    }

    fun insertUser(userName: String, password: String):Boolean{
        return try{
            GlobalScope.launch(Dispatchers.IO) {
                userDao.insert(User(userName,password))
            }
            true
        }catch (e:Exception){
            false
        }
    }

    fun confirmLogin(userName: String, password: String): Boolean {
        var resultName = false
        var resultPass = false
        var userList: List<User> = listOf()

        GlobalScope.launch(Dispatchers.IO) {
            userList = (selectUserList())
        }
        Log.d("TEST",""+userList)
        resultName = userList.any { it.getUser() == userName }
        resultPass = userList.any { it.getPassword() == password }
        return resultName && resultPass
    }
}