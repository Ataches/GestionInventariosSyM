package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.adapter.CartAdapter
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartFragment : Fragment(), ListListener {

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
        adapter = CartAdapter(FragmentData.getCartList(), this,)

        recyclerViewCart.adapter = adapter
        recyclerViewCart.layoutManager =
        LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

        reloadList()

        FragmentData.setCartListener(this)

        buttonCartToNewSale.setOnClickListener(AndroidController)
    }

    override fun onResume() {
        super.onResume()
        reloadList()
    }

    override fun reloadList() {
        GlobalScope.launch(Dispatchers.IO){
            adapter.setProductList(FragmentData.getProductList())
            adapter.setCartList(FragmentData.getCartList())
            requireActivity().runOnUiThread {
                textViewTotal.text = "Total: ${FragmentData.getTotalPrice()}"
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun setList(list: MutableList<Any>) {
        adapter.setCartList(list as MutableList<Product>)
        requireActivity().runOnUiThread {
            adapter.notifyDataSetChanged()
        }
    }
}
