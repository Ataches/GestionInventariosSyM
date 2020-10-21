package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.dao.CustomerDao
import com.example.stockmanagementsym.logic.business.Customer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CustomerLogic(private val customerDao: CustomerDao) {

    private var customerList: List<Customer> = listOf()

    suspend fun updateCustomer(customer: Customer):Boolean {
        return try{
            customerDao.update(customer)
            updateCustomerList()
            true
        }catch (e:Exception){
            false
        }
    }

    suspend fun createCustomer(newCustomer: Customer): Boolean {
        return try{
            customerDao.insert(newCustomer)
            updateCustomerList()
            true
        }catch (e: Exception){
            false
        }
    }

    suspend fun deleteCustomer(customer: Customer):Boolean{
        return try{
            customerDao.delete(customer)
            updateCustomerList()
            true
        }catch (e:Exception){
            false
        }
    }

    suspend fun searchCustomer(searchText: String): List<Customer> {
        return getCustomerList().filter{ item -> item.getName().toLowerCase().contains(searchText)}
    }

    suspend fun getCustomerList(): List<Customer> {
        if(customerList.isEmpty()){
            GlobalScope.launch(Dispatchers.IO){
                customerList = customerDao.selectCustomerList()
            }
            delay(100)
        }
        return customerList
    }

    private suspend fun updateCustomerList(){
        customerList = customerDao.selectCustomerList()
    }
}