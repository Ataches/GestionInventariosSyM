package com.example.stockmanagementsym

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.stockmanagementsym.data.User
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)
        buttonLogin.setOnClickListener { login() }
    }

    private fun login() {
        var editTextUser : EditText = findViewById(R.id.editTextUser)
        Toast.makeText(this, getString(R.string.welcome)+" "+editTextUser.text.toString(), Toast.LENGTH_SHORT).show()
        var intent = Intent(this, MainActivity::class.java)
        User.setUser(editTextUser.text.toString())
        intent.putExtra("userName", editTextUser.text.toString())
        startActivity(intent)
    }

}