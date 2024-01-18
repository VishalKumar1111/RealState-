package com.rlogixx.realstate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            val username = findViewById<EditText>(R.id.edUserName)
            val password = findViewById<EditText>(R.id.edPassWord)
            val login = findViewById<Button>(R.id.button)

        login.setOnClickListener {

            if(android.util.Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches() && password.text.toString().isNotBlank()){


                startActivity(Intent(this,Home::class.java))

            }else{
                Toast.makeText(this,"Enter a valid Email",Toast.LENGTH_SHORT).show()
            }

        }
    }
}