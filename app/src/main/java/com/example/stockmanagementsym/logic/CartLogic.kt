package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.CartObject
import com.example.stockmanagementsym.logic.business.Product

object CartLogic {

    fun getCartList():MutableList<Product>{
        return CartObject.getList()
    }

    fun getTotalPrice():Int{
        if(CartObject.getList().isNullOrEmpty())
            return 0
        return CartObject.getList().map{it.getPrice()*it.getQuantity()}.reduce{ acc, it -> acc + it}
    }
}