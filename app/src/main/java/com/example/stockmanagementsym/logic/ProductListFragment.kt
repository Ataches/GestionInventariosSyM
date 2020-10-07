package com.example.stockmanagementsym.logic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockmanagementsym.data.Product
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.data.Data
import com.example.stockmanagementsym.logic.adapter.ProductsListAdapter
import kotlinx.android.synthetic.main.fragment_product_list.*

class ProductsListFragment : Fragment(){

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var adapter = ProductsListAdapter(Data.getProductList())
        recyclerViewProductList.adapter = adapter
        recyclerViewProductList.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        buttonSearch.setOnClickListener{
            var searchText = editTextSearch.text.toString()
            var filteredList = mutableListOf<Product>()
            for(product in Data.getProductList()){
                if(product.getName().toLowerCase().contains(searchText.toLowerCase()))
                    filteredList.add(product)
            }
            adapter.listProducts = filteredList
            adapter.notifyDataSetChanged()

        }
        navController = Navigation.findNavController(view)
        buttonBackHome.setOnClickListener {
            goToHome()
        }
        buttonCart.setOnClickListener {
            goToCart()
        }
    }

    private fun goToHome(){
        navController.navigate(R.id.action_productsList_to_home)
    }
    private fun goToCart() {
        navController.navigate(R.id.action_productsList_to_cart)
    }
}
