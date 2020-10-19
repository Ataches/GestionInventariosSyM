package com.example.stockmanagementsym.presentation

import android.content.Intent
import android.view.View
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProvider
import com.example.stockmanagementsym.LoginActivity
import com.example.stockmanagementsym.MainActivity
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.*
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.presentation.fragment.CustomerListFragment
import com.example.stockmanagementsym.presentation.fragment.NewProductFragment
import com.example.stockmanagementsym.presentation.fragment.ProductListFragment
import com.example.stockmanagementsym.presentation.fragment.SaleListFragment
import com.example.stockmanagementsym.presentation.view.AndroidView
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.fragment_customer_list.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.fragment_sale_list.*
import java.util.*

class AndroidModel{

    private lateinit var dataBaseLogic: DataBaseLogic

    private var androidView:AndroidView ?= null
    private var customerLogic: CustomerLogic ?= null

    private var productLogic: ProductLogic ?= null
    private var saleLogic: SaleLogic ?= null
    private var userLogic:UserLogic ?= null
    private lateinit var dateSale: String
    private lateinit var customerNewSale: Customer
    private var newSale: Sale ?= null

    init{
        getAndroidView()
        FragmentData.setModel(this)
    }

    //Sale
    fun createSale(newSale: Sale): Boolean {
        customerNewSale.idCustomer
        val resultTransaction = getSaleLogic().createSale(newSale)
        FragmentData.reloadCartList()
        if(resultTransaction)
            this.newSale = null
        return resultTransaction
    }

    fun getNewSale(): Sale {
        if(newSale==null)
            newSale = Sale(customerNewSale,dateSale,getSaleLogic().getCartList())
        return newSale!!
    }

    fun getSalesList(): List<Sale> {
        return getSaleLogic().getSaleList()
    }

    fun setDateSale(dateSale: String) {
        this.dateSale = dateSale
    }

    fun addProduct(item: Product): String {
        return getSaleLogic().addProductToCart(item)
    }

    fun removeElementCart(item: Product): Boolean {
        return getSaleLogic().removeElementCart(item)
    }

    //Cart
    fun getCartList(): MutableList<Product> {
        return getSaleLogic().getCartList()
    }

    fun getTotalPrice(): Int {
        return getSaleLogic().getTotalPriceCart()
    }

    //Customer
    fun updateCustomer(customerToUpdate: Customer): Boolean {
        val customerToEdit = FragmentData.getCustomerToEdit()
        customerToUpdate.idCustomer = customerToEdit.idCustomer
        val resultTransaction = getCustomerLogic().updateCustomer(customerToUpdate)
        FragmentData.reloadCustomerList()
        return resultTransaction
    }

    fun getCustomerList(): List<Customer> {
        return getCustomerLogic().getCustomerList()
    }

    fun setCustomerSelected(view: View, item: Int) {
        customerNewSale = getCustomerList()[item]
        getAndroidView().showCustomerName(view, customerNewSale)
    }

    fun createCustomer(customer: Customer): Boolean {
        val resultTransaction = getCustomerLogic().createCustomer(customer)
        customerNewSale = customer //If it is a new customer register from new sale fragment
        FragmentData.reloadCustomerList()
        return resultTransaction
    }

    fun deleteCustomer(customer: Customer): Boolean {
        val resultTransaction = getCustomerLogic().deleteCustomer(customer)
        FragmentData.reloadCustomerList()
        return resultTransaction
    }

    //Product
    fun getProductList(): List<Product> {
        return getProductLogic().getProductList()
    }

    fun updateProduct(productToUpdate: Product):Boolean {
        var productToEdit = FragmentData.getProductToEdit()
        productToUpdate.idProduct = productToEdit.idProduct
        val resultTransaction = getProductLogic().updateProduct(productToUpdate)
        FragmentData.reloadProductList()
        return resultTransaction
    }

    fun deleteProduct(product: Product): Boolean {
        val resultTransaction = getProductLogic().deleteProduct(product)
        FragmentData.reloadProductList()
        return resultTransaction
    }
    //   New product creation
    fun createProduct(product: Product): Boolean {
        var result = getProductLogic().createProduct(product)
        FragmentData.reloadProductList()
        return result
    }

    fun getPhotoCamera(viewElement: View) {
        val newProductFragment = viewElement.findFragment<NewProductFragment>()
        newProductFragment.startCamera()
    }

    fun getPhotoGallery(viewElement: View) {
        val newProductFragment = viewElement.findFragment<NewProductFragment>()
        newProductFragment.startGallery()
    }

    //Searches
    fun searchSale(view: View) {
        val saleListFragment = view.findFragment<SaleListFragment>()
        saleListFragment.setList(
            getSaleLogic().searchSales(saleListFragment.editTextSearchSaleList.text.toString()).toMutableList()
        )
    }

    fun searchCustomer(viewElement: View) {
        val view = viewElement.findFragment<CustomerListFragment>()
        view.setList(
            getCustomerLogic().searchCustomer(view.editTextSearchCustomerList.text.toString())
                .toMutableList()
        )
    }

    fun searchProduct(viewElement: View) {
        val view = viewElement.findFragment<ProductListFragment>()
        view.setList(
            getProductLogic().searchProduct(view.editTextSearchProductList.text.toString())
                .toMutableList()
        )
    }

    //Logic classes
    private fun getUserLogic(): UserLogic {
        if(userLogic==null)
            userLogic = UserLogic(dataBaseLogic.getUserDao())
        return userLogic!!
    }

    private fun getCustomerLogic(): CustomerLogic {
        if(customerLogic == null)
            customerLogic = CustomerLogic(dataBaseLogic.getCustomerDao())
        return customerLogic!!
    }

    private fun getSaleLogic(): SaleLogic {
        if(saleLogic==null)
            saleLogic = SaleLogic(dataBaseLogic.getSaleDao())
        return saleLogic!!
    }

    private fun getProductLogic(): ProductLogic {
        if(productLogic==null)
            productLogic = ProductLogic(dataBaseLogic.getProductDao())
        return productLogic!!
    }
    fun getAndroidView(): AndroidView {
        if(androidView == null)
            androidView = AndroidView(this)
        return androidView!!
    }

    fun generateID(): String {
        return UUID.randomUUID().toString()
    }

    fun confirmLogin(login: LoginActivity, user: String, password: String) {
        dataBaseLogic = ViewModelProvider(login).get(DataBaseLogic::class.java)
        if(getUserLogic().confirmLogin(user,password)){
            FragmentData.setUser(userName = user)
            getAndroidView().showMessage(login,login.getString(R.string.welcome)+" "+user)

            val intent = Intent(login, MainActivity::class.java)
            login.startActivity(intent)
        }
        else
            getAndroidView().showMessage(login,
                                        "Usuario $user no encontrado o contraseña incorrecta"
                                        )
    }

}
