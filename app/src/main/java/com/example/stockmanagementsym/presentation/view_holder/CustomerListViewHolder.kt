package com.example.stockmanagementsym.presentation.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.logic.CustomerLogic
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.presentation.fragment.CustomerListFragment
import kotlinx.android.synthetic.main.item_customer.view.*

class CustomerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var customerLogic:CustomerLogic
    private lateinit var customerListFragment:CustomerListFragment
    private var customerLogicCreated: Boolean = false
    private var customerListFragmentCreated: Boolean = false
    
    fun bind(item: Customer) {
        itemView.textViewName.text = item.getName()
        itemView.textViewAddress.text = item.getAddress()
        itemView.textViewPhone.text = "Telefono: ${item.getPhone()}"
        itemView.textViewCity.text = "Ciudad: ${item.getCity()}"
        //itemView.buttonCart.setBackgroundDrawable(itemView.context.resources.getDrawable(item.getIDiconDrawable()))
        itemView.buttonEditCustomer.setOnClickListener{
            getCustomerLogic().setCustomerToEdit(item)
            getCustomerListFragment().setView(this)
            getCustomerListFragment().dialogNewCustomer(itemView,false)
        }
    }
    fun getCustomerLogic():CustomerLogic{
        if(customerLogicCreated)
            return customerLogic
        customerLogic = CustomerLogic()
        customerLogicCreated = true
        return customerLogic
    }
    fun getCustomerListFragment():CustomerListFragment{
        if(customerListFragmentCreated)
            return customerListFragment
        customerListFragment = CustomerListFragment()
        customerListFragmentCreated = true
        return customerListFragment
    }
}