package com.example.stockmanagementsym.presentation.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.AsyncTask
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class DialogView(private var androidView: AndroidView) {

    private lateinit var layoutInflater:LayoutInflater
    private var customerList:Array<String> =  arrayOf()

    // New Sale
    private fun dialogNewSale(view: View) {
        GlobalScope.launch(Dispatchers.IO){
            customerList = androidView.getCustomerList()
        }
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
                data = customerList,
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
    //Customer
    fun dialogRegisterCustomer(view: View, updateBoolean: Boolean) {
        layoutInflater =
            view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewNewCustomer = layoutInflater.inflate(R.layout.dialog_new_customer, null, false)
        var dialog: Dialog = Dialog(view.context)

        dialog.setContentView(viewNewCustomer)
        dialog.show()

        if(updateBoolean){
            viewNewCustomer.textViewCustomerTitle.text = viewNewCustomer.context.getString(R.string.titleAlertUpdateCustomer)
            viewNewCustomer.editTextCustomerName.setText(androidView.getCustomerToEdit().getName())
            viewNewCustomer.editTextCustomerAddress.setText(androidView.getCustomerToEdit().getAddress())
            viewNewCustomer.editTextPhone.setText(androidView.getCustomerToEdit().getPhone())
            viewNewCustomer.editTextCity.setText(androidView.getCustomerToEdit().getCity())
            viewNewCustomer.buttonProductListToNewProduct.setText(viewNewCustomer.context.getString(R.string.titleAlertUpdateCustomer))
        }else
            viewNewCustomer.textViewCustomerTitle.text = viewNewCustomer.context.getString(R.string.titleAlertNewCustomer)


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

    //New product - Search or create fragment data and obtains it's information, after asks to create or update product
    fun dialogRegisterProduct(view: View, updateBoolean: Boolean){
        val newProductFragment:NewProductFragment = view.findFragment()
        val product =
            Product(
                newProductFragment.editTextProductName.text.toString(),
                newProductFragment.editTextProductPrice.text.toString().toInt(),
                newProductFragment.editTextProductDesc.text.toString(),
                FragmentData.getStringBitMap(),
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
    fun dialogConfirmRegister(view: View, data: Any, title: String, message: String){

        val builder = AlertDialog.Builder(view.context)
        builder.setTitle(title)
        val messageDialog = message +
                "\n" + data
        builder.setPositiveButton("Si") { _, _ ->
            when (title) {
                view.context.getString(R.string.titleAlertNewProd) -> {
                    GlobalScope.launch(Dispatchers.IO){
                        showResultTransaction(view, androidView.createProduct(data as Product))
                    }
                    androidView.goToProductList()
                }

                view.context.getString(R.string.titleAlertUpdateProd) -> {
                    GlobalScope.launch(Dispatchers.IO){
                        showResultTransaction(view,androidView.updateProduct(data as Product))
                    }
                    androidView.goToProductList()
                }


                view.context.getString(R.string.titleAlertNewSale) -> dialogNewSale(view)

                view.context.getString(R.string.titleAlertNewSaleBd) ->{
                    GlobalScope.launch(Dispatchers.IO){
                        showResultTransaction(
                            view,
                            androidView.createSale(androidView.getNewSale())
                        )
                    }
                }
                view.context.getString(R.string.titleAlertNewCustomer) -> {

                    GlobalScope.launch(Dispatchers.IO) {
                        showResultTransaction(view, androidView.createCustomer(data as Customer))
                    }
                    data as Customer
                    showCustomerName(view, data.getName())
                }
                view.context.getString(R.string.titleAlertUpdateCustomer) -> {
                    GlobalScope.launch(Dispatchers.IO){
                        showResultTransaction(view, androidView.updateCustomer(data as Customer))
                    }
                }
            }
        }
        builder.setNegativeButton("No") { _, _ ->
            FragmentData.setBooleanUpdate(false)

            GlobalScope.launch(Dispatchers.IO) {
                showMessage(view.context, view.context.getString(R.string.modifyIfIsNecessary))
            }
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
            view.textViewDateSelected.text =
                view.context.getString(R.string.date) + ": " + dateSelected
            androidView.setDateSale(dateSelected)
        }, 2020, 9, 20)
        builder.show()
        return dateSelected
    }

    private fun dialogSelectList(view: View, data: Array<String>, title: String) {
        val builder = AlertDialog.Builder(view.context)

        builder.setTitle(title)

        builder.setItems(data) { _, item ->
            when(title){
                view.context.getString(R.string.selectCustomer) -> {
                    androidView.setCustomerSelected(item)
                    showCustomerName(view, customerList.get(item))
                }
            }
        }

        builder.create()
        builder.show()
    }

    // Show messages
    private fun showCustomerName(view: View, customerName: String) {
        view.textViewSaleCustomerNameSelected?.text = customerName
    }

    private fun showMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showResultTransaction(view: View,resultTransaction: Boolean) {
        if (resultTransaction)
            showMessage("Se realizo la operación con exito",view.context)
        else
            showMessage("No se pudo realizar la operación",view.context)
    }
    fun showMessage(message: String, context: Context) {
        CustomTask(context, message).execute()
    }
    private class CustomTask(val context: Context, val message: String) :
        AsyncTask<Void?, Void?, Void?>() {
        override fun onPostExecute(param: Void?) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        override fun doInBackground(vararg p0: Void?): Void? {
            return null
        }
    }
}
