package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.dao.UserDao
import com.example.stockmanagementsym.logic.business.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.anko.*

class UserLogic(private val userDao: UserDao, private var listManager: ListManager) {

    private var userList: MutableList<User> = mutableListOf()
    private var user: User? = null

    fun getUser(): User {
        return user!!
    }

    fun createUser(user: User) {
        doAsync {
            try {
                userDao.insert(user)
                updateUserList()
                notifyUserTransactionSuccess()
            }catch (e: Exception) {
                listManager.showResultTransaction(false)
            }
        }

    }

    suspend fun getUserList(): MutableList<User> {
        withContext(Dispatchers.IO) {
            if (userList.isEmpty())
                userList = userDao.selectUserList()
        }
        return userList
    }

    fun updateUser(user: User) {
        doAsync {
            try {
                userDao.update(user)
                updateUserList()
                notifyUserTransactionSuccess()
            }catch (e: Exception) {
                listManager.showResultTransaction(false)
            }
        }
    }

    fun deleteUser(user: User) {
        doAsyncResult{
            try {
                userDao.delete(user)
                updateUserList()
                notifyUserTransactionSuccess()
            }catch (e: Exception) {
                listManager.showResultTransaction(false)
            }
        }
    }

    private fun updateUserList() {
        userList = userDao.selectUserList()
    }

    suspend fun confirmLogin(userName: String, password: String): Boolean {
        withContext(Dispatchers.IO) {
            user = userDao.selectUser(userName, password)
        }
        return user != null
    }

    suspend fun searchUser(textSearched: String): List<User> {
        return getUserList().filter { it.getName().toLowerCase().contains(textSearched.toLowerCase()) }
    }

    private fun notifyUserTransactionSuccess() {
        listManager.reloadList()
        listManager.showResultTransaction(true)
    }

    fun setListManager(userLogicListManager: ListManager) {
        this.listManager = userLogicListManager
    }
}
