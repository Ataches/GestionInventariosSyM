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

    fun getDate(): String {
        return date
    }

    fun getProductList():MutableList<Product>{
        return productList
    }
}
