package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.dao.CustomerDao
import com.example.stockmanagementsym.logic.business.Customer

class CustomerLogic(private val customerDao: CustomerDao) {

    private var customerList: List<Customer> ?= null

    fun updateCustomer(customer: Customer):Boolean {
        return try{
            customerDao.update(customer)
            updateCustomerList()
            true
        }catch (e:Exception){
            false
        }
    }

    fun createCustomer(newCustomer: Customer): Boolean {
        return try{
            customerDao.insert(newCustomer)
            updateCustomerList()
            true
        }catch (e: Exception){
            false
        }
    }

    fun searchCustomer(searchText: String): List<Customer> {
        return getCustomerList().filter{ item -> item.getName().toLowerCase().contains(searchText)}
    }

    fun getCustomerList(): List<Customer> {
        if(customerList == null)
            customerList = customerDao.selectCustomerList()
        return customerList!!
    }

    private fun updateCustomerList(){
        customerList = customerDao.selectCustomerList()
    }
}