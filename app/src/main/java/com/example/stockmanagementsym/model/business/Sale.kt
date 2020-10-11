package com.example.stockmanagementsym.model.business

import java.util.Calendar

data class Sale(private var customer: Customer, private var date: Calendar, private var productList: MutableList<Product>) {
    fun getCustomer() : Customer {
        return customer
    }
    fun setCustomer(customer: Customer){
        this.customer = customer
    }

    fun getDate() : Calendar{
        return date
    }
    fun setDate(date: Calendar){
        this.date = date
    }

    fun getProductList() : MutableList<Product>{
        return productList
    }
    fun setProductList(productList:MutableList<Product>){
        this.productList = productList
    }
}