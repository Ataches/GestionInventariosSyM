package com.example.stockmanagementsym

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.stockmanagementsym.data.UserData
import com.example.stockmanagementsym.presentation.AndroidModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var androidModel:AndroidModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)
        androidModel = ViewModelProvider(this).get(AndroidModel::class.java)
        buttonLogin.setOnClickListener { login() }
    }

    private fun login() {
        val editTextUser : EditText = findViewById(R.id.editTextUser)
        val editTextPass : EditText = findViewById(R.id.editTextPass)

        androidModel.confirmLogin(this,editTextUser.text.toString(), editTextPass.text.toString())

    }

}