package com.example.stockmanagementsym.presentation

import android.view.View
import androidx.fragment.app.findFragment
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.CartLogic
import com.example.stockmanagementsym.logic.CustomerLogic
import com.example.stockmanagementsym.logic.ProductLogic
import com.example.stockmanagementsym.logic.SaleLogic
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.presentation.fragment.CustomerListFragment
import com.example.stockmanagementsym.presentation.fragment.NewProductFragment
import com.example.stockmanagementsym.presentation.fragment.ProductListFragment
import com.example.stockmanagementsym.presentation.fragment.SaleListFragment
import kotlinx.android.synthetic.main.fragment_customer_list.*
import kotlinx.android.synthetic.main.fragment_new_product.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.fragment_sale_list.*
import java.util.*

object Model {

    private lateinit var newProductFragment: NewProductFragment

    //Sale
    fun setCustomerNewSale(customer: Customer){
        getSaleLogic().setCustomerNewSale(customer)
    }

    fun getCustomerNewSale(): Customer {
        return getSaleLogic().getCustomerNewSale()
    }

    fun updateNewSale():Boolean {
        getSaleLogic().setCustomerNewSale(getCustomerNewSale())
        return getSaleLogic().updateNewSale()
    }
    fun getSalesList(): List<Sale> {
        return getSaleLogic().getSaleList()
    }

    fun setDateSale(date: Calendar) {
        getSaleLogic().setDateSale(date)
    }

    fun confirmNewSale(view:View) {

        val confirmRegister =
            DialogObject.dialogConfirmRegister(
                                                view = view,
                                                data = getCartList(),
                                                title = view.context.getString(R.string.titleAlertNewSale),
                                                message = view.context.getString(R.string.messageAlertNewSale)
                                              )


    }

    fun newSale(view:View) {
        DialogObject.dialogNewSale(view)
        FragmentData.setConfirmRegister(false)
    }
    //Cart
    fun getCartList(): MutableList<Product> {
        return getCartLogic().getCartList()
    }

    fun getTotalPrice(): Int {
        return getCartLogic().getTotalPrice()
    }

    //Customer
    fun setCustomerToEdit(item: Customer) {
        getCustomerLogic().setCustomerToEdit(item)
    }

    fun editCustomer():Boolean {
        getCustomerLogic().setCustomerEdited(getCustomerNewSale())
        FragmentData.reloadCustomerList()
        return getCustomerLogic().updateCustomer()
    }
    fun getCustomerList(): List<Customer> {
        return getCustomerLogic().getCustomerList()
    }
    fun setCustomerSelected(view: View, item: Int){
        setCustomerNewSale(getCustomerList().get(item))
        DialogObject.showCustomerName(view, getCustomerNewSale())
    }

    fun createNewCustomer():Boolean {
        getCustomerLogic().setNewCustomer(getCustomerNewSale())
        FragmentData.reloadCustomerList()
        return getCustomerLogic().createNewCustomer()
    }
    //Product
    //   Update product
    fun setProductToEdit(item: Product) {
        getProductLogic().setProductToEdit(item)
    }
    fun setProductEdited() {
        getProductLogic().setProductEdited(
            Product(newProductFragment.editTextProductName.text.toString(), newProductFragment.editTextProductPrice.text.toString().toInt(),
                newProductFragment.editTextProductDesc.text.toString(), R.drawable.ic_login.toString().toInt(), newProductFragment.editTextProductQuantity.text.toString().toInt())
        )
    }
    fun updateProduct() {
        getProductLogic().updateProduct()
    }
    //   New product creation
    fun setNewProduct(product: Product) {
        getProductLogic().setNewProduct(product)
    }
    fun getNewProduct(): Product {
        return getProductLogic().getNewProduct()
    }

    fun createNewProduct():Boolean{
        var result = getProductLogic().createNewProduct()
        FragmentData.reloadProductList()
        return result
    }
    fun setNewProductFragment(newProductFragment: NewProductFragment) {
        this.newProductFragment = newProductFragment
    }

    fun getNewProductFragment(): NewProductFragment {
        return newProductFragment
    }
    fun getPhotoCamera(viewElement:View) {
        val newProductFragment = viewElement.findFragment<NewProductFragment>()
        newProductFragment.startCamera()
    }

    fun getPhotoGallery(viewElement:View) {
        val newProductFragment = viewElement.findFragment<NewProductFragment>()
        newProductFragment.startGallery()
    }

    //Searches
    fun searchSale(viewElement:View) {
        val view = viewElement.findFragment<SaleListFragment>()
        view.setList(getSaleLogic().searchSales(view.editTextSearchSaleList.text.toString()).toMutableList())
    }
    fun searchCustomer(viewElement:View) {
        val view = viewElement.findFragment<CustomerListFragment>()
        view.setList(getCustomerLogic().searchCustomer(view.editTextSearchCustomerList.text.toString()).toMutableList())
    }

    fun searchProduct(viewElement:View) {
        val view = viewElement.findFragment<ProductListFragment>()
        view.setList(getProductLogic().searchProduct(view.editTextSearchProductList.text.toString()).toMutableList())
    }

    //Logic classes
    private fun getCustomerLogic(): CustomerLogic {
        return CustomerLogic
    }

    private fun getSaleLogic(): SaleLogic {
        return SaleLogic
    }

    private fun getProductLogic(): ProductLogic {
        return ProductLogic
    }

    private fun getCartLogic(): CartLogic {
        return CartLogic
    }
}
