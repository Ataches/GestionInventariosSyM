package com.example.gestioninventariossym.logica.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestioninventariossym.logica.Cart
import com.example.gestioninventariossym.logica.adapter.CartAdapter
import com.example.gestioninventariossym.R
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : Fragment(), CartListener {
    private var adapter:CartAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CartAdapter(Cart.getList(), this)
        adapter?.notifyDataSetChanged()

        recyclerViewCart.adapter = adapter
        recyclerViewCart.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        reloadCart()
    }

    override fun reloadCart() {
        adapter?.listProducts = Cart.getList()
        adapter?.notifyDataSetChanged()
    }
}
