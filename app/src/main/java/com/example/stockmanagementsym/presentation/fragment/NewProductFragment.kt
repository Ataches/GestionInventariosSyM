package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.presentation.Controller
import com.example.stockmanagementsym.presentation.FragmentData
import kotlinx.android.synthetic.main.fragment_new_product.*

class NewProductFragment: Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Controller.setFragmentTransaction(parentFragmentManager.beginTransaction())
        FragmentData.setIdFragment(this.id)

        buttonNewProductToHome.setOnClickListener (Controller)
        buttonNewProductToProductList.setOnClickListener (Controller)
        buttonNewProductToNewProduct.setOnClickListener (Controller)
    }

    override fun onResume() {
        super.onResume()
        Controller.setFragmentTransaction(parentFragmentManager.beginTransaction())
        FragmentData.setIdFragment(this.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_product, container, false)
    }

}
