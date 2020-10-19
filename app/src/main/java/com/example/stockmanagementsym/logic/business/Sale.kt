package com.example.stockmanagementsym.logic.business

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sale(
    private var customer: Customer,
    private var date: String,
    private var productList: MutableList<Product>
) {
    @PrimaryKey(autoGenerate = true)
    @NonNull @ColumnInfo(name = "id_sale")
    var idSale:Long = 0L

    fun getCustomer(): Customer {
        return customer
    }

    fun setCustomer(customer: Customer) {
        this.customer = customer
    }

    fun getDate(): String {
        return date
    }

    fun setDate(date: String) {
        this.date = date
    }

    fun getProductList():MutableList<Product>{
        return productList
    }
}
