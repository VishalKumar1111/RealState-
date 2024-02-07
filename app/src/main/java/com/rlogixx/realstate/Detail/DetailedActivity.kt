package com.rlogixx.realstate.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.rlogixx.realstate.R

class DetailedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)

        val threesixy = findViewById<ImageView>(R.id.img_threesixty)
        threesixy.setOnClickListener {

        }
    }
}