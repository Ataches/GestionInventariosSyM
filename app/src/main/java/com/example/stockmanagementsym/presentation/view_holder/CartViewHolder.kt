package com.example.stockmanagementsym.presentation.view_holder

import android.view.View
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.fragment.ListListener
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_cart.view.editTextQuantity
import kotlinx.android.synthetic.main.item_cart.view.textViewDescription
import kotlinx.android.synthetic.main.item_cart.view.textViewName
import kotlinx.android.synthetic.main.item_cart.view.textViewPrice
import kotlinx.android.synthetic.main.item_cart.view.textViewQuantity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var product = Product("",1,"",0,1)

    fun bind(productCart: Product, listener: ListListener) {
        itemView.textViewName.text = productCart.getName()
        itemView.textViewPrice.text = "$"+productCart.getPrice()
        itemView.textViewDescription.text = productCart.getDescription()
        itemView.textViewQuantity.text = "Cantidad: ${productCart.getQuantity()}"
        itemView.editTextQuantity.setText("""${productCart.getQuantity()}""")
        itemView.textViewProdRealQuantity.text =
            "Disponibles: ${(product.getQuantity()-productCart.getQuantity())}" +
                    ", total: ${product.getQuantity()}"

        itemView.buttonRemoveCart.setOnClickListener{
            FragmentData.removeElementCart(it.context,productCart)
            listener.reloadList()
        }
        itemView.buttonAddQuantityProdCart.setOnClickListener{
            val quantityString = itemView.editTextQuantity.text.toString()
            if(quantityString.isDigitsOnly()&&quantityString.isNotEmpty()){
                val quantity:Int = quantityString.toInt()
                if((product.getQuantity()>=quantity)&&(quantity > 0)){
                    productCart.setQuantity(quantity)
                    GlobalScope.launch(Dispatchers.IO){
                        listener.reloadList()
                    }
                }else
                    FragmentData.showMessage(it.context, "Digite un numero correcto de acuerdo a la cantidad disponible")
            }else
                FragmentData.showMessage(it.context, "Digite un numero correcto")
        }
    }

    fun setProductOriginal(product: Product) {
        this.product = product
    }

}