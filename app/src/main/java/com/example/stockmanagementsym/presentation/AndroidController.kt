package com.example.stockmanagementsym.presentation

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.presentation.view.AndroidView
import com.example.stockmanagementsym.presentation.view.FragmentData


object AndroidController: View.OnClickListener {

    private lateinit var navController: NavController
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
            R.id.buttonNewProductToGallery -> androidView.getGallery(view)
            R.id.buttonNewProductToCamera -> androidView.getCamera(view)
            R.id.buttonNewProduct -> androidView.registerProduct(view, FragmentData.getBooleanUpdate())

            //Sale list
            R.id.buttonSaleListToSearch -> androidView.searchSale(view)

            //User fragment
            R.id.buttonUserListToCreateUser -> navController.navigate(R.id.action_userFragment_to_newUserFragment)
            R.id.buttonUserListToSearch -> androidView.searchUser(view)
            //User fragment - new user
            R.id.buttonNewUserToUserList-> navController.navigate(R.id.action_newUserFragment_to_userFragment)
            R.id.buttonNewUserToHome -> navController.navigate(R.id.action_newUserFragment_to_home)
            R.id.buttonNewUserToGallery -> androidView.getGallery(view)
            R.id.buttonNewUserToCamera -> androidView.getCamera(view)
            R.id.buttonNewUserToNewUser -> androidView.newUser(view)
            R.id.buttonNewUserCancel -> navController.navigate(R.id.action_newUserFragment_to_home)
        }
    }

    fun goProductList(){
        navController.navigate(R.id.action_newProductFragment_to_shopFragment)
    }

    fun goNewProduct(){
        navController.navigate(R.id.action_shopFragment_to_newProductFragment)
    }

    fun goNewUserToUserList() {
        navController.navigate(R.id.action_newUserFragment_to_userFragment)
    }
}