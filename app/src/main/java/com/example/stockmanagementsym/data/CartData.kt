package com.example.stockmanagementsym.data

import com.example.stockmanagementsym.logic.business.Product
import java.lang.Exception

class CartData {
    private var cartList:MutableList<Product> = mutableListOf()

    fun addProduct(item: Product):String{
        if(cartList.filter{product -> product.idProduct == item.idProduct}.isNotEmpty())
            return "El producto ya está en el carrito"
        cartList.add(item)
        return "Producto añadido al carrito"
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
