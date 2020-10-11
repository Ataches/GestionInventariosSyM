package com.example.stockmanagementsym.logic.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.adapter.SaleListAdapter
import com.example.stockmanagementsym.model.data.Data
import kotlinx.android.synthetic.main.fragment_sales_registered.*

class SaleRegisteredFragment : Fragment(), View.OnClickListener {

    private lateinit var navController: NavController
    private lateinit var adapter:SaleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sales_registered, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SaleListAdapter(Data.getSalesList())
        recyclerViewSaleList.adapter = adapter
        recyclerViewSaleList.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        buttonSearch.setOnClickListener(this)
        buttonBackHome.setOnClickListener (this)
        navController = Navigation.findNavController(view)
    }

    override fun onResume() {
        super.onResume()
        adapter.salesList = Data.getSalesList()
        adapter.notifyDataSetChanged()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.buttonSearch -> searchProduct()
            R.id.buttonBackHome -> navController.navigate(R.id.action_salesRegistered_to_home)
        }
    }
    private fun searchProduct(){
        var searchText = editTextSearch.text.toString()
        var filteredList = Data.getSalesList().filter { product -> product.getCustomer().getName().toLowerCase().contains(searchText.toLowerCase())}
        adapter.salesList = filteredList
        adapter.notifyDataSetChanged()
    }
}
