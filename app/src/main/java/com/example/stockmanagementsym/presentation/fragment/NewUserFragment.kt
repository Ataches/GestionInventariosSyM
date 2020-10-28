package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.presentation.AndroidController
import kotlinx.android.synthetic.main.fragment_new_product.view.*
import kotlinx.android.synthetic.main.fragment_new_product.view.buttonNewProductToCamera
import kotlinx.android.synthetic.main.fragment_new_user.view.*

class NewUserFragment: Fragment(){

    private lateinit var viewElement:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewElement = view
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.newUser)

        view.buttonNewUserToUserList.setOnClickListener(AndroidController)
        view.buttonNewUserToHome.setOnClickListener(AndroidController)
        view.buttonNewUserToGallery.setOnClickListener (AndroidController)
        view.buttonNewUserToCamera.setOnClickListener (AndroidController)
        view.buttonNewUserToNewUser.setOnClickListener(AndroidController)
        view.buttonNewUserCancel.setOnClickListener(AndroidController)
    }

}
