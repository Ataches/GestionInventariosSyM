package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.CONSTANTS
import com.example.stockmanagementsym.data.dao.CustomerDao
import com.example.stockmanagementsym.logic.business.Customer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CustomerLogic(private val customerDao: CustomerDao) {

    private var customerList: MutableList<Customer> = mutableListOf()

    suspend fun updateCustomer(customer: Customer):Boolean {
        return try{
            withContext(Dispatchers.IO) {
                customerDao.update(customer)
                updateCustomerList()
            }
            true
        }catch (e:Exception){
            false
        }
    }

    suspend fun createCustomer(newCustomer: Customer): Boolean {
        return try{
            withContext(Dispatchers.IO) {
                customerDao.insert(newCustomer)
                updateCustomerList()
            }
            true
        }catch (e: Exception){
            false
        }
    }

    suspend fun deleteCustomer(customer: Customer):Boolean{
        return try{
            withContext(Dispatchers.IO) {
                customerDao.delete(customer)
                updateCustomerList()
            }
            true
        }catch (e:Exception){
            false
        }
    }

    suspend fun searchCustomer(searchText: String): List<Customer> {
        return getCustomerList().filter{ item -> item.getName().toLowerCase().contains(searchText.toLowerCase())}
    }

    suspend fun getCustomerList(): MutableList<Customer> {
        if(customerList.isEmpty()){
            withContext(Dispatchers.IO) {
                customerList = customerDao.selectCustomerList()
            }
        }
        return customerList
    }

    private suspend fun updateCustomerList(){
        customerList = customerDao.selectCustomerList()
    }
}