package com.example.stockmanagementsym.presentation.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.fragment.ListListener
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.item_cart.view.*

class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Product, listener: ListListener) {
        val product = FragmentData.getProductList().filter { it.idProduct == item.idProduct }[0]
        itemView.textViewName.text = item.getName()
        itemView.textViewPrice.text = "$"+item.getPrice()
        itemView.textViewDescription.text = item.getDescription()
        itemView.textViewQuantity.text = "Cantidad: ${item.getQuantity()}"
        itemView.editTextQuantity.setText("""${item.getQuantity()}""")
        itemView.textViewProdRealQuantity.text = "Disponibles: ${(product.getQuantity()-item.getQuantity())}, total: ${product.getQuantity()}"
        itemView.buttonRemoveCart.setOnClickListener{
            FragmentData.removeElementCart(it.context,item)
            listener.reloadList()
        }
        itemView.buttonAddQuantityProdCart.setOnClickListener{
            val quantity:Int = itemView.editTextQuantity.text.toString().toInt()
            if((product.getQuantity()>=quantity)&&(quantity > 0)){
                item.setQuantity(quantity)
                listener.reloadList()
            }else{
                FragmentData.showMessage(it.context, "Digite un numero correcto")
            }
        }
    }
}