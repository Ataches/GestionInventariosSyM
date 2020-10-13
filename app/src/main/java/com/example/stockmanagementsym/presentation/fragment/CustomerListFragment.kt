package com.example.stockmanagementsym.presentation.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.data.Data
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.presentation.adapter.CustomerListAdapter
import com.example.stockmanagementsym.presentation.view_holder.CustomerListViewHolder
import kotlinx.android.synthetic.main.dialog_new_customer.view.*
import kotlinx.android.synthetic.main.fragment_customer_list.*


class CustomerListFragment : Fragment(), View.OnClickListener {

    private lateinit var navController: NavController
    private lateinit var adapter:CustomerListAdapter
    private lateinit var customer:Customer
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
        buttonSearch.setOnClickListener{
            var searchText = editTextSearch.text.toString().toLowerCase()
            var filteredList = Data.getCustomerList().filter{ item -> item.getName().toLowerCase().contains(
                searchText
            )
            }
            adapter.customerList = filteredList
            adapter.notifyDataSetChanged()
        }

        navController = Navigation.findNavController(view)

        buttonBackHome.setOnClickListener(this)
        buttonCreateCustomer.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        adapter.customerList = Data.getCustomerList()
        adapter.notifyDataSetChanged()
    }
    override fun onClick(view: View) {
        when(view.id){
            R.id.buttonBackHome -> navController.navigate(R.id.action_customerListFragment_to_home)
            R.id.buttonCreateCustomer -> {
                dialogNewCustomer(view,true)
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun dialogNewCustomer(view: View, insert:Boolean) {
        var layoutInflater = view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewNewCustomer = layoutInflater.inflate(R.layout.dialog_new_customer, null, false)
        var dialog: Dialog = Dialog(view.context)

        dialog.setContentView(viewNewCustomer)
        dialog.show()

        viewNewCustomer.buttonNewProduct.setOnClickListener {

            setCustomer(Customer(
                viewNewCustomer.editTextCustomerName.text.toString(),
                viewNewCustomer.editTextCustomerAddress.text.toString(),
                viewNewCustomer.editTextPhone.text.toString(),
                viewNewCustomer.editTextCity.text.toString()
            ))

            try{
                if(insert) {
                    Data.addCustomer(customer)
                    Toast.makeText(view.context, "Cliente registrado con exito", Toast.LENGTH_SHORT).show()
                }else{
                    customerListViewHolder.getCustomerLogic().setCustomerEdited(getCustomer())
                    customerListViewHolder.getCustomerLogic().editCustomer()
                }
            }catch (e: Exception){
                Toast.makeText(view.context, "Ingrese datos correctos", Toast.LENGTH_SHORT).show()
            }

            dialog.dismiss()
        }
        viewNewCustomer.buttonNewProductCancel.setOnClickListener {
            dialog.dismiss()
        }
    }
    fun setCustomer(customer: Customer){
        this.customer = customer
    }
    fun getCustomer():Customer{
        return customer
    }
    private lateinit var customerListViewHolder: CustomerListViewHolder

    fun setView(customerListViewHolder: CustomerListViewHolder) {
        this.customerListViewHolder = customerListViewHolder
    }

}
