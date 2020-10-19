package com.example.stockmanagementsym

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.stockmanagementsym.presentation.AndroidModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var androidModel:AndroidModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        androidModel = AndroidModel()
        buttonLogin.setOnClickListener  { login() }
    }

    private fun login() {
        val editTextUser : EditText = findViewById(R.id.editTextUser)
        val editTextPass : EditText = findViewById(R.id.editTextPass)

        androidModel.confirmLogin(this,editTextUser.text.toString(), editTextPass.text.toString())

    }

}