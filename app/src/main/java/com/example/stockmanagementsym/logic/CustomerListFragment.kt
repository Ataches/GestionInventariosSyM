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
import com.example.stockmanagementsym.data.Data
import com.example.stockmanagementsym.logic.adapter.CustomerListAdapter
import kotlinx.android.synthetic.main.fragment_customer_list.*

class CustomerListFragment : Fragment(){

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var adapter = CustomerListAdapter(Data.getCustomerList())
        recyclerViewCustomerList.adapter = adapter
        recyclerViewCustomerList.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        buttonSearch.setOnClickListener{
            var searchText = editTextSearch.text.toString().toLowerCase()
            var filteredList = Data.getCustomerList().filter{
                item -> item.getName().toLowerCase().contains(searchText)
            }
            adapter.customerList = filteredList
            adapter.notifyDataSetChanged()
        }

        navController = Navigation.findNavController(view)
        buttonBackHome.setOnClickListener {
            goToHome()
        }
    }

    private fun goToHome(){
        navController.navigate(R.id.action_customerListFragment_to_home)
    }
}