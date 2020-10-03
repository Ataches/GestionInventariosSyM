<<<<<<< HEAD:app/src/main/java/com/example/stockmanagementsym/data/Product.kt
package com.example.stockmanagementsym.data
=======
package com.example.stocmanagementsym.data
>>>>>>> temp:app/src/main/java/com/example/gestioninventariossym/datos/Product.kt

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
