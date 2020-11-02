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
import com.example.stockmanagementsym.presentation.adapter.SaleListAdapter
import kotlinx.android.synthetic.main.fragment_sale_list.*

class SaleListFragment : Fragment(), IListListener {

    private var saleList: MutableList<Sale> = mutableListOf()
    private var adapter: SaleListAdapter = SaleListAdapter(saleList)

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

        recyclerViewSaleList.adapter = adapter
        recyclerViewSaleList.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

        FragmentData.notifySaleLogic(this)

        buttonSaleListToSearch.setOnClickListener(FragmentData.getController())
    }

    override fun reloadList(mutableList: MutableList<Any>) {
        adapter.setSaleList(mutableList as MutableList<Sale>)
        requireActivity().runOnUiThread {
            adapter.notifyDataSetChanged()
        }
    }

    override fun addElementsToList(mutableList: MutableList<Any>) {
        adapter.getSaleList().addAll(mutableList as MutableList<Sale>)
        requireActivity().runOnUiThread {
            adapter.notifyDataSetChanged()
        }
    }

}
