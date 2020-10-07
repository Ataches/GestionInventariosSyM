package com.example.stockmanagementsym.logic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.adapter.CartAdapter
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : Fragment(), CartListener{

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

        reloadCart()

        navController = Navigation.findNavController(view)
        buttonBackHome.setOnClickListener {
            goToHome()
        }
        buttonProductList.setOnClickListener {
            goToProductList()
        }

    }
    
    override fun reloadCart() {
        adapter.listProducts = CartObject.getList()
        textViewTotal.text = "Total: ${CartObject?.getTotalPrice()}"
        adapter.notifyDataSetChanged()

    }

    private fun goToHome(){
        navController.navigate(R.id.action_cart_to_home)
    }

    private fun goToProductList(){
        navController.navigate(R.id.action_cart_to_productsList)
    }
}
