package com.example.stockmanagementsym.logic

import android.content.Context
import com.example.stockmanagementsym.data.AppDataBase
import com.example.stockmanagementsym.data.Data
import com.example.stockmanagementsym.data.dao.CustomerDao
import com.example.stockmanagementsym.logic.business.Customer

class CustomerLogic(private val customerDao: CustomerDao) {

    private lateinit var newCustomer: Customer
    private lateinit var customerEdited: Customer
    private lateinit var customerToEdit: Customer

    private lateinit var context: Context


    fun setContext(context:Context){
        this.context = context
    }


    fun updateCustomer():Boolean {
        return try{
            Data.updateCustomer(customerToEdit,customerEdited)
            customerDao.update(customerToEdit, customerEdited)
            true
        }catch (e:Exception){
            false
        }
    }
    fun selectCustomer(): List<Customer> {
        return customerDao.select()
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