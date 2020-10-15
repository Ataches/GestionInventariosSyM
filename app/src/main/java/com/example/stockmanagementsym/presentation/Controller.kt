package com.example.stockmanagementsym.presentation

import android.view.View
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.presentation.fragment.HomeFragment
import com.example.stockmanagementsym.presentation.fragment.NewProductFragment
import com.example.stockmanagementsym.presentation.fragment.ProductListFragment

object Controller: View.OnClickListener {

    private lateinit var navController: NavController
    private lateinit var transaction: FragmentTransaction

    override fun onClick(view: View) {
        navController = Navigation.findNavController(view)
        when(view.id){
            //Home
            R.id.buttonHomeToProductList -> navController.navigate(R.id.action_home_to_productsList)
            R.id.buttonHomeToSaleList -> navController.navigate(R.id.action_home_to_saleList)
            R.id.buttonHomeToCustomersList -> navController.navigate(R.id.action_home_to_customerListFragment)

            //Product list

            R.id.buttonProductListToHome -> navController.navigate(R.id.action_productsList_to_home)
            R.id.buttonProductListToCart -> navController.navigate(R.id.action_productsList_to_cart)
            R.id.buttonProductListToSearch -> Model.searchProduct(view)
            R.id.buttonProductListToNewProduct -> {
                FragmentData.setUpdateBoolean(false)
                FragmentData.setNewProductFragment(NewProductFragment())
                goNewProduct()
            }

            //Cart
            R.id.buttonCartToHome -> navController.navigate(R.id.action_cart_to_home)
            R.id.buttonCartToProductList -> navController.navigate(R.id.action_cart_to_productsList)
            R.id.buttonCartToNewSale -> {
                if(FragmentData.getConfirmRegister())
                    Model.newSale(view)
                else
                    Model.confirmNewSale(view)
            }

            //Customer list
            R.id.buttonCustomerListToHome -> navController.navigate(R.id.action_customerListFragment_to_home)
            R.id.buttonCustomerListToCreateCustomer -> DialogObject.dialogNewCustomer(view,true)
            R.id.buttonCustomerToSearch -> {
                Model.searchCustomer(view)
                FragmentData.reloadCustomerList()
            }

            //New product
            R.id.buttonNewProductToHome -> goHome()
            R.id.buttonNewProductToProductList -> goProductList()
            R.id.buttonNewProductToGallery -> Model.getPhotoGallery(view)
            R.id.buttonNewProductToCamera -> Model.getPhotoCamera(view)
            R.id.buttonNewProductToNewProduct -> {
                if(FragmentData.getUpdateBoolean()){
                    Model.setProductEdited()
                    Model.updateProduct()
                    FragmentData.reloadProductList()
                    goProductList()
                }else{
                    DialogObject.confirmCreateProduct(view)
                    goProductList()
                }
            }

            //Sale list
            R.id.buttonSaleListToSearch -> Model.searchSale(view)
            R.id.buttonSaleListToHome -> navController.navigate(R.id.action_saleList_to_home)

            //Item views
            // Item view product list sale
            //R.id.buttonProductListSale

        }
    }

    private fun goHome(){
        transaction.replace(FragmentData.getIdFragment(), HomeFragment())
        transaction.commit()
    }

    private fun goProductList(){
        transaction.replace(FragmentData.getIdFragment(), ProductListFragment())
        transaction.commit()
    }

    fun goNewProduct(){
        transaction.replace(R.id.nav_host_fragment, FragmentData.getNewProductFragment())
        transaction.commit()
    }

    fun setFragmentTransaction(transaction: FragmentTransaction) {
        this.transaction = transaction
    }
}