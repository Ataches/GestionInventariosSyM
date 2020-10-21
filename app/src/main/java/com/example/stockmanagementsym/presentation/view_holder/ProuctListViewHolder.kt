package com.example.stockmanagementsym.presentation.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Product) {
        itemView.textViewName.text = item.getName()
        itemView.textViewPrice.text = "$ ${item.getPrice()}"
        itemView.textViewDescription.text = item.getDescription()
        itemView.textViewQuantity.text = "Cantidad: ${item.getQuantity()}"
        //itemView.buttonCart.setBackgroundDrawable(itemView.context.resources.getDrawable(item.getIDiconDrawable()))
        itemView.buttonAddProductToCart.setOnClickListener{
            val quantity:Int = itemView.editTextQuantity.text.toString().toInt()
            if((item.getQuantity() >= quantity) && (quantity > 0)){
                val cartProduct = Product(
                    name = item.getName(),
                    description = item.getDescription(),
                    price = item.getPrice(),
                    idIconDrawable = item.getIdIconDrawable(),
                    quantity = quantity
                )
                cartProduct.idProduct = item.idProduct
                FragmentData.showMessage(it.context, FragmentData.addProductToCart(cartProduct))
            }else
                FragmentData.showMessage(it.context, "Digite un numero correcto")
        }
        itemView.buttonEditProduct.setOnClickListener {
            FragmentData.setProductToEdit(item)
            FragmentData.setBooleanUpdate(true)
            FragmentData.goToNewProduct()
        }
        itemView.buttonDeleteProduct.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO){
                FragmentData.deleteProduct(item)
                FragmentData.reloadProductList()
            }
        }
    }
}