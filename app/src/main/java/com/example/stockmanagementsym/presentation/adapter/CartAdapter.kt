package com.example.stockmanagementsym.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.view_holder.CartViewHolder

class CartAdapter(private var cartList: MutableList<Product>) :
    RecyclerView.Adapter<CartViewHolder>() {

    private var productList: List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view,this)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val productCart = cartList[position]
        holder.setProductOriginal(productList.filter { it.idProduct == productCart.idProduct }[0])
        holder.bind(productCart)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    fun setCartList(cartList: MutableList<Product>){
        this.cartList = cartList
    }

    fun getCartList(): MutableList<Product> {
        return cartList
    }

    fun setProductList(productList: List<Product>) {
        this.productList = productList
    }

}