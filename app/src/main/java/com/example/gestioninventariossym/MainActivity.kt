package com.example.gestioninventariossym

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.gestioninventariossym.logica.fragments.ProductsListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        textViewWelcome.text = getString(R.string.welcome)+" "+intent.getStringExtra("userName")
    }
    fun startProductList(view: View){
        var transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, ProductsListFragment())
        transaction.commit()
        showHome()
    }

    fun startSalesRegistered(view:View){
        var transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, ProductsListFragment())
        transaction.commit()
    }
    fun startCustomersList(view:View){
        var transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, ProductsListFragment())
        transaction.commit()
    }
    private fun showHome() {
        constraintLayoutHome.setVisibility(View.INVISIBLE)
    }
    fun goToCart(view: View) {}
}