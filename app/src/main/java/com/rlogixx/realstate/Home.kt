package com.rlogixx.realstate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.img))
        imageList.add(SlideModel(R.drawable.img))
        imageList.add(SlideModel(R.drawable.img))

        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)








    }
}