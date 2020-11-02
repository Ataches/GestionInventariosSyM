package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.adapter.ProductListAdapter
import kotlinx.android.synthetic.main.fragment_product_list.view.*


class ProductListFragment : Fragment(), IListListener {

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

        FragmentData.notifyProductLogic(this)

        view.buttonProductListToSearch.setOnClickListener(FragmentData.getController())
        view.buttonProductListToNewProduct.setOnClickListener(FragmentData.getController())
    }

    override fun reloadList(mutableList: MutableList<Any>) {
        adapter.setProductList(mutableList as MutableList<Product>)
        requireActivity().runOnUiThread {
            adapter.notifyDataSetChanged()
        }
    }

    override fun addElementsToList(mutableList: MutableList<Any>) {
        adapter.addElementsToProductList(mutableList as MutableList<Product>)
        requireActivity().runOnUiThread {
            adapter.notifyDataSetChanged()
        }
    }
}
