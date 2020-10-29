package com.example.stockmanagementsym.presentation.view_holder

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.fragment.ListListener
import com.example.stockmanagementsym.presentation.view.FragmentData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_cart.view.editTextQuantity
import kotlinx.android.synthetic.main.item_cart.view.imageViewProduct
import kotlinx.android.synthetic.main.item_cart.view.textViewDescription
import kotlinx.android.synthetic.main.item_cart.view.textViewName
import kotlinx.android.synthetic.main.item_cart.view.textViewPrice
import kotlinx.android.synthetic.main.item_cart.view.textViewQuantity
import kotlinx.android.synthetic.main.item_product.view.*
import java.text.DecimalFormat

class CartViewHolder(itemView: View, var listener: ListListener) : RecyclerView.ViewHolder(itemView) {

    private var product = Product("",0.0,"","",1)

    fun bind(productCart: Product) {
        val df = DecimalFormat("$###,###,###")
        itemView.textViewName.text = productCart.getName()
        itemView.textViewPrice.text = df.format(productCart.getPrice())
        itemView.textViewDescription.text = productCart.getDescription()
        itemView.textViewQuantity.text = "Cantidad: ${productCart.getQuantity()}"
        itemView.editTextQuantity.setText("""${productCart.getQuantity()}""")


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
        }

        itemView.textViewProdRealQuantity.text =
            "Disponibles: ${(product.getQuantity()-productCart.getQuantity())}" +
                    "de total: ${product.getQuantity()}"

        itemView.buttonRemoveCart.setOnClickListener{
            FragmentData.removeElementCart(it.context,productCart)
            listener.reloadList()
        }

        itemView.buttonRemoveQuantityProdCart.setOnClickListener{
            val quantityString = itemView.editTextQuantity.text.toString()
            changeQuantity(quantityString,productCart,-1,it)
        }
        itemView.buttonAddQuantityProdCart.setOnClickListener{
            val quantityString = itemView.editTextQuantity.text.toString()
            changeQuantity(quantityString,productCart,1,it)
        }
    }

    private fun changeQuantity(quantityString: String, productCart: Product,stepAdd:Int,it:View) {
        if(quantityString.isDigitsOnly()&&quantityString.isNotEmpty()){
            val quantity:Int = quantityString.toInt()
            if((product.getQuantity()>=quantity)&&(quantity > 0)){
                if((quantity==productCart.getQuantity())&&((product.getQuantity()>quantity)||(stepAdd<0)))
                    productCart.setQuantity(quantity+stepAdd)
                else
                    productCart.setQuantity(quantity)
                listener.reloadList()
            }else
                FragmentData.showToastMessage(it.context, "Digite un numero correcto de acuerdo a la cantidad disponible")
        }else
            FragmentData.showToastMessage(it.context, "Digite un numero correcto")
    }

    private fun decoderStringToBitMap(string: String): Bitmap? {
        val byteArrayFromString = Base64.decode(string, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArrayFromString,0,byteArrayFromString.size)
    }

    fun setProductOriginal(product: Product) {
        this.product = product
    }

}