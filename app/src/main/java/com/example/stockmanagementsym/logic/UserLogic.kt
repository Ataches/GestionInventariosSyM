package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.CONSTANTS
import com.example.stockmanagementsym.data.MESSAGES
import com.example.stockmanagementsym.data.dao.UserDao
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.presentation.fragment.ListListener
import com.example.stockmanagementsym.presentation.view.Notifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.anko.doAsync


class UserLogic(private val userDao: UserDao, private var notifier: Notifier) {

    private lateinit var listListener: ListListener
    private var listManager: ListManager ?= null
    private lateinit var userListListener: ListListener
    private var userList: MutableList<User> = mutableListOf()
    private var user: User ?= null

    fun getUser(): User {
        return user!!
    }

    fun deleteUser(user: User){
        try{
            doAsync {
                userDao.delete(user)
                updateUserList()
                getListManager().showToastMessage(MESSAGES.USER_DELETE_SUCCESS)
                userListListener.reloadList()
            }
        }catch (e:Exception){
            getListManager().showToastMessage(MESSAGES.USER_DELETE_FAILURE)
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

    suspend fun updateUser(user: User): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                userDao.update(user)
            }
            updateUserList()
            true
        }catch (e:Exception){
            false
        }
    }

    private fun updateUserList() {
        doAsync {
            userList = userDao.selectUserList()
        }
    }

    suspend fun getUserList():MutableList<User> {
        withContext(Dispatchers.IO) {
            if(userList.isEmpty())
                userList = userDao.selectUserList()
        }
        return userList
    }

    suspend fun confirmLogin(userName: String, password: String):Boolean {
        withContext(Dispatchers.IO){
            user = userDao.selectUser(userName,password)
        }
        return user != null
    }

    suspend fun insertUser(userName: String, password: String) {
        withContext(Dispatchers.IO){
            insertUser(User(userName, password,"admin","",CONSTANTS.DEFAULT_USER_LATITUDE,CONSTANTS.DEFAULT_USER_LONGITUDE))
        }
    }

    suspend fun searchUser(textSearched: String): List<User> {
        return getUserList().filter { it.getName().toLowerCase().contains(textSearched.toLowerCase())}
    }

    fun setListListener(listListener: ListListener){
        this.listListener = listListener
    }

    private fun getListManager():ListManager{
        if(listManager==null)
            listManager = ListManager(notifier,listListener)
        return listManager!!
    }
}
