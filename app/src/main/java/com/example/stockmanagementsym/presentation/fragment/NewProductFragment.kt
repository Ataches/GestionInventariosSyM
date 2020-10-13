package com.example.stockmanagementsym.presentation.fragment

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
import com.example.stockmanagementsym.logic.ProductLogic
import com.example.stockmanagementsym.logic.business.Product
import kotlinx.android.synthetic.main.fragment_new_product.*

class NewProductFragment: Fragment(), View.OnClickListener {

    private lateinit var product:Product
    private lateinit var navController: NavController
    private lateinit var transaction: FragmentTransaction
    private lateinit var listListener: ListListener
    private var updateBoolean:Boolean=false
    private lateinit var productLogic: ProductLogic

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
            R.id.buttonNewProduct -> {
                if(updateBoolean){
                    setProduct(Product(editTextProductName.text.toString(), editTextProductPrice.text.toString().toInt(),
                        editTextProductDesc.text.toString(), R.drawable.ic_login.toString().toInt(), editTextProductQuantity.text.toString().toInt()))
                    getProductLogic().setProductEdited(getProduct())
                    getProductLogic().editProduct()
                    goProductList()
                }else
                    confirmCreateProduct(view)
            }
        }
    }

    private fun confirmCreateProduct(view:View){
        setProduct(Product(editTextProductName.text.toString(), editTextProductPrice.text.toString().toInt(),
            editTextProductDesc.text.toString(), R.drawable.ic_login.toString().toInt(), editTextProductQuantity.text.toString().toInt()))

        val builder = AlertDialog.Builder(view.context)
        builder.setTitle(getString(R.string.titleAlertNewProd))
        val message = getString(R.string.messageAlertNewProd)+
                             "\n"+getProduct()
        builder.setPositiveButton("Si"){ _,_ ->
            newProduct(view)
        }
        builder.setNegativeButton("No"){ _,_ ->
            Toast.makeText(view.context, "Modifique los datos si es necesario", Toast.LENGTH_SHORT).show()
        }
        builder.setMessage(message)
        builder.create()
        builder.show()
    }

    private fun newProduct(view: View) {
        try{
            Data.addProduct(getProduct())
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

    fun setListListener(listListener: ListListener){
        this.listListener = listListener
    }
    fun setUpdateBoolean(updateBoolean:Boolean){
        this.updateBoolean = updateBoolean
    }
    fun setProduct(product: Product){
        this.product = product
    }
    private fun getProduct(): Product {
        return product
    }
    fun setProductLogic(productLogic: ProductLogic){
        this.productLogic = productLogic
    }
    fun getProductLogic():ProductLogic{
        return productLogic
    }
}
