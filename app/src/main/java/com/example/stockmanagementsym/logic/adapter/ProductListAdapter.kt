package com.example.stocmanagementsym.logic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stocmanagementsym.data.Product
import com.example.stocmanagementsym.R


class ProductsListAdapter(var listProducts: List<Product>): RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(listProducts[position])
    }

    override fun getItemCount(): Int {
        return listProducts.size
    }
}