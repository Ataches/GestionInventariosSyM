package com.example.stockmanagementsym.presentation

import androidx.fragment.app.Fragment
import com.example.stockmanagementsym.presentation.fragment.*

object FragmentData {

    private lateinit var cartListener: ListListener
    private lateinit var productListListener: ListListener
    private lateinit var customerListListener: ListListener
    private var updateBoolean: Boolean = false

    private lateinit var newProductFragment: NewProductFragment
    private var idFragment: Int=0

    fun setUpdateBoolean(updateBoolean:Boolean){
        this.updateBoolean = updateBoolean
    }
    fun getUpdateBoolean():Boolean{
        return updateBoolean
    }

    fun setProductListListener(productListFragment: ListListener) {
        this.productListListener = productListFragment
    }
    fun setCartListener(cartFragment: ListListener) {
        this.cartListener = cartFragment
    }
    fun setCustomerListListener(customerListFragment: ListListener){
        this.customerListListener = customerListFragment
    }

    fun getNewProductFragment(): Fragment {
        return newProductFragment
    }
    fun setNewProductFragment(newProductFragment: NewProductFragment){
        this.newProductFragment = newProductFragment
    }

    fun getIdFragment(): Int {
        return idFragment
    }
    fun setIdFragment(idFragment: Int){
        this.idFragment = idFragment
    }

    fun reloadCustomerList(){
        customerListListener.reloadList()
    }
    fun reloadProductList(){
        productListListener.reloadList()
    }
    fun reloadCartList() {
        cartListener.reloadList()
    }

}