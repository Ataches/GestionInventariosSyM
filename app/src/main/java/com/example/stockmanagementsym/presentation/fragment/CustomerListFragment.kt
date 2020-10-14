package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.data.Data
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.presentation.Controller
import com.example.stockmanagementsym.presentation.FragmentData
import com.example.stockmanagementsym.presentation.Model
import com.example.stockmanagementsym.presentation.adapter.CustomerListAdapter
import kotlinx.android.synthetic.main.fragment_customer_list.*


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
        adapter = CustomerListAdapter(Data.getCustomerList())
        recyclerViewCustomerList.adapter = adapter
        recyclerViewCustomerList.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        FragmentData.setCustomerListListener(this)

        buttonCustomerToSearch.setOnClickListener(Controller)
        buttonCustomerListToHome.setOnClickListener(Controller)
        buttonCustomerListToCreateCustomer.setOnClickListener(Controller)
    }

    override fun onResume() {
        super.onResume()
        reloadList()
    }

    override fun reloadList() {
        adapter.customerList = Model.getCustomerList()
        adapter.notifyDataSetChanged()
    }

    override fun setList(list: MutableList<Any>) {
        adapter.customerList = list as MutableList<Customer>
        adapter.notifyDataSetChanged()
    }

}
