package com.example.stockmanagementsym.presentation.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.presentation.adapter.CartAdapter
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Sale
import com.example.stockmanagementsym.data.CartObject
import com.example.stockmanagementsym.data.Data
import kotlinx.android.synthetic.main.dialog_new_sale.view.*
import kotlinx.android.synthetic.main.fragment_cart.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CartFragment : Fragment(), ListListener, View.OnClickListener {

    private lateinit var adapter: CartAdapter
    private lateinit var navController: NavController
    private lateinit var customer:Customer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = CartAdapter(CartObject.getList(), this)
        adapter.notifyDataSetChanged()

        recyclerViewCart.adapter = adapter
        recyclerViewCart.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

        navController = Navigation.findNavController(view)
        buttonBackHome.setOnClickListener(this)
        buttonProductList.setOnClickListener(this)
        buttonNewSale.setOnClickListener(this)

    }

    override fun reloadList() {
        adapter.listProducts = CartObject.getList()
        textViewTotal.text = "Total: ${CartObject.getTotalPrice()}"
        adapter.notifyDataSetChanged()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonBackHome -> navController.navigate(R.id.action_cart_to_home)
            R.id.buttonProductList -> navController.navigate(R.id.action_cart_to_productsList)
            R.id.buttonNewSale -> alertNewSale(view)
        }
    }

    private fun alertNewSale(view: View) {

        val builder = AlertDialog.Builder(view.context)
        builder.setTitle(getString(R.string.titleAlertNewSale))
        val message = getString(R.string.messageAlertNewSale) +
                "\n" + CartObject.getList()
        builder.setPositiveButton("Si") { _, _ ->
            dialogNewSale(view)
        }
        builder.setNegativeButton("No") { _, _ ->
            Toast.makeText(view.context, "Modifique los datos si es necesario", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setMessage(message)
        builder.create()
        builder.show()
    }

    private fun dialogNewSale(view: View) {
        val viewNewSale = layoutInflater.inflate(R.layout.dialog_new_sale, null, false)
        var dialog: Dialog = Dialog(view.context)
        var date:Calendar = Calendar.getInstance()

        dialog.setContentView(viewNewSale)
        dialog.show()
        viewNewSale.buttonNewCustomer.setOnClickListener{
            Data.getCustomerList()
            var customerListFragment = CustomerListFragment()
            customerListFragment.dialogNewCustomer(viewNewSale,true)
            setCustomer(customerListFragment.getCustomer())
            showCustomerName(viewNewSale,customer)
        }
        viewNewSale.buttonSelectCustomerName.setOnClickListener{
            selectCustomer(viewNewSale)
        }
        viewNewSale.buttonDate.setOnClickListener {
            date = getDate(viewNewSale)
        }
        viewNewSale.buttonNewSale.setOnClickListener {
            Log.d("PRUEBA", "buttonNewSale"+customer.getName())
            newSale(view, getCustomer(), date)
            dialog.dismiss()
        }
        viewNewSale.buttonNewSaleCancel.setOnClickListener {
            dialog.dismiss()
            navController.navigate(R.id.action_cart_to_productsList)
        }
    }

    private fun getDate(view: View):Calendar{
        var date = Calendar.getInstance()
        val builder = DatePickerDialog(view.context, { _, yy, mm, dd ->
            date.set(yy,mm,dd)
            val df: DateFormat = SimpleDateFormat("dd-MMMM-yy")
            view.textViewDateSelected.text = getString(R.string.date)+": "+df.format(date.time)
        }, 2020, 9, 1)
        builder.show()
        return date
    }

    private fun selectCustomer(view: View){
        val builder=AlertDialog.Builder(view.context)
        val data =Data.getCustomerList().map {
            it.getName()+" "+
            it.getAddress()+" "+
            it.getPhone()+" "+
            it.getCity()
        }
        builder.setItems(data.toTypedArray()){ _, item ->
            setCustomer(Data.getCustomerList().get(item))
            showCustomerName(view, customer)
        }
        builder.create()
        builder.show()
    }

    private fun showCustomerName(view: View,customer: Customer) {
        view.textViewSaleCustomerNameSelected.text = getCustomer().getName()
    }

    private fun newSale(view: View, customer: Customer, date: Calendar) {
        try {
            Data.addSale(
                Sale(
                    customer, date, CartObject.getList()
                )
            )
            CartObject.clearCart()
            Toast.makeText(view.context, "Venta registrada con exito", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(view.context, "Ingrese datos correctos", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setCustomer(customer: Customer){
        this.customer = customer
    }
    private fun getCustomer():Customer{
        return customer
    }
}
