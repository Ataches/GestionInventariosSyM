package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.CartData
import com.example.stockmanagementsym.data.MESSAGES
import com.example.stockmanagementsym.logic.business.Product
import java.text.DecimalFormat

class CartLogic(private var listManager: ListManager) {

    private var cartData:CartData ?= null

    private fun getCartData():CartData{
        if(cartData==null)
            cartData = CartData()
        return cartData!!
    }
    fun setListManager(listManager: ListManager) {
        this.listManager = listManager
    }

    fun addProductToCart(item: Product){
        try {
            if (addProductToCartList(item))
                listManager.showAlertMessage(MESSAGES.CART_TITLE, MESSAGES.PRODUCT_ADDED_TO_CART)
            else
                listManager.showAlertMessage(MESSAGES.CART_TITLE, MESSAGES.PRODUCT_ALREADY_IN_CART)
            listManager.reloadList()
        }catch (e:Exception){
            listManager.showAlertMessage(MESSAGES.CART_TITLE, MESSAGES.PRODUCT_NOT_ADDED_TO_CART)
        }
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

    fun addProductToCartList(item: Product):Boolean{
        return getCartData().addProductToCartList(item)
    }

    fun removeElementCart(item: Product) : Boolean{
        return getCartData().removeElementList(item)
    }

    fun clearCart() {
        getCartData().clearCart()
    }

}