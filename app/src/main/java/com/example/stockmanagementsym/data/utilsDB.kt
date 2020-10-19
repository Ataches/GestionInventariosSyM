package com.example.stockmanagementsym.data

import android.util.Log
import com.example.stockmanagementsym.logic.business.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun rePopulateDb(database: AppDataBase?) {
    database?.let { db ->
        withContext(Dispatchers.IO) {
            Log.d("TEST", " SE REPOPULO ")
            val userDao = db.getUserDao()

            userDao.deleteAll()

            userDao.insert(User("a","a"))
        }
    }
}