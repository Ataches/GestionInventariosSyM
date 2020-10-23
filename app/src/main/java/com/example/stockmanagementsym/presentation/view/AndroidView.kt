package com.example.stockmanagementsym.presentation.view

import android.content.Context
import android.view.View
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.AndroidModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AndroidView(private val androidModel: AndroidModel)  {

    private var dialogView:DialogView ?= null
    var controller:AndroidController = AndroidController
    private var fragmentData:FragmentData ?= null

    init {
        controller.setAdroidView(this)
        getFragmentData().setAndroidView(this)
    }

    private fun getFragmentData():FragmentData{
        if(fragmentData == null){
            fragmentData  = FragmentData
        }
        return fragmentData!!
    }

    private fun getDialogView(): DialogView {
        if(dialogView==null)
            dialogView = DialogView(this)
        return dialogView!!
    }

    fun getPhotoGallery(view: View) {
        androidModel.getPhotoGallery(view)
    }

    fun getPhotoCamera(view: View) {
        androidModel.getPhotoCamera(view)
    }
    /*
        User
     */
    fun getUserList(): List<User> {
        return androidModel.getUserList()
    }

    /*
        Product
     */
    fun goToNewProduct(){
        controller.goNewProduct()
    }
    fun registerProduct(view: View, updateBoolean: Boolean) {
        getDialogView().dialogRegisterProduct(view, updateBoolean)
    }

    suspend fun createProduct(product: Product): Boolean {
        return androidModel.createProduct(product)
    }

    fun deleteProduct(product: Product): Boolean {
        return androidModel.deleteProduct(product)
    }

    suspend fun getProductList(): List<Product> {
        return androidModel.getProductList()
    }

    fun searchProduct(view: View) {
        androidModel.searchProduct(view)
    }

    suspend fun updateProduct(product: Product):Boolean{
        return androidModel.updateProduct(product)
    }

    fun goToProductList() {
        GlobalScope.launch(Dispatchers.IO){
            getFragmentData().reloadProductList()
        }
        controller.goProductList()
    }

    /*
        Customer
     */
    fun registerCustomer(view: View) {
        getDialogView().dialogRegisterCustomer(view,getFragmentData().getBooleanUpdate())
    }

    suspend fun createCustomer(customer: Customer): Boolean {
        return androidModel.createCustomer(customer)
    }

    suspend fun updateCustomer(customer: Customer): Boolean {
        return androidModel.updateCustomer(customer)
    }

    suspend fun deleteCustomer(customer: Customer): Boolean {
        return androidModel.deleteCustomer(customer)
    }

    fun setCustomerSelected(item: Int) {
        return androidModel.setCustomerSelected(item)
    }

    fun searchCustomer(view: View) {
        androidModel.searchCustomer(view)
    }

    suspend fun getCustomerList(): Array<String> {
        return androidModel.getCustomerList().map {
                                                        it.getName()+" "+
                                                                it.getAddress()+" "+
                                                                it.getPhone()+" "+
                                                                it.getCity()
                                                    }.toTypedArray()
    }

    fun goToNewCustomer(view: View) {
        controller.onClick(view)
    }

    fun getCustomerToEdit(): Customer {
        return getFragmentData().getCustomerToEdit()
    }

    fun getCustomerToString(customer: Customer): String {
        return androidModel.getCustomerToString(customer)
    }

    /*
        Sale
     */
    fun confirmNewSale(view: View) {
        getDialogView().dialogConfirmRegister(
            view,
            data = androidModel.getCartList(),
            title = view.context.getString(R.string.titleAlertNewSale),
            message = view.context.getString(R.string.messageAlertNewSale)
        )
    }

    suspend fun createSale(newSale: Sale): Boolean {
        return androidModel.createSale(newSale)
    }

    fun getNewSale(): Sale {
        return androidModel.getNewSale()
    }

    fun setDateSale(date: String) {
        return androidModel.setDateSale(date)
    }


    fun searchSale(view: View) {
        androidModel.searchSale(view)
    }

    fun getSaleToString(sale: Sale): String {
        return androidModel.getSaleToString(sale)
    }
    /*
        Cart
     */

    fun getCartListToString(mutableList: MutableList<Product>): String {
        return androidModel.getCartListToString(mutableList)
    }

    fun showMessage(context: Context, message:String){
        getDialogView().showMessage(message,context)
    }

    fun getProductToString(product: Product): String {
        return androidModel.getProductToString(product)
    }
}