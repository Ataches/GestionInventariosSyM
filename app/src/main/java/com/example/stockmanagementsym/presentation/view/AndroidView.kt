package com.example.stockmanagementsym.presentation.view

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.AndroidModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AndroidView(private val androidModel: AndroidModel) {

    private var dialogView: DialogView? = null
    var controller: AndroidController = AndroidController
    private var fragmentData: FragmentData? = null
    private lateinit var view:View

    init {
        controller.setAndroidView(this)
        getFragmentData().setAndroidView(this)
    }

    private fun getFragmentData(): FragmentData {
        if (fragmentData == null) {
            fragmentData = FragmentData
        }
        return fragmentData!!
    }

    private fun getDialogView(): DialogView {
        if (dialogView == null)
            dialogView = DialogView(this)
        return dialogView!!
    }

    fun getGallery(view: View) {
        androidModel.getPhotoGallery(view.context as Activity, view.context)
    }

    fun getCamera(view: View) {
        androidModel.getPhotoCamera(view.context as Activity, view.context)
    }
    /*
        User
     */

    fun getUser(): User {
        return androidModel.getUser()
    }

    suspend fun getUserList():MutableList<User> {
        return androidModel.getUserList()
    }

    fun setUserLocation(latitude: Double, longitude: Double) {
        androidModel.setUserLocation(latitude, longitude)
    }

    /*
        Product
     */
    fun goToNewProduct(view:View) {
        controller.goToNewProduct(view)
    }

    fun registerProduct(view: View, updateBoolean: Boolean) {
        getDialogView().dialogRegisterProduct(view, updateBoolean)
    }

    fun createProduct(product: Product,context: Context) {
        androidModel.createProduct(product,context)
    }
    fun askDeleteProduct(product: Product,context: Context){
        dialogConfirmRegister(product,context.getString(R.string.titleAlertDeleteProd),
                                context.getString(R.string.messageAlertDeleteProd),context as Activity)
    }
    fun deleteProduct(product: Product,context: Context) {
        GlobalScope.launch(Dispatchers.IO){
            getDialogView().showResultTransaction(androidModel.deleteProduct(product),context)
        }
    }

    suspend fun getProductList(): MutableList<Product> {
        return androidModel.getProductList()
    }

    fun searchProduct(view: View) {
        androidModel.searchProduct(view)
    }

    suspend fun goToNewProduct(product: Product): Boolean {
        return androidModel.updateProduct(product)
    }

    fun goToProductList(view: View) {
        reloadProductList()
        controller.goToProductList(view)
    }

    fun getProductToString(product: Product): String {
        return androidModel.getProductToString(product)
    }

    fun reloadProductList() {
        getFragmentData().reloadProductList()
    }

    fun getProductToEdit(): Product {
        return getFragmentData().getProductToEdit()
    }

    fun confirmNewProduct(product: Product,view: View) {
        getDialogView().dialogConfirmRegister(
            view,
            data = product,
            title = view.context.getString(R.string.titleAlertNewProd),
            message = view.context.getString(R.string.messageAlertNewProd)
        )
    }

    /*
        Customer
     */
    suspend fun createCustomer(customer: Customer, view: View): Boolean {
        this.view = view
        return androidModel.createCustomer(customer)
    }

    suspend fun updateCustomer(customer: Customer): Boolean {
        return androidModel.updateCustomer(customer)
    }

    fun updateCustomer(view: View) {
        getDialogView().dialogRegisterCustomer(view, true)
    }

    fun newCustomer(view: View) {
        getDialogView().dialogRegisterCustomer(view, false)
    }

    fun deleteCustomer(customer: Customer,context: Context){
        GlobalScope.launch(Dispatchers.IO){
            getDialogView().showResultTransaction(androidModel.deleteCustomer(customer),context)
            reloadCustomerList()
        }
    }

    fun setCustomerSelected(item: Int, view: View) {
        this.view = view
        androidModel.setCustomerSelected(item)
    }

    fun searchCustomer(view: View) {
        androidModel.searchCustomer(view)
    }

    suspend fun getCustomerList(): MutableList<Customer> {
        return androidModel.getCustomerList()
    }

    fun getCustomerToEdit(): Customer {
        return getFragmentData().getCustomerToEdit()
    }

    fun getCustomerToString(customer: Customer): String {
        return androidModel.getCustomerToString(customer)
    }

    fun reloadCustomerList() {
        getFragmentData().reloadCustomerList()
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

    suspend fun createSale(): Boolean {
        return androidModel.createSale()
    }

    fun getNewSale(): Sale {
        return androidModel.getNewSale()
    }

    fun setDateSale(date: String) {
        return androidModel.setDateSale(date)
    }

    fun searchSale(view: View) {
        androidModel.searchSale(view)
    }

    fun getSaleToString(sale: Sale): String {
        return androidModel.getSaleToString(sale)
    }

    fun showProductListSaleToString(item: Sale, context: Context) {
        showAlertMessage(
            context.getString(R.string.saleList),
            androidModel.getSaleToString(item),
            context
        )
    }

    suspend fun getSalesList(): MutableList<Sale> {
        return androidModel.getSalesList()
    }

    /*
        Cart
     */
    fun getCartListToString(mutableList: MutableList<Product>): String {
        return androidModel.getCartListToString(mutableList)
    }

    fun removeElementCart(item: Product, context: Context) {
        if (androidModel.removeElementCart(item))
            showToastMessage(context.getString(R.string.elementAddedToCart),context)
        else
            showToastMessage(context.getString(R.string.elementNotAddedToCart),context)
    }

    fun getCartList(): MutableList<Product> {
        return androidModel.getCartList()
    }

    fun reloadCartList() {
        getFragmentData().reloadCartList()
    }

    fun getTotalPriceCart(): String {
        return androidModel.getTotalPriceCart()
    }
    fun addProductToCart(item: Product, view: View) {
        androidModel.addProductToCart(item,view)
    }

    /*
        Messages
     */
    fun showToastMessage(message: String, context: Context){
        getDialogView().showToastMessage(message, context)
    }

    fun showAlertMessage(title: String, message: String, context: Context){
        getDialogView().showAlertMessage(title, message, context)
    }

    fun showResultTransaction(updateUser: Boolean, context: Context) {
        getDialogView().showResultTransaction(updateUser, context)
    }

    fun dialogConfirmRegister(data: Any, title: String, message: String, activity: Activity) {
        activity.runOnUiThread {
            getDialogView().dialogConfirmRegister(activity.findViewById(R.id.nav_host_fragment), data, title, message)
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

    fun reloadUserList() {
        getFragmentData().reloadUserList()
    }

    fun newUser(view: View) {
        getDialogView().dialogRegisterUser(view)
    }

    suspend fun deleteUser(user: User): Boolean {
        return androidModel.deleteUser(user)
    }

    suspend fun newUser(user: User): Boolean {
        return androidModel.newUser(user)
    }

    fun goNewUserToUserList(view: View) {
        reloadUserList()
        controller.goNewUserToUserList(view)
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

    fun searchUser(view: View) {
        androidModel.searchUser(view)
    }

    fun askLogOut(context: Context) {
        GlobalScope.launch(Dispatchers.IO){
            androidModel.askLogOut(context)
        }
    }

    fun askSaveLocation(context: Context) {
        GlobalScope.launch(Dispatchers.IO){
            androidModel.askSaveLocation(context)
        }
    }

    fun saveUserLocation(context: Context) {
        GlobalScope.launch(Dispatchers.IO){
            androidModel.saveUserLocation(context)
        }
    }

    fun logOut(context:Context) {
        androidModel.logOut(context)
    }

    fun setBitMap(bitMap: Bitmap) {
        androidModel.setBitMap(bitMap)
    }

    fun getStringFromBitMap(): String {
        return androidModel.getStringFromBitMap()
    }

    fun getBitMapFromString(string:String): Bitmap {
        return androidModel.getBitMapFromstring(string)
    }

    fun loadProductListFromREST(view: View) {
        androidModel.loadProductListFromREST(view)
    }

    fun getUserToString(user: User): String {
        return androidModel.getUserToString(user)
    }
}