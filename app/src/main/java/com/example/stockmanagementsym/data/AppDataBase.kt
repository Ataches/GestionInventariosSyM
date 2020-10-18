package com.example.stockmanagementsym.data

import android.content.Context
import androidx.room.*
import com.example.stockmanagementsym.data.dao.CustomerDao
import com.example.stockmanagementsym.data.dao.ProductDao
import com.example.stockmanagementsym.data.dao.SaleDao
import com.example.stockmanagementsym.data.dao.UserDao
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.logic.business.User

@Database(entities = [Customer::class, Product::class, Sale::class, User::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase(){
    abstract fun getCustomerDao(): CustomerDao
    abstract fun getProductDao(): ProductDao
    abstract fun getSaleDao(): SaleDao
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
        return customer.getName()+",.,"+customer.getAddress()+",.,"+
                 customer.getPhone()+",.,"+customer.getPhone()+",.,"+customer.getCity()
    }
    @TypeConverter
    fun storedStringToCustomer(string: String):Customer{
        val dataCustomer = string.split(",.,")
        return Customer(dataCustomer[0],dataCustomer[1],dataCustomer[2],dataCustomer[3],dataCustomer[4])
    }
    @TypeConverter
    fun productListToStoredString(productList:MutableList<Product>):String{
        var value:String = ""
        for (product in productList){
            value += product.getName()+",.,"+
                    product.getPrice()+",.,"+
                    product.getDescription()+",.,"+
                    product.getIdIconDrawable()+",.,"+
                    product.getQuantity()+"&ln"
        }
        return value
    }
    @TypeConverter
    fun storedStringToProductList(value: String): MutableList<Product>{
        val dataProducts = value.split("&ln")
        val productList: MutableList<Product> = mutableListOf()
        for(dataProduct in dataProducts){
            var data = dataProduct.split(",.,")
            Product(data[0], data[1], data[2].toInt(), data[3], data[4].toInt(), data[5].toInt())
        }
        return productList
    }

}