package com.rlogixx.realstate

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.rlogixx.realstate.Property.AdapterItem
import com.rlogixx.realstate.Property.FlatDataItem
import com.rlogixx.realstate.Property.PropertyAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Home : AppCompatActivity() {

    var baseUrl = "https://rashitalk.com/api/"
    lateinit var courseRV: RecyclerView
    lateinit var courseRVAdapter: PropertyAdapter
    lateinit var courseList: ArrayList<AdapterItem>
    lateinit var courseRV2: RecyclerView
    lateinit var courseRVAdapter2: PropertyAdapter
    lateinit var courseList2: ArrayList<AdapterItem>
    val imageList = ArrayList<SlideModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


//IMAGE SLIDER
        imageList.add(SlideModel(R.drawable.img))
        imageList.add(SlideModel(R.drawable.img))
        imageList.add(SlideModel(R.drawable.img))

//        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        //imageSlider.setImageList(imageList)

//RECYCLERVIEW2 ADAPTER

        courseRV = findViewById(R.id.recyclerView2)

        getAllData()
//        courseList = ArrayList()
//
//
        val layoutManager = GridLayoutManager(this, 2)

         courseRV.layoutManager = layoutManager
//
//
//        courseRVAdapter = PropertyAdapter(courseList)
//
//
//        courseRV.adapter = courseRVAdapter
//
//
//        courseList.add(AdapterItem(R.drawable.img,"Android Development"))
//        courseList.add(AdapterItem( R.drawable.img,"C++ Development"))
//        courseList.add(AdapterItem( R.drawable.img,"Java Development"))
//        courseList.add(AdapterItem( R.drawable.img,"Python Development"))
//        courseList.add(AdapterItem( R.drawable.img,"JavaScript Development",))
//
//        courseRVAdapter.notifyDataSetChanged()

//RECYCLERVIEW3 ADAPTER

        courseRV2 = findViewById(R.id.recyclerView3)


//        courseList2 = ArrayList()
//
//
//        val layoutManager2 = GridLayoutManager(this, 2)
//
//        courseRV2.layoutManager = layoutManager2
//
//
//        courseRVAdapter2 = PropertyAdapter(courseList2)
//
//
//        courseRV2.adapter = courseRVAdapter2
//
//
//        courseList2.add(AdapterItem(R.drawable.img,"Android Development"))
//        courseList2.add(AdapterItem( R.drawable.img,"C++ Development"))
//        courseList2.add(AdapterItem( R.drawable.img,"Java Development"))
//        courseList2.add(AdapterItem( R.drawable.img,"Python Development"))
//        courseList2.add(AdapterItem( R.drawable.img,"JavaScript Development",))
//
//        courseRVAdapter2.notifyDataSetChanged()








    }

    private fun getAllData(){

        var retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
        var retroData = retrofit.getPropertyData()

//        retroData.enqueue(object :Callback<List<FlatDataItem>>{})

        retroData.enqueue(object: Callback<List<FlatDataItem>>{
            override fun onResponse(
                call: Call<List<FlatDataItem>>,
                response: Response<List<FlatDataItem>>
            ) {
                var data = response.body()


                if (data != null){

                    val bannerItems = data.filter { it.section == "banner" }

                    if (bannerItems.isNotEmpty()) {
                        val imageSlider: ImageSlider = findViewById<ImageSlider>(R.id.image_slider)


                        val slideModels = bannerItems.map { SlideModel(it.files) }

                        imageSlider.setImageList(slideModels,ScaleTypes.FIT)
                    } else {

                        Log.e("Banner", "No items with the 'banner' section found.")
                    }

                }
                Log.d("data",data.toString())
                courseRVAdapter = PropertyAdapter(baseContext,data)
                courseRV.adapter = courseRVAdapter

            }

            override fun onFailure(call: Call<List<FlatDataItem>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}