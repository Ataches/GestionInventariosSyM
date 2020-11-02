package com.example.stockmanagementsym.presentation

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.stockmanagementsym.R


class AndroidController(private val androidView: AndroidView): View.OnClickListener {

    private var navController: NavController ?= null
    private lateinit var view:View

    override fun onClick(view: View) {
        this.view = view
        when(view.id){

            //Home
            R.id.buttonHomeToShop -> getNavController().navigate(R.id.action_home_to_shopFragment)
            R.id.buttonHomeToCustomersList -> getNavController().navigate(R.id.action_home_to_customerListFragment)
            R.id.buttonHomeToSaleList -> getNavController().navigate(R.id.action_home_to_saleList)
            R.id.buttonHomeToUserList -> getNavController().navigate(R.id.action_home_to_userFragment)

            //Product list
            R.id.buttonProductListToSearch -> androidView.searchProduct()
            R.id.buttonProductListToNewProduct -> getNavController().navigate(R.id.action_shopFragment_to_newProductFragment)

            //Cart
            R.id.buttonCartToNewSale -> androidView.confirmNewSale()

            //Customer list
            R.id.buttonCustomerListToCreateCustomer -> androidView.newCustomer()
            R.id.buttonCustomerToSearch -> androidView.searchCustomer()

            //New product
            R.id.buttonNewProductToHome -> getNavController().navigate(R.id.action_newProductFragment_to_home)
            R.id.buttonNewProductToProductList -> getNavController().navigate(R.id.action_newProductFragment_to_shopFragment)
            R.id.buttonNewProductToGallery -> androidView.getGallery(view.context)
            R.id.buttonNewProductToCamera -> androidView.getCamera(view.context)
            R.id.buttonNewProduct -> androidView.registerProduct(view)

            //Sale list
            R.id.buttonSaleListToSearch -> androidView.searchSale()

            //User fragment
            R.id.buttonUserListToCreateUser -> getNavController().navigate(R.id.action_userFragment_to_newUserFragment)
            R.id.buttonUserListToSearch -> androidView.searchUser()
            //User fragment - new user
            R.id.buttonNewUserToUserList-> getNavController().navigate(R.id.action_newUserFragment_to_userFragment)
            R.id.buttonNewUserToHome -> getNavController().navigate(R.id.action_newUserFragment_to_home)
            R.id.buttonNewUserToGallery -> androidView.getGallery(view.context)
            R.id.buttonNewUserToCamera -> androidView.getCamera(view.context)
            R.id.buttonNewUserToNewUser -> androidView.newUser(view)
            R.id.buttonNewUserCancel -> getNavController().navigate(R.id.action_newUserFragment_to_home)
        }
    }

    fun goToProductList(){
        if(getNavController().currentDestination!!.id == R.id.newProductFragment) //if you register a product from REST service, you will be in the same place (new product fragment).
            getNavController().navigate(R.id.action_newProductFragment_to_shopFragment)
    }

    fun goToNewProduct(){
        getNavController().navigate(R.id.action_shopFragment_to_newProductFragment)
    }

    fun goNewUserToUserList() {
        getNavController().navigate(R.id.action_newUserFragment_to_userFragment)
    }

    private fun getNavController(): NavController {
        if(navController==null)
            navController = Navigation.findNavController(view)
        return navController!!
    }
}