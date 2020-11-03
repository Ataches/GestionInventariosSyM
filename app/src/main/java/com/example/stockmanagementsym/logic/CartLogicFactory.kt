package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.logic.cart.AbstractCartLogic
import com.example.stockmanagementsym.logic.cart.CartLogic
import com.example.stockmanagementsym.logic.classes.AbstractLogicFactory

/*
    Created by Juan Sebastian Sanchez Mancilla on 2/11/2020
*/
class CartLogicFactory : AbstractLogicFactory() {
    override fun createCartList(): AbstractCartLogic? {
        return CartLogic()
    }
}
