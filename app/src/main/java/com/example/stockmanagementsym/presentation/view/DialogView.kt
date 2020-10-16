package com.example.stockmanagementsym.presentation.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.util.Log
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
    fun dialogNewCustomer(view: View, insert:Boolean) {
        layoutInflater = view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewNewCustomer = layoutInflater.inflate(R.layout.dialog_new_customer, null, false)
        var dialog: Dialog = Dialog(view.context)

        dialog.setContentView(viewNewCustomer)
        dialog.show()

        viewNewCustomer.buttonProductListToNewProduct.setOnClickListener {

            androidView.setCustomerNewSale(
                Customer(
                    androidView.getID(),
                    viewNewCustomer.editTextCustomerName.text.toString(),
                    viewNewCustomer.editTextCustomerAddress.text.toString(),
                    viewNewCustomer.editTextPhone.text.toString(),
                    viewNewCustomer.editTextCity.text.toString()
                )
            )
            if(insert) {
                showResultTransaction(androidView.createNewCustomer(),view)
                showCustomerName(view,androidView.getCustomerNewSale())
            }else{
                Log.d("PRUEBA", "Llega")
                showResultTransaction(androidView.updateCustomer(viewNewCustomer.context), view)
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
                                   data = androidView.getCustomerList()
                                )
        }
        viewNewSale.buttonDate.setOnClickListener {
            var date = dialogGetDate(viewNewSale)
            androidView.setDateSale(date)
        }
        viewNewSale.buttonNewSale.setOnClickListener {
            showResultTransaction(androidView.updateNewSale(), view)
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
        androidView.setNewProduct(
            Product(
                androidView.getID(),
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
                "\n"+androidView.getNewProduct()
        builder.setPositiveButton("Si"){ _,_ ->
            showResultTransaction(androidView.createNewProduct(), viewElement)
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
            androidView.getController().onClick(view)
        }
        builder.setNegativeButton("No") { _, _ ->
            FragmentData.setConfirmRegister(false)
            showMessage(view.context,"Modifique los datos si es necesario")
        }
        builder.setMessage(messageDialog)
        builder.create()
        builder.show()
    }
    private fun dialogGetDate(view: View):String{
        val date = Calendar.getInstance()
        var dateSelected:String = ""
        val builder = DatePickerDialog(view.context, { _, yyyy, mm, dd ->
            date.set(yyyy,mm,dd)
            dateSelected = FragmentData.getDate(date)
            view.textViewDateSelected.text = view.context.getString(R.string.date)+": "+ dateSelected
        }, 2020, 9, 1)
        builder.show()
        return dateSelected
    }
    fun dialogSelectList(view: View, data:Array<String> ){
        val builder=AlertDialog.Builder(view.context)

        builder.setItems(data){ _, item ->
            androidView.setCustomerSelected(view, item)
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
