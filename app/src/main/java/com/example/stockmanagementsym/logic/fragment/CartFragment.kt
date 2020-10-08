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
import com.example.stockmanagementsym.data.CartObject
import com.example.stockmanagementsym.logic.adapter.CartAdapter
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : Fragment(), ListListener, View.OnClickListener {

    private lateinit var adapter:CartAdapter
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = CartAdapter(CartObject.getList(), this)
        adapter.notifyDataSetChanged()

        recyclerViewCart.adapter = adapter
        recyclerViewCart.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

        navController = Navigation.findNavController(view)
        buttonBackHome.setOnClickListener (this)
        buttonProductList.setOnClickListener (this)

    }
    
    override fun reloadList() {
        adapter.listProducts = CartObject.getList()
        textViewTotal.text = "Total: ${CartObject.getTotalPrice()}"
        adapter.notifyDataSetChanged()
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.buttonBackHome -> navController.navigate(R.id.action_cart_to_home)
            R.id.buttonProductList -> navController.navigate(R.id.action_cart_to_productsList)
        }
    }
}
