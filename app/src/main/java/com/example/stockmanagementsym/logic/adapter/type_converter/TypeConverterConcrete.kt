package com.example.stockmanagementsym.logic.adapter.type_converter

import androidx.room.TypeConverter
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.User

class TypeConverterConcrete:ITypeConverterAdapter {

    private var userConverterAdaptee: UserConverterAdaptee? = null
    private var customerConverterAdaptee: CustomerConverterAdaptee ?= null
    private var productConverterAdaptee:ProductConverterAdaptee ?= null

    /*
        Product
     */
    @TypeConverter
    override fun productListToString(productList: MutableList<Product>): String {
        return getProductConverter().productListToStoredString(productList)
    }

    @TypeConverter
    override fun storedStringToProductList(storedString: String): MutableList<Product>{
        return getProductConverter().storedStringToProductList(storedString)
    }

    override fun productToString(product: Product): String {
        return getProductConverter().productToString(product)
    }

    /*
        Customer
     */
    @TypeConverter
    override fun customerToStoredString(customer: Customer):String{
        return getCustomerConverterAdaptee().customerToStoredString(customer)
    }

    @TypeConverter
    override fun storedStringToCustomer(string: String): Customer? {
        return getCustomerConverterAdaptee().storedStringToCustomer(string)
    }

    override fun customerToString(customer: Customer):String{
        return getCustomerConverterAdaptee().customerToString(customer)
    }
    override fun setBooleanStringToUser(stringToUser: Boolean) {
        getProductConverter().setBooleanStringToUser(stringToUser)
    }

    /*
        User
     */
    override fun getUserToString(user: User): String {
        return getUserConverterAdaptee().getUserToString(user)
    }

    private fun getCustomerConverterAdaptee(): CustomerConverterAdaptee {
        if(customerConverterAdaptee==null)
            customerConverterAdaptee = CustomerConverterAdaptee()
        return customerConverterAdaptee!!
    }

    private fun getProductConverter():ProductConverterAdaptee{
        if(productConverterAdaptee==null)
            productConverterAdaptee = ProductConverterAdaptee(false)
        return productConverterAdaptee!!
    }
    private fun getUserConverterAdaptee(): UserConverterAdaptee{
        if(userConverterAdaptee == null)
            userConverterAdaptee = UserConverterAdaptee()
        return userConverterAdaptee!!
    }
}