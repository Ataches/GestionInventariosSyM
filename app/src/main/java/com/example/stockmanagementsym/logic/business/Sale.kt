package com.example.stockmanagementsym.logic.business

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

@Entity
data class Sale(
    @NonNull @PrimaryKey @ColumnInfo(name = "id_sale")
    val idSale: String = UUID.randomUUID().toString(),
    private var customer: Customer,
    private var date: String,
    var productList: MutableList<Product>
) {

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
}
