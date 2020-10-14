package com.example.stockmanagementsym.presentation

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.findFragment
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.fragment.NewProductFragment
import kotlinx.android.synthetic.main.dialog_new_customer.view.*
import kotlinx.android.synthetic.main.dialog_new_sale.view.*
import kotlinx.android.synthetic.main.fragment_new_product.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DialogObject {

    private lateinit var layoutInflater:LayoutInflater

    fun dialogNewCustomer(view: View, insert:Boolean) {
        layoutInflater = view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewNewCustomer = layoutInflater.inflate(R.layout.dialog_new_customer, null, false)
        var dialog: Dialog = Dialog(view.context)

        dialog.setContentView(viewNewCustomer)
        dialog.show()

        viewNewCustomer.buttonProductListToNewProduct.setOnClickListener {

            Model.setCustomerNewSale(
                Customer(
                    viewNewCustomer.editTextCustomerName.text.toString(),
                    viewNewCustomer.editTextCustomerAddress.text.toString(),
                    viewNewCustomer.editTextPhone.text.toString(),
                    viewNewCustomer.editTextCity.text.toString()
                )
            )
            try{
                if(insert) {
                    Model.createNewCustomer()
                    showCustomerName(view,Model.getCustomerNewSale())
                    Toast.makeText(view.context, "Cliente registrado con exito", Toast.LENGTH_SHORT).show()
                }else{
                    Model.editCustomer()
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
    fun alertNewSale(view: View) {

        val builder = AlertDialog.Builder(view.context)
        builder.setTitle(view.context.getString(R.string.titleAlertNewSale))
        val message = view.context.getString(R.string.messageAlertNewSale) +
                "\n" + Model.getCartList()
        builder.setPositiveButton("Si") { _, _ ->
            dialogNewSale(view)
        }
        builder.setNegativeButton("No") { _, _ ->
            Toast.makeText(view.context, "Modifique los datos si es necesario", Toast.LENGTH_SHORT).show()
        }
        builder.setMessage(message)
        builder.create()
        builder.show()
    }
    fun dialogNewSale(view: View) {
        layoutInflater = view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewNewSale = layoutInflater.inflate(R.layout.dialog_new_sale, null, false)
        var dialog: Dialog = Dialog(view.context)


        dialog.setContentView(viewNewSale)
        dialog.show()

        viewNewSale.buttonNewCustomer.setOnClickListener{
            dialogNewCustomer(viewNewSale,true)
        }
        viewNewSale.buttonSelectCustomerName.setOnClickListener{
            selectCustomer(viewNewSale)
        }
        viewNewSale.buttonDate.setOnClickListener {
            Model.setDateSale(getDate(viewNewSale))
        }
        viewNewSale.buttonNewSale.setOnClickListener {
            Model.updateNewSale()
            FragmentData.reloadCartList()
            Toast.makeText(viewNewSale.context, "Venta registrada", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        viewNewSale.buttonNewSaleCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun selectCustomer(view: View){
        val builder=AlertDialog.Builder(view.context)
        val data = Model.getCustomerList().map {
                it.getName()+" "+
                it.getAddress()+" "+
                it.getPhone()+" "+
                it.getCity()
            }
        builder.setItems(data.toTypedArray()){ _, item ->
            Model.setCustomerNewSale(Model.getCustomerList().get(item))
            showCustomerName(view, Model.getCustomerNewSale())
        }
        builder.create()
        builder.show()
    }


    private fun getDate(view: View):Calendar{
        var date = Calendar.getInstance()
        val builder = DatePickerDialog(view.context, { _, yy, mm, dd ->
                date.set(yy,mm,dd)
                val df: DateFormat = SimpleDateFormat("dd-MMMM-yy")
                view.textViewDateSelected.text = view.context.getString(R.string.date)+": "+df.format(date.time)
            }, 2020, 9, 1)
        builder.show()
        return date
    }

    private fun showCustomerName(view: View,customer: Customer) {
        view?.textViewSaleCustomerNameSelected?.text = customer.getName()
    }

    //New product

    fun confirmCreateProduct(viewElement:View){
        val view = viewElement.findFragment<NewProductFragment>()
        Model.setNewProduct(
            Product(
                view.editTextProductName.text.toString(),
                view.editTextProductPrice.text.toString().toInt(),
                view.editTextProductDesc.text.toString(),
                R.drawable.ic_login.toString().toInt(),
                view.editTextProductQuantity.text.toString().toInt()
            )
        )

        val builder = AlertDialog.Builder(view.context)
        builder.setTitle(view.getString(R.string.titleAlertNewProd))
        val message = view.getString(R.string.messageAlertNewProd)+
                "\n"+Model.getNewProduct()
        builder.setPositiveButton("Si"){ _,_ ->
            Model.createNewProduct()
        }
        builder.setNegativeButton("No"){ _,_ ->
            Toast.makeText(view.context, "Modifique los datos si es necesario", Toast.LENGTH_SHORT).show()
        }
        builder.setMessage(message)
        builder.create()
        builder.show()
    }

}