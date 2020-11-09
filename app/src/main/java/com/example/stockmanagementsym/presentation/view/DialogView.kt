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
 * Class to show dialogs to interact with user
 */
class DialogView(private var androidView: AndroidView) {

    private var newSaleCustomerBoolean: Boolean = false
    private lateinit var layoutInflater: LayoutInflater
    private var customerList: Array<String> = arrayOf()
    private lateinit var view: View

    /**
     * Dialog to register a new user, with a view element search for the fragment new user.
     * That fragment content the user data to register in the data base.
     * This method is called by an event action of a button to register
     */
    fun dialogRegisterUser(viewElement: View) {
        view = viewElement
        val newUserFragment: NewUserFragment = viewElement.findFragment()

        val userIdentification = newUserFragment.editTextUserIdentification.text.toString()
        val userName = newUserFragment.editTextUserName.text.toString()
        val password = newUserFragment.editTextPassword.text.toString()
        val privilege = newUserFragment.editTextPrivilege.selectedItem.toString()

        if (userIdentification.isEmpty() || password.isEmpty() || privilege.isEmpty()) { // Check if the data is empty
            androidView.showToastMessage(R.string.emptyData)
            return
        }

        val user =
                try {    // Try to do an user object with the data registered by the user
                    User(
                            userIdentification,
                            userName,
                            password,
                            privilege,
                            androidView.getStringFromBitMap(),
                            CONSTANTS.DEFAULT_USER_LATITUDE, CONSTANTS.DEFAULT_USER_LONGITUDE
                    )
                } catch (e: Exception) {
                    androidView.showToastMessage(R.string.transactionFailure)
                }
        androidView.setBitMap(null) //Photo already saved in user object
        dialogConfirmRegister( // Asks to the user if he wants to register a new user in the data base
                user,
                (R.string.titleAlertNewUser),
                (R.string.messageAlertNewUser)
        )
    }

