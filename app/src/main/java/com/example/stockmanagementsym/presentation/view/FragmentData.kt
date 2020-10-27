package com.example.stockmanagementsym.presentation.view

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.presentation.fragment.ListListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object FragmentData {

    private lateinit var saleSelected: Sale
    private lateinit var customerToEdit: Customer
    private lateinit var productToEdit: Product
    private var booleanUpdate: Boolean = false

    private lateinit var cartListener: ListListener
    private lateinit var productListListener: ListListener
    private lateinit var customerListListener: ListListener
    private lateinit var userListListener: ListListener

    private lateinit var androidView: AndroidView

    fun getUserName(): String {
        return androidView.getUserNae()
    }

    fun getUserPrivilege(): String {
        return androidView.getUserPrivileges()
    }

    suspend fun deleteUser(user: User) {
        androidView.deleteUser(user)
    }

    suspend fun getUserList(): List<User> {
        return androidView.getUserList()
    }

    fun getUserPhotoData(): String {
        return androidView.getUserPhotoData()
    }

    fun getBitMap(): Bitmap {
        return androidView.getBitMap()
    }

    fun setAndroidView(androidView: AndroidView){
        this.androidView = androidView
    }

    fun setUserLocation(latitude: Double, longitude: Double) {
        androidView.setUserLocation(latitude,longitude)
    }

    fun getUserLatitude(): Double {
        return androidView.getUserLatitude()
    }

    fun getUserLongitude(): Double {
        return androidView.getUserLongitude()
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
    fun reloadCustomerList(){
        try{
            customerListListener.reloadList()
        }catch (e:Exception){
        }
    }

    fun reloadProductList(){
        productListListener.reloadList()
    }

    fun reloadCartList() {
        cartListener.reloadList()
    }

    fun reloadUserList(){
        userListListener.reloadList()
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
        androidView.showToastMessage(context,message)
    }

    fun setProductToEdit(item: Product) {
        productToEdit = item
    }

    fun getProductToEdit(): Product {
        return productToEdit
    }


    fun setBitMap(bitMap: Bitmap) {
        androidView.setBitMap(bitMap)
    }

    fun getCartList(): MutableList<Product> {
        return androidView.getCartList()
    }

    fun getTotalPriceCart(): String {
        return androidView.getTotalPriceCart()
    }

    suspend fun getCustomerList(): List<Customer> {
        return androidView.getCustomerList()
    }

    fun updateCustomer(customerToEdit: Customer, booleanUpdate: Boolean,view: View) {
        this.customerToEdit = customerToEdit
        this.booleanUpdate = booleanUpdate
        androidView.updateCustomer(view)
    }

    fun getCustomerToEdit(): Customer {
        return customerToEdit
    }

    suspend fun deleteCustomer(customer: Customer) {
        androidView.deleteCustomer(customer)
    }

    suspend fun getSaleList(): List<Sale> {
        return androidView.getSalesList()
    }

    fun addProductToCart(item: Product,view: View){
        androidView.addProductToCart(item,view)
    }

    suspend fun deleteProduct(item: Product) {
        androidView.deleteProduct(item)
    }


    fun removeElementCart(context: Context, item: Product) {
        androidView.removeElementCart(item,context)
    }

    fun showProductListSaleToString(item: Sale, context: Context) {
        return androidView.showProductListSaleToString(item,context)
    }

    suspend fun getProductList(): List<Product> {
        return androidView.getProductList()
    }
    fun updateProduct(product: Product, booleanUpdate: Boolean, view: View) {
        productToEdit = product
        this.booleanUpdate = booleanUpdate
        androidView.goToNewProduct()
    }

    fun askSaveLocation(context: Context) {
        androidView.askSaveLocation(context)
    }
}