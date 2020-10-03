package com.example.stockmanagementsym.logic.adapter
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.data.Product
import com.example.stockmanagementsym.logic.CartObject
import kotlinx.android.synthetic.main.item_product.view.*

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Product) {
        itemView.textViewNombre.text = item.getNombre()
        itemView.textViewPrecio.text = "$ ${item.getPrecio()}"
        itemView.textViewDescription.text = item.getDescripcion()
        itemView.textViewQtty.text = "Cantidad: ${item.getQuantity()}"
        //itemView.buttonCart.setBackgroundDrawable(itemView.context.resources.getDrawable(item.getIDiconDrawable()))
        itemView.buttonCart.setOnClickListener{
            Toast.makeText(it.context, CartObject.addProduct(item), Toast.LENGTH_SHORT).show()
        }
    }
}