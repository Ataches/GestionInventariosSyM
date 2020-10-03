package com.example.stockmanagementsym.logic

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockmanagementsym.MainActivity
import com.example.stockmanagementsym.data.Product
import com.example.stockmanagementsym.logic.adapter.ProductsListAdapter
import com.example.stockmanagementsym.R
import kotlinx.android.synthetic.main.activty_products.*

class ProductsList : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activty_products)

        var listProducts : List<Product> = listOf(
            Product("Inyector Peugeot", 75000, "Inyector para Peugeot", R.drawable.ic_login, 0),
            Product("Inyector B2600", 120000, "Inyector para B2600", R.drawable.ic_login, 0),
            Product("Inyector Sprint", 125000, "Inyector para Sprint", R.drawable.ic_login, 0)
        )
        var adapter = ProductsListAdapter(listProducts)
        recyclerViewProductList.adapter = adapter
        recyclerViewProductList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        buttonSearch.setOnClickListener{
            var searchText = editTextSearch.text.toString()
            var filteredList = mutableListOf<Product>()
            for(product in listProducts){
                if(product.getNombre().toLowerCase().contains(searchText.toLowerCase()))
                    filteredList.add(product)
            }
            adapter.listProducts = filteredList
            adapter.notifyDataSetChanged()

        }

    }

    fun goToCart(view: View) {
        var intent = Intent(view.context, Cart::class.java)
        startActivity(intent)
    }
    fun goToHome(view: View) {
        var intent = Intent(view.context, MainActivity::class.java)
        startActivity(intent)
    }
}