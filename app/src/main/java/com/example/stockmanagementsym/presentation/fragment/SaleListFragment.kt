package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.presentation.adapter.SaleListAdapter
import com.example.stockmanagementsym.data.Data
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.presentation.Controller
import com.example.stockmanagementsym.presentation.Model
import kotlinx.android.synthetic.main.fragment_sale_list.*

class SaleListFragment : Fragment(), ListListener {

    private lateinit var adapter:SaleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sale_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SaleListAdapter(Data.getSalesList())

        recyclerViewSaleList.adapter = adapter
        recyclerViewSaleList.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

        buttonSaleListToSearch.setOnClickListener(Controller)
        buttonSaleListToHome.setOnClickListener (Controller)
    }

    override fun onResume() {
        super.onResume()
        reloadList()
    }

    override fun reloadList() {
        adapter.salesList = Model.getSalesList()
        adapter.notifyDataSetChanged()
    }

    override fun setList(list: MutableList<Any>) {
        adapter.salesList = list as MutableList<Sale>
        adapter.notifyDataSetChanged()
    }
}
