package com.example.stockmanagementsym.presentation.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.presentation.Controller
import com.example.stockmanagementsym.presentation.FragmentData
import kotlinx.android.synthetic.main.item_product.view.textViewName
import kotlinx.android.synthetic.main.item_sale.view.*

class SaleListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Sale) {
        itemView.textViewName.text = item.getCustomer().getName()
        itemView.textViewAddress.text  = item.getCustomer().getAddress()
        itemView.textViewPhone.text = item.getCustomer().getPhone()
        itemView.textViewCity.text = item.getCustomer().getCity()
        itemView.textViewDateSale.text = itemView.context.getString(R.string.saleDate)+" "+FragmentData.getDate(item.getDate())
        itemView.buttonProductListSale.setOnClickListener(Controller)
    }
}