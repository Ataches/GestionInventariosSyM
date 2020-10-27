package com.example.stockmanagementsym.presentation

import android.app.Activity
import android.content.Context
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
import com.example.stockmanagementsym.presentation.fragment.*
import com.example.stockmanagementsym.presentation.view.AndroidView
import kotlinx.android.synthetic.main.fragment_customer_list.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.fragment_sale_list.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AndroidModel{

    private var logOutBoolean: Boolean = false
    private var userLatitude: Double = -1.0
    private var userLongitude: Double = -1.0
    private lateinit var dataBaseLogic: DataBaseLogic
    private var androidView:AndroidView ?= null

    private var customerLogic: CustomerLogic ?= null
    private var productLogic: ProductLogic ?= null

    private var user: User? = null
    private var saleLogic: SaleLogic ?= null
    private var userLogic:UserLogic ?= null
    private lateinit var dateSale: String
    private lateinit var customerNewSale: Customer
    private var newSale: Sale ?= null

    //User
    fun getUser():User{
        if(user==null)
            user = getUserLogic().getUser()
        return user!!
    }

    suspend fun newUser(user: User): Boolean {
        return try{
            if(getUserPrivileges()!="admin")
                return false
            withContext(Dispatchers.IO) {
                getUserLogic().insertUser(user)
            }
            getAndroidView().reloadUserList()
            true
        }catch (e:Exception){
            false
        }
    }

    suspend fun deleteUser(user: User): Boolean {
        return try{
            if(getUserPrivileges()!="admin")
                return false
            withContext(Dispatchers.IO) {
                getUserLogic().deleteUser(user)
            }
            getAndroidView().reloadUserList()
            true
        }catch (e:Exception){
            false
        }
    }

    suspend fun updateUser(user: User): Boolean {
        return try{
            if(getUserPrivileges()!="admin")
                return false
            withContext(Dispatchers.IO) {
                getUserLogic().updateUser(user)
            }
            getAndroidView().reloadUserList()
            true
        }catch (e:Exception){
            false
        }
    }

    fun getUserName(): String {
        return getUser().getName()
    }

    fun getUserPrivileges(): String {
        return getUser().getPrivilege()
    }

    suspend fun getUserList(): List<User> {
        var list:List<User> = listOf()
        withContext(Dispatchers.IO) {
            list = getUserLogic().getUserList()
        }
        return list
    }

    fun setUserLocation(latitude: Double, longitude: Double) {
        userLatitude = (latitude)
        userLongitude = (longitude)
    }

    fun getUserLatitude(): Double {
        return userLatitude
    }

    fun getUserLongitude(): Double {
        return userLongitude
    }

    //Sale
    suspend fun createSale(): Boolean {
        return try{
            withContext(Dispatchers.IO) {
                getNewSale().getProductList().forEach {
                        val product  = getProductLogic().searchProducts(it.idProduct)
                        product.setQuantity(product.getQuantity() - it.getQuantity())
                        getProductLogic().updateProduct(product)
                    }
                getSaleLogic().createSale(getNewSale())
                getAndroidView().reloadCartList()
            }
            this.newSale = null
            true
        }catch(e:Exception){
            false
        }
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

    fun addProductToCart(item: Product,view: View){
        GlobalScope.launch (Dispatchers.IO){
            getAndroidView().showAlertMessage(view.context.getString(R.string.cart),getSaleLogic().addProductToCart(item),view.context)
            getAndroidView().reloadCartList()
        }
    }

    fun removeElementCart(item: Product): Boolean {
        return getSaleLogic().removeElementCart(item)
    }

    fun getSaleToString(sale: Sale): String {
        return "Fecha: "+sale.getDate()+"\n\n"+
               "Cliente: \n\n"+getCustomerToString(sale.getCustomer()) +"\n\n"+
               "Listado de productos: \n\n"+ getProductLogic().productListToString(sale.getProductList(),false)
    }

    //Cart
    fun getCartList(): MutableList<Product> {
        return getSaleLogic().getCartList()
    }

    fun getTotalPriceCart(): String {
        return getSaleLogic().getTotalPriceCart()
    }

    fun getCartListToString(mutableList: MutableList<Product>): String {
        return getProductLogic().productListToString(mutableList,false)
    }

    //Customer
    suspend fun updateCustomer(customerToUpdate: Customer): Boolean {
        val customerToEdit = getAndroidView().getCustomerToEdit()
        customerToUpdate.idCustomer = customerToEdit.idCustomer
        val resultTransaction = getCustomerLogic().updateCustomer(customerToUpdate)
        getAndroidView().reloadCustomerList()
        return resultTransaction
    }

    suspend fun getCustomerList(): List<Customer> {
        return getCustomerLogic().getCustomerList()
    }

    fun setCustomerSelected(item: Int) {
        GlobalScope.launch (Dispatchers.IO){
            customerNewSale = getCustomerList()[item]
        }
    }

    suspend fun createCustomer(customer: Customer): Boolean {
        val resultTransaction = getCustomerLogic().createCustomer(customer)
        customerNewSale = customer //If it is a new customer register from new sale fragment
        getAndroidView().reloadCustomerList()
        return resultTransaction
    }

    suspend fun deleteCustomer(customer: Customer): Boolean {
        val resultTransaction = getCustomerLogic().deleteCustomer(customer)
        getAndroidView().reloadCustomerList()
        return resultTransaction
    }

    fun getCustomerToString(customer: Customer): String {
        return getCustomerLogic().customerToString(customer)
    }

    //Product
    suspend fun getProductList(): List<Product> {
        return try {
            var list = listOf<Product>()
            withContext(Dispatchers.IO) {
                list = getProductLogic().getProductList()
            }
            list
        }catch (e: Exception){
            listOf()
        }
    }

    suspend fun updateProduct(productToUpdate: Product):Boolean {
        return try{
            withContext(Dispatchers.IO) {
                val productToEdit = getAndroidView().getProductToEdit()
                productToUpdate.idProduct = productToEdit.idProduct
                getProductLogic().updateProduct(productToUpdate)
                getAndroidView().reloadProductList()
            }
            true
        }catch (e: Exception){
            false
        }
    }

    suspend fun deleteProduct(product: Product): Boolean {
        return try{
            withContext(Dispatchers.IO) {
                getProductLogic().deleteProduct(product)
            }
            getAndroidView().reloadProductList()
            true
        }catch (e: Exception){
            false
        }
    }

    fun getProductToString(product: Product): String {
        return getProductLogic().productToString(product,false)
    }

    //   New product creation
    suspend fun createProduct(product: Product): Boolean {
        val result = getProductLogic().createProduct(product)
        getAndroidView().reloadProductList()
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

    fun searchUser(view: View) {
        GlobalScope.launch(Dispatchers.IO){
            val view = view.findFragment<UserListFragment>()
            view.setList(
                getUserLogic().searchUser(view.editTextSearchUserList.text.toString())
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

    private fun getAndroidView(): AndroidView {
        if(androidView == null)
            androidView = AndroidView(this)
        return androidView!!
    }

    suspend fun confirmLogin(login: LoginActivity, user: String, password: String) {
        dataBaseLogic = ViewModelProvider(login).get(DataBaseLogic::class.java)
        if(getUserLogic().confirmLogin(user, password)){
            getAndroidView().showToastMessage(login,login.getString(R.string.welcome)+" "+getUser().getName())

            userLatitude = getUser().getLatitude()
            userLongitude = getUser().getLongitude()

            val intent = Intent(login, MainActivity::class.java)
            login.startActivity(intent)
        }
        else
            getAndroidView().showToastMessage(
                login,
                "Usuario $user no encontrado o contraseña incorrecta"
            )
    }

    suspend fun askLogOut(context: Context){
        val location = context.getString(R.string.location)+" "+userLatitude+" - "+userLongitude
        val activity = context as Activity
        withContext(Dispatchers.IO) {
            getAndroidView().dialogConfirmRegister(location,context.getString(R.string.location), context.getString(R.string.saveLocation),activity)
        }
    }

    suspend fun logOut(logOutBoolean:Boolean, context: Context){
        val activity = context as Activity
        if(logOutBoolean){
            user!!.setLatitude(userLatitude)
            user!!.setLongitude(userLongitude)

            withContext(Dispatchers.IO) {
                getAndroidView().showResultTransaction(getUserLogic().updateUser(user!!), context)
            }

            activity.finish()
            user = null
        }else
            getAndroidView().showToastMessage(context, context.getString(R.string.modifyIfIsNecessary))
    }
}
