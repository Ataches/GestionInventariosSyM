package com.example.stockmanagementsym.presentation.view

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.fragment.ListListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object FragmentData {

    private var customerToEdit: Customer ?= null
    private var productToEdit: Product?= null
    private var productToCart: Product?= null
    private var booleanUpdate: Boolean = false

    private var cartListener: ListListener?= null
    private var productListListener: ListListener?= null
    private var customerListListener: ListListener ?= null
    private var userListListener: ListListener?= null

    private var productListRESTLoaded = false

    private var androidView: AndroidView?= null

    fun setAndroidView(androidView: AndroidView){
        this.androidView = androidView
    }
    private fun getAndroidView():AndroidView{
        if(androidView==null)
            Log.d("FAIL","Fallo inicio android view")
        return androidView!!
    }

    fun finish(){
         customerToEdit = null
         productToEdit= null
         productToCart  = null
         booleanUpdate = false

         cartListener = null
         productListListener  = null
         customerListListener = null
         userListListener  = null

         androidView  = null
         productListRESTLoaded = false
    }

    fun getController(): AndroidController{
        return getAndroidView().controller
    }

    fun setProductListRESTLoaded(productListRESTLoaded: Boolean){
        this.productListRESTLoaded = productListRESTLoaded
    }
    fun getProductListRESTLoaded():Boolean{
        return productListRESTLoaded
    }
    fun getUserName(): String {
        return getAndroidView().getUserNae()
    }
    fun getUserPrivilege(): String {
        return getAndroidView().getUserPrivileges()
    }
    suspend fun deleteUser(user: User) {
        getAndroidView().deleteUser(user)
    }

    suspend fun getUserList(): MutableList<User> {
        return getAndroidView().getUserList()
    }

    fun getUserPhotoData(): String {
        return getAndroidView().getUserPhotoData()
    }

    fun setUserLocation(latitude: Double, longitude: Double) {
        getAndroidView().setUserLocation(latitude,longitude)
    }

    fun getUserLatitude(): Double {
        return getAndroidView().getUserLatitude()
    }

    fun getUserLongitude(): Double {
        return getAndroidView().getUserLongitude()
    }

    fun getBitMapFromString(stringBitMap: String): Bitmap {
        return getAndroidView().getBitMapFromString(stringBitMap)
    }

    /*
        List listeners
     */
    fun setProductListListener(productListFragment: ListListener) {
        productListListener = productListFragment
    }

    fun setCartListener(cartFragment: ListListener) {
        cartListener = cartFragment
    }

    fun setCustomerListListener(customerListFragment: ListListener){
        customerListListener = customerListFragment
    }

    fun setUserListListener(userListListener: ListListener){
        this.userListListener = userListListener
    }

    /*
        List listeners - reload list
     */
    fun reloadCustomerList(){
        if(customerListListener!=null)
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
        Update bolean
     */
    fun setBooleanUpdate(confirmUpdate: Boolean) {
        this.booleanUpdate = confirmUpdate
    }

    fun getBooleanUpdate():Boolean{
        return booleanUpdate
    }

    fun getDate(date: Calendar): String {
        val df: DateFormat = SimpleDateFormat("dd-MMMM-yyyy")
        return df.format(date.time)
    }
    fun showToastMessage(context: Context, message:String){
        getAndroidView().showToastMessage(message,context)
    }

    fun setBitMap(bitMap: Bitmap) {
        getAndroidView().setBitMap(bitMap)
    }

    suspend fun getCustomerList(): MutableList<Customer> {
        return getAndroidView().getCustomerList()
    }

    fun updateCustomer(customerToEdit: Customer, booleanUpdate: Boolean,view: View) {
        this.customerToEdit = customerToEdit
        this.booleanUpdate = booleanUpdate
        getAndroidView().updateCustomer(view)

    }

    fun getCustomerToEdit(): Customer {
        return customerToEdit!!
    }

    fun deleteCustomer(customer: Customer, context: Context) {
        getAndroidView().deleteCustomer(customer, context)
    }

    suspend fun getSaleList(): MutableList<Sale> {
        return getAndroidView().getSalesList()
    }

    /*
        Product data
     */
    fun getProductToEdit(): Product {
        return productToEdit!!
    }

    fun getProductToCart(): Product {
        return productToCart!!
    }
    fun setProductToCart(product: Product) {
        productToCart = product
    }

    suspend fun getProductList(): MutableList<Product> {
        return getAndroidView().getProductList()
    }
    fun updateProduct(product: Product, booleanUpdate: Boolean, view: View) {
        productToEdit = product
        this.booleanUpdate = booleanUpdate
        getAndroidView().goToNewProduct(view)
    }

    fun askDeleteProduct(item: Product, context: Context) {
        getAndroidView().askDeleteProduct(item,context)
    }

    fun showProductListSaleToString(item: Sale, context: Context) {
        return getAndroidView().showProductListSaleToString(item,context)
    }

    /*
        Cart data
     */
    fun addProductToCart(item: Product,view: View){
        getAndroidView().addProductToCart(item,view)
    }


    fun removeElementCart(context: Context, item: Product) {
        getAndroidView().removeElementCart(item,context)
    }

    fun getCartList(): MutableList<Product> {
        return getAndroidView().getCartList()
    }

    fun getTotalPriceCart(): String {
        return getAndroidView().getTotalPriceCart()
    }

    fun askSaveLocation(context: Context) {
        getAndroidView().askSaveLocation(context)
    }

    fun loadProductListFromREST(view:View) {
        getAndroidView().loadProductListFromREST(view)
    }

    fun showAlertMessage(title: String, message: String, context: Context) {
        getAndroidView().showAlertMessage(title,message,context)
    }

    fun confirmNewProduct(product: Product,itemView: View) {
        getAndroidView().confirmNewProduct(product,itemView)
    }
}