package com.example.stockmanagementsym.logic.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.data.Product
import com.example.stockmanagementsym.logic.CartObject
import com.example.stockmanagementsym.logic.CartListener
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_product.view.textViewNombre
import kotlinx.android.synthetic.main.item_product.view.textViewPrecio

class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Product, listener:CartListener) {
        itemView.textViewNombre.text = item.getNombre()
        itemView.textViewPrecio.text = """${item.getPrecio()}"""
        itemView.textViewDescription.text = item.getDescripcion()
        itemView.textViewQtty.text = "Cantidad: ${item.getQuantity()}"
        itemView.buttonDelProdCart.setOnClickListener{
            CartObject.getList().remove(item)
            listener.reloadCart()
        }
        itemView.buttonAddProdCart.setOnClickListener{
            item.setQuantity(item.getQuantity()+1)
            listener.reloadCart()
        }
    }
}