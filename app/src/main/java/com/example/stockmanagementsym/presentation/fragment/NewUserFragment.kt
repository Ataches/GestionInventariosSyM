package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.stockmanagementsym.R
import kotlinx.android.synthetic.main.fragment_new_user.view.*

class NewUserFragment: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.newUser)

        view.buttonNewUserToUserList.setOnClickListener(FragmentData.getController())
        view.buttonNewUserToHome.setOnClickListener(FragmentData.getController())
        view.buttonNewUserToGallery.setOnClickListener (FragmentData.getController())
        view.buttonNewUserToCamera.setOnClickListener (FragmentData.getController())
        view.buttonNewUserToNewUser.setOnClickListener(FragmentData.getController())
        view.buttonNewUserCancel.setOnClickListener(FragmentData.getController())
    }

}
