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
import com.example.stockmanagementsym.presentation.view.NotifierView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/*
    Created by Juan Sebastian Sanchez Mancilla on 30/10/2020
*/
class AndroidView(private val androidModel: AndroidModel) {

    private var viewNewUserFragment: View ?= null
    private var viewNewProductFragment: View ?= null
    private lateinit var textSearched: String
    private lateinit var context: Context
    private var androidController: AndroidController ?= null
    private var productListRESTLoaded: Boolean = false
    private var cartListener: ListListener ?= null
    private var customerListListener: ListListener ?= null
    private lateinit var productListListener: ListListener
    private lateinit var userListListener: ListListener
    private lateinit var saleListListener: ListListener

    private var customerToEdit: Customer ?= null
    private var productToEdit: Product?= null
    private var booleanUpdate: Boolean = false

    private var notifierView: NotifierView? = null
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
        List listeners
     */
    fun setProductListListener(productListListener: ListListener) {
        this.productListListener = productListListener
    }

    fun setCartListener(cartListener: ListListener) {
        this.cartListener = cartListener
        androidModel.setCartListManager()
    }

    fun setCustomerListListener(customerListListener: ListListener){
        this.customerListListener = customerListListener
    }

    fun setUserListListener(userListListener: ListListener){
        this.userListListener = userListListener
        androidModel.setUserListManager()
    }

    fun setSaleListListener(saleListListener: ListListener) {
        this.saleListListener = saleListListener
    }

    fun getProductListListener(): ListListener {
        return productListListener
    }

    fun getCartListener(): ListListener? {
        return cartListener
    }

    fun getCustomerListListener(): ListListener {
        return customerListListener!!
    }

    fun getUserListListener(): ListListener{
        return userListListener
    }

    fun getSaleListListener(): ListListener {
        return saleListListener
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
    suspend fun getUserList():MutableList<User> {
        return androidModel.getUserList()
    }

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
        dialogConfirmRegister(product,R.string.titleAlertDeleteProd,
                                R.string.messageAlertDeleteProd)
    }
    fun deleteProduct(product: Product) {
        GlobalScope.launch(Dispatchers.IO){
            getNotifierView().showResultTransaction(androidModel.deleteProduct(product))
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

    fun goToProductList() {
        reloadProductList()
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
        goToNewProduct()
    }

    private fun goToNewProduct() {
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

    /*
        Product list REST boolean
     */
    fun setProductListRESTLoaded(productListRESTLoaded: Boolean){
        this.productListRESTLoaded = productListRESTLoaded
    }
    fun getProductListRESTLoaded():Boolean{
        return productListRESTLoaded
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
    suspend fun createCustomer(customer: Customer, view: View): Boolean {
        this.view = view
        return androidModel.createCustomer(customer)
    }

    suspend fun updateCustomer(customer: Customer): Boolean {
        return androidModel.updateCustomer(customer)
    }

    fun updateCustomer(customerToEdit: Customer, booleanUpdate: Boolean) {
        this.customerToEdit = customerToEdit
        this.booleanUpdate = booleanUpdate
        getDialogView().dialogRegisterCustomer(true)
    }

    fun getCustomerToEdit(): Customer {
        return customerToEdit!!
    }

    fun newCustomer() {
        getDialogView().dialogRegisterCustomer(false)
    }

    fun deleteCustomer(customer: Customer){
        GlobalScope.launch(Dispatchers.IO){
            getNotifierView().showResultTransaction(androidModel.deleteCustomer(customer))
            reloadCustomerList()
        }
    }

    fun setCustomerSelected(item: Int, view: View) {
        this.view = view
        androidModel.setCustomerSelected(item)
    }

    fun searchCustomer() {
        androidModel.searchCustomer()
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
    fun confirmNewSale() {
        getDialogView().dialogConfirmRegister(
            data = androidModel.getCartList(),
            title = R.string.titleAlertNewSale,
            message = R.string.messageAlertNewSale
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
        getNotifierView().showAlertMessage(
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

    fun removeElementCart(item: Product) {
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
        getNotifierView().setNotifierContext(context)
        getNotifierView().showToastMessage(message)
    }
    fun showToastMessage(message: String){
        getNotifierView().showToastMessage(message)
    }
    fun showToastMessage(messageID: Int){
        getNotifierView().showToastMessage(messageID)
    }

    fun showAlertMessage(titleID: Int, messageID: Int, context: Context){
        getNotifierView().setNotifierContext(context)
        getNotifierView().showAlertMessage(titleID, messageID)
    }

    fun showResultTransaction(updateUser: Boolean) {
        getNotifierView().showResultTransaction(updateUser)
    }

    fun dialogConfirmRegister(data: Any, title: Int, message:Int) {
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

    fun searchUser(view: View) {
        androidModel.searchUser(view)
    }

    fun askLogOut() {
        GlobalScope.launch(Dispatchers.IO){
            androidModel.askLogOut()
        }
    }

    fun askSaveLocation() {
        GlobalScope.launch(Dispatchers.IO){
            androidModel.askSaveLocation()
        }
    }

    fun saveUserLocation() {
        GlobalScope.launch(Dispatchers.IO){
            androidModel.saveUserLocation()
        }
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

    fun loadProductListFromREST() {
        androidModel.loadProductListFromREST()
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

    fun setListSearched(mutableList: MutableList<Any>, listFragmentID: Int) {
        when(listFragmentID){
            R.id.customerListFragment-> customerListListener!!.setList(mutableList)
        }
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
}