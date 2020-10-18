package com.example.stockmanagementsym.presentation.view

import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.presentation.AndroidModel
import com.example.stockmanagementsym.presentation.fragment.ListListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Exception

object FragmentData {

    private lateinit var customerToEdit: Customer
    private lateinit var productToEdit: Product

    private var booleanUpdate: Boolean = false
    private lateinit var cartListener: ListListener
    private lateinit var productListListener: ListListener
    private lateinit var customerListListener: ListListener

    private lateinit var androidModel: AndroidModel
    private lateinit var userName: String

    fun setUser(userName:String){
        this.userName = userName
    }
    fun getUser(): String {
        return userName
    }

    fun setModel(androidModel: AndroidModel){
        this.androidModel = androidModel
    }
    fun setProductListListener(productListFragment: ListListener) {
        productListListener = productListFragment
    }
    fun setCartListener(cartFragment: ListListener) {
        cartListener = cartFragment
    }
    fun setCustomerListListener(customerListFragment: ListListener){
        customerListListener = customerListFragment
    }

    fun reloadCustomerList(){
        try{
            customerListListener.reloadList()
        }catch (e:Exception){
        }
    }
    fun reloadProductList(){
        productListListener.reloadList()
    }
    fun reloadCartList() {
        cartListener.reloadList()
    }

    fun setBooleanUpdate(confirmUpdate: Boolean) {
        this.booleanUpdate = confirmUpdate
    }
    fun getBooleanUpdate():Boolean{
        return booleanUpdate
    }

    fun getDate(date: Calendar): String {
        val df: DateFormat = SimpleDateFormat("dd-MMMM-yyyy")
        return df.format(date.time)
    }



    fun showMessage(context: Context, message:String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun setProductToEdit(item: Product) {
        productToEdit = item
    }
    fun getProductToEdit(): Product {
        return productToEdit
    }

    fun getCartList(): MutableList<Product> {
        return androidModel.getCartList()
    }

    fun getTotalPrice(): Int {
        return androidModel.getTotalPrice()
    }

    fun getCustomerList(): List<Customer> {
        return androidModel.getCustomerList()
    }

    fun setCustomerToEdit(customerToEdit: Customer) {
        this.customerToEdit = customerToEdit
    }

    fun getCustomerToEdit(): Customer {
        return customerToEdit
    }

    fun deleteCustomer(customer: Customer) {
        androidModel.deleteCustomer(customer)
    }

    fun getSalesList(): List<Sale> {
        return androidModel.getSalesList()
    }

    fun addProduct(item: Product): String {
        return androidModel.addProduct(item)
    }

    fun deleteProduct(item: Product) {
        androidModel.deleteProduct(item)
    }

    fun removeElementCart(context: Context, item: Product) {
        if(androidModel.removeElementCart(item))
            showMessage(context,context.getString(R.string.elementAddedToCart))
        else
            showMessage(context,context.getString(R.string.elementNotAddedToCart))
    }

    fun getProductList(): List<Product> {
        return androidModel.getProductList()
    }

    fun goToNewProduct() {
        androidModel.getAndroidView().goToNewProduct()
    }

    fun goToNewCustomer(view:View) {
        androidModel.getAndroidView().goToNewCustomer(view)
    }

}