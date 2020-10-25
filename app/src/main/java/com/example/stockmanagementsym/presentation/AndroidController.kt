package com.example.stockmanagementsym.presentation

import android.view.View
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.presentation.view.AndroidView
import com.example.stockmanagementsym.presentation.view.FragmentData


object AndroidController: View.OnClickListener {

    private lateinit var navController: NavController
    private lateinit var transaction: FragmentTransaction
    private lateinit var androidView: AndroidView

    fun setAdroidView(androidView: AndroidView){
        this.androidView = androidView
    }

    override fun onClick(view: View) {
        navController = Navigation.findNavController(view)
        when(view.id){

            //Home
            R.id.buttonHomeToShop -> navController.navigate(R.id.action_home_to_shopFragment)
            R.id.buttonHomeToCustomersList -> navController.navigate(R.id.action_home_to_customerListFragment)
            R.id.buttonHomeToSaleList -> navController.navigate(R.id.action_home_to_saleList)
            R.id.buttonHomeToUserList -> navController.navigate(R.id.action_home_to_userFragment)

            //Product list
            R.id.buttonProductListToSearch -> androidView.searchProduct(view)
            R.id.buttonProductListToNewProduct -> navController.navigate(R.id.action_shopFragment_to_newProductFragment)

            //Cart
            R.id.buttonCartToNewSale -> androidView.confirmNewSale(view)

            //Customer list
            R.id.buttonCustomerListToCreateCustomer -> androidView.newCustomer(view)
            R.id.buttonCustomerToSearch -> androidView.searchCustomer(view)

            //New product
            R.id.buttonNewProductToHome -> navController.navigate(R.id.action_newProductFragment_to_home)
            R.id.buttonNewProductToProductList -> navController.navigate(R.id.action_newProductFragment_to_shopFragment)
            R.id.buttonNewProductToGallery -> androidView.getPhotoGallery(view)
            R.id.buttonNewProductToCamera -> androidView.getPhotoCamera(view)
            R.id.buttonNewProductToNewProduct -> {
                androidView.registerProduct(view, FragmentData.getBooleanUpdate())
            }

            //Sale list
            R.id.buttonSaleListToSearch -> androidView.searchSale(view)

            //User fragment
            R.id.buttonUserListToCreateUser -> androidView.newUser(view)
            R.id.buttonUserListToSearch -> androidView.searchUser(view)
        }
    }

    fun goProductList(){
        navController.navigate(R.id.action_newProductFragment_to_shopFragment)
    }

    fun goNewProduct(){
        navController.navigate(R.id.action_shopFragment_to_newProductFragment)
    }
}