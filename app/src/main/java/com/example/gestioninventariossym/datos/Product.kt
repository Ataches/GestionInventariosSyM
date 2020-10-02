package com.example.gestioninventariossym.datos

data class Product(
    private val name: String,
    private val price: Int,
    private val description: String,
    private var idIconDrawable: Int,
    private var quantity: Int
    ){

    fun getNombre():String{
        return name
    }

    fun getPrecio():Int{
        return price
    }
    fun getDescripcion():String{
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
