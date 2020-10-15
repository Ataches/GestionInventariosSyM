package com.example.stockmanagementsym.presentation.view_holder

import android.view.View
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.data.CartObject
import com.example.stockmanagementsym.logic.ProductLogic
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.Controller
import com.example.stockmanagementsym.presentation.DialogObject
import com.example.stockmanagementsym.presentation.FragmentData
import com.example.stockmanagementsym.presentation.Model
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
        itemView.buttonProductListToCart.setOnClickListener{
            var quantity:Int
            try {
                quantity = itemView.editTextQuantity.text.toString().toInt()
                item.setQuantity(quantity)
                DialogObject.showMessage(it.context, CartObject.addProduct(item))
            }catch (e: Exception){
                DialogObject.showMessage(it.context, "Digite un numero correcto")
            }
        }
        itemView.buttonEditProduct.setOnClickListener {

            val fragment = itemView.findFragment<ProductListFragment>()
            val transaction = fragment.parentFragmentManager.beginTransaction()

            Model.setNewProductFragment(NewProductFragment())
            FragmentData.setUpdateBoolean(true)
            FragmentData.setNewProductFragment(Model.getNewProductFragment())
            Controller.setFragmentTransaction(transaction)
            Controller.goNewProduct()


            Model.setProductToEdit(item)

        }
    }
}