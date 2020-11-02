package com.example.stockmanagementsym.logic

import android.util.Log
import com.example.stockmanagementsym.data.dao.UserDao
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.logic.list_manager.IListManager
import com.example.stockmanagementsym.logic.list_manager.ListManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

class UserLogic(private val userDao: UserDao, private var listManager: IListManager) {
    private var userList: MutableList<User> = mutableListOf()
    private var user: User? = null

    fun loadData() {
        doAsyncResult {
            if(userList.isEmpty())
                userList = userDao.selectUserList()
            uiThread {
                listManager.reloadList(userList.toMutableList())
            }
        }
    }

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

    fun searchUser(textSearched: String): List<User> {
        return userList.filter { it.getName().toLowerCase().contains(textSearched.toLowerCase()) }
    }

    private fun notifyUserTransactionSuccess() {
        listManager.reloadList(userList.toMutableList())
        listManager.showResultTransaction(true)
    }
}
