package com.example.stockmanagementsym.logic.classes

import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.list_manager.IListManager
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

class CustomerLogic : AbstractListLogic() {

    override fun loadData() {
        doAsyncResult {
            if (elementList.isEmpty())
                elementList = customerLogicDao.selectCustomerList().toMutableList()
            uiThread {
                iListManager!!.reloadList(elementList)
            }
        }
    }

    override fun insert(element: Any) {
        doAsyncResult {
            try {
                customerLogicDao.insert(element as Customer)
                updateMutableList()
            } catch (e: Exception) {
                iListManager?.showResultTransaction(false)
            }
        }
    }

    override fun update(element: Any) {
        doAsyncResult {
            try {
                customerLogicDao.update(element as Customer)
                updateMutableList()
            } catch (e: Exception) {
                iListManager?.showResultTransaction(false)
            }
        }
    }

    override fun updateMutableList() {
        doAsyncResult {
            try {
                elementList = customerLogicDao.selectCustomerList().toMutableList()
                uiThread {
                    notifyUserTransactionSuccess()
                }
            } catch (e: Exception) {
                iListManager?.showResultTransaction(false)
            }
        }
    }

    override fun delete(element: Any) {
        doAsyncResult {
            try {
                customerLogicDao.delete(element as Customer)
                updateMutableList()
            } catch (e: Exception) {
                iListManager?.showResultTransaction(false)
            }
        }
    }

    override fun searchTextInMutableList(searchedText: String) {
        val listSearched = (elementList as MutableList<Customer>).filter { item ->
            item.getName().toLowerCase().contains(searchedText.toLowerCase())
        }
        iListManager?.reloadList(listSearched.toMutableList())
    }

    override fun setListManager(iListManager: IListManager) {
        this.iListManager = iListManager
    }

}