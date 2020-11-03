package com.example.stockmanagementsym.presentation

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.presentation.fragment.FragmentData
import com.example.stockmanagementsym.presentation.fragment.ICart
import com.example.stockmanagementsym.presentation.fragment.IListListener
import com.example.stockmanagementsym.presentation.view.DialogView
import com.example.stockmanagementsym.presentation.view.NotifierView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Juan Sebastian Sanchez Mancilla on 30/10/2020
 */
class AndroidView(private val androidModel: AndroidModel) {

    private var viewNewUserFragment: View? = null
    private var viewNewProductFragment: View? = null
    private lateinit var textSearched: String
    private lateinit var context: Context
    private var androidController: AndroidController? = null

    private var customerToEdit: Customer? = null
    private var productToEdit: Product? = null
    private var booleanUpdate: Boolean = false

    private var notifierView: NotifierView? = null
    private var dialogView: DialogView? = null
    private var fragmentData: FragmentData? = null
    private lateinit var view: View

    init {
        getFragmentData().setAndroidView(this)
    }

    fun getAndroidController():AndroidController{
        if(androidController==null)
            androidController = AndroidController(this)
        return androidController!!
    }

    fun setControllerOnClickListener(view: View) {
        getAndroidController().onClick(view)
    }

    private fun getFragmentData(): FragmentData {
        if (fragmentData == null) {
            fragmentData = FragmentData
        }
        return fragmentData!!
    }

    fun setContext(context: Context) {
        getNotifierView().setNotifierContext(context)
        this.context = context
    }
    fun getContext():Context{
        return context
    }
    fun getString(stringID: Int): String {
        return context.getString(stringID)
    }
    private fun getDialogView(): DialogView {
        if (dialogView == null)
            dialogView = DialogView(this)
        return dialogView!!
    }

    fun getGallery(context: Context) {
        androidModel.getPhotoGallery(context)
    }

    fun getCamera(context: Context) {
        androidModel.getPhotoCamera(context)
    }

    /*
        User
     */
    fun setUserLocation(latitude: Double, longitude: Double) {
        androidModel.setUserLocation(latitude, longitude)
    }

    /*
        Product
     */
    fun registerProduct(view: View) {
        getDialogView().dialogRegisterProduct(view, getBooleanUpdate())
    }

    fun createProduct(product: Product) {
        androidModel.createProduct(product)
    }
    fun askDeleteProduct(product: Product){
        dialogConfirmRegister(product, R.string.titleAlertDeleteProd,
                R.string.messageAlertDeleteProd)
    }

    fun deleteProduct(product: Product) {
        androidModel.deleteProduct(product)
    }

    fun getProductList(): MutableList<Any> {
        return androidModel.getProductList()
    }

    fun searchProduct() {
        androidModel.searchProduct()
    }

    fun updateProduct(product: Product){
        androidModel.updateProduct(product)
    }

    fun goToProductList() {
        getAndroidController().goToProductList()
    }

    fun getProductToString(product: Product): String {
        return androidModel.getProductToString(product)
    }

    fun getProductToEdit(): Product {
        return productToEdit!!
    }

    fun updateProduct(product: Product, booleanUpdate: Boolean) {
        productToEdit = product
        this.booleanUpdate = booleanUpdate
        updateProduct()
    }

    private fun updateProduct() {
        getAndroidController().goToNewProduct()
    }
    /*
        Update boolean
     */
    fun setBooleanUpdate(confirmUpdate: Boolean) {
        this.booleanUpdate = confirmUpdate
    }
    fun getBooleanUpdate():Boolean{
        return booleanUpdate
    }

    fun confirmNewProduct(product: Product) {
        getDialogView().dialogConfirmRegister(
            data = product,
            title = (R.string.titleAlertNewProd),
            message = (R.string.messageAlertNewProd)
        )
    }

    /*
        Customer
     */
    // Method to create a customer in the BD
    fun createCustomer(customer: Customer) {
        androidModel.createCustomer(customer)
    }

    // Method to update a customer in the BD
    fun updateCustomer(customer: Customer) {
        androidModel.updateCustomer(customer)
    }

    // Method to edit a customer selected from customer view holder, update boolean indicates to the dialog
    // if it is a customer to edit, so the dialog will load the customer to edit data
    fun updateCustomer(customerToEdit: Customer, booleanUpdate: Boolean) {
        this.customerToEdit = customerToEdit
        this.booleanUpdate = booleanUpdate
        getDialogView().dialogRegisterCustomer(true)
    }

    // Method to delete a customer in the BD
    fun deleteCustomer(customer: Customer) {
        androidModel.deleteCustomer(customer)
    }

    fun getCustomerToEdit(): Customer {
        return customerToEdit!!
    }

    fun newCustomer() {
        getDialogView().dialogRegisterCustomer(false)
    }

    fun setCustomerSelected(item: Int, view: View) {
        this.view = view
        androidModel.setCustomerSelected(item)
    }

    fun searchCustomer() {
        androidModel.searchCustomer()
    }

    fun getCustomerList(): MutableList<Any> {
        return androidModel.getCustomerList()
    }

    fun getCustomerToString(customer: Customer): String {
        return androidModel.getCustomerToString(customer)
    }


