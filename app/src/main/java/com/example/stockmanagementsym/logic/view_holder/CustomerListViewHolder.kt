package com.example.stockmanagementsym.logic.view_holder

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.data.Customer
import com.example.stockmanagementsym.data.Data
import kotlinx.android.synthetic.main.item_customer.view.*

class CustomerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Customer) {
        itemView.textViewName.text = item.getName()
        itemView.textViewAddress.text = item.getAddress()
        itemView.textViewPhone.text = "Telefono: ${item.getPhone()}"
        itemView.textViewCity.text = "Ciudad: ${item.getCity()}"
        //itemView.buttonCart.setBackgroundDrawable(itemView.context.resources.getDrawable(item.getIDiconDrawable()))
        itemView.buttonEditCustomer.setOnClickListener{
            try{
                Data.editCustomer()
            }catch (e:Exception){
                Toast.makeText(it.context, "Ingrese datos correctos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}