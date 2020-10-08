package com.example.stockmanagementsym.logic.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.data.Data
import com.example.stockmanagementsym.data.Product
import com.example.stockmanagementsym.logic.ListListener
import kotlinx.android.synthetic.main.fragment_create_product.*

class CreateProductFragment(private var listListener: ListListener): Fragment(), View.OnClickListener {

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        navController = Navigation.findNavController(view)
        buttonBackHome.setOnClickListener (this)
        buttonProductList.setOnClickListener (this)
        buttonCreateProduct.setOnClickListener (this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_product, container, false)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.buttonBackHome -> returnOriginalView(view)
            R.id.buttonProductList -> returnOriginalView(view)
            R.id.buttonCreateProduct -> createProduct(view)
        }
    }

    private fun returnOriginalView(view:View){
        var transaction = parentFragmentManager.beginTransaction()
        when(view.id){
            R.id.buttonBackHome -> {
                transaction.replace(this.id, ProductListFragment())
                transaction.commit()
            }
            R.id.buttonProductList ->{
                transaction.replace(this.id, HomeFragment())
                transaction.commit()
            }

        }
    }

    private fun createProduct(view: View) {
        try{
            Data.addProduct(Product(editTextProductName.text.toString(), editTextProductPrice.text.toString().toInt(),
                editTextProductDesc.text.toString(), R.drawable.ic_login, editTextProductQuantity.text.toString().toInt()))
            listListener.reloadList()
            returnOriginalView(view)
            navController.navigate(R.id.action_createProductFragment_to_productsList)
        }catch (e:Exception){
            Toast.makeText(view.context, "Ingrese datos correctos", Toast.LENGTH_SHORT).show()
        }
    }

}