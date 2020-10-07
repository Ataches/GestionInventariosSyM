package com.example.stockmanagementsym.logic.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.data.Customer
import kotlinx.android.synthetic.main.item_client.view.*

class CustomerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Customer) {
        itemView.textViewName.text = item.getName()
        itemView.textViewAddress.text = item.getAddress()
        itemView.textViewPhone.text = "Telefono: ${item.getPhone()}"
        itemView.textViewCity.text = "Ciudad: ${item.getCity()}"
        //itemView.buttonCart.setBackgroundDrawable(itemView.context.resources.getDrawable(item.getIDiconDrawable()))
        itemView.buttonEdit.setOnClickListener{

        }
    }
}