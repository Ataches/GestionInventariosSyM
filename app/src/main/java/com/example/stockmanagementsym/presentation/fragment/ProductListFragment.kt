package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.adapter.ProductsListAdapter
import com.example.stockmanagementsym.presentation.view.FragmentData
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
        adapter = ProductsListAdapter(FragmentData.getProductList())
        recyclerViewProductList.adapter = adapter
        recyclerViewProductList.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

        FragmentData.setProductListListener(this)

        buttonProductListToSearch.setOnClickListener(AndroidController)
        buttonProductListToHome.setOnClickListener (AndroidController)
        buttonProductListToCart.setOnClickListener (AndroidController)
        buttonProductListToNewProduct.setOnClickListener (AndroidController)
    }

    override fun onResume() {
        super.onResume()
        reloadList()
    }

    override fun reloadList() {
        adapter.productsList = FragmentData.getProductList()
        adapter.notifyDataSetChanged()
    }

    override fun setList(list: MutableList<Any>) {
        adapter.productsList = list as MutableList<Product>
        adapter.notifyDataSetChanged()
    }
}
