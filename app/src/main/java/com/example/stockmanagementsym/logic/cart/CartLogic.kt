package com.example.stockmanagementsym.logic.cart

import com.example.stockmanagementsym.data.CartData
import com.example.stockmanagementsym.data.MESSAGES
import com.example.stockmanagementsym.logic.business.Product
import java.text.DecimalFormat

class CartLogic: AbstractCartLogic() {

    private var cartData: CartData? = null


    override fun getCartData(): CartData {
        if (cartData == null)
            cartData = CartData()
        return cartData!!
    }

    override fun addProductToCart(item: Product) {
        try {
            if (getCartData().addProductToCartList(item))
                iListManager?.showAlertMessage(MESSAGES.CART_TITLE, MESSAGES.PRODUCT_ADDED_TO_CART)
            else
                iListManager?.showAlertMessage(MESSAGES.CART_TITLE, MESSAGES.PRODUCT_ALREADY_IN_CART)
            iListManager?.reloadList(getCartData().getCartList().toMutableList())
        }catch (e:Exception){
            iListManager?.showAlertMessage(MESSAGES.CART_TITLE, MESSAGES.PRODUCT_NOT_ADDED_TO_CART)
        }
    }

    override fun getList():MutableList<Product>{
        return getCartData().getCartList()
    }

    override fun getTotalPrice(): String {
        val df = DecimalFormat("$###,###,###")
        if(getList().isNullOrEmpty())
            return df.format(0.0)
        val totalPrice = getList().map{it.getPrice()*it.getQuantity()}.reduce{ acc, it -> acc + it}
        return df.format(totalPrice)
    }

    override fun removeElementCart(item: Product){
        try{
            getCartData().removeElementList(item)
            notifyUserTransactionSuccess()
        }catch (e:Exception){
            iListManager?.showResultTransaction(false)
        }
    }

    override fun clearCart() {
        if(getCartData().clearCart())
            iListManager?.reloadList(mutableListOf()) //Data cart is empty
        else
            iListManager?.showResultTransaction(false)
    }
}