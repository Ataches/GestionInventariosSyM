package com.example.stockmanagementsym.data

import com.example.stockmanagementsym.R

object Data {
    private lateinit var customerList : MutableList<Customer>
    private lateinit var productList : MutableList<Product>
    private var customerListCreated = false
    private var productListCreated = false

    fun getProductList(): List<Product>{
        if(customerListCreated)
            return productList
        productList = mutableListOf(
            Product("Inyector Peugeot", 75000, "Inyector para Peugeot", R.drawable.ic_login, 0),
            Product("Inyector B2600", 120000, "Inyector para B2600", R.drawable.ic_login, 0),
            Product("Inyector Sprint", 125000, "Inyector para Sprint", R.drawable.ic_login, 0)
        )
        customerListCreated=true
        return productList
    }

    fun getCustomerList():List<Customer>{
        if(productListCreated)
            return customerList
        customerList = mutableListOf(
            Customer("Carlos Gutierrez", "Cr 9 #20 68", "3203232321", "Bogotá D.C"),
            Customer("Antonio Perez", "Cra 9 #30 60", "3001231231", "Bogotá D.C"),
            Customer("Miguel Velasquez", "Calle 6a #20 22", "3103828329", "Bogotá D.C")
        )
        productListCreated=true
        return customerList
    }
    fun addCustomer(customer: Customer){
        customerList.add(customer)
    }
    fun addProduct(product: Product){
        productList.add(product)
    }

    fun editCustomer() {

    }

}