    /*
        Sale data
     */
    fun getDate(date: Calendar): String {
        val df: DateFormat = SimpleDateFormat("dd-MMMM-yyyy")
        return df.format(date.time)
    }

    fun confirmNewSale() {
        getDialogView().dialogConfirmRegister(
            data = androidModel.getCartList(),
            title = R.string.titleAlertNewSale,
            message = R.string.messageAlertNewSale
        )
    }

    fun createSale() {
        androidModel.createSale()
    }

    fun getNewSale(): Sale? {
        return androidModel.getNewSale()
    }

    fun setDateSale(date: String) {
        return androidModel.setDateSale(date)
    }

    fun searchSale() {
        androidModel.searchSale()
    }

    fun getSaleToString(sale: Sale): String {
        return androidModel.getSaleToString(sale)
    }

    fun showProductListSaleToString(item: Sale) {
        getNotifierView().showAlertMessage(
                R.string.saleList,
                androidModel.getSaleToString(item)
        )
    }

    /*
        Cart
     */
    fun getCartListToString(mutableList: MutableList<Product>): String {
        return androidModel.getCartListToString(mutableList)
    }

    fun removeElementCart(item: Product) {
        androidModel.removeElementCart(item)
    }

    fun getCartList(): MutableList<Product> {
        return androidModel.getCartList()
    }

    fun getTotalPriceCart(): String {
        return androidModel.getTotalPriceCart()
    }
    fun addProductToCart(item: Product) {
        androidModel.addProductToCart(item)
    }

    /*
        Messages
     */
    fun showToastMessage(message: String, context: Context) {
        getNotifierView().setNotifierContext(context)
        getNotifierView().showToastMessage(message)
    }

    fun showToastMessage(message: String) {
        getNotifierView().showToastMessage(message)
    }

    fun showToastMessage(messageID: Int) {
        getNotifierView().showToastMessage(messageID)
    }

    fun showAlertMessage(titleID: Int, messageID: Int, context: Context?) {
        if (context != null)
            getNotifierView().setNotifierContext(context)
        getNotifierView().showAlertMessage(titleID, messageID)
    }

    fun showResultTransaction(updateUser: Boolean) {
        getNotifierView().showResultTransaction(updateUser)
    }

    fun dialogConfirmRegister(data: Any, title: Int, message: Int) {
        (context as Activity).runOnUiThread {
            getDialogView().dialogConfirmRegister(data, title, message)
        }
    }

    /*
        User
     */
    fun getUserNae(): String {
        return androidModel.getUserName()
    }

    fun getUserPrivileges(): String {
        return androidModel.getUserPrivileges()
    }

    fun newUser(view: View) {
        getDialogView().dialogRegisterUser(view)
    }

    fun deleteUser(user: User) {
        androidModel.deleteUser(user)
    }

    fun getNotifierView(): NotifierView {
        if(notifierView == null)
            notifierView = NotifierView()
        return notifierView!!
    }

    fun newUser(user: User){
        androidModel.createUser(user)
    }

    fun goNewUserToUserList() {
        getAndroidController().goNewUserToUserList()
    }

    fun getUserLatitude(): Double {
        return androidModel.getUserLatitude()
    }

    fun getUserLongitude(): Double {
        return androidModel.getUserLongitude()
    }

    fun getUserPhotoData(): String {
        return androidModel.getUserPhotoData()
    }

    fun searchUser() {
        androidModel.searchUser()
    }

    fun askLogOut() {
        dialogConfirmRegister(androidModel.getUserName(), R.string.logOut, R.string.messageLogOut)
    }

    fun askSaveLocation() {
        androidModel.askSaveLocation()
    }

    fun saveUserLocation() {
        androidModel.saveUserLocation()
    }

    fun logOut() {
        androidModel.logOut()
    }

    fun setBitMap(bitMap: Bitmap?) {
        androidModel.setBitMap(bitMap)
    }

    fun getStringFromBitMap(): String {
        return androidModel.getStringFromBitMap()
    }

    fun getBitMapFromString(string:String): Bitmap {
        return androidModel.getBitMapFromString(string)
    }

    fun getUserToString(user: User): String {
        return androidModel.getUserToString(user)
    }

    fun setTextSearched(textSearched: String) {
        this.textSearched = textSearched
    }
    fun getTextSearched(): String {
        return textSearched
    }

    fun setNewProductFragmentView(view: View) {
        viewNewProductFragment = view
    }

    fun getNewProductFragmentView(): View? {
        return viewNewProductFragment
    }

    fun setNewUserFragmentView(view: View) {
        viewNewUserFragment = view
    }

    fun getNewUserFragmentView(): View? {
        return viewNewUserFragment
    }

    fun notifyProductLogic(listListener: IListListener) {
        androidModel.notifyProductLogic(listListener)
    }

    fun notifyCartLogic(listListener: IListListener, iCart: ICart) {
        androidModel.notifyCartLogic(listListener, iCart)
    }

    fun notifySaleLogic(listListener: IListListener) {
        androidModel.notifySaleLogic(listListener)
    }

    fun notifyUserLogic(listListener: IListListener) {
        androidModel.notifyUserLogic(listListener)
    }

    fun notifyCustomerLogic(listListener: IListListener) {
        androidModel.notifyCustomerLogic(listListener)
    }
}