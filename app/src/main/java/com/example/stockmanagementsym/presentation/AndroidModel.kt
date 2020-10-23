package com.example.stockmanagementsym.presentation

import android.content.Intent
import android.util.Log
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
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.presentation.fragment.CustomerListFragment
import com.example.stockmanagementsym.presentation.fragment.NewProductFragment
import com.example.stockmanagementsym.presentation.fragment.ProductListFragment
import com.example.stockmanagementsym.presentation.fragment.SaleListFragment
import com.example.stockmanagementsym.presentation.view.AndroidView
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.fragment_customer_list.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.fragment_sale_list.*
import kotlinx.coroutines.*

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
    //User
    fun getUserList(): List<User> {
        var list:List<User> = listOf()
        GlobalScope.launch(Dispatchers.IO){
            list = getUserLogic().selectUserList()
        }
        return list
    }

    //Sale
    suspend fun createSale(newSale: Sale): Boolean {
        newSale.getProductList().forEach {
            val product  = getProductLogic().searchProducts(it.idProduct)
            product.setQuantity(product.getQuantity() - it.getQuantity())
            getProductLogic().updateProduct(product)
        }
        val resultTransaction = getSaleLogic().createSale(newSale)
        FragmentData.reloadCartList()
        if(resultTransaction)
            this.newSale = null
        return resultTransaction
    }

    fun getNewSale(): Sale {
        if(newSale==null)
            newSale = Sale(customerNewSale, dateSale, getSaleLogic().getCartList())
        return newSale!!
    }

    suspend fun getSalesList(): List<Sale> {
        return getSaleLogic().getSaleList()
    }

    fun setDateSale(dateSale: String) {
        this.dateSale = dateSale
    }

    fun addProductToCart(item: Product): String {
        val stringResult = getSaleLogic().addProductToCart(item)
        GlobalScope.launch (Dispatchers.IO){
            FragmentData.reloadCartList()
        }
        return stringResult
    }

    fun removeElementCart(item: Product): Boolean {
        return getSaleLogic().removeElementCart(item)
    }

    fun getSaleToString(sale: Sale): String {
        return "Cliente: \n"+getCustomerLogic().customerToString(sale.getCustomer()) +"\n\n"+
                "Fecha: "+sale.getDate()+"\n\n"+
                "Listado de productos: \n"+ getProductLogic().productListToString(sale.getProductList(),false)
    }

    //Cart
    fun getCartList(): MutableList<Product> {
        return getSaleLogic().getCartList()
    }

    fun getTotalPrice(): Int {
        return getSaleLogic().getTotalPriceCart()
    }

    fun getCartListToString(mutableList: MutableList<Product>): String {
        return getProductLogic().productListToString(mutableList,false)
    }

    //Customer
    suspend fun updateCustomer(customerToUpdate: Customer): Boolean {
        val customerToEdit = FragmentData.getCustomerToEdit()
        customerToUpdate.idCustomer = customerToEdit.idCustomer
        val resultTransaction = getCustomerLogic().updateCustomer(customerToUpdate)
        FragmentData.reloadCustomerList()
        return resultTransaction
    }

    suspend fun getCustomerList(): List<Customer> {
        return getCustomerLogic().getCustomerList()
    }

    fun setCustomerSelected(item: Int) {
        GlobalScope.launch(Dispatchers.IO){
            customerNewSale = getCustomerList()[item]
        }
    }

    suspend fun createCustomer(customer: Customer): Boolean {
        val resultTransaction = getCustomerLogic().createCustomer(customer)
        customerNewSale = customer //If it is a new customer register from new sale fragment
        FragmentData.reloadCustomerList()
        return resultTransaction
    }

    suspend fun deleteCustomer(customer: Customer): Boolean {
        val resultTransaction = getCustomerLogic().deleteCustomer(customer)
        FragmentData.reloadCustomerList()
        return resultTransaction
    }

    fun getCustomerToString(customer: Customer): String {
        return getCustomerLogic().customerToString(customer)
    }

    //Product
    suspend fun getProductList(): List<Product> {
        var list:List<Product> = listOf()
        withContext(Dispatchers.IO) {
           list = getProductLogic().getProductList()
        }
        return list
    }

    fun updateProduct(productToUpdate: Product):Boolean {
        var resultTransaction = false
        GlobalScope.launch(Dispatchers.IO){
            val productToEdit = FragmentData.getProductToEdit()
            productToUpdate.idProduct = productToEdit.idProduct
            resultTransaction = getProductLogic().updateProduct(productToUpdate)
            FragmentData.reloadProductList()
        }
        return resultTransaction
    }
    fun deleteProduct(product: Product): Boolean {
        var resultTransaction:Boolean = false
        GlobalScope.launch(Dispatchers.IO){
            resultTransaction = getProductLogic().deleteProduct(product)
        }
        return resultTransaction
    }

    fun getProductToString(product: Product): String {
        return getProductLogic().productToString(product,false)
    }

    //   New product creation
    suspend fun createProduct(product: Product): Boolean {
        val result = getProductLogic().createProduct(product)
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
        GlobalScope.launch(Dispatchers.IO){
            val saleListFragment = view.findFragment<SaleListFragment>()
            saleListFragment.setList(
                getSaleLogic().searchSales(saleListFragment.editTextSearchSaleList.text.toString())
                    .toMutableList()
            )
        }
    }

    fun searchCustomer(viewElement: View) {
        GlobalScope.launch(Dispatchers.IO){
            val view = viewElement.findFragment<CustomerListFragment>()
            view.setList(
                getCustomerLogic().searchCustomer(view.editTextSearchCustomerList.text.toString())
                    .toMutableList()
            )
        }
    }

    fun searchProduct(viewElement: View) {
        GlobalScope.launch(Dispatchers.IO){
            val view = viewElement.findFragment<ProductListFragment>()
            view.setList(
                getProductLogic().searchProduct(view.editTextSearchProductList.text.toString())
                    .toMutableList()
            )
        }
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

    suspend fun confirmLogin(login: LoginActivity, user: String, password: String) {
        dataBaseLogic = ViewModelProvider(login).get(DataBaseLogic::class.java)
        if(getUserLogic().confirmLogin(user, password)){
            FragmentData.setUser(userName = user)
            getAndroidView().showMessage(login,login.getString(R.string.welcome)+" "+user)

            val intent = Intent(login, MainActivity::class.java)
            login.startActivity(intent)
        }
        else
            getAndroidView().showMessage(
                login,
                "Usuario $user no encontrado o contraseña incorrecta"
            )


    }
}
