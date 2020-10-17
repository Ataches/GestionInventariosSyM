package com.example.stockmanagementsym.presentation.view_holder

import android.view.View
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.data.CartData
import com.example.stockmanagementsym.logic.ProductLogic
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.fragment.NewProductFragment
import com.example.stockmanagementsym.presentation.fragment.ProductListFragment
import com.example.stockmanagementsym.presentation.view.FragmentData
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
        itemView.buttonProductListToCart.setOnClickListener{
            var quantity:Int
            try {
                quantity = itemView.editTextQuantity.text.toString().toInt()
                item.setQuantity(quantity)
                FragmentData.showMessage(it.context, FragmentData.addProduct(item))
            }catch (e: Exception){
                FragmentData.showMessage(it.context, "Digite un numero correcto")
            }
        }
        itemView.buttonEditProduct.setOnClickListener {

            val fragment = itemView.findFragment<ProductListFragment>()
            val transaction = fragment.parentFragmentManager.beginTransaction()

            FragmentData.setNewProductFragment(NewProductFragment())
            FragmentData.setUpdateBoolean(true)
            FragmentData.setNewProductFragment(FragmentData.getNewProductFragment())
            AndroidController.setFragmentTransaction(transaction)
            AndroidController.goNewProduct()

            FragmentData.setProductToEdit(item)
            AndroidController.onClick(it)

        }
    }
}