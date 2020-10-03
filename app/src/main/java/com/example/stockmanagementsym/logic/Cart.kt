<<<<<<< HEAD
package com.example.stockmanagementsym.logic
=======
package com.example.stocmanagementsym.logic
>>>>>>> temp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
<<<<<<< HEAD
import com.example.stockmanagementsym.MainActivity
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.adapter.CartAdapter
=======
import com.example.stocmanagementsym.MainActivity
import com.example.stocmanagementsym.R
import com.example.stocmanagementsym.logic.adapter.CartAdapter
>>>>>>> temp
import kotlinx.android.synthetic.main.activity_cart.*

class Cart : AppCompatActivity(), CartListener{
    private lateinit var adapter:CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_cart)

        adapter = CartAdapter(CartObject.getList(), this)
        adapter.notifyDataSetChanged()

        recyclerViewCart.adapter = adapter
        recyclerViewCart.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    override fun reloadCart() {
        adapter.listProducts = CartObject.getList()
        adapter.notifyDataSetChanged()
    }

    fun goToProductList(view: View){
        var intent = Intent(view.context, ProductsList::class.java)
        startActivity(intent)
    }
    fun goToHome(view: View) {
        var intent = Intent(view.context, MainActivity::class.java)
        startActivity(intent)
    }
}
