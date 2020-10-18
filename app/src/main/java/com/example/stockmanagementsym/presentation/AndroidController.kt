package com.example.stockmanagementsym.presentation

import android.view.View
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.presentation.fragment.HomeFragment
import com.example.stockmanagementsym.presentation.fragment.NewProductFragment
import com.example.stockmanagementsym.presentation.fragment.ProductListFragment
import com.example.stockmanagementsym.presentation.view.AndroidView
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.item_customer.view.*

object AndroidController: View.OnClickListener {

    private lateinit var navController: NavController
    private lateinit var transaction: FragmentTransaction
    private lateinit var androidView: AndroidView

    fun setViewClass(androidView: AndroidView){
        this.androidView = androidView
    }

    override fun onClick(view: View) {
        navController = Navigation.findNavController(view)
        when(view.id){
            //Home
            R.id.buttonHomeToProductList -> {
                navController.navigate(R.id.action_home_to_productsList)
            }
            R.id.buttonHomeToSaleList -> {
                navController.navigate(R.id.action_home_to_saleList)
            }
            R.id.buttonHomeToCustomersList -> {
                navController.navigate(R.id.action_home_to_customerListFragment)
            }

            //Product list

            R.id.buttonProductListToHome -> navController.navigate(R.id.action_productsList_to_home)
            R.id.buttonProductListToCart -> navController.navigate(R.id.action_productsList_to_cart)
            R.id.buttonProductListToSearch -> androidView.searchProduct(view)
            R.id.buttonProductListToNewProduct -> navController.navigate(R.id.action_productsList_to_newProductFragment)

            //Cart
            R.id.buttonCartToHome -> navController.navigate(R.id.action_cart_to_home)
            R.id.buttonCartToProductList -> navController.navigate(R.id.action_cart_to_productsList)
            R.id.buttonCartToNewSale -> androidView.confirmNewSale(view)

            //Customer list
            R.id.buttonCustomerListToHome -> navController.navigate(R.id.action_customerListFragment_to_home)
            R.id.buttonCustomerListToCreateCustomer -> androidView.registerCustomer(view,FragmentData.getBooleanUpdate())
            R.id.buttonCustomerToSearch -> {
                androidView.searchCustomer(view)
                FragmentData.reloadCustomerList()
            }

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
            R.id.buttonEditCustomer -> androidView.registerCustomer(view,FragmentData.getBooleanUpdate())
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
}