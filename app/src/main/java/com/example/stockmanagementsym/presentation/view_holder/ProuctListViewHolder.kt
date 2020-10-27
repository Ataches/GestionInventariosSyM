package com.example.stockmanagementsym.presentation.view_holder

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(product: Product) {
        val df = DecimalFormat("$###,###,###")
        itemView.textViewName.text = product.getName()
        itemView.textViewDescription.text = product.getDescription()
        itemView.textViewPrice.text = df.format(product.getPrice())
        itemView.textViewQuantity.text = "Cantidad: ${product.getQuantity()}"
        if(product.getStringBitMap().isNotEmpty()){
            itemView.imageViewProduct.setImageBitmap(decoderStringToBitMap(product.getStringBitMap()))
            itemView.imageViewProduct.setBackgroundResource(0)
        }else{
            itemView.imageViewProduct.setImageBitmap(null)
            itemView.imageViewProduct.setBackgroundResource(R.drawable.ic_image)
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
                    FragmentData.addProductToCart(cartProduct,it)
                }else
                    FragmentData.showToastMessage(it.context, "Digite un numero correcto de acuerdo a la cantidad disponible")
            }else
                FragmentData.showToastMessage(it.context, "Digite un numero correcto")
        }
        itemView.buttonEditProduct.setOnClickListener {
            FragmentData.updateProduct(product,true, it)
        }
        itemView.buttonDeleteProduct.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO){
                FragmentData.deleteProduct(product)
            }
        }
    }

    private fun decoderStringToBitMap(string: String): Bitmap? {
        val byteArrayFromString = Base64.decode(string, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArrayFromString,0,byteArrayFromString.size)
    }
}