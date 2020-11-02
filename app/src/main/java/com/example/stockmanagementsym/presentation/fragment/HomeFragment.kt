package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.stockmanagementsym.R
import kotlinx.android.synthetic.main.fragment_home.view.*


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
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.home)

        view.textViewUserName.text = FragmentData.getUserName()

        view.buttonHomeToShop.setOnClickListener(FragmentData.getController())
        view.buttonHomeToSaleList.setOnClickListener(FragmentData.getController())
        view.buttonHomeToCustomersList.setOnClickListener(FragmentData.getController())
        view.buttonHomeToUserList.setOnClickListener(FragmentData.getController())

        FragmentData.paintPhoto(
            FragmentData.getUserPhotoData(),
            view.imageViewUserHome,
            0
        )
    }
}