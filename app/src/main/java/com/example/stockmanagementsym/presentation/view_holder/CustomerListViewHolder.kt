package com.example.stockmanagementsym.presentation.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.item_customer.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CustomerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Customer) {
        itemView.textViewName.text = item.getName()
        itemView.textViewAddress.text = item.getAddress()
        itemView.textViewPhone.text = "Telefono: ${item.getPhone()}"
        itemView.textViewCity.text = "Ciudad: ${item.getCity()}"
        //itemView.buttonCart.setBackgroundDrawable(itemView.context.resources.getDrawable(item.getIDiconDrawable()))
        itemView.buttonEditCustomer.setOnClickListener{
            FragmentData.setCustomerToEdit(item)
            FragmentData.setBooleanUpdate(true)
            FragmentData.goToNewCustomer(it)
        }
        itemView.buttonDeleteCustomer.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO){
                FragmentData.deleteCustomer(item)
            }
        }
    }
}