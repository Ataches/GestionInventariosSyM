package com.example.stockmanagementsym.presentation.view

import android.content.Context
import android.view.View
import androidx.fragment.app.findFragment
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.AndroidModel
import com.example.stockmanagementsym.presentation.fragment.ProductListFragment

class AndroidView(private val androidModel: AndroidModel)  {

    private var dialogView:DialogView ?= null
    var controller:AndroidController = AndroidController

    init {
        controller.setViewClass(this)
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
        Product
     */
    fun goToNewProduct(){
        controller.goNewProduct()
    }
    fun registerProduct(view: View, updateBoolean: Boolean) {
        getDialogView().dialogRegisterProduct(view, updateBoolean)
    }

    fun createProduct(product: Product): Boolean {
        val resultTransaction=androidModel.createProduct(product)
        controller.goProductList()
        return resultTransaction
    }

    fun searchProduct(view: View) {
        androidModel.searchProduct(view)
    }

    fun updateProduct(product: Product):Boolean{
        val resultTransaction = androidModel.updateProduct(product)
        controller.goProductList()
        return resultTransaction
    }
    /*
        Customer
     */
    fun registerCustomer(view: View, updateBoolean: Boolean) {
        getDialogView().dialogRegisterCustomer(view,updateBoolean)
    }

    fun createCustomer(customer: Customer): Boolean {
        return androidModel.createCustomer(customer)
    }

    fun updateCustomer(customer: Customer): Boolean {
        return androidModel.updateCustomer(customer)
    }

    fun setCustomerSelected(view: View, item: Int) {
        return androidModel.setCustomerSelected(view, item)
    }

    fun getCustomerNewSale(): Customer {
        return androidModel.getCustomerNewSale()
    }

    fun searchCustomer(view: View) {
        androidModel.searchCustomer(view)
    }

    fun getCustomerList(): Array<String> {
        return androidModel.getCustomerList().map {
                                                it.getName()+" "+
                                                        it.getAddress()+" "+
                                                        it.getPhone()+" "+
                                                        it.getCity()
                                            }.toTypedArray()
    }

    fun showCustomerName(view: View, customerNewSale: Customer) {
        getDialogView().showCustomerName(view, customerNewSale)
    }

    fun goToNewCustomer(view: View) {
        controller.onClick(view)
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

    fun createSale(): Boolean {
        return androidModel.createSale()
    }

    fun setDateSale(date: String) {
        return androidModel.setDateSale(date)
    }


    fun searchSale(view: View) {
        androidModel.searchSale(view)
    }




    fun getID(): String {
        return androidModel.generateID()
    }

    fun showMessage(context: Context, message:String){
        getDialogView().showMessage(context,message)
    }

}