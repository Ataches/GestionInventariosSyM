package com.example.stockmanagementsym.data

import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import java.util.*

object Data {
    private lateinit var customerList : MutableList<Customer>
    private lateinit var productList : MutableList<Product>
    private lateinit var salesList : MutableList<Sale>
    private var customerListCreated = false
    private var productListCreated = false
    private var salesListCreated = false

    fun getProductList(): MutableList<Product>{
        if(productListCreated)
            return productList
        productList = mutableListOf(
            Product("Inyector Peugeot", 75000, "Inyector para Peugeot", R.drawable.ic_login, 0),
            Product("Inyector B2600", 120000, "Inyector para B2600", R.drawable.ic_login, 0),
            Product("Inyector Sprint", 125000, "Inyector para Sprint", R.drawable.ic_login, 0)
        )
        productListCreated=true
        return productList
    }

    fun getCustomerList():MutableList<Customer>{
        if(customerListCreated)
            return customerList
        customerList = mutableListOf(
            Customer("Carlos Gutierrez", "Cr 9 #20 68", "3203232321", "Bogotá D.C"),
            Customer("Antonio Perez", "Cra 9 #30 60", "3001231231", "Bogotá D.C"),
            Customer("Miguel Velasquez", "Calle 6a #20 22", "3103828329", "Bogotá D.C")
        )
        customerListCreated=true
        return customerList
    }

    fun getSalesList():MutableList<Sale>{
        if(salesListCreated)
            return salesList
        var date= Calendar.getInstance()
        date.set(2020,10,8)
        salesList = mutableListOf(
            Sale(getCustomerList()[0],date, mutableListOf(getProductList()[0], getProductList()[1]))
        )
        salesListCreated=true
        return salesList
    }
    fun addCustomer(customer: Customer){
        getCustomerList().add(customer)
    }
    fun addProduct(product: Product){
        getProductList().add(product)
    }
    fun addSale(sale: Sale){
        getSalesList().add(sale)
    }
    fun updateCustomer(customerToEdit: Customer, customerEdited: Customer ){
        getCustomerList().remove(getCustomerList().filter { it.getName() == customerToEdit.getName() && it.getAddress() == customerToEdit.getAddress() &&
                                                            it.getCity() == customerToEdit.getCity() && it.getPhone() == customerToEdit.getPhone()}[0])
        getCustomerList().add(customerEdited)
    }

    fun updateProduct(productToEdit: Product, productEdited: Product) {
        getProductList().remove(getProductList().filter { it.getName() == productToEdit.getName() && it.getDescription() == productToEdit.getDescription() &&
                it.getPrice() == productToEdit.getPrice() && it.getQuantity() == productToEdit.getQuantity()}[0])
        getProductList().add(productEdited)
    }
}