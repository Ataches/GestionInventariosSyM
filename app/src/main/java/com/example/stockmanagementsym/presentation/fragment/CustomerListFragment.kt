package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.adapter.CustomerListAdapter
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.fragment_customer_list.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CustomerListFragment : Fragment(), ListListener {

    private lateinit var adapter:CustomerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.customerList)

        adapter = CustomerListAdapter(mutableListOf())
        view.recyclerViewCustomerList.adapter = adapter
        view.recyclerViewCustomerList.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        reloadList()

        FragmentData.setCustomerListListener(this)

        view.buttonCustomerToSearch.setOnClickListener(AndroidController)
        view.buttonCustomerListToCreateCustomer.setOnClickListener(AndroidController)
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    override fun reloadList() {
        GlobalScope.launch(Dispatchers.IO){
            adapter.setCustomerList(FragmentData.getCustomerList())
            requireActivity().runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun setList(list: MutableList<Any>) {
        GlobalScope.launch(Dispatchers.IO){
            adapter.setCustomerList(list as MutableList<Customer>)
            requireActivity().runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun addElementsToList(list: MutableList<Any>) {
        GlobalScope.launch(Dispatchers.IO){
            adapter.getCustomerList().addAll(list as MutableList<Customer>)
            requireActivity().runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }
}