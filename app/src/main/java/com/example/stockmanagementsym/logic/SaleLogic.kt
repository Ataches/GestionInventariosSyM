package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.dao.SaleDao
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaleLogic(private var saleDao: SaleDao) {

    private var cartLogic:CartLogic ?= null
    private var saleList: List<Sale> = listOf()

    private fun getCartLogic(): CartLogic {
        if(cartLogic==null)
            cartLogic = CartLogic()
        return cartLogic!!
    }

    suspend fun getSaleList(): List<Sale> {
        withContext(Dispatchers.IO) {
            saleList = saleDao.selectSaleList()
        }
        return saleList
    }

    suspend fun createSale(sale: Sale): Boolean {
        saleDao.insert(sale)
        return try {
            withContext(Dispatchers.IO) {
                getCartLogic().clearCart()
                updateSaleList()
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    private suspend fun updateSaleList() {
        saleList = saleDao.selectSaleList()
    }

    suspend fun searchSales(searchText:String): List<Sale> {
        return getSaleList().filter { sale -> sale.getCustomer().getName().toLowerCase().contains(searchText.toLowerCase())}
    }



    //Cart
    fun addProductToCart(item: Product):String{
        return getCartLogic().addProduct(item)
    }

    fun getTotalPriceCart(): Double {
        return getCartLogic().getTotalPrice()
    }
    fun getCartList(): MutableList<Product> {
        return getCartLogic().getCartList()
    }

    fun removeElementCart(item: Product):Boolean{
        return getCartLogic().removeElementCart(item)
    }
}