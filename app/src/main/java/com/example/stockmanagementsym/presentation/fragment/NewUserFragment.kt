package com.example.stockmanagementsym.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.AndroidActivityResult
import com.example.stockmanagementsym.presentation.fragment.FragmentData
import kotlinx.android.synthetic.main.fragment_new_user.*
import kotlinx.android.synthetic.main.fragment_new_user.view.*

class NewUserFragment: Fragment(){

    private lateinit var androidActivityResult: AndroidActivityResult

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.textViewUserTitle.text = FragmentData.getString(R.string.createUser)

        if(FragmentData.userIsNull()){
            view.buttonNewUserToHome.visibility = View.GONE
            view.buttonNewUserToHome.isEnabled = false
            view.buttonNewUserToHome.isClickable = false

            view.buttonNewUserToUserList.visibility = View.GONE
            view.buttonNewUserToUserList.isEnabled = false
            view.buttonNewUserToUserList.isClickable = false
        }

        view.buttonNewUserToUserList.setOnClickListener(FragmentData.getController())
        view.buttonNewUserToHome.setOnClickListener(FragmentData.getController())
        view.buttonNewUserToGallery.setOnClickListener {
            FragmentData.setNewUserImageView(view.imageViewNewUser)
            FragmentData.setControllerOnClickListener(it)
        }
        view.buttonNewUserToCamera.setOnClickListener {
            FragmentData.setNewUserImageView(view.imageViewNewUser)
            FragmentData.setControllerOnClickListener(it)
        }
        view.buttonNewUserToNewUser.setOnClickListener(FragmentData.getController())
        view.buttonNewUserCancel.setOnClickListener(FragmentData.getController())
        view.buttonRegisterWithGoogle.setOnClickListener {
            FragmentData.getAndroidActivityResult().setRegisterBoolean(true)
            FragmentData.getAndroidActivityResult().startGoogleIntent()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        FragmentData.getAndroidActivityResult().onActivityResult(requestCode, resultCode, data)
    }
}
