package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.CartData
import com.example.stockmanagementsym.data.MESSAGES
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.list_manager.IListManager
import java.text.DecimalFormat

class CartLogic(private var listManager: IListManager) {

    private var cartData: CartData? = null


    private fun getCartData(): CartData {
        if (cartData == null)
            cartData = CartData()
        return cartData!!
    }

    fun addProductToCart(item: Product) {
        try {
            if (getCartData().addProductToCartList(item))
                listManager.showAlertMessage(MESSAGES.CART_TITLE, MESSAGES.PRODUCT_ADDED_TO_CART)
            else
                listManager.showAlertMessage(MESSAGES.CART_TITLE, MESSAGES.PRODUCT_ALREADY_IN_CART)
            listManager.reloadList(getCartData().getCartList().toMutableList())
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

    fun removeElementCart(item: Product){
        try{
            getCartData().removeElementList(item)
            listManager.showResultTransaction(true)
        }catch (e:Exception){
            listManager.showResultTransaction(false)
        }
    }

    fun clearCart() {
        if(getCartData().clearCart())
            listManager.reloadList(mutableListOf()) //Data cart is empty
        else
            listManager.showResultTransaction(false)
    }
}