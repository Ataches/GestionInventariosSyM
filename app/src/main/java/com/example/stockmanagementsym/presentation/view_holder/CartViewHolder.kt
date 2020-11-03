package com.example.stockmanagementsym.presentation.view_holder

import android.view.View
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.adapter.CartAdapter
import com.example.stockmanagementsym.presentation.fragment.FragmentData
import kotlinx.android.synthetic.main.item_cart.view.*
import java.text.DecimalFormat

class CartViewHolder(itemView: View, private var adapter: CartAdapter) :
RecyclerView.ViewHolder(itemView) {

    private var product = Product("", 0.0, "", "", 1)

    fun bind(productCart: Product) {
        val df = DecimalFormat("$###,###,###")
        itemView.textViewName.text = productCart.getName()
        itemView.textViewPrice.text = df.format(productCart.getPrice())
        itemView.textViewDescription.text = productCart.getDescription()
        itemView.textViewQuantity.text = "Cantidad: ${productCart.getQuantity()}"
        itemView.editTextQuantity.setText("""${productCart.getQuantity()}""")

        FragmentData.paintPhoto(
            productCart.getStringBitMap(),
            itemView.imageViewProduct,
            R.drawable.ic_done
        )

        itemView.textViewProdRealQuantity.text =
            "Disponibles: ${(product.getQuantity() - productCart.getQuantity())}" +
                    " de total: ${product.getQuantity()}"

        itemView.buttonRemoveCart.setOnClickListener {
            FragmentData.removeElementCart(productCart)
            adapter.notifyDataSetChanged()
        }

        itemView.buttonRemoveQuantityProdCart.setOnClickListener {
            val quantityString = itemView.editTextQuantity.text.toString()
            changeQuantity(quantityString, productCart, -1)
        }
        itemView.buttonAddQuantityProdCart.setOnClickListener {
            val quantityString = itemView.editTextQuantity.text.toString()
            changeQuantity(quantityString, productCart, 1)
        }
    }

    private fun changeQuantity(quantityString: String, productCart: Product,stepAdd:Int) {
        if(quantityString.isDigitsOnly()&&quantityString.isNotEmpty()){
            val quantity:Int = quantityString.toInt()
            if(((quantity+stepAdd > 0)&&(stepAdd<0))||((quantity+stepAdd <= product.getQuantity())&&(stepAdd>0))){ //If step add is negative means that you need to sum it to quantity to decrease quantity
                if((quantity==productCart.getQuantity())&&((product.getQuantity()>quantity)||(stepAdd<0)))
                    productCart.setQuantity(quantity+stepAdd)
                else
                    productCart.setQuantity(quantity)
                adapter.notifyDataSetChanged()
            }else
                FragmentData.showToastMessage(R.string.wrongQuantityNumber)
        }else
            FragmentData.showToastMessage(R.string.wrongNumber)
    }

    fun setProductOriginal(product: Product) {
        this.product = product
    }

}