package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.logic.classes.*

/*
    Created by Juan Sebastian Sanchez Mancilla on 2/11/2020
*/
class ListLogicFactory : AbstractLogicFactory() {
    override fun createProductList(): AbstractListLogic {
        return ProductLogic()
    }

    override fun createCustomerList(): AbstractListLogic? {
        return CustomerLogic()
    }

    override fun createSaleList(): AbstractListLogic? {
        return SaleLogic()
    }

    override fun createUserList(): AbstractListLogic? {
        return UserLogic()
    }
}