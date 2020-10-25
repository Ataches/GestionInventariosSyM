package com.example.stockmanagementsym.presentation.view

import android.content.Context
import android.view.View
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.AndroidModel

class AndroidView(private val androidModel: AndroidModel) {

    private var dialogView: DialogView? = null
    var controller: AndroidController = AndroidController
    private var fragmentData: FragmentData? = null

    init {
        controller.setAdroidView(this)
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

    fun getPhotoGallery(view: View) {
        androidModel.getPhotoGallery(view)
    }

    fun getPhotoCamera(view: View) {
        androidModel.getPhotoCamera(view)
    }
    /*
        User
     */

    fun getUser(): User {
        return androidModel.getUser()
    }

    suspend fun getUserList(): List<User> {
        return androidModel.getUserList()
    }

    /*
        Product
     */
    fun goToNewProduct() {
        controller.goNewProduct()
    }

    fun registerProduct(view: View, updateBoolean: Boolean) {
        getDialogView().dialogRegisterProduct(view, updateBoolean)
    }

    suspend fun createProduct(product: Product): Boolean {
        return androidModel.createProduct(product)
    }

    suspend fun deleteProduct(product: Product): Boolean {
        return androidModel.deleteProduct(product)
    }

    suspend fun getProductList(): List<Product> {
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
        controller.goProductList()
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

    /*
        Customer
     */
    suspend fun createCustomer(customer: Customer): Boolean {
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

    suspend fun deleteCustomer(customer: Customer): Boolean {
        return androidModel.deleteCustomer(customer)
    }

    suspend fun setCustomerSelected(item: Int) {
        return androidModel.setCustomerSelected(item)
    }

    fun searchCustomer(view: View) {
        androidModel.searchCustomer(view)
    }

    suspend fun getCustomerList(): List<Customer> {
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

    fun showProductListSaleToString(item: Sale, view: View) {
        showAlertMessage(
            view.context.getString(R.string.saleList),
            androidModel.getSaleToString(item),
            view
        )
    }

    suspend fun getSalesList(): List<Sale> {
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
            showToastMessage(context, context.getString(R.string.elementAddedToCart))
        else
            showToastMessage(context, context.getString(R.string.elementNotAddedToCart))
    }

    fun getCartList(): MutableList<Product> {
        return androidModel.getCartList()
    }

    fun reloadCartList() {
        getFragmentData().reloadCartList()
    }

    fun getTotalPriceCart(): Double {
        return androidModel.getTotalPriceCart()
    }

    /*
        Messages
     */
    fun showToastMessage(context: Context, message:String){
        getDialogView().showToastMessage(message,context)
    }

    fun showAlertMessage(title:String, message: String, view: View){
        //getDialogView().showAlertMessage(title,message,view)
    }

    suspend fun deleteUser(user: User): Boolean {
        return androidModel.deleteUser(user)
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
    suspend fun newUser(user: User): Boolean {
        return androidModel.newUser(user)
    }

    fun addProductToCart(item: Product,view: View) {
        androidModel.addProductToCart(item,view)
    }
}