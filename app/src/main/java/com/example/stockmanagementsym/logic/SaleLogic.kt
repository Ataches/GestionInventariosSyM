package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.MESSAGES
import com.example.stockmanagementsym.data.dao.SaleDao
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.presentation.fragment.ListListener
import com.example.stockmanagementsym.presentation.view.Notifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaleLogic(private val saleDao: SaleDao, private val notifier: Notifier) {

    private lateinit var listListener: ListListener
    private var listManager: ListManager ?= null
    private var cartLogic:CartLogic ?= null
    private var saleList: MutableList<Sale> = mutableListOf()

    private fun getCartLogic(): CartLogic {
        if(cartLogic==null)
            cartLogic = CartLogic()
        return cartLogic!!
    }

    suspend fun getSaleList(): MutableList<Sale> {
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
    fun addProductToCart(item: Product){
        try {
            if(getCartLogic().addProductToCartList(item))
                getListManager().showAlertMessage(MESSAGES.CART_TITLE,MESSAGES.PRODUCT_ADDED_TO_CART)
            else
                getListManager().showAlertMessage(MESSAGES.CART_TITLE,MESSAGES.PRODUCT_ALREADY_IN_CART)
            getListManager().reloadList()
        }catch (e:Exception){
            getListManager().showAlertMessage(MESSAGES.CART_TITLE,MESSAGES.PRODUCT_NOT_ADDED_TO_CART)
        }
    }

    fun getTotalPriceCart(): String {
        return getCartLogic().getTotalPrice()
    }
    fun getCartList(): MutableList<Product> {
        return getCartLogic().getCartList()
    }

    fun removeElementCart(item: Product):Boolean{
        return getCartLogic().removeElementCart(item)
    }

    fun setListListener(listListener: ListListener){
        this.listListener = listListener
    }

    private fun getListManager():ListManager{
        if(listManager==null)
            listManager = ListManager(notifier,listListener)
        return listManager!!
    }
}