package com.example.stockmanagementsym.data

import com.example.stockmanagementsym.logic.business.Product

class CartData {
    private var cartList:MutableList<Product> = mutableListOf()
    private lateinit var listSearched:List<Product>

    fun addProduct(item: Product):String{
        listSearched = cartList.filter{
            product -> product.getName() == item.getName()
        }
        if(listSearched.isNotEmpty())
            return "El producto ya está en el carrito"
        cartList.add(item)
        return "Producto añadido al carrito"
    }
    fun getCartList():MutableList<Product>{
        return cartList
    }

    fun clearCart() {
        cartList = mutableListOf()
    }
    fun removeElementList(item:Product):Boolean{
        return cartList.remove(item)
    }
}