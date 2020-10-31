package com.example.stockmanagementsym.presentation.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.presentation.fragment.FragmentData
import kotlinx.android.synthetic.main.item_customer.view.*

class CustomerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Customer) {
        itemView.textViewName.text = item.getName()
        itemView.textViewPassword.text = item.getAddress()
        itemView.textViewPrivilege.text = "Telefono: ${item.getPhone()}"
        itemView.textViewCity.text = "Ciudad: ${item.getCity()}"
        itemView.buttonEditCustomer.setOnClickListener{
            FragmentData.updateCustomer(item,true,it)
        }
        itemView.buttonDeleteCustomer.setOnClickListener{
            FragmentData.deleteCustomer(item,itemView.context)
        }
    }
}