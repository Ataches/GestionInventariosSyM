package com.example.stockmanagementsym.logic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmanagementsym.data.AppDataBase
import com.example.stockmanagementsym.data.dao.CustomerDao
import com.example.stockmanagementsym.data.dao.ProductDao
import com.example.stockmanagementsym.data.dao.SaleDao
import com.example.stockmanagementsym.data.dao.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataBaseLogic(application: Application) : AndroidViewModel(application) {

    private var saleDao:SaleDao ?= null
    private var productDao: ProductDao ?= null
    private var customerDao: CustomerDao?=null
    private var userDao: UserDao ?= null

    fun getUserDao(): UserDao {
        if(userDao==null)
            userDao = AppDataBase.getAppDataBase(getApplication())!!.getUserDao()
        return userDao!!
    }

    fun getCustomerDao(): CustomerDao {
        if(customerDao==null)
            customerDao = AppDataBase.getAppDataBase(getApplication())!!.getCustomerDao()
        return customerDao!!
    }

    fun getSaleDao(): SaleDao {
        if(saleDao==null)
            saleDao=AppDataBase.getAppDataBase(getApplication())!!.getSaleDao()
        return saleDao!!
    }

    fun getProductDao(): ProductDao {
        if(productDao==null)
            productDao = AppDataBase.getAppDataBase(getApplication())!!.getProductDao()
        return productDao!!
    }

}