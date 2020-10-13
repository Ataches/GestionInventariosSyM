package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.Data
import com.example.stockmanagementsym.logic.business.Customer

class CustomerLogic {

    private lateinit var customerEdited: Customer
    private lateinit var customerToEdit: Customer

    fun editCustomer():Boolean {
        return try{
            Data.updateCustomer(customerToEdit,customerEdited)
            true
        }catch (e:Exception){
            false
        }
    }

    fun setCustomerToEdit(customerToEdit : Customer) {
        this.customerToEdit = customerToEdit
    }

    fun setCustomerEdited(customerEdited: Customer) {
        this.customerEdited = customerEdited
    }
}