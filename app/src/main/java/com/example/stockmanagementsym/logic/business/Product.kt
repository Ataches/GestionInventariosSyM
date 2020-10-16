package com.example.stockmanagementsym.logic.business

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Product(
    @NonNull @PrimaryKey @ColumnInfo(name = "id_product")
    val idProduct: String = UUID.randomUUID().toString(),
    private val name: String,
    private val price: Int,
    private val description: String,
    @ColumnInfo(name = "id_image")
    private var idIconDrawable: Int,
    private var quantity: Int
){

    fun getName():String{
        return name
    }

    fun getPrice():Int{
        return price
    }
    fun getDescription():String{
        return description
    }

    fun getIdIconDrawable(): Int {
        return idIconDrawable
    }

    fun getQuantity(): Int {
        return quantity
    }

    fun setQuantity(quantity: Int) {
        this.quantity = quantity
    }
}
