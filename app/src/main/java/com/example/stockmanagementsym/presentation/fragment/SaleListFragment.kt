package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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

    private var saleList: MutableList<Sale> = mutableListOf()
    private var adapter:SaleListAdapter = SaleListAdapter(saleList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sale_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.saleList)
        adapter

        recyclerViewSaleList.adapter = adapter
        recyclerViewSaleList.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

        reloadList()

        buttonSaleListToSearch.setOnClickListener(AndroidController)
    }

    override fun reloadList() {
        GlobalScope.launch(Dispatchers.IO){
            adapter.setSaleList(FragmentData.getSaleList())
            requireActivity().runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun setList(list: MutableList<Any>) {
        GlobalScope.launch(Dispatchers.IO){
            adapter.setSaleList(list as MutableList<Sale>)
            requireActivity().runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun addElementsToList(mutableList: MutableList<Any>) {
        GlobalScope.launch(Dispatchers.IO){
            adapter.getSaleList().addAll(mutableList as MutableList<Sale>)
            requireActivity().runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

}
