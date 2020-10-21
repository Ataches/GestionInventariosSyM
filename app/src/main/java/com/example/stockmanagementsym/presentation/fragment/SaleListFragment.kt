package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.adapter.SaleListAdapter
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.fragment_sale_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SaleListFragment : Fragment(), ListListener {

    private lateinit var adapter:SaleListAdapter
    private var saleList: List<Sale> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sale_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SaleListAdapter(saleList)

        recyclerViewSaleList.adapter = adapter
        recyclerViewSaleList.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

        reloadList()

        buttonSaleListToSearch.setOnClickListener(AndroidController)
        buttonSaleListToHome.setOnClickListener (AndroidController)
    }

    override fun reloadList() {
        GlobalScope.launch(Dispatchers.IO){
            adapter.salesList = getSaleList()
            requireActivity().runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun setList(list: MutableList<Any>) {
        GlobalScope.launch(Dispatchers.IO){
            adapter.salesList = list as MutableList<Sale>
            requireActivity().runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

    private suspend fun getSaleList(): List<Sale> {
        return FragmentData.getSaleList()
    }
}
