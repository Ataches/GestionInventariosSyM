package com.example.stockmanagementsym.presentation.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.findFragment
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.fragment.NewProductFragment
import kotlinx.android.synthetic.main.dialog_new_customer.view.*
import kotlinx.android.synthetic.main.dialog_new_sale.view.*
import kotlinx.android.synthetic.main.fragment_new_product.*
import java.util.*

class DialogView(private var androidView: AndroidView) {

    private lateinit var layoutInflater:LayoutInflater

    //Customer
    fun dialogRegisterCustomer(view: View, updateBoolean: Boolean) {
        layoutInflater =
            view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewNewCustomer = layoutInflater.inflate(R.layout.dialog_new_customer, null, false)
        var dialog: Dialog = Dialog(view.context)

        dialog.setContentView(viewNewCustomer)
        dialog.show()

        viewNewCustomer.textViewCustomerTitle.text =
            if(updateBoolean)
                viewNewCustomer.context.getString(R.string.titleAlertUpdateCustomer)
            else
                viewNewCustomer.context.getString(R.string.titleAlertNewCustomer)

        viewNewCustomer.buttonProductListToNewProduct.setOnClickListener {
            val customer =
                Customer(
                    viewNewCustomer.editTextCustomerName.text.toString(),
                    viewNewCustomer.editTextCustomerAddress.text.toString(),
                    viewNewCustomer.editTextPhone.text.toString(),
                    viewNewCustomer.editTextCity.text.toString()
                )


            if (updateBoolean){
                dialogConfirmRegister(
                    view,
                    customer,
                    view.context.getString(R.string.titleAlertUpdateCustomer),
                    view.context.getString(R.string.messageAlertUpdateCustomer)
                )
                FragmentData.setBooleanUpdate(false)
            }
            else
                dialogConfirmRegister(
                    view,
                    customer,
                    view.context.getString(R.string.titleAlertNewCustomer),
                    view.context.getString(R.string.messageAlertNewCustomer)
                )

            dialog.dismiss()
        }
        viewNewCustomer.buttonNewProductCancel.setOnClickListener {
            dialog.dismiss()
        }
    }
    // New Sale
    private fun dialogNewSale(view: View) {
        layoutInflater = view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewNewSale = layoutInflater.inflate(R.layout.dialog_new_sale, null, false)
        val dialog: Dialog = Dialog(view.context)


        dialog.setContentView(viewNewSale)
        dialog.show()

        viewNewSale.buttonNewCustomer.setOnClickListener{
            dialogRegisterCustomer(viewNewSale, false)
        }
        viewNewSale.buttonSelectCustomerName.setOnClickListener{
                dialogSelectList(
                                   viewNewSale,
                                   data = androidView.getCustomerList(),
                                   view.context.getString(R.string.selectCustomer)
                                )
        }
        viewNewSale.buttonDate.setOnClickListener {
            val date = dialogGetDate(viewNewSale)
            androidView.setDateSale(date)
        }
        viewNewSale.buttonNewSale.setOnClickListener {
            dialogConfirmRegister(
                                    view,
                                    data = androidView.getNewSale(),
                                    title = view.context.getString(R.string.titleAlertNewSaleBd),
                                    message = view.context.getString(R.string.messageAlertNewSale)
                                 )
            dialog.dismiss()
        }
        viewNewSale.buttonNewSaleCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    //New product - Search or create fragment data and obtains it's information, after asks to create or update product
    fun dialogRegisterProduct(view: View, updateBoolean: Boolean){
        val newProductFragment:NewProductFragment = view.findFragment()
        val product =
            Product(
                newProductFragment.editTextProductName.text.toString(),
                newProductFragment.editTextProductPrice.text.toString().toInt(),
                newProductFragment.editTextProductDesc.text.toString(),
                R.drawable.ic_login.toString().toInt(),
                newProductFragment.editTextProductQuantity.text.toString().toInt()
            )
        if (updateBoolean) {
            dialogConfirmRegister(
                view,
                product,
                newProductFragment.getString(R.string.titleAlertUpdateProd),
                newProductFragment.getString(R.string.messageAlertUpdateProd)
            )
            FragmentData.setBooleanUpdate(false)
        }else {
            dialogConfirmRegister(
                view,
                product,
                newProductFragment.getString(R.string.titleAlertNewProd),
                newProductFragment.getString(R.string.messageAlertNewProd)
            )
        }
    }

    // Dialogs
    fun dialogConfirmRegister(view: View, data:Any, title:String, message:String){

        val builder = AlertDialog.Builder(view.context)
        builder.setTitle(title)
        val messageDialog = message +
                "\n" + data
        builder.setPositiveButton("Si") { _, _ ->
            when (title) {
                view.context.getString(R.string.titleAlertNewProd) -> {
                    showResultTransaction(androidView.createProduct(data as Product), view)
                }
                view.context.getString(R.string.titleAlertUpdateProd) -> {
                    showResultTransaction(androidView.updateProduct(data as Product), view)
                }
                view.context.getString(R.string.titleAlertNewSale) -> {
                    dialogNewSale(view)
                }
                view.context.getString(R.string.titleAlertNewSaleBd) -> {
                    showResultTransaction(androidView.createSale(androidView.getNewSale()), view)
                }
                view.context.getString(R.string.titleAlertNewCustomer) -> {
                    showResultTransaction(androidView.createCustomer(data as Customer), view)
                    showCustomerName(view, data)
                }
                view.context.getString(R.string.titleAlertUpdateCustomer) ->
                    showResultTransaction(androidView.updateCustomer(data as Customer), view)
            }
        }
        builder.setNegativeButton("No") { _, _ ->
            FragmentData.setBooleanUpdate(false)
            showMessage(view.context, view.context.getString(R.string.modifyIfIsNecessary))
        }
        builder.setMessage(messageDialog)
        builder.create()
        builder.show()
    }
    private fun dialogGetDate(view: View):String{
        val date = Calendar.getInstance()
        var dateSelected: String = ""
        val builder = DatePickerDialog(view.context, { _, yy, mm, dd ->
            date.set(yy, mm, dd)
            dateSelected = FragmentData.getDate(date)
            view.textViewDateSelected.text = view.context.getString(R.string.date) + ": " + dateSelected
            androidView.setDateSale(dateSelected)
        }, 2020, 9, 20)
        builder.show()
        return dateSelected
    }

    private fun dialogSelectList(view: View, data: Array<String>, title:String) {
        val builder = AlertDialog.Builder(view.context)

        builder.setTitle(title)

        builder.setItems(data) { _, item ->
            when(title){
                view.context.getString(R.string.selectCustomer)->{
                    androidView.setCustomerSelected(view, item)
                }
            }
        }

        builder.create()
        builder.show()
    }

    // Show messages
    fun showCustomerName(view: View,customer: Customer) {
        view.textViewSaleCustomerNameSelected?.text = customer.getName()
    }

    private fun showResultTransaction(resultTransaction: Boolean, view: View) {
        if (resultTransaction)
            showMessage(view.context, "Se realizo la operación con exito")
        else
            showMessage(view.context, "No se pudo realizar la operación")
    }

    fun showMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
