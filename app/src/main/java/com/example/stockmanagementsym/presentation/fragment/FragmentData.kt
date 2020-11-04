package com.example.stockmanagementsym.presentation.fragment

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.data.CONSTANTS
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.AndroidView
import com.squareup.picasso.Picasso
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 *  Created by Juan Sebastian Sanchez Mancilla on 30/10/2020
 *  Intermediate object between fragments and android view
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

    fun setControllerOnClickListener(view: View) {
        getAndroidView().setControllerOnClickListener(view)
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

    fun deleteUser(user: User) {
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
        Bitmap data
     */
    fun setBitMap(bitMap: Bitmap?) {
        getAndroidView().setBitMap(bitMap)
    }

    fun getBitMapFromString(stringBitMap: String): Bitmap {
        return getAndroidView().getBitMapFromString(stringBitMap)
    }

    /*
        Customer data
     */
    fun updateCustomer(customerToEdit: Customer, booleanUpdate: Boolean) {
        getAndroidView().updateCustomer(customerToEdit,booleanUpdate)
    }

    fun deleteCustomer(customer: Customer) {
        getAndroidView().deleteCustomer(customer)
    }

    /*
        Product data
     */
    fun getProductToEdit(): Product {
        return getAndroidView().getProductToEdit()
    }

    fun getProductList(): MutableList<Any> {
        return getAndroidView().getProductList()
    }

    fun confirmNewProduct(product: Product) {
        getAndroidView().confirmNewProduct(product)
    }
    fun updateProduct(product: Product, booleanUpdate: Boolean) {
        getAndroidView().updateProduct(product, booleanUpdate)
    }
    fun askDeleteProduct(item: Product) {
        getAndroidView().askDeleteProduct(item)
    }

    fun showProductListSaleToString(item: Sale) {
        return getAndroidView().showProductListSaleToString(item)
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

    fun removeElementCart(item: Product) {
        getAndroidView().removeElementCart(item)
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
    fun askSaveLocation() {
        getAndroidView().askSaveLocation()
    }

    /*
        Messages
     */
    fun showToastMessage(message: Int) {
        getAndroidView().showToastMessage(message)
    }

    fun finish(){
        androidView  = null
    }

    fun setContext(context: Context) {
        getAndroidView().setContext(context)
    }

    fun setTextSearched(textSearched: String) {
        getAndroidView().setTextSearched(textSearched)
    }

    fun getNewProductFragmentView(): View? {
        return getAndroidView().getNewProductFragmentView()
    }

    fun setNewProductFragmentView(view: View) {
        getAndroidView().setNewProductFragmentView(view)
    }

    fun getNewUserFragmentView(): View? {
        return getAndroidView().getNewUserFragmentView()
    }

    fun setNewUserFragmentView(view: View) {
        getAndroidView().setNewUserFragmentView(view)
    }

    fun notifyProductLogic(listListener: IListListener) {
        getAndroidView().notifyProductLogic(listListener)
    }

    fun notifyCartLogic(listListener: IListListener, iCart: ICart) {
        getAndroidView().notifyCartLogic(listListener, iCart)
    }

    fun notifySaleLogic(listListener: IListListener) {
        getAndroidView().notifySaleLogic(listListener)
    }

    fun notifyUserLogic(listListener: IListListener) {
        getAndroidView().notifyUserLogic(listListener)
    }

    fun notifyCustomerLogic(listListener: IListListener) {
        getAndroidView().notifyCustomerLogic(listListener)
    }

    fun showAlertMessage(titleID: Int, messageID: Int) {
        getAndroidView().showAlertMessage(titleID, messageID, null)
    }

    /**
        Method to paint an image in a image view, depends on data length.
        Type photo: - Photo from a url (if string length is less than a constant)
                    - Bitmap in a string
        If the photo data is empty or equals to a constant the photo can be changed by a drawable element
        The background in the image view can be changed by a drawable, is necessary the drawable ID
     */
    fun paintPhoto(photoData: String, imageView: ImageView, drawableID: Int) {
        if ((photoData.isNotEmpty())&&(photoData != CONSTANTS.STRING_VOID_ELEMENT)) {
            if (photoData.length < CONSTANTS.URL_MAX_LENGTH) {
                try {
                    Picasso.get().load(photoData).into(imageView)
                    imageView.background = null
                } catch (e: Exception) {
                    showToastMessage(R.string.imageLoadFailure)
                }
            } else {
                imageView.setImageBitmap(getBitMapFromString(photoData))
                imageView.background = null
            }
        } else {
            if (drawableID != CONSTANTS.DEFAULT_DRAWABLE_ID) {
                imageView.setImageBitmap(null)
                imageView.setBackgroundResource(drawableID)
            }
        }
    }
}