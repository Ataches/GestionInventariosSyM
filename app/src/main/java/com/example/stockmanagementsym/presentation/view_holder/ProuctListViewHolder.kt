package com.example.stockmanagementsym.presentation.view_holder

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.data.CONSTANTS
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.view.FragmentData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.view.*
import java.text.DecimalFormat

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(product: Product) {
        val df = DecimalFormat("$###,###,###")
        itemView.textViewName.text = product.getName()
        itemView.textViewDescription.text = product.getDescription()
        itemView.textViewPrice.text = df.format(product.getPrice())
        itemView.textViewQuantity.text = """${product.getQuantity()}"""

        val userPhotoData = product.getStringBitMap()
        if(userPhotoData.isNotEmpty()){
            if(userPhotoData.length<400){
                try {
                    Picasso.get().load(userPhotoData).into(itemView.imageViewProduct)
                    itemView.imageViewProduct.background = null
                }catch (e:Exception){
                    FragmentData.showToastMessage(itemView.context, ""+e)
                }
            }else{
                itemView.imageViewProduct.setImageBitmap(decoderStringToBitMap(product.getStringBitMap()))
                itemView.imageViewProduct.background = null
            }
        }else{
            itemView.imageViewProduct.setImageBitmap(null)
            itemView.imageViewProduct.setBackgroundResource(R.drawable.ic_image)
        }

        if(product.idProduct == CONSTANTS.ID_PRODUCT_DEFAULT)
            itemView.constraintLayoutCard.setBackgroundResource(R.color.buttonSecondary)
        else
            itemView.constraintLayoutCard.setBackgroundResource(R.color.cardBackground)

        itemView.buttonAddProductToCart.setOnClickListener{
            if(product.idProduct != CONSTANTS.ID_PRODUCT_DEFAULT){
                FragmentData.addProductToCart(product,it)
            }else{
                FragmentData.showToastMessage(it.context,"Producto no registrado, redirigiendo")
                FragmentData.confirmNewProduct(product,itemView)
            }
        }
        itemView.buttonEditProduct.setOnClickListener {
            FragmentData.updateProduct(product,true, it)
        }
        itemView.buttonDeleteProduct.setOnClickListener{
            FragmentData.askDeleteProduct(product,itemView.context)
        }
    }

    private fun decoderStringToBitMap(string: String): Bitmap? {
        val byteArrayFromString = Base64.decode(string, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArrayFromString,0,byteArrayFromString.size)
    }
}