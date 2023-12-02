package com.rlogixx.realstate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.rlogixx.realstate.Property.AdapterItem
import com.rlogixx.realstate.Property.PropertyAdapter

class Home : AppCompatActivity() {


    lateinit var courseRV: RecyclerView
    lateinit var courseRVAdapter: PropertyAdapter
    lateinit var courseList: ArrayList<AdapterItem>
    lateinit var courseRV2: RecyclerView
    lateinit var courseRVAdapter2: PropertyAdapter
    lateinit var courseList2: ArrayList<AdapterItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val imageList = ArrayList<SlideModel>()
//IMAGE SLIDER
        imageList.add(SlideModel(R.drawable.img))
        imageList.add(SlideModel(R.drawable.img))
        imageList.add(SlideModel(R.drawable.img))

        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)

//RECYCLERVIEW2 ADAPTER

        courseRV = findViewById(R.id.recyclerView2)


        courseList = ArrayList()


        val layoutManager = GridLayoutManager(this, 2)

         courseRV.layoutManager = layoutManager


        courseRVAdapter = PropertyAdapter(courseList)


        courseRV.adapter = courseRVAdapter


        courseList.add(AdapterItem(R.drawable.img,"Android Development"))
        courseList.add(AdapterItem( R.drawable.img,"C++ Development"))
        courseList.add(AdapterItem( R.drawable.img,"Java Development"))
        courseList.add(AdapterItem( R.drawable.img,"Python Development"))
        courseList.add(AdapterItem( R.drawable.img,"JavaScript Development",))

        courseRVAdapter.notifyDataSetChanged()

//RECYCLERVIEW3 ADAPTER

        courseRV2 = findViewById(R.id.recyclerView3)


        courseList2 = ArrayList()


        val layoutManager2 = GridLayoutManager(this, 2)

        courseRV2.layoutManager = layoutManager2


        courseRVAdapter2 = PropertyAdapter(courseList2)


        courseRV2.adapter = courseRVAdapter2


        courseList2.add(AdapterItem(R.drawable.img,"Android Development"))
        courseList2.add(AdapterItem( R.drawable.img,"C++ Development"))
        courseList2.add(AdapterItem( R.drawable.img,"Java Development"))
        courseList2.add(AdapterItem( R.drawable.img,"Python Development"))
        courseList2.add(AdapterItem( R.drawable.img,"JavaScript Development",))

        courseRVAdapter2.notifyDataSetChanged()








    }
}