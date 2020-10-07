package com.example.stockmanagementsym.data

import com.example.stockmanagementsym.R

object Data {
    private lateinit var customerList : List<Customer>
    private lateinit var productList : List<Product>

    fun getProductList(): List<Product>{
        productList = listOf(
            Product("Inyector Peugeot", 75000, "Inyector para Peugeot", R.drawable.ic_login, 0),
            Product("Inyector B2600", 120000, "Inyector para B2600", R.drawable.ic_login, 0),
            Product("Inyector Sprint", 125000, "Inyector para Sprint", R.drawable.ic_login, 0)
        )
        return productList
    }

    fun getCustomerList():List<Customer>{
        customerList = listOf(
            Customer("Carlos Gutierrez", "Cr 9 #20 68", "3203232321", "Bogotá D.C"),
            Customer("Antonio Perez", "Cra 9 #30 60", "3001231231", "Bogotá D.C"),
            Customer("Miguel Velasquez", "Calle 6a #20 22", "3103828329", "Bogotá D.C")
        )
        return customerList
    }
}