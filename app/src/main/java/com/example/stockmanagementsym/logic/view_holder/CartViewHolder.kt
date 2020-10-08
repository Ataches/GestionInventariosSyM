package com.example.stockmanagementsym.logic.view_holder

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.data.Product
import com.example.stockmanagementsym.data.CartObject
import com.example.stockmanagementsym.logic.ListListener
import kotlinx.android.synthetic.main.item_cart.view.*

class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Product, listener:ListListener) {
        itemView.textViewName.text = item.getName()
        itemView.textViewPrice.text = "$"+item.getPrice()
        itemView.textViewDescription.text = item.getDescription()
        itemView.textViewQuantity.text = "Cantidad: ${item.getQuantity()}"
        itemView.editTextQuantity.setText("""${item.getQuantity()}""")
        itemView.buttonRemoveCart.setOnClickListener{
            CartObject.getList().remove(item)
            listener.reloadList()
        }
        itemView.buttonAddProdCart.setOnClickListener{
            var quantity:Int
            try {
                quantity = itemView.editTextQuantity.text.toString().toInt()
                item.setQuantity(quantity)
                listener.reloadList()
            }catch (e:Exception){
                Toast.makeText(it.context, "Digite un numero correcto", Toast.LENGTH_SHORT).show()
            }
        }
    }
}