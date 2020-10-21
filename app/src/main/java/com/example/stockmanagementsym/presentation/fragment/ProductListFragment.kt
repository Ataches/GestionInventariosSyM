package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.adapter.ProductsListAdapter
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ProductListFragment : Fragment(), ListListener{

    private var productList: List<Product> = listOf()
    private var adapter:ProductsListAdapter = ProductsListAdapter(productList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerViewProductList.adapter = adapter
        recyclerViewProductList.layoutManager = GridLayoutManager(view.context, 2)

        reloadList()

        FragmentData.setProductListListener(this)

        buttonProductListToSearch.setOnClickListener(AndroidController)
        buttonProductListToHome.setOnClickListener(AndroidController)
        buttonAddProductToCart.setOnClickListener(AndroidController)
        buttonProductListToNewProduct.setOnClickListener(AndroidController)
    }

    override fun reloadList() {
        GlobalScope.launch(Dispatchers.IO){
            adapter.setProductList(getProductList())
            requireActivity().runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun setList(list: MutableList<Any>) {
        GlobalScope.launch(Dispatchers.IO){
            adapter.setProductList(list as MutableList<Product>)
            requireActivity().runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

    private suspend fun getProductList(): List<Product> {
        return FragmentData.getProductList()
    }
}
