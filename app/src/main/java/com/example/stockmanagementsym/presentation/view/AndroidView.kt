package com.example.stockmanagementsym.presentation.view

import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.AndroidModel

class AndroidView(private val androidModel: AndroidModel)  {

    private var dialogObject:DialogView ?= null
    var controller:AndroidController = AndroidController

    private lateinit var userName: String

    init {
        controller.setViewClass(this)
    }

    private fun getDialogView(): DialogView {
        if(dialogObject==null)
            dialogObject = DialogView(this)
        return dialogObject!!
    }

    fun showCustomerName(view: View, customerNewSale: Customer) {
        getDialogView().showCustomerName(view, customerNewSale)
    }

    fun dialogNewSale(view: View) {
        getDialogView().dialogNewSale(view)
    }

    fun dialogConfirmRegister(
        view: View,
        data: MutableList<Product>,
        title: String,
        message: String
    ){
        getDialogView().dialogConfirmRegister(
                                                view = view,
                                                data = data,
                                                title = title,
                                                message = message
                                            )
    }

    fun searchProduct(view: View) {
        androidModel.searchProduct(view)
    }
    fun newSale(view: View) {
        androidModel.newSale(view)
    }
    fun confirmNewSale(view: View) {
        androidModel.confirmNewSale(view)
    }

    fun dialogNewCustomer(view: View, insertBoolean: Boolean) {
        getDialogView().dialogNewCustomer(view,insertBoolean)
    }

    fun searchCustomer(view: View) {
        androidModel.searchCustomer(view)
    }

    fun getPhotoGallery(view: View) {
        androidModel.getPhotoGallery(view)
    }

    fun getPhotoCamera(view: View) {
        androidModel.getPhotoCamera(view)
    }

    fun updateProduct() {
        androidModel.setProductEdited()
        androidModel.updateProduct()
    }

    fun confirmCreateProduct(view: View) {
        getDialogView().confirmCreateProduct(view)
    }

    fun searchSale(view: View) {
        androidModel.searchSale(view)
    }

    fun createNewCustomer(customer: Customer): Boolean {
        return androidModel.createNewCustomer(customer)
    }

    fun getCustomerNewSale(): Customer {
        return androidModel.getCustomerNewSale()
    }

    fun updateCustomer(customer: Customer): Boolean {
        return androidModel.updateCustomer(customer)
    }

    fun getCustomerList(): Array<String> {
        return androidModel.getCustomerList().map {
                                                it.getName()+" "+
                                                        it.getAddress()+" "+
                                                        it.getPhone()+" "+
                                                        it.getCity()
                                            }.toTypedArray()
    }

    fun setDateSale(date: String) {
        return androidModel.setDateSale(date)
    }

    fun createNewSale(): Boolean {
        return androidModel.createNewSale()
    }

    fun setCustomerSelected(view: View, item: Int) {
        return androidModel.setCustomerSelected(view, item)
    }

    fun setProductToEdit(item: Product) {
        androidModel.setProductToEdit(item)
    }

    fun createNewProduct(product: Product): Boolean {
        return androidModel.createNewProduct(product)
    }

    fun getID(): String {
        return androidModel.generateID()
    }

    fun showMessage(context: Context, message:String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}