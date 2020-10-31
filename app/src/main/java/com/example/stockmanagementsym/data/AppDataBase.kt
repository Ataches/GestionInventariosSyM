package com.example.stockmanagementsym.data

import android.content.Context
import androidx.room.*
import com.example.stockmanagementsym.data.dao.CustomerDao
import com.example.stockmanagementsym.data.dao.ProductDao
import com.example.stockmanagementsym.data.dao.SaleDao
import com.example.stockmanagementsym.data.dao.UserDao
import com.example.stockmanagementsym.logic.adapter.type_converter.TypeConverterConcrete
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.logic.business.User

@Database(entities = [Customer::class, Product::class, Sale::class, User::class], version = 1)
@TypeConverters(TypeConverterConcrete::class)
abstract class AppDataBase : RoomDatabase(){
    abstract fun getCustomerDao(): CustomerDao
    abstract fun getProductDao(): ProductDao
    abstract fun getSaleDao(): SaleDao
    abstract fun getUserDao(): UserDao


    companion object{
        private var dataBaseInstance: AppDataBase? = null

        fun getAppDataBase(context: Context): AppDataBase?{
            if(dataBaseInstance == null)
                synchronized(AppDataBase::class.java) {
                    if (dataBaseInstance == null) {
                        dataBaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDataBase::class.java,
                            "DataBase"
                        ).build()
                    }
                }
            return dataBaseInstance
        }

        fun destroyDataBase(){
            dataBaseInstance = null
        }
    }
}
