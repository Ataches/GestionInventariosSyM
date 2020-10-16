package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textViewWelcome.text = getString(R.string.welcome)+" "+ FragmentData.getUser()
        buttonHomeToProductList.setOnClickListener(AndroidController)
        buttonHomeToSaleList.setOnClickListener(AndroidController)
        buttonHomeToCustomersList.setOnClickListener(AndroidController)
    }
}