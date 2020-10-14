package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.data.Data
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.Controller
import com.example.stockmanagementsym.presentation.FragmentData
import com.example.stockmanagementsym.presentation.adapter.ProductsListAdapter
import kotlinx.android.synthetic.main.fragment_product_list.*

class ProductListFragment : Fragment(), ListListener{

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

        FragmentData.setProductListListener(this)
        Controller.setFragmentTransaction(parentFragmentManager.beginTransaction())

        buttonProductListToSearch.setOnClickListener(Controller)
        buttonProductListToHome.setOnClickListener (Controller)
        buttonProductListToCart.setOnClickListener (Controller)
        buttonProductListToNewProduct.setOnClickListener (Controller)
    }

    override fun onResume() {
        super.onResume()
        Controller.setFragmentTransaction(parentFragmentManager.beginTransaction())
        reloadList()
    }

    override fun reloadList() {
        adapter.productsList = Data.getProductList()
        adapter.notifyDataSetChanged()
    }

    override fun setList(list: MutableList<Any>) {
        adapter.productsList = list as MutableList<Product>
        adapter.notifyDataSetChanged()
    }
}
