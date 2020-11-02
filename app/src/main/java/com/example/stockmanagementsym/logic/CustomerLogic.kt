package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.dao.CustomerDao
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.list_manager.IListManager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

class CustomerLogic(private val customerDao: CustomerDao, private val listManager: IListManager) {

    private var customerList: MutableList<Customer> = mutableListOf()

    fun loadData() {
        doAsyncResult {
            if(customerList.isEmpty())
                customerList = customerDao.selectCustomerList()
            uiThread {
                listManager.reloadList(customerList.toMutableList())
            }
        }
    }

    fun updateCustomer(customer: Customer) {
        doAsync {
            try {
                customerDao.update(customer)
                updateCustomerList()
                uiThread {
                    notifyUserTransactionSuccess()
                }
            } catch (e: Exception) {
                listManager.showResultTransaction(false)
            }
        }
    }

    fun createCustomer(newCustomer: Customer) {
        doAsync {
            try {
                customerDao.insert(newCustomer)
                updateCustomerList()
                uiThread {
                    notifyUserTransactionSuccess()
                }
            } catch (e: Exception) {
                listManager.showResultTransaction(false)
            }
        }
    }

    fun deleteCustomer(customer: Customer) {
        doAsync {
            try {
                customerDao.delete(customer)
                updateCustomerList()
                uiThread {
                    notifyUserTransactionSuccess()
                }
            } catch (e: Exception) {
                listManager.showResultTransaction(false)
            }
        }
    }

    fun searchCustomer(searchText: String) {
        val listSearched = customerList.filter { item ->
            item.getName().toLowerCase().contains(searchText.toLowerCase())
        }
        listManager.reloadList(listSearched.toMutableList())
    }

    fun getCustomerList(): MutableList<Customer> {
        if(customerList.isEmpty())
            loadData()
        return customerList
    }

    private fun updateCustomerList() {
        customerList = customerDao.selectCustomerList()
    }

    private fun notifyUserTransactionSuccess() {
        listManager.reloadList(customerList.toMutableList())
        listManager.showResultTransaction(true)
    }
}