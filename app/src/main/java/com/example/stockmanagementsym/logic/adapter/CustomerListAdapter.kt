package com.example.stockmanagementsym.logic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.model.business.Customer
import com.example.stockmanagementsym.logic.view_holder.CustomerListViewHolder


class CustomerListAdapter(var customerList: List<Customer>): RecyclerView.Adapter<CustomerListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerListViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_customer, parent, false)
        return CustomerListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerListViewHolder, position: Int) {
        holder.bind(customerList[position])
    }

    override fun getItemCount(): Int {
        return customerList.size
    }
}