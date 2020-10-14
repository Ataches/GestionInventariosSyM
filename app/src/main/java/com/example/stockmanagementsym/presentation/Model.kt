package com.example.stockmanagementsym.presentation

import android.view.View
import androidx.fragment.app.findFragment
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.CartLogic
import com.example.stockmanagementsym.logic.CustomerLogic
import com.example.stockmanagementsym.logic.ProductLogic
import com.example.stockmanagementsym.logic.SaleLogic
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.presentation.fragment.*
import kotlinx.android.synthetic.main.fragment_customer_list.*
import kotlinx.android.synthetic.main.fragment_new_product.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.fragment_sale_list.*
import java.util.*

object Model {

    private lateinit var newProductFragment: NewProductFragment

    //Sale
    fun setCustomerNewSale(customer: Customer){
        getSaleLogic().setCustomerNewSale(customer)
    }

    fun getCustomerNewSale(): Customer {
        return getSaleLogic().getCustomerNewSale()
    }

    fun updateNewSale() {
        getSaleLogic().setCustomerNewSale(getCustomerNewSale())
        getSaleLogic().updateNewSale()
    }
    fun getSalesList(): List<Sale> {
        return getSaleLogic().getSaleList()
    }

    fun setDateSale(date: Calendar) {
        getSaleLogic().setDateSale(date)
    }

    //Cart
    fun getCartList(): MutableList<Product> {
        return getCartLogic().getCartList()
    }

    fun getTotalPrice(): Int {
        return getCartLogic().getTotalPrice()
    }

    //Customer
    fun setCustomerToEdit(item: Customer) {
        getCustomerLogic().setCustomerToEdit(item)
    }

    fun editCustomer() {
        getCustomerLogic().setCustomerEdited(getCustomerNewSale())
        getCustomerLogic().updateCustomer()
    }

    fun getCustomerList(): List<Customer> {
        return getCustomerLogic().getCustomerList()
    }
    fun createNewCustomer() {
        getCustomerLogic().setNewCustomer(getCustomerNewSale())
        getCustomerLogic().createNewCustomer()
    }

    //Product
    //   Update product
    fun setProductToEdit(item: Product) {
        getProductLogic().setProductToEdit(item)
    }
    fun setProductEdited() {
        getProductLogic().setProductEdited(
            Product(newProductFragment.editTextProductName.text.toString(), newProductFragment.editTextProductPrice.text.toString().toInt(),
                newProductFragment.editTextProductDesc.text.toString(), R.drawable.ic_login.toString().toInt(), newProductFragment.editTextProductQuantity.text.toString().toInt())
        )
    }
    fun updateProduct() {
        getProductLogic().updateProduct()
    }
    //   New product creation
    fun setNewProduct(product: Product) {
        getProductLogic().setNewProduct(product)
    }
    fun getNewProduct(): Product {
        return getProductLogic().getNewProduct()
    }
    fun createNewProduct(){
        getProductLogic().createNewProduct()
        FragmentData.reloadProductList()
    }

    fun setNewProductFragment(newProductFragment: NewProductFragment) {
        this.newProductFragment = newProductFragment
    }
    fun getNewProductFragment(): NewProductFragment {
        return newProductFragment
    }

    //Searches
    fun searchSale(viewElement:View) {
        val view = viewElement.findFragment<SaleListFragment>()
        view.setList(getSaleLogic().searchSales(view.editTextSearchSaleList.text.toString()).toMutableList())
    }
    fun searchCustomer(viewElement:View) {
        val view = viewElement.findFragment<CustomerListFragment>()
        view.setList(getCustomerLogic().searchCustomer(view.editTextSearchCustomerList.text.toString()).toMutableList())
    }

    fun searchProduct(viewElement:View) {
        val view = viewElement.findFragment<ProductListFragment>()
        view.setList(getProductLogic().searchProduct(view.editTextSearchProductList.text.toString()).toMutableList())
    }
    //Logic classes
    private fun getCustomerLogic(): CustomerLogic {
        return CustomerLogic
    }

    private fun getSaleLogic(): SaleLogic {
        return SaleLogic
    }

    private fun getProductLogic(): ProductLogic {
        return ProductLogic
    }

    private fun getCartLogic(): CartLogic {
        return CartLogic
    }
}
