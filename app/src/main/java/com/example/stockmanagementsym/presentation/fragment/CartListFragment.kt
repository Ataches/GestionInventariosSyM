package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.adapter.CartAdapter
import kotlinx.android.synthetic.main.fragment_cart.*

interface ICart {
    fun setProductList(mutableList: MutableList<Any>)
}

class CartListFragment : Fragment(), IListListener, ICart {

    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = CartAdapter(FragmentData.getCartList())

        recyclerViewCart.adapter = adapter
        recyclerViewCart.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

        FragmentData.notifyCartLogic(this, this)

        buttonCartToNewSale.setOnClickListener(FragmentData.getController())
    }

    override fun setProductList(mutableList: MutableList<Any>) {
        adapter.setProductList(mutableList as MutableList<Product>)
    }

    override fun reloadList(mutableList: MutableList<Any>) {
        adapter.setProductList(FragmentData.getProductList() as MutableList<Product>)
        adapter.setCartList(FragmentData.getCartList())
        requireActivity().runOnUiThread {
            textViewTotal.text = "Total: ${FragmentData.getTotalPriceCart()}"
            adapter.notifyDataSetChanged()
        }
    }

    override fun addElementsToList(mutableList: MutableList<Any>) {
        adapter.getCartList().addAll(mutableList as MutableList<Product>)
        requireActivity().runOnUiThread {
            adapter.notifyDataSetChanged()
        }
    }
}

