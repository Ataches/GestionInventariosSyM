<<<<<<< HEAD:app/src/main/java/com/example/stockmanagementsym/LoginActivity.kt
package com.example.stockmanagementsym
=======
package com.example.stocmanagementsym
>>>>>>> temp:app/src/main/java/com/example/gestioninventariossym/LoginActivity.kt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
<<<<<<< HEAD:app/src/main/java/com/example/stockmanagementsym/LoginActivity.kt
import com.example.stockmanagementsym.logic.User
=======
import com.example.stocmanagementsym.logic.User
>>>>>>> temp:app/src/main/java/com/example/gestioninventariossym/LoginActivity.kt

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)
    }

    fun login(view: View) {
        var editTextUser : EditText = findViewById(R.id.editTextUser)
        Toast.makeText(this, getString(R.string.welcome)+" "+editTextUser.text.toString(), Toast.LENGTH_SHORT).show()
        var intent = Intent(this, MainActivity::class.java)
        User.setUser(editTextUser.text.toString())
        intent.putExtra("userName", editTextUser.text.toString())
        startActivity(intent)
    }

}