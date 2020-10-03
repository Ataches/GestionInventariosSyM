<<<<<<< HEAD:app/src/main/java/com/example/stockmanagementsym/logic/adapter/CarAdapter.kt
package com.example.stockmanagementsym.logic.adapter
=======
package com.example.stocmanagementsym.logic.adapter
>>>>>>> temp:app/src/main/java/com/example/gestioninventariossym/logica/adapter/CartAdapter.kt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
<<<<<<< HEAD:app/src/main/java/com/example/stockmanagementsym/logic/adapter/CarAdapter.kt
import com.example.stockmanagementsym.data.Product
import com.example.stockmanagementsym.logic.CartListener
import com.example.stockmanagementsym.R
=======
import com.example.stocmanagementsym.data.Product
import com.example.stocmanagementsym.logic.CartListener
import com.example.stocmanagementsym.R
>>>>>>> temp:app/src/main/java/com/example/gestioninventariossym/logica/adapter/CartAdapter.kt

class CartAdapter(var listProducts: List<Product>, var listener: CartListener): RecyclerView.Adapter<CartViewHolder>() {
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