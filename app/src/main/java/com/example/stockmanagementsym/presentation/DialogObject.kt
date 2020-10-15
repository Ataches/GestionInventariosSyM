package com.example.stockmanagementsym.presentation

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

object DialogObject {

    private lateinit var layoutInflater:LayoutInflater

    //Customer
    fun dialogNewCustomer(view: View, insert:Boolean) {
        layoutInflater = view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewNewCustomer = layoutInflater.inflate(R.layout.dialog_new_customer, null, false)
        var dialog: Dialog = Dialog(view.context)

        dialog.setContentView(viewNewCustomer)
        dialog.show()

        viewNewCustomer.buttonProductListToNewProduct.setOnClickListener {

            Model.setCustomerNewSale(
                Customer(
                    viewNewCustomer.editTextCustomerName.text.toString(),
                    viewNewCustomer.editTextCustomerAddress.text.toString(),
                    viewNewCustomer.editTextPhone.text.toString(),
                    viewNewCustomer.editTextCity.text.toString()
                )
            )
            if(insert) {
                showResultTransaction(Model.createNewCustomer(),view)
                showCustomerName(view,Model.getCustomerNewSale())
            }else{
                showResultTransaction(Model.editCustomer(), view)
            }

            dialog.dismiss()
        }
        viewNewCustomer.buttonNewProductCancel.setOnClickListener {
            dialog.dismiss()
        }
    }
    // New Sale
    fun dialogNewSale(view: View) {
        layoutInflater = view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewNewSale = layoutInflater.inflate(R.layout.dialog_new_sale, null, false)
        var dialog: Dialog = Dialog(view.context)


        dialog.setContentView(viewNewSale)
        dialog.show()

        viewNewSale.buttonNewCustomer.setOnClickListener{
            dialogNewCustomer(viewNewSale,true)
        }
        viewNewSale.buttonSelectCustomerName.setOnClickListener{
                dialogSelectList(
                                   viewNewSale,
                                   data = Model.getCustomerList().map {
                                                                        it.getName()+" "+
                                                                        it.getAddress()+" "+
                                                                        it.getPhone()+" "+
                                                                        it.getCity()
                                                                }.toTypedArray()
                                )
        }
        viewNewSale.buttonDate.setOnClickListener {
            Model.setDateSale(dialogGetDate(viewNewSale))
        }
        viewNewSale.buttonNewSale.setOnClickListener {
            showResultTransaction(Model.updateNewSale(), view)
            FragmentData.reloadCartList()
            dialog.dismiss()
        }
        viewNewSale.buttonNewSaleCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    //New product
    fun confirmCreateProduct(viewElement:View){
        val newProductFragment = viewElement.findFragment<NewProductFragment>()
        Model.setNewProduct(
            Product(
                newProductFragment.editTextProductName.text.toString(),
                newProductFragment.editTextProductPrice.text.toString().toInt(),
                newProductFragment.editTextProductDesc.text.toString(),
                R.drawable.ic_login.toString().toInt(),
                newProductFragment.editTextProductQuantity.text.toString().toInt()
            )
        )

        val builder = AlertDialog.Builder(newProductFragment.context)
        builder.setTitle(newProductFragment.getString(R.string.titleAlertNewProd))
        val message = newProductFragment.getString(R.string.messageAlertNewProd)+
                "\n"+Model.getNewProduct()
        builder.setPositiveButton("Si"){ _,_ ->
            showResultTransaction(Model.createNewProduct(), viewElement)
        }
        builder.setNegativeButton("No"){ _,_ ->
            showMessage(newProductFragment.requireContext(), "Modifique los datos si es necesario")
        }
        builder.setMessage(message)
        builder.create()
        builder.show()
    }

    // Dialogs
    fun dialogConfirmRegister(view: View, data:Any, title:String, message:String){

        val builder = AlertDialog.Builder(view.context)
        builder.setTitle(title)
        val messageDialog = message +
                "\n" + data
        builder.setPositiveButton("Si"){_,_ ->
            FragmentData.setConfirmRegister(true)
            Controller.onClick(view)
        }
        builder.setNegativeButton("No") { _, _ ->
            FragmentData.setConfirmRegister(false)
            showMessage(view.context,"Modifique los datos si es necesario")
        }
        builder.setMessage(messageDialog)
        builder.create()
        builder.show()
    }
    private fun dialogGetDate(view: View):Calendar{
        val date = Calendar.getInstance()
        val builder = DatePickerDialog(view.context, { _, yyyy, mm, dd ->
            date.set(yyyy,mm,dd)
            view.textViewDateSelected.text = view.context.getString(R.string.date)+": "+FragmentData.getDate(date)
        }, 2020, 9, 1)
        builder.show()
        return date
    }
    fun dialogSelectList(view: View, data:Array<String> ){
        val builder=AlertDialog.Builder(view.context)

        builder.setItems(data){ _, item ->
            Model.setCustomerSelected(view, item)
        }

        builder.create()
        builder.show()
    }

    // Show messages
    fun showCustomerName(view: View,customer: Customer) {
        view.textViewSaleCustomerNameSelected?.text = customer.getName()
    }
    private fun showResultTransaction(resultTransaction: Boolean, view: View){
        if (resultTransaction)
            showMessage(view.context, "Se realizo la operación con exito")
        else
            showMessage(view.context, "No se pudo realizar la operación")
    }
    fun showMessage(context:Context ,message:String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
