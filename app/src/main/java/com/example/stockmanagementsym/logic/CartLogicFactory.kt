package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.logic.cart.AbstractCartLogic
import com.example.stockmanagementsym.logic.cart.CartLogic

class CartLogicFactory : AbstractLogicFactory() {
    override fun createCartList(): AbstractCartLogic? {
        return CartLogic()
    }
}
