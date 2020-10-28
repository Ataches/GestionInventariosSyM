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
import com.example.stockmanagementsym.presentation.adapter.ProductListAdapter
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.fragment_product_list.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class ProductListFragment : Fragment(), ListListener{

    private var productList: MutableList<Product> = mutableListOf()
    private var adapter: ProductListAdapter = ProductListAdapter(productList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view.recyclerViewProductList.adapter = adapter
        view.recyclerViewProductList.layoutManager = GridLayoutManager(view.context, 2)

        FragmentData.setProductListListener(this)
        reloadList()
        FragmentData.loadProductListFromREST(view)
        view.buttonProductListToSearch.setOnClickListener(AndroidController)
        view.buttonProductListToNewProduct.setOnClickListener(AndroidController)
    }

    override fun reloadList() {
        GlobalScope.launch(Dispatchers.IO){
            adapter.setProductList(FragmentData.getProductList())
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

    override fun addElementsToList(mutableList: MutableList<Any>) {
        GlobalScope.launch(Dispatchers.IO){
            adapter.getProductList().addAll(mutableList as MutableList<Product>)
            requireActivity().runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }
}
