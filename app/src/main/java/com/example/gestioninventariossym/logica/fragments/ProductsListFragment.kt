package com.example.gestioninventariossym.logica.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestioninventariossym.datos.Product
import com.example.gestioninventariossym.logica.adapter.ProductsListAdapter
import com.example.gestioninventariossym.R
import kotlinx.android.synthetic.main.fragment_products.*

class ProductsListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var listProducts : List<Product> = listOf(
            Product("Inyector Peugeot", 75000, "Inyector para Peugeot", R.drawable.ic_login, 0),
            Product("Inyector B2600", 120000, "Inyector para B2600", R.drawable.ic_login, 0),
            Product("Inyector Sprint", 125000, "Inyector para Sprint", R.drawable.ic_login, 0)
        )
        var adapter = ProductsListAdapter(listProducts)
        recyclerViewProductList.adapter = adapter
        recyclerViewProductList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
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
}