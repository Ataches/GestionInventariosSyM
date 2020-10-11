package com.example.stockmanagementsym.logic.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.ListListener
import com.example.stockmanagementsym.logic.adapter.ProductsListAdapter
import com.example.stockmanagementsym.model.data.Data
import kotlinx.android.synthetic.main.fragment_product_list.*

class ProductListFragment : Fragment(), ListListener, View.OnClickListener {
    private lateinit var navController: NavController
    private lateinit var adapter:ProductsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ProductsListAdapter(Data.getProductList())
        recyclerViewProductList.adapter = adapter
        recyclerViewProductList.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        buttonSearch.setOnClickListener(this)
        navController = Navigation.findNavController(view)
        buttonBackHome.setOnClickListener (this)
        buttonCart.setOnClickListener (this)
        buttonNewProduct.setOnClickListener (this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.buttonSearch -> searchProduct()
            R.id.buttonBackHome -> navController.navigate(R.id.action_productsList_to_home)
            R.id.buttonCart -> navController.navigate(R.id.action_productsList_to_cart)
            R.id.buttonNewProduct -> {
                var transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.nav_host_fragment, NewProductFragment(this))
                transaction.commit()
            }
        }
    }
    private fun searchProduct(){
        var searchText = editTextSearch.text.toString()
        var filteredList = Data.getProductList().filter {product -> product.getName().toLowerCase().contains(searchText.toLowerCase())}
        adapter.listProducts = filteredList
        adapter.notifyDataSetChanged()
    }
    override fun reloadList() {
        adapter.listProducts = Data.getProductList()
        adapter.notifyDataSetChanged()
    }
}
