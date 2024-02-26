package com.rlogixx.realstate.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.rlogixx.realstate.API.ApiInterface
import com.rlogixx.realstate.Home
import com.rlogixx.realstate.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val login = findViewById<Button>(R.id.button)

        val username = findViewById<EditText>(R.id.edUserName)
        val userPassword = findViewById<EditText>(R.id.edPassWord)

        login.setOnClickListener {

            if (android.util.Patterns.EMAIL_ADDRESS.matcher(username.text.toString())
                    .matches() && userPassword.text.toString().isNotBlank()
            ) {

                    getData()
//                startActivity(Intent(this, Home::class.java))

            } else {
                //failed
                Toast.makeText(this, "Enter a valid Email", Toast.LENGTH_SHORT).show()
            }

        }





    }

    fun getData(){

        val retrofit = Retrofit.Builder()
            .baseUrl("https://rashitalk.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)


        val username = findViewById<EditText>(R.id.edUserName)
        val userPassword = findViewById<EditText>(R.id.edPassWord)

        val email = username.text.toString().trim()
        val password = userPassword.text.toString().trim()
        Log.e("email","email is $email and $password")

//        val loginRequest = LoginRequest(email, password)

        retrofit.login(LoginRequest(email, password)).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.e("response", response.toString())
                if (response.isSuccessful) {
                    val loginResponse = response.body()


                    // Check if loginResponse is not null
                    if (loginResponse != null) {
                        val userId = loginResponse.id
                        val userEmail = loginResponse.email
                        val accessToken = loginResponse.access_token
                        val status = loginResponse.status
//                        val builder = AlertDialog.Builder(this@MainActivity)
//                        builder.setTitle("Success")
//                        builder.setMessage("Login Successful")
//                        builder.setPositiveButton("OK") { dialog, which ->
//                            // handle OK button click
//                        }
//                        val dialog = builder.create()
//                        dialog.show()


                        val intent:Intent = Intent(this@MainActivity,Home::class.java)
                        startActivity(intent)
//                        startActivity(Intent(this, Home::class.java))
//                        Toast.makeText(this@MainActivity,userId,Toast.LENGTH_LONG).show()

                        // Handle the response data as needed
                    }
                } else {
                    // Handle error (e.g., non-200 status code)
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        Log.e("loginerror",errorBody)
                    }
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setTitle("Error")
                    builder.setMessage("Login Failed. Please check Your credential")
                    builder.setPositiveButton("OK") { dialog, which ->
                        // handle OK button click
                    }
                    val dialog = builder.create()
                    dialog.show()

//                    Toast.makeText(this@MainActivity,errorBody,Toast.LENGTH_LONG).show()
                    // Log or handle the errorBody
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Handle failure (e.g., network error)
            }
        })
    }
}