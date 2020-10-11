package com.example.stockmanagementsym.logic.view_holder
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.model.business.Product
import com.example.stockmanagementsym.model.business.Sale
import com.example.stockmanagementsym.model.data.CartObject
import kotlinx.android.synthetic.main.item_product.view.*

class SaleListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Sale) {
        itemView.textViewName.text = item.getCustomer().getName()
    }
}