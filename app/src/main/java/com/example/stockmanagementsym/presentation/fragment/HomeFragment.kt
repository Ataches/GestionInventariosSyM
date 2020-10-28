package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.view.FragmentData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.layout_navigation_header.view.*


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
        view.buttonHomeToShop.setOnClickListener(AndroidController)
        view.buttonHomeToSaleList.setOnClickListener(AndroidController)
        view.buttonHomeToCustomersList.setOnClickListener(AndroidController)
        view.buttonHomeToUserList.setOnClickListener(AndroidController)

        val userPhotoData = FragmentData.getUserPhotoData()

        if(userPhotoData!=""){
            if(userPhotoData.length<400){
                try {
                    Picasso.get().load(userPhotoData).into(view.imageViewUserHome)
                    view.imageViewUserHome.background = null
                }catch (e:Exception){
                    FragmentData.showToastMessage(view.context, ""+e)
                }
            }else{
                view.imageViewUserHome.setImageBitmap(FragmentData.getBitMapFromString(FragmentData.getUserPhotoData()))
                view.imageViewUserHome.background = null
            }
        }
    }
}