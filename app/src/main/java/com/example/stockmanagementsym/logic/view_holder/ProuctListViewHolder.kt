package com.example.stockmanagementsym.logic.view_holder
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.model.business.Product
import com.example.stockmanagementsym.model.data.CartObject
import kotlinx.android.synthetic.main.item_product.view.*

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Product) {
        itemView.textViewName.text = item.getName()
        itemView.textViewPrice.text = "$ ${item.getPrice()}"
        itemView.textViewDescription.text = item.getDescription()
        itemView.textViewQuantity.text = "Cantidad: ${item.getQuantity()}"
        //itemView.buttonCart.setBackgroundDrawable(itemView.context.resources.getDrawable(item.getIDiconDrawable()))
        itemView.buttonCart.setOnClickListener{
            var quantity:Int
            try {
                quantity = itemView.editTextQuantity.text.toString().toInt()
                item.setQuantity(quantity)
                Toast.makeText(it.context, CartObject.addProduct(item), Toast.LENGTH_SHORT).show()
            }catch (e:Exception){
                Toast.makeText(it.context, "Digite un numero correcto", Toast.LENGTH_SHORT).show()
            }
        }
    }
}