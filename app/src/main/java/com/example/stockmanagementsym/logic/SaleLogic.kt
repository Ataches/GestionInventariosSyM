package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.dao.SaleDao
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.logic.list_manager.IListManager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

class SaleLogic(private val saleDao: SaleDao, private val listManager: IListManager) {

    private var saleList: MutableList<Sale> = mutableListOf()

    fun loadData() {
        doAsyncResult {
            if(saleList.isEmpty())
                saleList = saleDao.selectSaleList()
            uiThread {
                listManager.reloadList(saleList.toMutableList())
            }
        }
    }

    fun createSale(sale: Sale) {
        doAsync {
            try {
                saleDao.insert(sale)
                updateSaleList()
                notifyUserTransactionSuccess()
            } catch (e: Exception) {
                listManager.showResultTransaction(false)
            }
        }
    }

    fun getSaleList(): MutableList<Sale> {
        if(saleList.isEmpty())
            loadData()
        return saleList
    }

    private fun updateSaleList() {
        saleList = saleDao.selectSaleList()
    }

    fun searchSales(searchText: String): List<Sale> {
        return getSaleList().filter { sale -> sale.getCustomer().getName().toLowerCase().contains(searchText.toLowerCase()) }
    }

    private fun notifyUserTransactionSuccess() {
        listManager.reloadList(saleList.toMutableList())
        listManager.showResultTransaction(true)
    }
}