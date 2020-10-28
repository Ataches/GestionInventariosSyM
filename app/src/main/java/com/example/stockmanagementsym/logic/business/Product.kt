package com.example.stockmanagementsym.logic.business

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Product(
    @SerializedName("name")
    private val name: String,
    @SerializedName("price")
    private val price: Double,
    @SerializedName("description")
    private val description: String,
    @SerializedName("photoUrl")
    @ColumnInfo(name = "id_image")
    private var stringBitMap: String,
    @SerializedName("quantity")
    private var quantity: Int
){
    @PrimaryKey(autoGenerate = true)
    @NonNull @ColumnInfo(name = "id_product")
    var idProduct:Long = 0L

    fun getName():String{
        return name
    }

    fun getPrice():Double{
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