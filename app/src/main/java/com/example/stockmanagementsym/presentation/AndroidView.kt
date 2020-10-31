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
import com.example.stockmanagementsym.presentation.fragment.ListListener
import com.example.stockmanagementsym.presentation.view.DialogView
import com.example.stockmanagementsym.presentation.view.Notifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/*
    Created by Juan Sebastian Sanchez Mancilla on 30/10/2020
*/
class AndroidView(private val androidModel: AndroidModel) {

    private var androidController: AndroidController ?= null
    private var productListRESTLoaded: Boolean = false
    private var cartListener: ListListener?= null
    private var productListListener: ListListener?= null
    private var customerListListener: ListListener ?= null
    private var userListListener: ListListener?= null

    private var customerToEdit: Customer ?= null
    private var productToEdit: Product?= null
    private var booleanUpdate: Boolean = false

    private var notifier: Notifier? = null
    private var dialogView: DialogView? = null
    private var fragmentData: FragmentData? = null
    private lateinit var view:View

    init {
        getFragmentData().setAndroidView(this)
    }

    fun getAndroidController():AndroidController{
        if(androidController==null)
            androidController = AndroidController(this)
        return androidController!!
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
        List listeners
     */
    fun setProductListListener(productListListener: ListListener) {
        this.productListListener = productListListener
    }

    fun setCartListener(cartListener: ListListener) {
        this.cartListener = cartListener
    }

    fun setCustomerListListener(customerListListener: ListListener){
        this.customerListListener = customerListListener
    }

    fun setUserListListener(userListListener: ListListener){
        this.userListListener = userListListener
    }

    fun getProductListListener(): ListListener {
        return productListListener!!
    }

    fun getCartListener(): ListListener {
        return cartListener!!
    }

    fun getCustomerListListener(): ListListener {
        return customerListListener!!
    }

    fun getUserListListener(): ListListener{
        return userListListener!!
    }


    /*
        List listeners - reload list
     */
    fun reloadCustomerList(){
        if(customerListListener !=null)
            customerListListener!!.reloadList()
    }

    fun reloadProductList(){
        productListListener!!.reloadList()
    }

    fun reloadCartList() {
        cartListener!!.reloadList()
    }

    fun reloadUserList(){
        userListListener!!.reloadList()
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
            getNotifier().showResultTransaction(androidModel.deleteProduct(product))
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
        getAndroidController().goToProductList(view)
    }

    fun getProductToString(product: Product): String {
        return androidModel.getProductToString(product)
    }

    fun getProductToEdit(): Product {
        return productToEdit!!
    }

    fun updateProduct(product: Product, booleanUpdate: Boolean, view: View) {
        productToEdit = product
        this.booleanUpdate = booleanUpdate
        goToNewProduct(view)
    }

    private fun goToNewProduct(view:View) {
        getAndroidController().goToNewProduct(view)
    }
    fun addProductListToProductFragment(list: List<Product>) {
        getProductListListener().addElementsToList(list.toMutableList())
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

    /*
        Product list REST boolean
     */
    fun setProductListRESTLoaded(productListRESTLoaded: Boolean){
        this.productListRESTLoaded = productListRESTLoaded
    }
    fun getProductListRESTLoaded():Boolean{
        return productListRESTLoaded
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

    fun updateCustomer(customerToEdit: Customer, booleanUpdate: Boolean,view: View) {
        this.customerToEdit = customerToEdit
        this.booleanUpdate = booleanUpdate
        getDialogView().dialogRegisterCustomer(view, true)
    }

    fun getCustomerToEdit(): Customer {
        return customerToEdit!!
    }

    fun newCustomer(view: View) {
        getDialogView().dialogRegisterCustomer(view, false)
    }

    fun deleteCustomer(customer: Customer,context: Context){
        GlobalScope.launch(Dispatchers.IO){
            getNotifier().showResultTransaction(androidModel.deleteCustomer(customer))
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

    fun getCustomerToString(customer: Customer): String {
        return androidModel.getCustomerToString(customer)
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

    fun showProductListSaleToString(item: Sale) {
        getNotifier().showAlertMessage(
            R.string.saleList,
            androidModel.getSaleToString(item)
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
        showResultTransaction(androidModel.removeElementCart(item))
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
    fun showToastMessage(message: String, context: Context){
        getNotifier().setNotifierContext(context)
        getNotifier().showToastMessage(message)
    }
    fun showToastMessage(message: String){
        getNotifier().showToastMessage(message)
    }

    fun showAlertMessage(title: Int, message: Int){
        getNotifier().showAlertMessage(title, message)
    }
    fun showAlertMessage(title: String, message: String,context: Context){
        getNotifier().setNotifierContext(context)
        getNotifier().showAlertMessage(title, message)
    }

    fun showResultTransaction(updateUser: Boolean) {
        getNotifier().showResultTransaction(updateUser)
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

    fun newUser(view: View) {
        getDialogView().dialogRegisterUser(view)
    }

    suspend fun deleteUser(user: User) {
        androidModel.deleteUser(user)
    }

    fun getNotifier(): Notifier {
        if(notifier == null)
            notifier = Notifier()
        return notifier!!
    }

    suspend fun newUser(user: User): Boolean {
        return androidModel.newUser(user)
    }

    fun goNewUserToUserList(view: View) {
        reloadUserList()
        getAndroidController().goNewUserToUserList(view)
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

    fun loadProductListFromREST() {
        androidModel.loadProductListFromREST()
    }

    fun getUserToString(user: User): String {
        return androidModel.getUserToString(user)
    }
}