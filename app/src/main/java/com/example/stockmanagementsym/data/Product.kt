package com.example.stockmanagementsym.data

data class Product(
    private val name: String,
    private val price: Int,
    private val description: String,
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

    fun getIDiconDrawable(): Int {
        return idIconDrawable
    }

    fun getQuantity(): Int {
        return quantity
    }

    fun setQuantity(quantity: Int) {
        this.quantity = quantity
    }
}
