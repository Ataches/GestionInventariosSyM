package com.example.stockmanagementsym.logic.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.data.Data
import com.example.stockmanagementsym.data.Product
import com.example.stockmanagementsym.logic.ListListener
import kotlinx.android.synthetic.main.fragment_new_product.*

class NewProductFragment(private var listListener: ListListener): Fragment(), View.OnClickListener {

    private lateinit var navController: NavController
    private lateinit var transaction: FragmentTransaction

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        transaction = parentFragmentManager.beginTransaction()
        buttonBackHome.setOnClickListener (this)
        buttonProductList.setOnClickListener (this)
        buttonNewProduct.setOnClickListener (this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_product, container, false)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.buttonBackHome -> goHome()
            R.id.buttonProductList -> goProductList()
            R.id.buttonNewProduct -> confirmCreateProduct(view)
        }
    }

    private fun confirmCreateProduct(view:View){
        val dataProduct = mapOf("Name" to editTextProductName.text.toString(), "Price" to editTextProductPrice.text.toString(),
        "Description" to editTextProductDesc.text.toString(),"Image" to R.drawable.ic_login.toString(), "Quantity" to editTextProductQuantity.text.toString())

        val builder = AlertDialog.Builder(view.context)
        builder.setTitle(getString(R.string.titleAlertCreateProd))
        val message = getString(R.string.messageAlertCreatedProd)+
                             "\n"+dataProduct
        builder.setPositiveButton("Si"){ _,_ ->
            newProduct(view, dataProduct)
        }
        builder.setNegativeButton("No"){ _,_ ->
            Toast.makeText(view.context, "Modifique los datos si es necesario", Toast.LENGTH_SHORT).show()
        }
        builder.setMessage(message)
        builder.create()
        builder.show()
    }

    private fun newProduct(view: View, dataProduct:Map<String,String>) {
        try{
            Data.addProduct(Product(dataProduct.getValue("Name"),dataProduct.getValue("Price").toInt(),
                                    dataProduct.getValue("Description"),dataProduct.getValue("Image").toInt(),dataProduct.getValue("Quantity").toInt()))
            listListener.reloadList()
            Toast.makeText(view.context, "Producto registrado con exito", Toast.LENGTH_SHORT).show()
            goProductList()
        }catch (e:Exception){
            Toast.makeText(view.context, "Ingrese datos correctos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goHome(){
        transaction.replace(this.id, HomeFragment())
        transaction.commit()
    }

    private fun goProductList(){
        transaction.replace(this.id, ProductListFragment())
        transaction.commit()
    }
}