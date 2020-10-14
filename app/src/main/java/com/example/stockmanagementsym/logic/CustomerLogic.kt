package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.Data
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.presentation.Model

object CustomerLogic {

    private lateinit var newCustomer: Customer
    private lateinit var customerEdited: Customer
    private lateinit var customerToEdit: Customer

    fun updateCustomer():Boolean {
        return try{
            Data.updateCustomer(customerToEdit,customerEdited)
            true
        }catch (e:Exception){
            false
        }
    }

    fun createNewCustomer(): Boolean {
        return try{
            Data.createCustomer(newCustomer)
            true
        }catch (e: Exception){
            false
        }
    }

    fun setCustomerToEdit(customerToEdit : Customer) {
        this.customerToEdit = customerToEdit
    }

    fun setCustomerEdited(customerEdited: Customer) {
        this.customerEdited = customerEdited
    }

    fun getCustomerList(): List<Customer> {
        return Data.getCustomerList()
    }

    fun searchCustomer(searchText: String): List<Customer> {
        return Data.getCustomerList().filter{ item -> item.getName().toLowerCase().contains(searchText)}
    }

    fun setNewCustomer(newCustomer: Customer) {
        this.newCustomer = newCustomer
    }

}