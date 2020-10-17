package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.CartData
import com.example.stockmanagementsym.logic.business.Product

class CartLogic {
    private var cartObject = CartData()
    private var cartData:CartData ?= null

    fun getCartData():CartData{
        if(cartData==null)
            cartData = CartData()
        return cartData!!
    }

    fun getCartList():MutableList<Product>{
        return getCartData().getCartList()
    }

    fun getTotalPrice():Int{
        if(cartObject.getCartList().isNullOrEmpty())
            return 0
        return cartObject.getCartList().map{it.getPrice()*it.getQuantity()}.reduce{ acc, it -> acc + it}
    }
    fun addProduct(item: Product):String{
        return getCartData().addProduct(item)
    }

    fun removeElementCart(item: Product) : Boolean{
        return getCartData().removeElementList(item)
    }

    fun clearCart() {
        return getCartData().clearCart()
    }
}