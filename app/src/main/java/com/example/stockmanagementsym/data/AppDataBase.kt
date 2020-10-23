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

class Converters {
    @TypeConverter
    fun customerToStoredString(customer: Customer):String{
        return customer.getName()+"-lim-"+
                customer.getAddress()+"-lim-"+
                customer.getPhone()+"-lim-"+
                customer.getCity()
    }
    @TypeConverter
    fun storedStringToCustomer(string: String): Customer? {
        val dataCustomer = string.split("-lim-")
        if(dataCustomer.isNotEmpty())
            return Customer(
                name = dataCustomer[0],
                address = dataCustomer[1],
                phone = dataCustomer[2],
                city = dataCustomer[3]
            )
        return null
    }
    @TypeConverter
    fun productListToStoredString(productList:MutableList<Product>):String{
        var value = ""
        for (product in productList){
            value+= product.getName()+"-lim-"+
                    product.getPrice()+"-lim-"+
                    product.getDescription()+"-lim-"+
                    product.getStringBitMap()+"-lim-"+
                    product.getQuantity()+"-ln-"
        }
        return value
    }
    @TypeConverter
    fun storedStringToProductList(value: String): MutableList<Product>{
        var dataProducts = value.split("-ln-")
        dataProducts = dataProducts.filter{it != ""}//Remove void elements in list
        val productList: MutableList<Product> = mutableListOf()
        if(dataProducts.isNotEmpty())
            for(dataProduct in dataProducts){
                val data = dataProduct.split("-lim-")
                val product = Product(
                    name = data[0],
                    price = data[1].toInt(),
                    description = data[2],
                    stringBitMap = data[3],
                    quantity = data[4].toInt()
                )
                productList.add(product)
            }
        return productList
    }

}

