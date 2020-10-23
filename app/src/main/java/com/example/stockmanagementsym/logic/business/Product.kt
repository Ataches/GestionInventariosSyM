package com.example.stockmanagementsym.logic.business

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    private val name: String,
    private val price: Int,
    private val description: String,
    @ColumnInfo(name = "id_image")
    private var stringBitMap: String,
    private var quantity: Int
){
    @PrimaryKey(autoGenerate = true)
    @NonNull @ColumnInfo(name = "id_product")
    var idProduct:Long = 0L

    fun getName():String{
        return name
    }

    fun getPrice():Int{
        return price
    }
    fun getDescription():String{
        return description
    }

    fun getStringBitMap(): String {
        return stringBitMap
    }

    fun getQuantity(): Int {
        return quantity
    }

    fun setQuantity(quantity: Int) {
        this.quantity = quantity
    }
}
