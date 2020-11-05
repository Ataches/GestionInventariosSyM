package com.example.stockmanagementsym.presentation.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.findFragment
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.data.CONSTANTS
import com.example.stockmanagementsym.data.MESSAGES
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.presentation.AndroidView
import com.example.stockmanagementsym.presentation.fragment.NewProductFragment
import com.example.stockmanagementsym.presentation.fragment.NewUserFragment
import kotlinx.android.synthetic.main.dialog_new_customer.view.*
import kotlinx.android.synthetic.main.dialog_new_sale.view.*
import kotlinx.android.synthetic.main.fragment_new_product.*
import kotlinx.android.synthetic.main.fragment_new_user.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread
import java.util.*


/**
 * Created by Juan Sebastian Sanchez Mancilla on 30/10/2020
 */
class DialogView(private var androidView: AndroidView) {

    private var newSaleCustomerBoolean: Boolean = false
    private lateinit var layoutInflater: LayoutInflater
    private var customerList:Array<String> =  arrayOf()
    private lateinit var view: View

    //New user
    fun dialogRegisterUser(viewElement: View) {
        view = viewElement
        val newUserFragment: NewUserFragment = viewElement.findFragment()
        val user =
                try{
                    User(
                        newUserFragment.editTextUserName.text.toString(),
                        newUserFragment.editTextPassword.text.toString(),
                        newUserFragment.editTextPrivilege.text.toString(),
                        androidView.getStringFromBitMap(),
                        CONSTANTS.DEFAULT_USER_LATITUDE, CONSTANTS.DEFAULT_USER_LONGITUDE,
                    )
                } catch (e: Exception) {
                    androidView.showToastMessage(R.string.emptyData)
                }
        androidView.setBitMap(null) //Photo already saved in user data
        dialogConfirmRegister(
            user,
            (R.string.titleAlertNewUser),
            (R.string.messageAlertNewUser)
        )
    }

