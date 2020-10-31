package com.example.stockmanagementsym.presentation

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.presentation.fragment.FragmentData


class AndroidController(private val androidView: AndroidView): View.OnClickListener {

    private var navController: NavController ?= null

    override fun onClick(view: View) {
        when(view.id){

            //Home
            R.id.buttonHomeToShop -> getNavController(view).navigate(R.id.action_home_to_shopFragment)
            R.id.buttonHomeToCustomersList -> getNavController(view).navigate(R.id.action_home_to_customerListFragment)
            R.id.buttonHomeToSaleList -> getNavController(view).navigate(R.id.action_home_to_saleList)
            R.id.buttonHomeToUserList -> getNavController(view).navigate(R.id.action_home_to_userFragment)

            //Product list
            R.id.buttonProductListToSearch -> androidView!!.searchProduct(view)
            R.id.buttonProductListToNewProduct -> getNavController(view).navigate(R.id.action_shopFragment_to_newProductFragment)

            //Cart
            R.id.buttonCartToNewSale -> androidView!!.confirmNewSale(view)

            //Customer list
            R.id.buttonCustomerListToCreateCustomer -> androidView!!.newCustomer(view)
            R.id.buttonCustomerToSearch -> androidView!!.searchCustomer(view)

            //New product
            R.id.buttonNewProductToHome -> getNavController(view).navigate(R.id.action_newProductFragment_to_home)
            R.id.buttonNewProductToProductList -> getNavController(view).navigate(R.id.action_newProductFragment_to_shopFragment)
            R.id.buttonNewProductToGallery -> androidView!!.getGallery(view)
            R.id.buttonNewProductToCamera -> androidView!!.getCamera(view)
            R.id.buttonNewProduct -> androidView!!.registerProduct(view, FragmentData.getBooleanUpdate())

            //Sale list
            R.id.buttonSaleListToSearch -> androidView!!.searchSale(view)

            //User fragment
            R.id.buttonUserListToCreateUser -> getNavController(view).navigate(R.id.action_userFragment_to_newUserFragment)
            R.id.buttonUserListToSearch -> androidView!!.searchUser(view)
            //User fragment - new user
            R.id.buttonNewUserToUserList-> getNavController(view).navigate(R.id.action_newUserFragment_to_userFragment)
            R.id.buttonNewUserToHome -> getNavController(view).navigate(R.id.action_newUserFragment_to_home)
            R.id.buttonNewUserToGallery -> androidView!!.getGallery(view)
            R.id.buttonNewUserToCamera -> androidView!!.getCamera(view)
            R.id.buttonNewUserToNewUser -> androidView!!.newUser(view)
            R.id.buttonNewUserCancel -> getNavController(view).navigate(R.id.action_newUserFragment_to_home)
        }
    }

    fun goToProductList(view: View){
        if(getNavController(view).currentDestination!!.id == R.id.newProductFragment) //if you register a product from REST service, you will be in the same place (new product fragment).
            getNavController(view).navigate(R.id.action_newProductFragment_to_shopFragment)
    }

    fun goToNewProduct(view: View){
        getNavController(view).navigate(R.id.action_shopFragment_to_newProductFragment)
    }

    fun goNewUserToUserList(view: View) {
        getNavController(view).navigate(R.id.action_newUserFragment_to_userFragment)
    }

    private fun getNavController(view: View): NavController {
        if(navController==null)
            navController = Navigation.findNavController(view)
        return navController!!
    }
}