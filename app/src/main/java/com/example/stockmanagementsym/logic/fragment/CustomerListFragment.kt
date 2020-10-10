package com.example.stockmanagementsym.logic.fragment

import android.app.Dialog
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
import com.example.stockmanagementsym.data.Customer
import com.example.stockmanagementsym.data.Data
import com.example.stockmanagementsym.logic.adapter.CustomerListAdapter
import kotlinx.android.synthetic.main.dialog_new_customer.view.*
import kotlinx.android.synthetic.main.fragment_customer_list.*


class CustomerListFragment : Fragment(), View.OnClickListener {

    private lateinit var navController: NavController
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

    override fun onClick(view: View) {
        when(view.id){
            R.id.buttonBackHome -> navController.navigate(R.id.action_customerListFragment_to_home)
            R.id.buttonCreateCustomer -> dialogNewCustomer(view)
        }
    }

    private fun dialogNewCustomer(view: View) {
        val viewNewCustomer = layoutInflater.inflate(R.layout.dialog_new_customer, null, false)
        var dialog: Dialog = Dialog(view.context)

        dialog.setContentView(viewNewCustomer)
        dialog.show()

        viewNewCustomer.buttonNewProduct.setOnClickListener {
            val dataProduct = mapOf(
                "Name" to viewNewCustomer.editTextCustomerName.text.toString(),
                "Address" to viewNewCustomer.editTextCustomerAddress.text.toString(),
                "Phone" to viewNewCustomer.editTextPhone.text.toString(),
                "City" to viewNewCustomer.editTextCity.text.toString()
            )
            newCustomer(view, dataProduct)
            dialog.dismiss()
        }
        viewNewCustomer.buttonNewProductCancel.setOnClickListener {
            dialog.dismiss()
        }
    }
    private fun returnView(view: View){
        val viewNewCustomer = layoutInflater.inflate(R.layout.fragment_customer_list, null)

    }
    private fun newCustomer(view: View, dataProduct: Map<String, String>) {
        try{
            Data.addCustomer(
                Customer(
                    dataProduct.getValue("Name"), dataProduct.getValue("Address"),
                    dataProduct.getValue("Phone"), dataProduct.getValue("City")
                )
            )
            adapter.notifyDataSetChanged()
            Toast.makeText(view.context, "Cliente registrado con exito", Toast.LENGTH_SHORT).show()
        }catch (e: Exception){
            Toast.makeText(view.context, "Ingrese datos correctos", Toast.LENGTH_SHORT).show()
        }
    }
}