    // New Sale
    private fun dialogNewSale() {
        loadCustomerList()
        layoutInflater = androidView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = layoutInflater.inflate(R.layout.dialog_new_sale, null, false)
        val dialog = Dialog(androidView.getContext())

        dialog.setContentView(view)
        dialog.show()

        view.buttonNewCustomer.setOnClickListener {
            newSaleCustomerBoolean = true
            dialogRegisterCustomer(false)
        }
        view.buttonSelectCustomerName.setOnClickListener {
            newSaleCustomerBoolean = true
            if (customerList.isEmpty())
                doAsyncResult {
                    loadCustomerList()
                    uiThread {
                        dialogSelectList(
                            data = customerList,
                            R.string.selectCustomer
                        )
                    }
                }
            else
                dialogSelectList(
                    data = customerList,
                    R.string.selectCustomer
                )
        }

        view.buttonDate.setOnClickListener {
            val date = dialogGetDate(view)
            androidView.setDateSale(date)
        }
        view.buttonNewSale.setOnClickListener {
            val newSale = androidView.getNewSale()
            if (newSale != null)
                dialogConfirmRegister(
                    data = newSale,
                    title = R.string.titleAlertNewSaleBd,
                    message = R.string.messageAlertNewSale
                )
            dialog.dismiss()
        }
        view.buttonNewSaleCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    // Method used to load de customer list data to the new sale fragment
    private fun loadCustomerList() {
        doAsync {
            customerList = (androidView.getCustomerList() as MutableList<Customer>).map {
                MESSAGES.STRING_NAME + it.getName() + CONSTANTS.STRING_NEW_LINE +
                        MESSAGES.STRING_ADDRESS + it.getAddress() + CONSTANTS.STRING_NEW_LINE +
                        MESSAGES.STRING_PHONE + it.getPhone() + CONSTANTS.STRING_NEW_LINE +
                        MESSAGES.STRING_CITY + it.getCity() + CONSTANTS.STRING_NEW_LINE
            }.toTypedArray()
        }
    }

    //Customer
    fun dialogRegisterCustomer(updateBoolean: Boolean) {
        layoutInflater = androidView.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.dialog_new_customer, null, false)
        val dialog = Dialog(androidView.getContext())

        dialog.setContentView(view)
        dialog.show()

        if (updateBoolean) {
            val customerToEdit = androidView.getCustomerToEdit()
            view.textViewCustomerTitle.text = view.context.getString(R.string.titleAlertUpdateCustomer)
            view.editTextCustomerName.setText(customerToEdit.getName())
            view.editTextCustomerAddress.setText(customerToEdit.getAddress())
            view.editTextPhone.setText(customerToEdit.getPhone())
            view.editTextCity.setText(customerToEdit.getCity())
            view.buttonNewCustomerToNewCustomer.text = view.context.getString(R.string.titleAlertUpdateCustomer)
        } else
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
                        customer,
                        R.string.titleAlertUpdateCustomer,
                        R.string.messageAlertUpdateCustomer
                )
                androidView.setBooleanUpdate(false)
            } else
                dialogConfirmRegister(
                        customer,
                        R.string.titleAlertNewCustomer,
                        R.string.messageAlertNewCustomer
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
        val newProductFragment:NewProductFragment = view.findFragment()
        val product =
                try{
                    Product(
                            newProductFragment.editTextProductName.text.toString(),
                            newProductFragment.editTextProductPrice.text.toString().toDouble(),
                            newProductFragment.editTextProductDesc.text.toString(),
                            newProductFragment.editTextProductQuantity.text.toString().toInt(),
                            androidView.getStringFromBitMap()
                    )
                } catch (e: Exception) {
                    androidView.showToastMessage(R.string.emptyData)
                }
        androidView.setBitMap(null) //Photo already saved in product data
        androidView.goToProductList()
        if (updateBoolean) {
            dialogConfirmRegister(
                product,
                R.string.titleAlertUpdateProd,
                R.string.messageAlertUpdateProd
            )
            androidView.setBooleanUpdate(false)
        } else {
            dialogConfirmRegister(
                product,
                (R.string.titleAlertNewProd),
                (R.string.messageAlertNewProd)
            )
        }
    }

    // Dialogs
    fun dialogConfirmRegister(data: Any, title: Int, message: Int){
        val builder = AlertDialog.Builder(androidView.getContext())
        builder.setTitle(title)
        val messageDialog = ""+androidView.getString(message)+"\n\n" + getRegisterData(data, title)

        builder.setMessage(messageDialog)

        builder.setPositiveButton("Si") { _, _ ->
            when (title) {
                R.string.titleAlertNewProd -> androidView.createProduct(data as Product)
                R.string.titleAlertUpdateProd -> androidView.updateProduct(data as Product)
                R.string.titleAlertDeleteProd -> androidView.deleteProduct(data as Product)

                (R.string.titleAlertNewSale) -> {
                    if (androidView.getCartList().isEmpty())
                        androidView.showToastMessage(R.string.emptyData)
                    else
                        dialogNewSale()
                }

                R.string.titleAlertNewSaleBd -> androidView.insertSale()

                (R.string.titleAlertNewCustomer) -> {
                    doAsyncResult {
                        val customer = data as Customer
                        androidView.createCustomer(customer)
                        androidView.getCustomerToString(customer)
                        uiThread {
                            if (newSaleCustomerBoolean) {
                                showCustomerSelected(androidView.getCustomerToString(customer))
                                loadCustomerList() // Loads the customer list to show it in new sale fragment
                                newSaleCustomerBoolean = false
                            }
                        }
                    }
                }

                R.string.titleAlertUpdateCustomer -> androidView.updateCustomer(data as Customer)

                R.string.titleAlertNewUser -> {
                    androidView.goNewUserToUserList()
                    androidView.newUser(data as User)
                }
                (R.string.location) -> {
                    androidView.saveUserLocation()
                    androidView.askLogOut()
                }
                (R.string.logOut) -> androidView.logOut()
            }
        }
        builder.setNegativeButton("No") { _, _ ->
            androidView.setBooleanUpdate(false)
            androidView.setBitMap(null) // If user cancel the update of products or users is needed to remove the picture
            when (title) {
                (R.string.location) -> androidView.askLogOut()

                else -> androidView.showToastMessage(androidView.getString(R.string.modifyIfIsNecessary))
            }
        }
        builder.create()
        builder.show()
    }

    private fun getRegisterData(data: Any, title: Int): String { //Converts the data to string or the way to show to user
        return when(title){
            R.string.titleAlertNewCustomer -> androidView.getCustomerToString(data as Customer)
            R.string.titleAlertUpdateCustomer -> androidView.getCustomerToString(data as Customer)

            R.string.titleAlertNewSale -> androidView.getCartListToString(data as MutableList<Product>)

            R.string.titleAlertNewSaleBd -> androidView.getSaleToString(data as Sale)

            R.string.titleAlertNewProd -> androidView.getProductToString(data as Product)
            R.string.titleAlertUpdateProd -> androidView.getProductToString(data as Product)
            R.string.titleAlertDeleteProd -> androidView.getProductToString(data as Product)

            R.string.titleAlertNewUser -> androidView.getUserToString(data as User)

            else -> ""+data
        }
    }

    private fun dialogGetDate(view: View):String{
        val calendar = Calendar.getInstance()
        var dateSelected = ""
        val builder = DatePickerDialog(view.context, { _, yy, mm, dd ->
            calendar.set(yy, mm, dd)
            dateSelected = androidView.getDate(calendar)
            view.textViewDateSelected.text =
                    androidView.getString(R.string.date) + ": " + dateSelected
            androidView.setDateSale(dateSelected)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        builder.show()
        return dateSelected
    }

    private fun dialogSelectList(data: Array<String>, title: Int) {
        val builder = AlertDialog.Builder(view.context)

        builder.setTitle(androidView.getString(title))

        builder.setItems(data) { _, item ->
            when(title){
                (R.string.selectCustomer) -> {
                    androidView.setCustomerSelected(item)
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
}
