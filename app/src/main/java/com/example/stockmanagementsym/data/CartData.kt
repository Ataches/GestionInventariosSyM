package com.example.stockmanagementsym.data

import com.example.stockmanagementsym.logic.business.Product
import java.lang.Exception

class CartData {
    private var cartList:MutableList<Product> = mutableListOf()

    fun addProduct(product: Product):String{
        if(cartList.any { it == product })
            return "El producto ya está en el carrito"
        cartList.add(createProductCart(product))
        return "Producto añadido al carrito"
    }
    private fun createProductCart(product: Product):Product{
        val cartProduct = Product(
                name = product.getName(),
                description = product.getDescription(),
                price = product.getPrice(),
                stringBitMap = product.getStringBitMap(),
                quantity = CONSTANTS.QUANTITY_DEFAULT_PROD_TO_CART
        )
        cartProduct.idProduct = product.idProduct
        return cartProduct
    }
    fun clearCart():Boolean{
        return try{
            cartList = mutableListOf()
            true
        }catch (e:Exception){
            false
        }
    }

    fun removeElementList(item:Product):Boolean{
        return cartList.remove(item)
    }

    fun getCartList():MutableList<Product>{
        return cartList
    }
}
