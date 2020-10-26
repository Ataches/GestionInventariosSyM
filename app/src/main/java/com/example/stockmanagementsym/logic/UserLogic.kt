package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.dao.UserDao
import com.example.stockmanagementsym.logic.business.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class UserLogic(private val userDao: UserDao) {

    private var userList: List<User> = listOf()
    private var user: User ?= null

    fun getUser(): User {
        return user!!
    }

    private suspend fun selectUserList(): List<User> {
        withContext(Dispatchers.IO) {
            userList = userDao.selectUserList()
        }
        updateUserList()
        return userList
    }

    suspend fun deleteUser(user: User): Boolean {
        return try{
            withContext(Dispatchers.IO) {
                userDao.delete(user)
            }
            updateUserList()
            true
        }catch (e:Exception){
            false
        }
    }

    suspend fun insertUser(user: User):Boolean{
        return try{
            withContext(Dispatchers.IO) {
                userDao.insert(user)
            }
            updateUserList()
            true
        }catch (e: Exception){
            false
        }
    }

    private suspend fun updateUserList() {
        withContext(Dispatchers.IO) {
            userList = userDao.selectUserList()
        }
    }

    suspend fun getUserList():List<User> {
        withContext(Dispatchers.IO) {
            if(userList.isEmpty())
                userList = userDao.selectUserList()
        }
        return userList
    }

    suspend fun confirmLogin(userName: String, password: String):Boolean {
        withContext(Dispatchers.IO) {
            insertUser(User(userName, password,"admin"))
            user = userDao.selectUser(userName,password)
        }
        return user != null
    }

    suspend fun searchUser(textSearched: String): List<User> {
        return getUserList().filter { it.getName().toLowerCase().contains(textSearched.toLowerCase())}
    }

}
