package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.CartObject
import com.example.stockmanagementsym.data.Data
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Sale
import java.util.*

object SaleLogic {

    private lateinit var customerNewSale: Customer
    private lateinit var date: Calendar

    fun updateNewSale(): Boolean {
        return try {
            Data.updateSale(
                Sale(
                    customerNewSale, date, CartObject.getList()
                )
            )
            CartObject.clearCart()
            true
        } catch (e: Exception) {
            false
        }
    }
    fun searchSales(searchText:String): List<Sale> {
        return Data.getSalesList().filter { sale -> sale.getCustomer().getName().toLowerCase().contains(searchText.toLowerCase())}
    }

    fun getSaleList(): List<Sale> {
        return Data.getSalesList()
    }

    fun setDateSale(date: Calendar) {
        this.date = date
    }

    fun setCustomerNewSale(customerNewSale: Customer) {
        this.customerNewSale = customerNewSale
    }

    fun getCustomerNewSale(): Customer {
        return customerNewSale
    }
}