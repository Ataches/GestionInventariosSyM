package com.example.stockmanagementsym.logic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.stockmanagementsym.R
import kotlinx.android.synthetic.main.fragment_sales_registered.*

class SalesRegistered : Fragment(){

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sales_registered, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = Navigation.findNavController(view)
        buttonBackHome.setOnClickListener {
            goToHome()
        }
    }

    private fun goToHome(){
        navController.navigate(R.id.action_salesRegistered_to_home)
    }
}