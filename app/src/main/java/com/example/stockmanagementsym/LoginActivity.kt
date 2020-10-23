package com.example.stockmanagementsym

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.stockmanagementsym.presentation.AndroidModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var androidModel:AndroidModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
        setContentView(R.layout.activity_login)
        androidModel = AndroidModel()
        buttonLogin.setOnClickListener  { GlobalScope.launch(Dispatchers.IO){login()} }
    }

    private suspend fun login() {
        val editTextUser : EditText = findViewById(R.id.editTextUser)
        val editTextPass : EditText = findViewById(R.id.editTextPass)

        androidModel.confirmLogin(this,editTextUser.text.toString(), editTextPass.text.toString())
    }


    private fun checkPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 102)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 102)
            if(!grantResults.all{ it == PackageManager.PERMISSION_GRANTED})
                finish()
    }
}