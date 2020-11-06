package com.example.stockmanagementsym

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.stockmanagementsym.logic.AndroidActivityResult
import com.example.stockmanagementsym.presentation.AndroidModel
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Juan Sebastian Sanchez Mancilla on 4/11/2020
 * Launcher class to start the application.
 * Allows to user to register and login.
 */
class LoginActivity : AppCompatActivity(), ILauncher {

    private var androidActivityResult: AndroidActivityResult? = null
    private lateinit var androidModel: AndroidModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        androidModel = AndroidModel()
        androidModel.setContext(this) // Needed to show messages to the user
        androidModel.setILauncher(this) // To go back from user registration

        /**
         * Normal login.
         * Obtains user data, name and password, sets the google data in null in model class (isn't used)
         * and ask to model if the user exits
         */
        buttonLogin.setOnClickListener {
            showLoading(true)
            val userName = editTextUser.text.toString()
            val password = editTextPass.text.toString()
            if (userName.isEmpty() || password.isEmpty()) {
                androidModel.showToastMessage(getString(R.string.loginFailure) + ". " + getString(R.string.emptyData))
                showLoading(false)
            } else {
                androidModel.setGoogleAccount(null)
                androidModel.setGoogleSingInClient(null)
                androidModel.confirmLogin(userName, password)
            }
        }

        buttonRegisterWithGoogle.setOnClickListener {
            showLoading(true)
            getAndroidActivityResult().startGoogleIntent()
        }
        buttonRegister.setOnClickListener {
            showLoading(true)
            androidModel.goFromLoginToNewUser(this)
            login_container.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getAndroidActivityResult().onActivityResult(requestCode, resultCode, data)
    }

    private fun showLoading(booleanLoading: Boolean) {
        if (booleanLoading) {
            this.runOnUiThread {
                progressBar.visibility = View.VISIBLE
                buttonLogin.isEnabled = false
                buttonRegisterWithGoogle.isEnabled = false
            }
        } else {
            this.runOnUiThread {
                progressBar.visibility = View.GONE
                buttonLogin.isEnabled = true
                buttonRegisterWithGoogle.isEnabled = true
            }
        }
    }

    override fun goBackFromNewUser() {
        login_container.visibility = View.GONE
        showLoading(false)
    }

    override fun getAndroidModel(): AndroidModel {
        return androidModel
    }

    internal fun getAndroidActivityResult(): AndroidActivityResult {
        if (androidActivityResult == null)
            androidActivityResult = AndroidActivityResult(this,this, this)
        return androidActivityResult!!
    }
}