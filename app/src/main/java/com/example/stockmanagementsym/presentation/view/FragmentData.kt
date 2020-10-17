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
import com.example.stockmanagementsym.presentation.fragment.NewProductFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object FragmentData {

    private lateinit var customerToEdit: Customer
    private lateinit var itemToEdit: Product

    private var confirmRegister: Boolean = false
    private lateinit var cartListener: ListListener
    private lateinit var productListListener: ListListener
    private var customerListListener: ListListener? = null
    private var updateBoolean: Boolean = false

    private lateinit var newProductFragment: NewProductFragment
    private var idFragment: Int=0

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

    fun setUpdateBoolean(updateBoolean:Boolean){
        FragmentData.updateBoolean = updateBoolean
    }
    fun getUpdateBoolean():Boolean{
        return updateBoolean
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

    fun getNewProductFragment(): NewProductFragment {
        return newProductFragment
    }
    fun setNewProductFragment(newProductFragment: NewProductFragment){
        FragmentData.newProductFragment = newProductFragment
    }

    fun getIdFragment(): Int {
        return idFragment
    }
    fun setIdFragment(idFragment: Int){
        FragmentData.idFragment = idFragment
    }

    fun reloadCustomerList(){
        customerListListener?.reloadList()
    }
    fun reloadProductList(){
        productListListener.reloadList()
    }
    fun reloadCartList() {
        cartListener.reloadList()
    }

    fun setConfirmRegister(confirmRegister: Boolean) {
        FragmentData.confirmRegister = confirmRegister
    }
    fun getConfirmRegister():Boolean{
        return confirmRegister
    }

    fun getDate(date: Calendar): String {
        val df: DateFormat = SimpleDateFormat("dd-MMMM-yyyy")
        return df.format(date.time)
    }



    fun showMessage(context: Context, message:String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun setProductToEdit(item: Product) {
        itemToEdit = item
    }
    fun getProductToEdit(): Product {
        return itemToEdit
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

    fun dialogNewCustomer(itemView: View, b: Boolean) {
        androidModel.getAndroidView().dialogNewCustomer(itemView,b)
    }

    fun getSalesList(): List<Sale> {
        return androidModel.getSalesList()
    }

    fun addProduct(item: Product): String {
        return androidModel.addProduct(item)
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
}