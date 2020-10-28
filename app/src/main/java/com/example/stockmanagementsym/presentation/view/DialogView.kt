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
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.presentation.fragment.NewProductFragment
import com.example.stockmanagementsym.presentation.fragment.NewUserFragment
import kotlinx.android.synthetic.main.dialog_new_customer.view.*
import kotlinx.android.synthetic.main.dialog_new_sale.view.*
import kotlinx.android.synthetic.main.fragment_new_product.*
import kotlinx.android.synthetic.main.fragment_new_user.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class DialogView(private var androidView: AndroidView) {

    private lateinit var layoutInflater:LayoutInflater
    private var customerList:Array<String> =  arrayOf()
    private lateinit var view: View

    //New user
    fun dialogRegisterUser(viewElement: View) {
        view = viewElement
        val newUserFragment:NewUserFragment = viewElement.findFragment()
        val user =
            User(
                    newUserFragment.editTextUserName.text.toString(),
                    newUserFragment.editTextPassword.text.toString(),
                    newUserFragment.editTextPrivilege.text.toString(),
                    androidView.getStringFromBitMap(),
                    -1.0, -1.0,
            )

        dialogConfirmRegister(
            viewElement,
            user,
            view.context.getString(R.string.titleAlertNewUser),
            view.context.getString(R.string.messageAlertNewUser)
        )
    }

    // New Sale
    private fun dialogNewSale(viewElement: View) {
        loadCustomerList()
        layoutInflater = viewElement.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = layoutInflater.inflate(R.layout.dialog_new_sale, null, false)
        val dialog: Dialog = Dialog(viewElement.context)

        dialog.setContentView(view)
        dialog.show()

        view.buttonNewCustomer.setOnClickListener{
            dialogRegisterCustomer(view, false)
        }
        view.buttonSelectCustomerName.setOnClickListener{
            dialogSelectList(
                data = customerList,
                viewElement.context.getString(R.string.selectCustomer)
            )
        }

        view.buttonDate.setOnClickListener {
            val date = dialogGetDate(view)
            androidView.setDateSale(date)
        }
        view.buttonNewSale.setOnClickListener {
            dialogConfirmRegister(
                viewElement,
                data = androidView.getNewSale(),
                title = viewElement.context.getString(R.string.titleAlertNewSaleBd),
                message = viewElement.context.getString(R.string.messageAlertNewSale)
            )
            dialog.dismiss()
        }
        view.buttonNewSaleCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun loadCustomerList() {
        GlobalScope.launch(Dispatchers.IO){
            customerList = androidView.getCustomerList().map {
                it.getName()+" "+
                        it.getAddress()+" "+
                        it.getPhone()+" "+
                        it.getCity()
            }.toTypedArray()
        }
    }

    //Customer
    fun dialogRegisterCustomer(viewElement: View, updateBoolean: Boolean) {
        layoutInflater =
            viewElement.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.dialog_new_customer, null, false)
        val dialog: Dialog = Dialog(viewElement.context)

        dialog.setContentView(view)
        dialog.show()

        if(updateBoolean){
            view.textViewCustomerTitle.text = view.context.getString(R.string.titleAlertUpdateCustomer)
            view.editTextCustomerName.setText(androidView.getCustomerToEdit().getName())
            view.editTextCustomerAddress.setText(androidView.getCustomerToEdit().getAddress())
            view.editTextPhone.setText(androidView.getCustomerToEdit().getPhone())
            view.editTextCity.setText(androidView.getCustomerToEdit().getCity())
            view.buttonNewCustomerToNewCustomer.text = view.context.getString(R.string.titleAlertUpdateCustomer)
        }else
            view.textViewCustomerTitle.text = view.context.getString(R.string.titleAlertNewCustomer)


        view.buttonNewCustomerToNewCustomer.setOnClickListener {
            val customer =
                Customer(
                    view.editTextCustomerName.text.toString(),
                    view.editTextCustomerAddress.text.toString(),
                    view.editTextPhone.text.toString(),
                    view.editTextCity.text.toString()
                )


            if (updateBoolean){
                dialogConfirmRegister(
                    viewElement,
                    customer,
                    viewElement.context.getString(R.string.titleAlertUpdateCustomer),
                    viewElement.context.getString(R.string.messageAlertUpdateCustomer)
                )
                FragmentData.setBooleanUpdate(false)
            }
            else
                dialogConfirmRegister(
                    viewElement,
                    customer,
                    viewElement.context.getString(R.string.titleAlertNewCustomer),
                    viewElement.context.getString(R.string.messageAlertNewCustomer)
                )

            dialog.dismiss()
        }
        view.buttonNewCustomerCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    //New product - Search fragment data and obtains it's information, after asks to create or update product
    fun dialogRegisterProduct(viewElement: View, updateBoolean: Boolean){
        view = viewElement
        val newProductFragment:NewProductFragment = viewElement.findFragment()
        val product =
            Product(
                newProductFragment.editTextProductName.text.toString(),
                newProductFragment.editTextProductPrice.text.toString().toDouble(),
                newProductFragment.editTextProductDesc.text.toString(),
                androidView.getStringFromBitMap(),
                newProductFragment.editTextProductQuantity.text.toString().toInt()
            )
        if (updateBoolean) {
            dialogConfirmRegister(
                viewElement,
                product,
                newProductFragment.getString(R.string.titleAlertUpdateProd),
                newProductFragment.getString(R.string.messageAlertUpdateProd)
            )
            FragmentData.setBooleanUpdate(false)
        }else {
            dialogConfirmRegister(
                viewElement,
                product,
                newProductFragment.getString(R.string.titleAlertNewProd),
                newProductFragment.getString(R.string.messageAlertNewProd)
            )
        }
    }

    // Dialogs
    fun dialogConfirmRegister(viewElement: View, data: Any, title: String, message: String){
        view = viewElement
        val builder = AlertDialog.Builder(viewElement.context)
        builder.setTitle(title)
        val messageDialog = message +
                "\n\n" + getRegisterData(data,title,viewElement)
        builder.setPositiveButton("Si") { _, _ ->
            when (title) {
                viewElement.context.getString(R.string.titleAlertNewProd) -> {
                    GlobalScope.launch(Dispatchers.IO){
                        showResultTransaction(androidView.createProduct(data as Product),view.context)
                    }
                    androidView.goToProductList()
                }

                viewElement.context.getString(R.string.titleAlertUpdateProd) -> {
                    GlobalScope.launch(Dispatchers.IO){
                        showResultTransaction(androidView.goToNewProduct(data as Product),view.context)
                    }
                    androidView.goToProductList()
                }


                viewElement.context.getString(R.string.titleAlertNewSale) -> dialogNewSale(viewElement)

                viewElement.context.getString(R.string.titleAlertNewSaleBd) ->{
                    GlobalScope.launch(Dispatchers.IO){
                        showResultTransaction(androidView.createSale(),view.context)
                    }
                }

                viewElement.context.getString(R.string.titleAlertNewCustomer) -> {
                    val customer = data as Customer
                    GlobalScope.launch(Dispatchers.IO) {
                        showResultTransaction(androidView.createCustomer(customer,view),view.context)
                        loadCustomerList()
                    }
                    androidView.getCustomerToString(customer)
                    showCustomerSelected(androidView.getCustomerToString(customer))
                }

                viewElement.context.getString(R.string.titleAlertUpdateCustomer) -> {
                    GlobalScope.launch(Dispatchers.IO){
                        showResultTransaction(androidView.updateCustomer(data as Customer),view.context)
                    }
                }
                viewElement.context.getString(R.string.titleAlertNewUser) -> {
                    GlobalScope.launch(Dispatchers.IO){
                        showResultTransaction(androidView.newUser(data as User),view.context)
                    }
                    androidView.goNewUserToUserList()
                }
                viewElement.context.getString(R.string.location) -> {
                    androidView.saveUserLocation(viewElement.context)
                    androidView.askLogOut(viewElement.context)
                }
                viewElement.context.getString(R.string.logOut) ->{
                    androidView.logOut(viewElement.context)
                }
            }
        }
        builder.setNegativeButton("No") { _, _ ->
            FragmentData.setBooleanUpdate(false)
            when(title){
                viewElement.context.getString(R.string.location) -> {
                    androidView.askLogOut(viewElement.context)
                }
                else -> {
                    GlobalScope.launch(Dispatchers.IO) {
                        showToastMessage(viewElement.context.getString(R.string.modifyIfIsNecessary),viewElement.context)
                    }
                }
            }
        }
        builder.setMessage(messageDialog)
        builder.create()
        builder.show()
    }

    private fun getRegisterData(data: Any, title: String, view: View): String {
        return when(title){
            view.context.getString(R.string.titleAlertNewCustomer) -> androidView.getCustomerToString(data as Customer)

            view.context.getString(R.string.titleAlertUpdateCustomer) -> androidView.getCustomerToString(data as Customer)

            view.context.getString(R.string.titleAlertNewSale) -> androidView.getCartListToString(data as MutableList<Product>)

            view.context.getString(R.string.titleAlertNewSaleBd) -> androidView.getSaleToString(data as Sale)

            view.context.getString(R.string.titleAlertNewProd) -> androidView.getProductToString(data as Product)
            view.context.getString(R.string.titleAlertUpdateProd) -> androidView.getProductToString(data as Product)

            else -> ""+data
        }
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

    private fun dialogSelectList(data: Array<String>, title: String) {
        val builder = AlertDialog.Builder(view.context)

        builder.setTitle(title)

        builder.setItems(data) { _, item ->
            when(title){
                view.context.getString(R.string.selectCustomer) -> {
                    androidView.setCustomerSelected(item,view)
                    showCustomerSelected(customerList[item])
                }
            }
        }

        builder.create()
        builder.show()
    }

    // Show messages
    fun showCustomerSelected(customerNewSale: String) {
        view.textViewSaleCustomerNameSelected?.text = customerNewSale
    }

    fun showResultTransaction(resultTransaction: Boolean, context:Context) {
        if (resultTransaction)
            showToastMessage("Se realizo la operación con exito",context)
        else
            showToastMessage("No se pudo realizar la operación",context)
    }
    fun showToastMessage(message: String, context: Context) {
        MessageTask("", message,"Toast",context).execute()
    }
    fun showAlertMessage(title: String, message: String, context:Context) {
        MessageTask(title,message,"Alert",context).execute()
    }

    private class MessageTask(
        val title: String,
        val message: String,
        val type: String,
        val context: Context
    ) : AsyncTask<Void?, Void?, Void?>() {
        override fun onPostExecute(param: Void?) {
            when(type){
                "Toast" -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                "Alert" -> showAlertMessage(title,message, context)
            }
        }

        fun showAlertMessage(title: String, message: String, context:Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setNeutralButton("Cerrar"){_,_->}
            builder.create()
            builder.show()
        }

        override fun doInBackground(vararg p0: Void?): Void? {
            return null
        }
    }


}
