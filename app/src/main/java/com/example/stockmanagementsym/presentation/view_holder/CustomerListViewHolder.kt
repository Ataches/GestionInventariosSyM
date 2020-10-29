package com.example.stockmanagementsym.presentation.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.item_customer.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync

class CustomerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Customer) {
        itemView.textViewName.text = item.getName()
        itemView.textViewPassword.text = item.getAddress()
        itemView.textViewPrivilege.text = "Telefono: ${item.getPhone()}"
        itemView.textViewCity.text = "Ciudad: ${item.getCity()}"
        itemView.buttonEditCustomer.setOnClickListener{
            doAsync {
                FragmentData.updateCustomer(item,true,it)
                FragmentData.reloadCustomerList()
            }
        }
        itemView.buttonDeleteCustomer.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO){
                FragmentData.deleteCustomer(item)
            }
        }
    }
}