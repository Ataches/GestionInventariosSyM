package com.example.stockmanagementsym

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.stockmanagementsym.presentation.AndroidModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private var account: GoogleSignInAccount ?= null
    private lateinit var androidModel:AndroidModel
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        androidModel = AndroidModel()
        buttonLogin.setOnClickListener  {
            GlobalScope.launch(Dispatchers.IO){
                androidModel.setGoogleAccount(null)
                androidModel.setGoogleSingInClient(null)
                login(editTextUser.text.toString(),editTextPass.text.toString())
            }
        }
        configureGoogleClient()
        account = GoogleSignIn.getLastSignedInAccount(this)
        buttonLoginGoogle.setOnClickListener {
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, 50)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val context: Context = this
        if(requestCode==50){
            var user:String = ""
            var password:String = ""
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                account = task.result
                androidModel.setGoogleAccount(account)
                androidModel.setGoogleSingInClient(googleSignInClient)
                user = account!!.displayName.toString()
                password = account!!.id.toString()
            }catch (e:Exception){
                androidModel.showToastMessage(context,getString(R.string.loginFailure)+" "+e)
            }

            GlobalScope.launch(Dispatchers.IO){
                login(user,password)
            }
        }
    }

    private suspend fun login(user: String, password: String) {
        androidModel.confirmLogin(this,user, password)
    }

    private fun configureGoogleClient(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestProfile()
            .requestEmail()
            .requestId()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)
    }
}