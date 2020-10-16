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
                    androidView.newSale(view)
                else
                    androidView.confirmNewSale(view)
            }

            //Customer list
            R.id.buttonCustomerListToHome -> navController.navigate(R.id.action_customerListFragment_to_home)
            R.id.buttonCustomerListToCreateCustomer -> androidView.dialogNewCustomer(view,true)
            R.id.buttonCustomerToSearch -> {
                androidView.searchCustomer(view)
                FragmentData.reloadCustomerList()
            }

            //New product
            R.id.buttonNewProductToHome -> goHome()
            R.id.buttonNewProductToProductList -> goProductList()
            R.id.buttonNewProductToGallery -> androidView.getPhotoGallery(view)
            R.id.buttonNewProductToCamera -> androidView.getPhotoCamera(view)
            R.id.buttonNewProductToNewProduct -> {
                if(FragmentData.getUpdateBoolean()){
                    androidView.updateProduct()
                    FragmentData.reloadProductList()
                    goProductList()
                }else{
                    androidView.confirmCreateProduct(view)
                    goProductList()
                }
            }

            //Sale list
            R.id.buttonSaleListToSearch -> androidView.searchSale(view)
            R.id.buttonSaleListToHome -> navController.navigate(R.id.action_saleList_to_home)

            //Item views
            // Item view product list sale
            //R.id.buttonProductListSale

            //Product view holder
            /*
            R.id.buttonProductListToCart -> {/*
                                                var quantity:Int
                                                try {
                                                    quantity = itemView.editTextQuantity.text.toString().toInt()
                                                    item.setQuantity(quantity)
                                                    DialogView.showMessage(it.context, CartObject.addProduct(item))
                                                }catch (e: Exception){
                                                    DialogView.showMessage(it.context, "Digite un numero correcto")
                                                }
                                            */}

             */
            R.id.buttonEditProduct -> {

                androidView.setProductToEdit(FragmentData.getProductToEdit())
                /*
                val fragment = itemView.findFragment<ProductListFragment>()
                val transaction = fragment.parentFragmentManager.beginTransaction()

                Model.setNewProductFragment(NewProductFragment())
                FragmentData.setUpdateBoolean(true)
                FragmentData.setNewProductFragment(Model.getNewProductFragment())
                Controller.setFragmentTransaction(transaction)
                Controller.goNewProduct()


                Model.setProductToEdit(item)

                 */
            }

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