package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.dao.SaleDao
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale

class SaleLogic(private var saleDao: SaleDao) {

    private var cartLogic:CartLogic ?= null
    private var saleList: List<Sale> ?= null

    private fun getCartLogic(): CartLogic {
        if(cartLogic==null)
            cartLogic = CartLogic()
        return cartLogic!!
    }

    fun getSaleList(): List<Sale> {
        if(saleList==null)
            saleList = saleDao.selectSaleList()
        return saleList!!
    }

    fun createSale(sale: Sale): Boolean {
        saleDao.insert(sale)
        return try {
            getCartLogic().clearCart()
            updateSaleList()
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun updateSaleList() {
        saleList = saleDao.selectSaleList()
    }

    fun searchSales(searchText:String): List<Sale> {
        return getSaleList().filter { sale -> sale.getCustomer().getName().toLowerCase().contains(searchText.toLowerCase())}
    }

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