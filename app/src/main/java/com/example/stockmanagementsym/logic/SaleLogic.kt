package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.dao.SaleDao
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SaleLogic(private var saleDao: SaleDao) {

    private var cartLogic:CartLogic ?= null
    private var saleList: List<Sale> = listOf()


    private fun getCartLogic(): CartLogic {
        if(cartLogic==null)
            cartLogic = CartLogic()
        return cartLogic!!
    }

    suspend fun getSaleList(): List<Sale> {
        GlobalScope.launch(Dispatchers.IO){
            saleList = saleDao.selectSaleList()
        }
        delay(100)
        return saleList
    }


    suspend fun createSale(sale: Sale): Boolean {
        saleDao.insert(sale)
        return try {
            getCartLogic().clearCart()
            updateSaleList()
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

    fun getTotalPriceCart(): Int {
        return getCartLogic().getTotalPrice()
    }

    fun getCartList(): MutableList<Product> {
        return getCartLogic().getCartList()
    }
    fun removeElementCart(item: Product):Boolean{
        return getCartLogic().removeElementCart(item)
    }

}