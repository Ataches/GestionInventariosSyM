package com.example.stockmanagementsym.presentation.view_holder

import android.view.View
import android.widget.Toast
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.data.CartObject
import com.example.stockmanagementsym.logic.ProductLogic
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.fragment.NewProductFragment
import com.example.stockmanagementsym.presentation.fragment.ProductListFragment
import kotlinx.android.synthetic.main.item_product.view.*

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var productLogic: ProductLogic
    private lateinit var productListFragment: ProductListFragment
    private var productLogicCreated: Boolean = false
    private var productListFragmentCreated: Boolean = false

    fun bind(item: Product) {
        itemView.textViewName.text = item.getName()
        itemView.textViewPrice.text = "$ ${item.getPrice()}"
        itemView.textViewDescription.text = item.getDescription()
        itemView.textViewQuantity.text = "Cantidad: ${item.getQuantity()}"
        //itemView.buttonCart.setBackgroundDrawable(itemView.context.resources.getDrawable(item.getIDiconDrawable()))
        itemView.buttonCart.setOnClickListener{
            var quantity:Int
            try {
                quantity = itemView.editTextQuantity.text.toString().toInt()
                item.setQuantity(quantity)
                Toast.makeText(it.context, CartObject.addProduct(item), Toast.LENGTH_SHORT).show()
            }catch (e: Exception){
                Toast.makeText(it.context, "Digite un numero correcto", Toast.LENGTH_SHORT).show()
            }
        }
        itemView.buttonEditProduct.setOnClickListener {

            val findFragment = itemView.findFragment<ProductListFragment>()
            val transaction = findFragment.fragmentManager?.beginTransaction()
            val newProductFragment= NewProductFragment()
            newProductFragment.setUpdateBoolean(true)
            getProductLogic().setProductToEdit(item)
            newProductFragment.setProductLogic(getProductLogic())
            transaction?.replace(R.id.nav_host_fragment, newProductFragment)
            transaction?.commit()

        }
    }
    private fun getProductLogic(): ProductLogic {
        if(productLogicCreated)
            return productLogic
        productLogic = ProductLogic()
        productLogicCreated = true
        return productLogic
    }
    private fun getProductListFragment(): ProductListFragment {
        if(productListFragmentCreated)
            return productListFragment
        productListFragment = ProductListFragment()
        productListFragmentCreated = true
        return productListFragment
    }
}