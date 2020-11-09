package com.example.stockmanagementsym.data

import android.content.Context
import com.example.stockmanagementsym.R

/**
 * Created by Juan Sebastian Sanchez Mancilla on 30/10/2020
 * Object to load strings to show to the user
 */
object MESSAGES {

    private lateinit var context: Context

    lateinit var STRING_DATE: String
    lateinit var STRING_USER_CUSTOMER: String
    lateinit var STRING_PRODUCT_LIST: String

    lateinit var STRING_PRIVILEGE:String
    lateinit var STRING_IDENTIFICATION:String
    lateinit var STRING_NAME:String
    lateinit var STRING_ADDRESS:String
    lateinit var STRING_PHONE:String
    lateinit var STRING_CITY:String

    const val PRODUCT_LIST_UPDATE_FAILURE = R.string.productListUpdateFailure
    const val PRODUCT_LIST_UPDATE_SUCCESS = R.string.productListUpdateSuccess

    const val PRODUCT_ADDED_TO_CART = R.string.elementAddedToCart
    const val PRODUCT_ALREADY_IN_CART = R.string.productAlreadyInCart
    const val CART_TITLE = R.string.cartList
    const val PRODUCT_NOT_ADDED_TO_CART = R.string.elementNotAddedToCart

    const val TRANSACTION_SUCCESS = R.string.transactionSuccess
    const val TRANSACTION_FAILURE = R.string.transactionFailure

    /**
     * When a context is received will load some strings
     */
    fun setContext(context:Context){
        this.context = context
        loadStrings()
    }

    private fun loadStrings() {
        STRING_DATE = context.getString(R.string.date)
        STRING_USER_CUSTOMER = context.getString(R.string.userCustomer)
        STRING_PRODUCT_LIST = context.getString(R.string.productList)
        STRING_PRIVILEGE = context.getString(R.string.userPrivilege)
        STRING_IDENTIFICATION = context.getString(R.string.identification)
        STRING_NAME = context.getString(R.string.name)
        STRING_ADDRESS = context.getString(R.string.address)
        STRING_PHONE = context.getString(R.string.phone)
        STRING_CITY = context.getString(R.string.city)
    }
}