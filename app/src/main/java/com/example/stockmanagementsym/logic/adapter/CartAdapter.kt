package com.example.stockmanagementsym.logic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.data.Product
import com.example.stockmanagementsym.logic.ListListener
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.view_holder.CartViewHolder

class CartAdapter(var listProducts: List<Product>, var listener: ListListener): RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(listProducts[position], listener)
    }

    override fun getItemCount(): Int {
        return listProducts.size
    }
}