package com.example.stockmanagementsym.logic.classes

import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.logic.list_manager.IListManager
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

class SaleLogic : AbstractListLogic() {

    override fun loadData() {
        doAsyncResult {
            if (elementList.isEmpty())
                elementList = saleLogicDao.selectSaleList().toMutableList()
            uiThread {
                iListManager?.reloadList(elementList)
            }
        }
    }

    override fun insert(element: Any) {
        doAsyncResult {
            try {
                saleLogicDao.insert(element as Sale)
                updateMutableList()
            } catch (e: Exception) {
                iListManager?.showResultTransaction(false)
            }
        }
    }

    override fun updateMutableList() {
        doAsyncResult {
            try {
                elementList = saleLogicDao.selectSaleList().toMutableList()
                uiThread {
                    notifyUserTransactionSuccess()
                }
            } catch (e: Exception) {
                iListManager?.showResultTransaction(false)
            }
        }
    }

    override fun searchTextInMutableList(searchedText: String) {
        val listSearched = (getList() as MutableList<Sale>).filter { sale -> sale.getCustomer().getName().toLowerCase().contains(searchedText.toLowerCase()) }
        iListManager?.reloadList(listSearched.toMutableList())
    }

    override fun setListManager(iListManager: IListManager) {
        this.iListManager = iListManager
    }
}