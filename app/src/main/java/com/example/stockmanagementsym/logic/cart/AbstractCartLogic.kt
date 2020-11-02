package com.example.stockmanagementsym.logic.cart

import com.example.stockmanagementsym.data.CartData
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.list_manager.IListManager

/**
 *  Created by Juan Sebastian Sanchez Mancilla on 30/10/2020
 *  Abstract implementation of cart logic class
 */
abstract class AbstractCartLogic {

    var iListManager: IListManager? = null

    abstract fun getCartData(): CartData
    abstract fun addProductToCart(item: Product)
    abstract fun getTotalPrice(): String
    abstract fun removeElementCart(item: Product)
    abstract fun clearCart()

    abstract fun getList(): MutableList<Product>

    abstract fun setListManager(iListManager: IListManager)
}
