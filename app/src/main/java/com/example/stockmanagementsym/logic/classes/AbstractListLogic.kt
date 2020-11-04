package com.example.stockmanagementsym.logic.classes

import com.example.stockmanagementsym.data.dao.CustomerDao
import com.example.stockmanagementsym.data.dao.ProductDao
import com.example.stockmanagementsym.data.dao.SaleDao
import com.example.stockmanagementsym.data.dao.UserDao
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.logic.list_manager.IListManager
import com.example.stockmanagementsym.presentation.fragment.ICart
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

abstract class AbstractListLogic {

    lateinit var productLogicDao: ProductDao
    lateinit var customerLogicDao: CustomerDao
    lateinit var saleLogicDao: SaleDao
    lateinit var userLogicDao: UserDao

    var iListManager: IListManager? = null
    var elementList: MutableList<Any> = mutableListOf()

    fun notifyUserTransactionSuccess() {
        iListManager?.reloadList(elementList)
        iListManager?.showResultTransaction(true)
    }

    // If you call this method is possible that the data didn't already
    // charged for it the loadData() method.
    // You must call it two times if the data is empty or call it in a
    // coroutine that uses the context to wait before data loads.
    fun getList(): MutableList<Any> {
        if (elementList.isEmpty()){
            doAsyncResult {
                loadData()
                uiThread {
                    iListManager?.reloadList(elementList)
                }
            }
        }
        return elementList
    }

    fun setProductDao(productLogicDao: ProductDao) {
        this.productLogicDao = productLogicDao
    }

    fun setCustomerDao(customerLogicDao: CustomerDao) {
        this.customerLogicDao = customerLogicDao
    }

    fun setSaleDao(saleLogicDao: SaleDao) {
        this.saleLogicDao = saleLogicDao
    }

    fun setUserDao(userLogicDao: UserDao) {
        this.userLogicDao = userLogicDao
    }

    abstract fun loadData()
    abstract fun insert(element: Any)
    open fun update(element: Any) {}
    abstract fun updateMutableList()

    open fun delete(element: Any) {}
    abstract fun searchTextInMutableList(searchedText: String)
    abstract fun setListManager(iListManager: IListManager)

    open fun searchByIDInMutableList(id: Long): Any? {
        return null
    }

    open fun setCartListener(iCart: ICart) {}
    open fun decreaseMutableListQuantity(mutableList: MutableList<Any>) {}

    open fun confirmLogin(userName: String, password: String){}
    open fun getUser(): User?{return null}
}
