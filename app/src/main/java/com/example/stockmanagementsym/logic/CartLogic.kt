package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.CartData
import com.example.stockmanagementsym.logic.business.Product
import java.text.DecimalFormat

class CartLogic {

    private var cartData:CartData ?= null

    private fun getCartData():CartData{
        if(cartData==null)
            cartData = CartData()
        return cartData!!
    }

    fun getCartList():MutableList<Product>{
        return getCartData().getCartList()
    }

    fun getTotalPrice(): String {
        val df = DecimalFormat("$###,###,###")
        if(getCartList().isNullOrEmpty())
            return df.format(0.0)
        val totalPrice = getCartList().map{it.getPrice()*it.getQuantity()}.reduce{ acc, it -> acc + it}
        return df.format(totalPrice)
    }

    fun addProduct(item: Product):String{
        return getCartData().addProduct(item)
    }

    fun removeElementCart(item: Product) : Boolean{
        return getCartData().removeElementList(item)
    }

    fun clearCart() {
        getCartData().clearCart()
    }

}