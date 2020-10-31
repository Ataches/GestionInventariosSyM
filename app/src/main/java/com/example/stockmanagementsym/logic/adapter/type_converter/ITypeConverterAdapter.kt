package com.example.stockmanagementsym.logic.adapter.type_converter

import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.User

interface ITypeConverterAdapter {

    fun productListToString(productList:MutableList<Product>):String
    fun storedStringToProductList(storedString: String): MutableList<Product>

    fun productToString(product: Product):String

    fun storedStringToCustomer(string: String): Customer?
    fun customerToStoredString(customer: Customer): String

    fun setBooleanStringToUser(stringToUser: Boolean)
    fun customerToString(customer: Customer): String
    fun getUserToString(user: User): String
}
