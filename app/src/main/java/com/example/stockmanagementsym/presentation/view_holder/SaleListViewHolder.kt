package com.example.stockmanagementsym.presentation.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.logic.business.Sale
import kotlinx.android.synthetic.main.item_product.view.*

class SaleListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Sale) {
        itemView.textViewName.text = item.getCustomer().getName()
    }
}