package com.example.stockmanagementsym.logic.classes

import com.example.stockmanagementsym.logic.cart.AbstractCartLogic
import com.example.stockmanagementsym.logic.classes.AbstractListLogic

/*
    Created by Juan Sebastian Sanchez Mancilla on 2/11/2020
*/
abstract class AbstractLogicFactory {
    open fun createProductList(): AbstractListLogic? {return null}
    open fun createUserList(): AbstractListLogic? {return null}
    open fun createSaleList(): AbstractListLogic? {return null}
    open fun createCustomerList(): AbstractListLogic? {return null}

    open fun createCartList(): AbstractCartLogic? {return null}
}
