package com.example.stockmanagementsym.data

import android.content.Context
import androidx.room.*
import com.example.stockmanagementsym.data.dao.CustomerDao
import com.example.stockmanagementsym.data.dao.UserDao
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.logic.business.User

@Database(entities = [Customer::class, Product::class, Sale::class, User::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase(){
    abstract fun getCustomerDao(): CustomerDao
    abstract fun getUserDao(): UserDao


    companion object{
        private var dataBaseInstance: AppDataBase? = null

        fun getAppDataBase(context: Context): AppDataBase?{
            if(dataBaseInstance == null)
                dataBaseInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "DataBase"
                ).allowMainThreadQueries()
                 .build()
            return dataBaseInstance
        }

        fun destroyDataBase(){
            dataBaseInstance = null
        }
    }
}

class Converters {
    @TypeConverter
    fun customerToStoredString(customer: Customer):String{
        val value:String = ""

        return value
    }
    @TypeConverter
    fun storedStringToCustomer(string: String):Customer{
        val customer:Customer= Customer("","","","","")

        return customer
    }
    @TypeConverter
    fun productListToStoredString(productList:MutableList<Product>):String{
        val value:String = ""
        for (product in productList){

        }
        return value
    }
    @TypeConverter
    fun storedStringToProductList(value: String): MutableList<Product>{
        var productList: MutableList<Product> = mutableListOf(Product("", "", 5, "", 4, 3))

        return productList
    }

}