package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.MESSAGES
import com.example.stockmanagementsym.data.dao.SaleDao
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.anko.doAsync

class SaleLogic(private val saleDao: SaleDao, private val cartLogic: CartLogic,private val listManager: ListManager) {

    private var saleList: MutableList<Sale> = mutableListOf()

    fun createSale(sale: Sale) {
        doAsync {
            try {
                cartLogic.clearCart()
                updateSaleList()
                saleDao.insert(sale)
                notifyUserTransactionSuccess()
            } catch (e: Exception) {
                listManager.showResultTransaction(false)
            }
        }
    }

    suspend fun getSaleList(): MutableList<Sale> {
        withContext(Dispatchers.IO) {
            saleList = saleDao.selectSaleList()
        }
        return saleList
    }

    private fun updateSaleList() {
        doAsync {
            saleList = saleDao.selectSaleList()
        }
    }

    suspend fun searchSales(searchText: String): List<Sale> {
        return getSaleList().filter { sale -> sale.getCustomer().getName().toLowerCase().contains(searchText.toLowerCase()) }
    }

    private fun notifyUserTransactionSuccess() {
        listManager.reloadList()
        listManager.showResultTransaction(true)
    }
}