package com.example.stockmanagementsym.presentation.view_holder

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.android.synthetic.main.item_product.view.editTextQuantity
import kotlinx.android.synthetic.main.item_product.view.imageViewProduct
import kotlinx.android.synthetic.main.item_product.view.textViewDescription
import kotlinx.android.synthetic.main.item_product.view.textViewName
import kotlinx.android.synthetic.main.item_product.view.textViewPrice
import kotlinx.android.synthetic.main.item_product.view.textViewQuantity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(product: Product) {
        itemView.textViewName.text = product.getName()
        itemView.textViewPrice.text = "$ ${product.getPrice()}"
        itemView.textViewDescription.text = product.getDescription()
        itemView.textViewQuantity.text = "Cantidad: ${product.getQuantity()}"
        if(product.getStringBitMap().isNotEmpty()){
            itemView.imageViewProduct.setImageBitmap(decoderStringToBitMap(product.getStringBitMap()))
            itemView.imageViewProduct.setBackgroundResource(0)
        }
        itemView.buttonAddProductToCart.setOnClickListener{
            val quantityString = itemView.editTextQuantity.text.toString()
            if(quantityString.isDigitsOnly()&&quantityString.isNotEmpty()){
                val quantity:Int = quantityString.toInt()
                if((product.getQuantity() >= quantity) && (quantity > 0)){
                    val cartProduct = Product(
                        name = product.getName(),
                        description = product.getDescription(),
                        price = product.getPrice(),
                        stringBitMap = product.getStringBitMap(),
                        quantity = quantity
                    )
                    cartProduct.idProduct = product.idProduct
                    FragmentData.showMessage(it.context, FragmentData.addProductToCart(cartProduct))
                }else
                    FragmentData.showMessage(it.context, "Digite un numero correcto de acuerdo a la cantidad disponible")
            }else
                FragmentData.showMessage(it.context, "Digite un numero correcto")
        }
        itemView.buttonEditProduct.setOnClickListener {
            FragmentData.setProductToEdit(product)
            FragmentData.setBooleanUpdate(true)
            FragmentData.goToNewProduct()
        }
        itemView.buttonDeleteProduct.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO){
                FragmentData.deleteProduct(product)
                FragmentData.reloadProductList()
            }
        }
    }

    private fun decoderStringToBitMap(string: String): Bitmap? {
        val byteArrayFromString = Base64.decode(string, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArrayFromString,0,byteArrayFromString.size)
    }
}