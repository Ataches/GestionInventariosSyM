package com.example.gestioninventariossym

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.gestioninventariossym.logica.ProductsList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        textViewWelcome.text = getString(R.string.welcome)+" "+intent.getStringExtra("userName")
        buttonProductList.setOnClickListener{
            var intent = Intent(this, ProductsList::class.java)
            startActivity(intent)
        }
    }
    fun startProductList(view: View){
        var intent = Intent(view.context, ProductsList::class.java)
        startActivity(intent)
    }

    fun startSalesRegistered(view:View){
        var intent = Intent(this, ProductsList::class.java)
        startActivity(intent)
    }
    fun startCustomersList(view:View){
        var intent = Intent(this, ProductsList::class.java)
        startActivity(intent)
    }
    private fun showHome() {
        constraintLayoutHome.setVisibility(View.INVISIBLE)
    }
}