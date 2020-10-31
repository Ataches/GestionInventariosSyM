package com.example.stockmanagementsym.presentation.fragment

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.AndroidView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/*
    Created by Juan Sebastian Sanchez Mancilla on 30/10/2020
    Intermediate object between fragments and android view
*/
object FragmentData {

    private var androidView: AndroidView?= null

    fun setAndroidView(androidView: AndroidView){
        FragmentData.androidView = androidView
    }

    private fun getAndroidView(): AndroidView {
        if(androidView ==null)
            Log.d("FAIL","Failed to load android view in Fragment data object")
        return androidView!!
    }

    fun getController(): AndroidController{
        return getAndroidView().getAndroidController()
    }

    /*
        User data
     */
    fun getUserName(): String {
        return getAndroidView().getUserNae()
    }
    fun getUserPrivilege(): String {
        return getAndroidView().getUserPrivileges()
    }
    suspend fun getUserList(): MutableList<User> {
        return getAndroidView().getUserList()
    }

    suspend fun deleteUser(user: User) {
        getAndroidView().deleteUser(user)
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

    /*
        List listeners
     */
    fun setProductListListener(productListFragment: ListListener) {
        getAndroidView().setProductListListener(productListFragment)
    }

    fun setCartListener(cartFragment: ListListener) {
        getAndroidView().setCartListener(cartFragment)
    }

    fun setCustomerListListener(customerListFragment: ListListener){
        getAndroidView().setCustomerListListener(customerListFragment)
    }

    fun setUserListListener(userListListener: ListListener){
        getAndroidView().setUserListListener(userListListener)
    }

    /*
        Sale data
     */
    suspend fun getSaleList(): MutableList<Sale> {
        return getAndroidView().getSalesList()
    }
    fun getDate(date: Calendar): String {
        val df: DateFormat = SimpleDateFormat("dd-MMMM-yyyy")
        return df.format(date.time)
    }

    /*
        Bitmap data
     */
    fun setBitMap(bitMap: Bitmap) {
        getAndroidView().setBitMap(bitMap)
    }

    fun getBitMapFromString(stringBitMap: String): Bitmap {
        return getAndroidView().getBitMapFromString(stringBitMap)
    }

    /*
        Customer data
     */
    suspend fun getCustomerList(): MutableList<Customer> {
        return getAndroidView().getCustomerList()
    }

    fun updateCustomer(customerToEdit: Customer, booleanUpdate: Boolean,view: View) {
        getAndroidView().updateCustomer(customerToEdit,booleanUpdate,view)
    }

    fun deleteCustomer(customer: Customer, context: Context) {
        getAndroidView().deleteCustomer(customer, context)
    }

    /*
        Product data
     */
    fun getProductToEdit(): Product {
        return getAndroidView().getProductToEdit()
    }

    suspend fun getProductList(): MutableList<Product> {
        return getAndroidView().getProductList()
    }

    fun confirmNewProduct(product: Product,itemView: View) {
        getAndroidView().confirmNewProduct(product,itemView)
    }
    fun updateProduct(product: Product, booleanUpdate: Boolean, view: View) {
        getAndroidView().updateProduct(product, booleanUpdate, view)
    }
    fun askDeleteProduct(item: Product, context: Context) {
        getAndroidView().askDeleteProduct(item,context)
    }

    fun showProductListSaleToString(item: Sale) {
        return getAndroidView().showProductListSaleToString(item)
    }

    /*
        Product list REST
     */
    fun loadProductListFromREST() {
        getAndroidView().loadProductListFromREST()
    }
    fun setProductListRESTLoaded(productListRESTLoaded: Boolean){
        getAndroidView().setProductListRESTLoaded(productListRESTLoaded)
    }
    fun getProductListRESTLoaded():Boolean{
        return getAndroidView().getProductListRESTLoaded()
    }
    /*
        Update boolean
     */
    fun setBooleanUpdate(confirmUpdate: Boolean) {
        getAndroidView().setBooleanUpdate(confirmUpdate)
    }

    fun getBooleanUpdate():Boolean{
        return getAndroidView().getBooleanUpdate()
    }

    /*
        Cart data
     */
    fun addProductToCart(item: Product){
        getAndroidView().addProductToCart(item)
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

    /*
        Logout
     */
    fun askSaveLocation(context: Context) {
        getAndroidView().askSaveLocation(context)
    }

    /*
        Messages
     */
    fun showToastMessage(context: Context, message:String){
        getAndroidView().showToastMessage(message,context)
    }

    fun finish(){
        androidView  = null
    }
}