package com.example.stockmanagementsym.presentation

import android.content.Context
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.presentation.view.AndroidView
import com.example.stockmanagementsym.presentation.view.FragmentData
import com.google.android.material.navigation.NavigationView


object AndroidController: View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var navController: NavController
    private lateinit var transaction: FragmentTransaction
    private lateinit var androidView: AndroidView

    fun setViewClass(androidView: AndroidView){
        this.androidView = androidView
    }

    override fun onClick(view: View) {
        navController = Navigation.findNavController(view)
        when(view.id){

            //App tool bar
            R.id.item_menu_product_list -> navController.navigate(R.id.action_home_to_productsList)
            R.id.item_menu_customer_list -> navController.navigate(R.id.action_home_to_customerListFragment)
            R.id.item_menu_sale_list -> navController.navigate(R.id.action_home_to_saleList)
            R.id.item_menu_create_user -> {
            }

            //Home
            R.id.buttonHomeToProductList -> {
                navController.navigate(R.id.action_home_to_productsList)
            }
            R.id.buttonHomeToCustomersList -> {
                navController.navigate(R.id.action_home_to_customerListFragment)
            }
            R.id.buttonHomeToSaleList -> {
                navController.navigate(R.id.action_home_to_saleList)
            }

            //Product list

            R.id.buttonProductListToHome -> navController.navigate(R.id.action_productsList_to_home)
            R.id.buttonAddProductToCart -> navController.navigate(R.id.action_productsList_to_cart)
            R.id.buttonProductListToSearch -> androidView.searchProduct(view)
            R.id.buttonProductListToNewProduct -> navController.navigate(R.id.action_productsList_to_newProductFragment)

            //Cart
            R.id.buttonCartToHome -> navController.navigate(R.id.action_cart_to_home)
            R.id.buttonCartToProductList -> navController.navigate(R.id.action_cart_to_productsList)
            R.id.buttonCartToNewSale -> androidView.confirmNewSale(view)

            //Customer list
            R.id.buttonCustomerListToHome -> navController.navigate(R.id.action_customerListFragment_to_home)
            R.id.buttonCustomerListToCreateCustomer -> androidView.registerCustomer(view)
            R.id.buttonCustomerToSearch -> androidView.searchCustomer(view)

            //New product
            R.id.buttonNewProductToHome -> navController.navigate(R.id.action_newProductFragment_to_home)
            R.id.buttonNewProductToProductList -> navController.navigate(R.id.action_newProductFragment_to_productsList)
            R.id.buttonNewProductToGallery -> androidView.getPhotoGallery(view)
            R.id.buttonNewProductToCamera -> androidView.getPhotoCamera(view)
            R.id.buttonNewProductToNewProduct -> {
                androidView.registerProduct(view, FragmentData.getBooleanUpdate())
            }

            //Sale list
            R.id.buttonSaleListToSearch -> androidView.searchSale(view)
            R.id.buttonSaleListToHome -> navController.navigate(R.id.action_saleList_to_home)

            //Item view holder customer
            R.id.buttonEditCustomer -> androidView.registerCustomer(view)
        }
    }

    fun goProductList(){
        navController.navigate(R.id.action_newProductFragment_to_productsList)
    }

    fun goNewProduct(){
        navController.navigate(R.id.action_productsList_to_newProductFragment)
    }

    fun setFragmentTransaction(transaction: FragmentTransaction) {
        this.transaction = transaction
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navController = Navigation.findNavController(item.actionView)
        when(item.itemId){
            R.id.item_menu_product_list -> {
                navController.navigate(R.id.action_home_to_productsList)
            }
            R.id.item_menu_customer_list -> navController.navigate(R.id.action_home_to_customerListFragment)
            R.id.item_menu_sale_list -> navController.navigate(R.id.action_home_to_saleList)
        }
        return true
    }
}