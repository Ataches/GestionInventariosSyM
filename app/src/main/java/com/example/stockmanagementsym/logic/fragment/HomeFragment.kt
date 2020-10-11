package com.example.stockmanagementsym.logic.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stockmanagementsym.model.data.User
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.stockmanagementsym.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), View.OnClickListener {

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
        buttonProductList.setOnClickListener(this)
        buttonSalesRegistered.setOnClickListener(this)
        buttonCustomersList.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.buttonProductList -> navController.navigate(R.id.action_home_to_productsList)
            R.id.buttonSalesRegistered -> navController.navigate(R.id.action_home_to_salesRegistered)
            R.id.buttonCustomersList -> navController.navigate(R.id.action_home_to_customerListFragment)
        }
    }
}