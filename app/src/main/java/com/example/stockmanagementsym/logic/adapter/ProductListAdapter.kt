<<<<<<< HEAD:app/src/main/java/com/example/stockmanagementsym/logic/adapter/ProductListAdapter.kt
package com.example.stockmanagementsym.logic.adapter
=======
package com.example.stocmanagementsym.logic.adapter
>>>>>>> temp:app/src/main/java/com/example/gestioninventariossym/logica/adapter/ProductsListAdapter.kt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
<<<<<<< HEAD:app/src/main/java/com/example/stockmanagementsym/logic/adapter/ProductListAdapter.kt
import com.example.stockmanagementsym.data.Product
import com.example.stockmanagementsym.R
=======
import com.example.stocmanagementsym.data.Product
import com.example.stocmanagementsym.R
>>>>>>> temp:app/src/main/java/com/example/gestioninventariossym/logica/adapter/ProductsListAdapter.kt


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