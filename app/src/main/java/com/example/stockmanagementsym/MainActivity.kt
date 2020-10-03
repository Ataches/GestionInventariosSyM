package com.example.stockmanagementsym

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.stockmanagementsym.logic.ProductsList
import com.example.stockmanagementsym.logic.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        textViewWelcome.text = getString(R.string.welcome)+" "+ User.getUser()
    }
    fun goToProductList(view: View){
        var intent = Intent(view.context, ProductsList::class.java)
        startActivity(intent)
    }
    fun goToSalesRegistered(view: View) {}
    fun goToCustomersList(view: View) {}
}