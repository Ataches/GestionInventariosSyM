package com.example.stockmanagementsym

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stockmanagementsym.logic.User
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class Home : Fragment() {

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = Navigation.findNavController(view)
        textViewWelcome.text = getString(R.string.welcome)+" "+ User.getUser()
        buttonProductList.setOnClickListener{
            goToProductList()
        }
        buttonSalesRegistered.setOnClickListener{
            goToSalesRegistered()
        }
        buttonCustomersList.setOnClickListener{
            goToCustomersList()
        }
    }
    private fun goToProductList(){
        navController.navigate(R.id.action_home_to_productsList)
    }
    private fun goToSalesRegistered() {
        navController.navigate(R.id.action_home_to_salesRegistered)
    }
    private fun goToCustomersList() {
        navController.navigate(R.id.action_home_to_customerListFragment)
    }
}