package com.example.stockmanagementsym.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.presentation.view_holder.SaleListViewHolder
import com.example.stockmanagementsym.logic.business.Sale


class SaleListAdapter(var salesList: List<Sale>): RecyclerView.Adapter<SaleListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleListViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_sale, parent, false)
        return SaleListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SaleListViewHolder, position: Int) {
        holder.bind(salesList[position])
    }

    override fun getItemCount(): Int {
        return salesList.size
    }
}