    /**
     * Dialog to confirm if the user wants a new sale data base register.
     * Needs a sale date and customer data to register the products in Cart.
     * This method:  - Calls an user list or the dialog new customer to register the customer data.
     *               - Calls a date dialog to register the sale date
     *               - Sets the listeners that are used to do the register
     */
    private fun dialogNewSale() {
        loadCustomerList() // Loads the customer list to show in select list dialog
        layoutInflater = androidView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater // Search the layout inflater to start a inflate
        view = layoutInflater.inflate(R.layout.dialog_new_sale, null, false) // Do an inflate with the new sale dialog
        val dialog = Dialog(androidView.getContext())

        dialog.setContentView(view)
        dialog.show()

        view.buttonNewCustomer.setOnClickListener {
            newSaleCustomerBoolean = true // Allows to this class to know if the user is doing a new customer, with this when the user do the register. He will see the customer selected
            dialogRegisterCustomer(false) // Starts a new dialog to register a new customer, so it won't be updated
        }
        view.buttonSelectCustomerName.setOnClickListener {
            newSaleCustomerBoolean = true // Allows to this class to know if the user is doing a new customer, with this when the user do the register. He will see the customer selected
            if (customerList.isEmpty()) // If the customer list is empty start a new load
                doAsyncResult { // To load the customer list
                    loadCustomerList()
                    uiThread { // When the data loads
                        dialogSelectList( // Show to the user a select list dialog with the customers data
                                data = customerList,
                                R.string.selectCustomer
                        )
                    }
                }
            else
                dialogSelectList( // Show to the user a select list dialog with the customers data
                        data = customerList,
                        R.string.selectCustomer
                )
        }

        view.buttonDate.setOnClickListener {
            val date = dialogGetDate(view) // Starts a date dialog to allow to the user to select the date sale
            androidView.setDateSale(date) // Saves temporarily the date sale in model class
        }
        view.buttonNewSale.setOnClickListener {
            val newSale = androidView.getNewSale() // The model will do a new sale object with the data registered by the user
            if (newSale != null){
                dialogConfirmRegister( // Ask to the user if he wants to register the data in the data base
                        data = newSale,
                        title = R.string.titleAlertNewSaleBd,
                        message = R.string.messageAlertNewSale
                )
                dialog.dismiss()
            } else
                androidView.showToastMessage(R.string.emptyData)
        }
        view.buttonNewSaleCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    /**
     * Method used to load the customer list data to the new sale fragment
     * Search the customer list data and converts it to an typed array that
     * will be used to do an select dialog.
     */
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

    /**
     * Show to the user a dialog to update or create a customer.
     */
    fun dialogRegisterCustomer(updateBoolean: Boolean) {
        layoutInflater = androidView.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater // Search the layout inflater to start a inflate
        val view = layoutInflater.inflate(R.layout.dialog_new_customer, null, false) // Do an inflate with the new customer dialog
        val dialog = Dialog(androidView.getContext())

        dialog.setContentView(view)
        dialog.show()

        if (updateBoolean) { // If its a customer update, will show the customer data registered.
            val customerToEdit = androidView.getCustomerToEdit()
            view.textViewCustomerTitle.text = view.context.getString(R.string.titleAlertUpdateCustomer)
            view.editTextCustomerName.setText(customerToEdit.getName())
            view.editTextCustomerAddress.setText(customerToEdit.getAddress())
            view.editTextPhone.setText(customerToEdit.getPhone())
            view.editTextCity.setText(customerToEdit.getCity())
            view.buttonNewCustomerToNewCustomer.text = view.context.getString(R.string.titleAlertUpdateCustomer)
        } else // If not, only changes the title
            view.textViewCustomerTitle.text = view.context.getString(R.string.titleAlertNewCustomer)


        view.buttonNewCustomerToNewCustomer.setOnClickListener { // When the user wants to register a new customer check if it is an update or a new register and that the data isn't empy
            val customerName = view.editTextCustomerName.text.toString()
            val customerAddress = view.editTextCustomerAddress.text.toString()
            val customerPhone = view.editTextPhone.text.toString()

            if (customerName.isEmpty() || customerAddress.isEmpty() || customerPhone.isEmpty()) // Checks if the data is empty
                androidView.showToastMessage(R.string.emptyData)
            else {
                val customer = // Do a customer object with the data registered
                        Customer( 
                                view.editTextCustomerName.text.toString(),
                                view.editTextCustomerAddress.text.toString(),
                                view.editTextPhone.text.toString(),
                                view.editTextCity.text.toString()
                        )


                if (updateBoolean) { // Puts the corresponding title to the dialog to confirm the register
                    dialogConfirmRegister(
                            customer,
                            R.string.titleAlertUpdateCustomer,
                            R.string.messageAlertUpdateCustomer
                    )
                } else
                    dialogConfirmRegister(
                            customer,
                            R.string.titleAlertNewCustomer,
                            R.string.messageAlertNewCustomer
                    )
                dialog.dismiss()
            }
        }
        view.buttonNewCustomerCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    /**
     * Register product dialog - Search fragment data and obtains it's information, after asks to create or update product
     * This dialog checks: - If it's a product update or not
     *                     - If it's a product that is coming from the REST list or not
     */
    fun dialogRegisterProduct(viewElement: View, updateBoolean: Boolean, booleanNewProductREST: Boolean){
        view = viewElement
        val newProductFragment:NewProductFragment = view.findFragment() // Searches the new product fragment that contains the product data

        val productName = newProductFragment.editTextProductName.text.toString()
        val productPrice = newProductFragment.editTextProductPrice.text.toString()
        val productQuantity = newProductFragment.editTextProductQuantity.text.toString()

        if(productName.isEmpty() || productPrice.isEmpty() || productQuantity.isEmpty()){  // Check if the data is empty
            androidView.showToastMessage(R.string.emptyData)
            return
        }

        val product = // Do an product object with the data registered
                try{
                    Product(
                            productName,
                            productPrice.toDouble(),
                            newProductFragment.editTextProductDesc.text.toString(),
                            productQuantity.toInt(),
                            androidView.getStringFromBitMap()
                    )
                } catch (e: Exception) {
                    androidView.showToastMessage(R.string.transactionFailure)
                }
        androidView.goToProductList() // Return to the product list from the register product fragment
        if (updateBoolean && !booleanNewProductREST) { // If it is an update needs to check if it's an product from REST list. If it is, the register will be a new register
            dialogConfirmRegister(
                product,
                R.string.titleAlertUpdateProd,
                R.string.messageAlertUpdateProd
            )
        } else { // The register is a new product register. It could be new product or a new product from REST list
            dialogConfirmRegister(
                product,
                (R.string.titleAlertNewProd),
                (R.string.messageAlertNewProd)
            )
        }
    }

    /**
     * Method to confirm to the user if wants to register data.
     * Receives an message ID and title ID to show to the user.
     * Depending on the title will do an action.
     */
    fun dialogConfirmRegister(data: Any, title: Int, message: Int){
        val builder = AlertDialog.Builder(androidView.getContext()) // Starts to build the dialog
        builder.setTitle(title)
        val messageDialog = ""+androidView.getString(message)+ // Searches a string in view with the message ID
                                    "\n\n" + getRegisterData(data, title) // Calls a method that try to convert the data to an string readable by the user

        builder.setMessage(messageDialog)

        builder.setPositiveButton("Si") { _, _ ->
            when (title) { // Depends on the dialog title the action to do
                R.string.titleAlertNewProd -> {
                    androidView.createProduct(data as Product)
                    androidView.setBooleanUpdate(false) // Register ends
                    androidView.setBooleanNewProductREST(false) // Register ends
                    androidView.setBitMap(null) // If user update or register products or users is needed to remove the picture
                }
                R.string.titleAlertUpdateProd -> {
                    androidView.updateProduct(data as Product)
                    androidView.setBooleanUpdate(false) // Register ends
                    androidView.setBooleanNewProductREST(false) // Register ends
                    androidView.setBitMap(null) // If user update or register products or users is needed to remove the picture
                }
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
                        uiThread {
                            if (newSaleCustomerBoolean) { // If it's a new customer sale will show it to the user in the dialog
                                showCustomerSelected(androidView.getCustomerToString(customer))
                                loadCustomerList() // Loads the customer list to show it in new sale fragment
                                newSaleCustomerBoolean = false // Register ends
                            }
                        }
                    }
                }

                R.string.titleAlertUpdateCustomer -> {
                    androidView.updateCustomer(data as Customer)
                    androidView.setBooleanUpdate(false)
                }

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
            when (title) {
                R.string.location -> androidView.askLogOut() // The user could not want save the location, only ends his session

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

            else -> ""+data // If didn't find a way to transform the data, will return the same data
        }
    }

    private fun dialogGetDate(view: View):String{ // Dialog to ask to the user the date
        val calendar = Calendar.getInstance()
        var dateSelected = ""
        val builder = DatePickerDialog(view.context, { _, yy, mm, dd ->
            calendar.set(yy, mm, dd)
            dateSelected = androidView.getDate(calendar)
            view.textViewDateSelected.text =
                    androidView.getString(R.string.date)+dateSelected
            androidView.setDateSale(dateSelected)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)) // Puts the today's date
        builder.show()
        return dateSelected
    }

    private fun dialogSelectList(data: Array<String>, title: Int) { // Asks to the user which element wants from string array
        val builder = AlertDialog.Builder(androidView.getContext())

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

    // Show to the user in the new sale fragment the customer selected
    fun showCustomerSelected(customerNewSale: String) {
        view.textViewSaleCustomerNameSelected?.text = customerNewSale
    }
}
